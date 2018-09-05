package org.spider.home;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.spider.core.pipeline.DownloadsPipeline;
import org.spider.home.modle.HomeInfo;
import org.spider.home.modle.HomeInfoDO;
import org.spider.util.Console;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuxin
 * @version Id: HomeDownloadsPipeline.java, v 0.1 2018/9/4 下午8:38
 */
@Slf4j
public class HomeDownloadsPipeline implements DownloadsPipeline<List<HomeInfo>> {

    static String HOME = "ZZ_HOME";
    static Jedis jedis;
    static String tableName;

    static MysqlService mysqlService;


    public HomeDownloadsPipeline(boolean isOld) {
        jedis = new Jedis("127.0.0.1", 6379);
        if (isOld){
            tableName = "zhengzhou_home";
        }else {
            tableName="zhengzhou_home_new";
        }
        mysqlService = new MysqlService(tableName);
    }

    @Override
    public void persistence(List<HomeInfo> homeInfos) {
        for (int i = 0; i < homeInfos.size(); i++) {
            HomeInfo homeInfo = homeInfos.get(i);
            String href = homeInfo.getHref();
            if (isExist(href)) {
                log.debug("警告:{},已存在,不要重新插入",href);
                Console.customerunNormal("警告:已存在,不要重新插入",href);
                homeInfos.remove(homeInfo);
            }
            jedis.set(href, "-");
        }
        mysqlService.insert(converter(homeInfos));
    }

    @Override
    public boolean checkPersistence(List<HomeInfo> homeInfos) {
        return true;
    }

    private boolean isExist(String url) {
        if (StringUtils.isBlank(url) || StringUtils.isEmpty(url)) {
            return false;
        }
        return StringUtils.isNotEmpty(jedis.get(url));
    }

    private List<HomeInfoDO> converter(List<HomeInfo> homeInfos) {
        List<HomeInfoDO> homeInfoDOS = new ArrayList<>();
        for (HomeInfo homeInfo : homeInfos) {
        homeInfoDOS.add(converter(homeInfo));
        }
        return homeInfoDOS;
    }

    private HomeInfoDO converter(HomeInfo homeInfo) {
        String address = homeInfo.getAddress();
        String[] split = address.split(" ");
        String area = homeInfo.getArea();
        String buildDate = homeInfo.getBuildDate();
        String href = homeInfo.getHref();
        String roomType = homeInfo.getRoomType();
        Integer areaInt = Integer.parseInt(area.replace("m²", ""));
        String community = split[0].trim();
        address = split[1];
        Date bdate = converter(buildDate);
        BigDecimal bigDecimal = new BigDecimal(homeInfo.getTotalPrice().replace("万", "")).multiply(new BigDecimal(1000));
        Long totalPrice =  bigDecimal.longValue();
        long unitPrice = Long.parseLong(homeInfo.getUnitPrice().replace("元/m²", ""));
        return HomeInfoDO.builder().address(address).community(community).buildDate(bdate).totalPrice(totalPrice)
                .unitPrice(unitPrice).roomType(roomType).area(areaInt).href(href).build();
    }


    public Date converter(String dateStr) {
        Date parse = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年建造");
        try {
            parse = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
        }
        return parse;
    }


}
