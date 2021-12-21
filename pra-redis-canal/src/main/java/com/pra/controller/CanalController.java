package com.pra.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pra.mysql.dao.CanalDao;
import com.pra.mysql.entity.CanalEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/canal")
public class CanalController {

    @Resource
    private CanalDao canalDao;

    @GetMapping("insert")
    public Object insert_() {
        CanalEntity entity = new CanalEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setAge(19);
        entity.setDescription("帅哥");
        entity.setName("CALLAN");
        entity.setCreateDatetime(new Date());
        canalDao.insert(entity);
        return "ok";
    }

    @DeleteMapping("delete")
    public Object delete_() {
        List<CanalEntity> canalEntities = canalDao.selectList(new QueryWrapper<CanalEntity>().lambda().eq(CanalEntity::getAge, 19));
        CanalEntity canalEntity = canalEntities.get(0);
        canalDao.deleteById(canalEntity.getId());
        return "ok";
    }

    @PutMapping("update")
    public Object update_() {
        List<CanalEntity> canalEntities = canalDao.selectList(new QueryWrapper<CanalEntity>().lambda().eq(CanalEntity::getAge, 19));
        CanalEntity canalEntity = canalEntities.get(0);
        canalDao.updateById(canalEntity);
        return "ok";
    }
}
