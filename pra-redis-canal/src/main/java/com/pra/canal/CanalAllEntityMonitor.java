package com.pra.canal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.context.CanalContext;
import top.javatool.canal.client.handler.EntryHandler;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * 监听数据库中全部的表信息
 *
 * ！！！ 监听全部的表信息 就要关闭 某个表的单独监听的配置
 */
@Component
@CanalTable(value = "all")
public class CanalAllEntityMonitor implements EntryHandler<Map<String, String>> {
    @Override
    public void insert(/*添加到数据库的对象*/Map<String, String> params) {
        // 获取到表的名字
        String table = CanalContext.getModel().getTable();
        System.out.println("===============监听全部的表信息===========");
        System.out.println("监听全部的表信息" + table);
        System.out.println(params);
    }

    @Override
    public void update(Map<String, String> before, Map<String, String> after) {
        // 获取到表的名字
        String table = CanalContext.getModel().getTable();

    }

    @Override
    public void delete(Map<String, String> stringStringMap) {
        // 获取到表的名字
        String table = CanalContext.getModel().getTable();
    }
}
