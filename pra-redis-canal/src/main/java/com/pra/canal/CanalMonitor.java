package com.pra.canal;

import com.pra.mysql.entity.CanalEntity;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * 监听mysql中binlog 的日志变化
 *
 * question: 使用rabbitmq 异步监听消息 如何保证消息消费的顺序性
 * answer: (同一个消费队列的时间)消费消息的时间关闭手动确认机制 手动ack
 *         只有当一个消息被确认消费的时间才会消费下一个消息对象
 */
@Component
@CanalTable(value = "canal_entity")
// 监听数据库的名字在canal的 的example文件中的配置文件中指定的默认地址
public class CanalMonitor implements EntryHandler<CanalEntity> {
    @Override
    public void insert(CanalEntity canalEntity) {
        //  监听添加的数据
        System.out.println("================添加的数据======================");
        System.out.println(canalEntity);

        // 将信息发送给消息中间件 异步更新缓存redis 中的数据
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

        // 将信息发送给消息中间件 异步更新缓存redis 中的数据
    }

    @Override
    public void delete(CanalEntity canalEntity) {
        System.out.println("================删除的数据======================");
        System.out.println(canalEntity);
        // 监听删除的数据

        // 将信息发送给消息中间件 异步更新缓存redis 中的数据
    }
}
