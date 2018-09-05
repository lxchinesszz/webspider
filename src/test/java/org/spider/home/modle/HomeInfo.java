package org.spider.home.modle;

import lombok.Builder;
import lombok.Data;

/**
 * @author liuxin
 * @version Id: HomeInfo.java, v 0.1 2018/9/4 下午6:50
 */
@Builder
@Data
public class HomeInfo {
    private String address;
    private String roomType;
    private String buildDate;
    private String unitPrice;
    private String totalPrice;
    private String href;
    private String area;
}
