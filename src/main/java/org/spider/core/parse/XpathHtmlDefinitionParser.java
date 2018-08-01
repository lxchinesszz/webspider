package org.spider.core.parse;

import org.seimicrawler.xpath.JXDocument;
import org.spider.core.HtmlDefinition;
import org.spider.core.task.SpiderLocation;


/**
 * @author liuxin
 * @version Id: XpathHtmlDefinitionParser.java, v 0.1 2018/7/27 下午4:32
 */
public class XpathHtmlDefinitionParser implements HtmlDefinitionParser {
    @Override
    public HtmlDefinition parse(SpiderLocation spiderLocation, String htmlText) {
        JXDocument jxDocument = JXDocument.create(htmlText);
        Object title = jxDocument.selOne("/html/head/title");
        return new HtmlDefinition(String.valueOf(title), spiderLocation.getLocation(), jxDocument, htmlText);
    }
}
