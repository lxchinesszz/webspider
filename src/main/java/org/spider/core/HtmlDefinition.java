package org.spider.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.nodes.Document;

/**
 * @author liuxin
 * @version Id: HtmlDefinition.java, v 0.1 2018/7/26 下午10:19
 */
@Data
@AllArgsConstructor
public class HtmlDefinition<D> {
    /**
     * 网络标题
     */
    String title;
    /**
     * 请求的url
     */
    String url;

    /**
     * 生成的document文本
     */
    D document;

    /**
     * 原始页面文本数据
     */
    String htmlText;

}
