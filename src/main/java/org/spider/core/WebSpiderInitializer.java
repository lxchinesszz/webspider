package org.spider.core;

import lombok.extern.slf4j.Slf4j;
import org.spider.config.*;
import org.spider.core.content.HtmlContent;
import org.spider.core.content.WebStaticHtmlContentResolver;
import org.spider.core.middleware.DefaultWeightRequestMiddlewareRegisterImpl;
import org.spider.core.middleware.RandomAgentRequestMiddleware;
import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.middleware.WeightRequestConfigRegister;
import org.spider.core.pipeline.PrintDownloadsPipeline;
import org.spider.exception.SpiderException;

import java.util.Arrays;
import java.util.List;

/**
 * @author liuxin
 * @version Id: WebSpiderInitializer.java, v 0.1 2018/7/27 下午10:16
 */
@Slf4j
public class WebSpiderInitializer implements SpiderInitializer {

    private KernelSpider kernelWebSpider;

    /**
     * 默认根据权重来配置
     *
     * @param requestMiddlewares
     */
    private void registerGlobalRequestMiddleware(List<RequestMiddleware> requestMiddlewares) {
        WeightRequestConfigRegister weightRequestConfigRegister = new DefaultWeightRequestMiddlewareRegisterImpl(kernelWebSpider);
        weightRequestConfigRegister.registerGlobalRequestMiddleware(requestMiddlewares);
    }


    @Override
    public Spider initialize(WebSpiderConfig webSpiderConfig, SpiderHandler spiderHandler, HtmlContent htmlContent) {
        prepareCheckConfig(webSpiderConfig, spiderHandler);
        this.kernelWebSpider = new DefaultWebKernelSpider(spiderHandler, initDefaultHtmlContent(htmlContent),
                webSpiderConfig.getDownloadsPipeline(),
                webSpiderConfig.getParseTypeEnum());
        registerGlobalRequestMiddleware(webSpiderConfig.getRequestMiddlewares());
        return this.kernelWebSpider;
    }

    private void prepareCheckConfig(WebSpiderConfig webSpiderConfig, SpiderHandler spiderHandler) {
        checkSpiderHandler(spiderHandler);
        checkWebSpiderConfig(webSpiderConfig);
    }

    private void checkSpiderHandler(SpiderHandler spiderHandler) {
        if (null == spiderHandler) {
            throw new SpiderException("SpiderHandler must not null");
        }
    }

    private void checkWebSpiderConfig(WebSpiderConfig webSpiderConfig) {
        if (null == webSpiderConfig) {
            webSpiderConfig = WebSpiderConfig.builder().parseTypeEnum(ParseTypeEnum.JSOUP)
                    .browserTypeEnum(BrowserVersion.CHROME)
                    .downloadsPipeline(new PrintDownloadsPipeline())
                    .requestMiddlewares(Arrays.asList(new RandomAgentRequestMiddleware()))
                    .build();
            log.warn("WebSpiderConfig 未配置:{}", webSpiderConfig);
            return;
        }
        if (webSpiderConfig.isRandomAgent()) {
            RequestMiddleware randomAgentRequestMiddleware = new RandomAgentRequestMiddleware();
            webSpiderConfig.getRequestMiddlewares().add(randomAgentRequestMiddleware);
        }
        log.info("webSpiderConfig配置成功:{}", webSpiderConfig);
    }


    private HtmlContent initDefaultHtmlContent(HtmlContent htmlContent){
        if (null == htmlContent) {
            htmlContent = new WebStaticHtmlContentResolver();
            log.info("HtmlContent 默认为 "+htmlContent.getClass().getName());
        }
        return htmlContent;
    }

}
