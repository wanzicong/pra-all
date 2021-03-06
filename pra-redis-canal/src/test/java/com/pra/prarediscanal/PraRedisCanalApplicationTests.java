package com.pra.prarediscanal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pra.mysql.dao.CanalDao;
import com.pra.mysql.entity.CanalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class PraRedisCanalApplicationTests {
    @Resource
    private CanalDao canalDao;

    @Test
    void contextLoads() {
    }

    @Test
    void insert_() {
        CanalEntity entity = new CanalEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setAge(19);
        entity.setDescription("帅哥");
        entity.setName("CALLAN");
        entity.setCreateDatetime(new Date());
        canalDao.insert(entity);
    }

    @Test
    void delete_() {
        List<CanalEntity> canalEntities = canalDao.selectList(new QueryWrapper<CanalEntity>().lambda().eq(CanalEntity::getAge, 19));
        CanalEntity canalEntity = canalEntities.get(0);
        canalDao.deleteById(canalEntity.getId());
    }

    @Test
    void update_() {
        List<CanalEntity> canalEntities = canalDao.selectList(new QueryWrapper<CanalEntity>().lambda().eq(CanalEntity::getAge, 19));
        CanalEntity canalEntity = canalEntities.get(0);
        canalEntity.setCreateDatetime(new Date());
        canalDao.updateById(canalEntity);
    }

}
