package org.spider.home;

import cn.hutool.db.Entity;
import cn.hutool.db.SqlRunner;
import cn.hutool.db.ds.DSFactory;
import lombok.extern.slf4j.Slf4j;
import org.spider.home.modle.HomeInfo;
import org.spider.home.modle.HomeInfoDO;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxin
 * @version Id: MysqlService.java, v 0.1 2018/9/4 下午9:28
 */
@Slf4j
public class MysqlService {

    SqlRunner runner;
    String tableName;

    public MysqlService(String tableName) {
        DataSource ds = DSFactory.get();
        runner = SqlRunner.create(ds);
        this.tableName = tableName;
    }

    public void insert(List<HomeInfoDO> homeInfoDOS) {
        List<Entity> entities = new ArrayList<>();
        for (HomeInfoDO homeInfoDO : homeInfoDOS) {
            entities.add(converter(homeInfoDO));
        }
        try {
            runner.insert(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Entity converter(HomeInfoDO homeInfoDO) {
        return Entity.create(tableName).set("address", homeInfoDO.getAddress())
                .set("area", homeInfoDO.getArea()).set("buildDate", homeInfoDO.getBuildDate())
                .set("href", homeInfoDO.getHref()).set("roomType", homeInfoDO.getRoomType())
                .set("unitPrice", homeInfoDO.getUnitPrice())
                .set("totalPrice", homeInfoDO.getTotalPrice()).set("community", homeInfoDO.getCommunity());
    }

}
