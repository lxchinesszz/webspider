package org.spider.boot.ip;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liuxin
 * @version Id: IpEntities.java, v 0.1 2018/7/31 下午4:07
 */
@Data
@AllArgsConstructor
public class IpEntities {
    private String ip;
    private String port;
    private String address;
    private String type;
}
