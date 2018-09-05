package org.spider.home.modle;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author liuxin
 * @version Id: HomeInfoDO.java, v 0.1 2018/9/5 上午9:09
 */
@Data
@Builder
public class HomeInfoDO {
    /**
     * 地址
     */
    private String address;
    /**
     * 房型
     */
    private String roomType;
    /**
     * 建造时间
     */
    private Date buildDate;
    /**
     * 单价
     */
    private long unitPrice;
    /**
     * 总价
     */
    private long totalPrice;
    /**
     * 链接
     */
    private String href;
    /**
     * 面积
     */
    private Integer area;
    /**
     * 小区
     */
    private String community;
}
