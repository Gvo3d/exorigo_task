package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.repositories.UserDaoImpl;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Override
    public UserEntity getOne(Integer integer) {
        return userDaoImpl.getEntity(integer);
    }

    @Override
    public List<UserEntity> getAll(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return userDaoImpl.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend);
    }

    @Override
    public boolean exists(Integer integer) {
        return userDaoImpl.exists(integer);
    }

    @Override
    public void delete(Integer integer) {
        userDaoImpl.delete(integer);
    }

    @Override
    public boolean update(UserEntity object) {
        return userDaoImpl.update(object);
    }

    @Override
    public void create(UserEntity object) {
        userDaoImpl.create(object);
    }

    @Override
    public void updateUserPass(Integer id, String newPassword) {
        userDaoImpl.updateUserPass(id, newPassword);
    }
}
