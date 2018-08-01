package org.spider.core.content;

import org.spider.core.middleware.RequestMiddleware;
import org.spider.core.task.SpiderLocation;
import org.spider.util.FileTools;

import java.util.List;

/**
 * 从本地文件中获取Html文本
 * @author liuxin
 * @version Id: HtmlFileContent.java, v 0.1 2018/7/30 下午2:35
 */
public class HtmlFileContentResolver extends AbstractHtmlContentResolver {
    @Override
    public String doHtmlFromLocation(SpiderLocation spiderLocation, List<RequestMiddleware> globalRequestMiddleware) {
        String location = spiderLocation.getLocation();
        return FileTools.fileContent(location);
    }
}
