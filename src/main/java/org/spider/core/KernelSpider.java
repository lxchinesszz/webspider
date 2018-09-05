package org.spider.core;


import lombok.extern.slf4j.Slf4j;
import org.spider.config.Banner;
import org.spider.config.ParseTypeEnum;
import org.spider.config.StaticBanner;
import org.spider.core.content.HtmlContent;
import org.spider.core.content.HtmlFileContentResolver;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.core.task.FileClientConsumer;
import org.spider.core.task.SpiderTaskData;
import org.spider.core.task.SpiderLocation;
import org.spider.core.task.WebClientConsumer;
import org.spider.exception.SpiderException;
import org.spider.core.middleware.RequestConfigRegister;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.util.KernelSpiderApplictionTools;
import org.spider.util.SpiderThreadExecutor;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 网页爬取获取的网页将要持久化的实体类
 * Document 具体实现类
 */
@Slf4j
public abstract class KernelSpider implements Spider, RequestConfigRegister, RequestSupport, CrawlSupport {
    /**
     * Synchronization monitor for the "active" flag
     */
    private final Object activeMonitor = new Object();
    /**
     * 当前爬虫的运行状态
     */
    private volatile boolean active;
    /**
     * 默认只处理对请求头user-agent做随机生成
     * 可以扩展为
     * BrowserTypeEnum获取对应的浏览器头
     */
    public List<RequestMiddleware> globalRequestMiddleware;
    /**
     * 域名<=>处理器,没有实现调用,后期可能会添加
     */
    protected Map<String, RequestMiddleware> urlAndRequestMiddlewareMap = new HashMap<>();

    /**
     * 用户的爬虫逻辑
     */
    private SpiderHandler spiderHandler;

    /**
     * 用户定义的下载处理器,对爬虫结果做最后的处理
     */
    private DownloadsPipeline downloadsPipeline;
    /**
     * Synchronization monitor for the "refresh" and "destroy"
     */
    private final Object startupShutdownMonitor = new Object();

    /**
     * Reference to the JVM shutdown hook, if registered
     */
    private Thread shutdownHook;

    /**
     * 解析类型:
     * 推荐使用Jsoup解析
     */
    private ParseTypeEnum parseTypeEnum;

    /**
     * html页面文本爬取
     * 支持动态和非动态网页的爬取
     */
    private HtmlContent htmlContent;
    /**
     * 消费者策略
     * - FileClientConsumer: 从本地获取爬取目标
     * ①对应的Html内容解析器—>HtmlFileContentResolver
     * <p>
     * - WebClientConsumer: 从网络获取爬取目标
     * ①动态网页解析器: WebDynamicHtmlContentResolver
     * ②静态网页解析器: WebStaticHtmlContentResolver
     */
    private Runnable runnableConsumer;

    public BlockingQueue<SpiderLocation> spiderTaskQueue = new LinkedBlockingDeque<>();


    public KernelSpider(SpiderHandler spiderHandler, HtmlContent htmlContent, DownloadsPipeline downloadsPipeline, ParseTypeEnum parseTypeEnum) {
        this.spiderHandler = spiderHandler;
        this.downloadsPipeline = downloadsPipeline;
        this.active = true;
        this.parseTypeEnum = parseTypeEnum;
        this.htmlContent = htmlContent;
        banner();
        registerShutdownHook();
        KernelSpiderApplictionTools.setKernelSpider(this);
    }

    /**
     * 日志打印子类可以覆盖重写
     */
    public void banner() {
        Banner banner = new StaticBanner();
        banner.printBanner();
    }

    @Override
    public void start(List<String> urls) {
        if (!active) {
            throw new SpiderException("当前程序正在运行,不能重复启动。");
        }
        requests(urls);
        crawl();
        while (active) {
            if (SpiderThreadExecutor.isComplete() & !SpiderThreadExecutor.isMainFlag()) {
                synchronized (activeMonitor) {
                    active = false;
                }
            }
        }
    }

    @Override
    public void start(String... urls) {
        start(Arrays.asList(urls));
    }

    @Override
    public void close() {
        SpiderThreadExecutor.close();
    }


    /**
     * 提交爬虫任务
     * 异步请求获取数据
     */
    private void crawl() {
        SpiderTaskData spiderTaskData = SpiderTaskData.builder().htmlContent(htmlContent)
                .parseTypeEnum(parseTypeEnum).downloadsPipeline(downloadsPipeline)
                .queue(spiderTaskQueue)
                .spiderHandler(spiderHandler).globalRequestMiddleware(globalRequestMiddleware).build();
        if (htmlContent instanceof HtmlFileContentResolver) {
            runnableConsumer = new FileClientConsumer(spiderTaskQueue, spiderTaskData);
        } else {
            runnableConsumer = new WebClientConsumer(spiderTaskQueue, spiderTaskData);
        }
        SpiderThreadExecutor.execute(runnableConsumer);
    }

    /**
     * 钩子程序
     */
    private void registerShutdownHook() {
        if (this.shutdownHook == null) {
            // No shutdown hook registered yet.
            this.shutdownHook = new Thread() {
                @Override
                public void run() {
                    synchronized (startupShutdownMonitor) {
                        SpiderThreadExecutor.close();
                    }
                }
            };
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }
    }


    @Override
    public int taskCount() {
        return SpiderThreadExecutor.tackCount();
    }

    /**
     * 实现RequestConfigRegister接口对中间件提供注册支持
     */

    @Override
    public void registerMatchingRequestMiddleware(String url, RequestMiddleware requestMiddleware) {
        urlAndRequestMiddlewareMap.put(url, requestMiddleware);
    }

    @Override
    public boolean registerGlobalRequestMiddleware(List<RequestMiddleware> requestMiddlewares) {
        if (null == this.globalRequestMiddleware) {
            this.globalRequestMiddleware = requestMiddlewares;
            log.info("GlobalRequestMiddleware 添加成功");
            return true;
        }
        log.warn("GlobalRequestMiddleware 已经存在,不能重新覆盖。");
        return false;
    }

    @Override
    public List<RequestMiddleware> getGlobalRequestMiddleware() {
        return this.globalRequestMiddleware;
    }

    @Override
    public boolean removeRequestMiddleware(String url) {
        synchronized (this.activeMonitor) {
            RequestMiddleware requestMiddleware = this.urlAndRequestMiddlewareMap.remove(url);
            return null != requestMiddleware ? true : false;
        }
    }


    /**
     * @param urls
     */
    @Override
    public void innerCrawl(List<String> urls) {
        for (String url : urls) {
            try {
                if (!spiderTaskQueue.offer(new SpiderLocation(UUID.randomUUID().toString(), url), 2, TimeUnit.SECONDS)) {
                    log.error("url:{},加入队列超时:2s", url);
                } else {
                    log.info("url:{},加入队列", url);
                }
            } catch (Exception e) {

            }
        }
    }

    /***********************************************************/

    /**
     * 生成爬取信息
     *
     * @param location 可以是网络路径url也可以是本地文件地址
     *                 注意如果是本地文件,那么html解析器默认为@HtmlFileContent
     */
    @Override
    public abstract void requests(List<String> location);


}
