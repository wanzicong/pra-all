package com.pra.canal;

import com.pra.mysql.entity.CanalEntity;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * 监听mysql中binlog 的日志变化
 */
@Component
@CanalTable(value = "canal_entity")
public class CanalMonitor implements EntryHandler<CanalEntity> {
    @Override
    public void insert(CanalEntity canalEntity) {
        //  监听添加的数据
        System.out.println("================添加的数据======================");
        System.out.println(canalEntity);
    }

    /**
     * 监听
     * @param before 更新前的数据
     * @param after 更新成功后的数据
     */
    @Override
    public void update(CanalEntity before, CanalEntity after) {
        // 监听更新的数据
        System.out.println("================更新的数据======================");
        System.out.println(before);
        System.out.println(after);
    }

    @Override
    public void delete(CanalEntity canalEntity) {
        System.out.println("================删除的数据======================");
        System.out.println(canalEntity);
        // 监听删除的数据
    }
}
