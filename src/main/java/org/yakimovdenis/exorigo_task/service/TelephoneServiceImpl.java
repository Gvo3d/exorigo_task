package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.repositories.TelephoneDaoImpl;

import java.util.List;

@Service
public class TelephoneServiceImpl implements TelephoneService {

    @Autowired
    private TelephoneDaoImpl telephoneDaoImpl;

    @Override
    public TelephoneEntity getOne(Integer integer) {
        return telephoneDaoImpl.getEntity(integer);
    }

    @Override
    public List<TelephoneEntity> getAll(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend) {
        return telephoneDaoImpl.getAllEntities(searcheableParameter, searcheableValue, orderingParameter, isAscend);
    }

    @Override
    public boolean exists(Integer integer) {
        return telephoneDaoImpl.exists(integer);
    }

    @Override
    public void delete(Integer integer) {
        telephoneDaoImpl.delete(integer);
    }

    @Override
    public boolean update(TelephoneEntity object) {
        return telephoneDaoImpl.update(object);
    }

    @Override
    public void create(TelephoneEntity object) {
        telephoneDaoImpl.create(object);
    }
}
