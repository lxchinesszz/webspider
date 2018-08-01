package org.spider.core.parse;

import org.spider.config.ParseTypeEnum;

/**
 * @author liuxin
 * @version Id: HtmlDefinitionParserStrategy.java, v 0.1 2018/7/27 下午4:59
 */
public class HtmlDefinitionParserFactory {

    public static HtmlDefinitionParser getParse(ParseTypeEnum parseTypeEnum) {
        if (ParseTypeEnum.JSOUP.equals(parseTypeEnum)) {
            return new JsoupHtmlDefinitionParser();
        } else if (ParseTypeEnum.XPATH.equals(parseTypeEnum)) {
            return new XpathHtmlDefinitionParser();
        }
        return null;
    }
}
