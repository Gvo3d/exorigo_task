package org.yakimovdenis.exorigo_task.service;

import org.springframework.stereotype.Service;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.repositories.RoleDao;
import org.yakimovdenis.exorigo_task.repositories.RoleDaoImpl;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDaoImpl roleDaoImpl;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDaoImpl = (RoleDaoImpl) roleDao;
    }

    @Override
    public RoleEntity getOne(Integer integer) {
        return roleDaoImpl.getEntity(integer);
    }

    @Override
    public List<RoleEntity> getAll(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return roleDaoImpl.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend);
    }

    @Override
    public boolean exists(Integer integer) {
        return roleDaoImpl.exists(integer);
    }

    @Override
    public void delete(Integer integer) {
        roleDaoImpl.delete(integer);
    }

    @Override
    public boolean update(RoleEntity object) {
        return roleDaoImpl.update(object);
    }

    @Override
    public void create(RoleEntity object) {
        roleDaoImpl.create(object);
    }
}