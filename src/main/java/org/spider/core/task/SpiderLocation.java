package org.spider.core.task;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liuxin
 * @version Id: SpiderUrl.java, v 0.1 2018/7/28 下午8:49
 */
@Data
@AllArgsConstructor
public class SpiderLocation {
    /**
     * 唯一标识
     */
    private String id;
    /**
     * 目标地址:
     * 可以是网络资源路径
     * 也可以是本地资源路径
     */
    private String location;
    /**
     * 是否爬取过
     * true : 已经爬取过
     * false: 未爬取过
     */
    private volatile boolean is;

    public SpiderLocation(String id, String location) {
        this.id = id;
        this.location = location;
    }
}
