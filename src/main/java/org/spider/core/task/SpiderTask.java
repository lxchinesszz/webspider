package org.spider.core.task;

import lombok.Data;
import lombok.Getter;
import org.spider.core.SpiderHandler;
import org.spider.config.ParseTypeEnum;
import org.spider.core.WillRequests;
import org.spider.core.content.HtmlContent;
import org.spider.core.HtmlDefinition;
import org.spider.core.WebClientHolder;
import org.spider.core.parse.HtmlDefinitionParser;
import org.spider.core.parse.HtmlDefinitionParserFactory;
import org.spider.util.KernelSpiderApplictionTools;

import java.util.concurrent.Callable;


/**
 * @author liuxin
 * @version Id: SpiderTask.java, v 0.1 2018/7/27 下午2:10
 */

@Data
public class SpiderTask implements Callable<Object> {

    private SpiderLocation spiderLocation;


    private SpiderTaskData spiderTaskData;

    public SpiderTask(SpiderLocation spiderLocation, SpiderTaskData spiderTaskData) {
        this.spiderLocation = spiderLocation;
        this.spiderTaskData = spiderTaskData;
    }

    @Override
    public Object call() throws Exception {
        String htmlText = spiderTaskData.getHtmlContent().getHtmlText(spiderLocation, KernelSpiderApplictionTools.getKernelSpider());
        HtmlDefinitionParser htmlDefinitionParser = HtmlDefinitionParserFactory.getParse(spiderTaskData.getParseTypeEnum());
        HtmlDefinition htmlDefinition = htmlDefinitionParser.parse(spiderLocation, htmlText);
        return spiderTaskData.getSpiderHandler().doCrawl(htmlDefinition,new WillRequests(spiderTaskData.getQueue()));
    }
}
