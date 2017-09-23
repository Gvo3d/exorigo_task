package org.yakimovdenis.exorigo_task.service;

import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

public interface TelephoneService extends EntityCRUDService<TelephoneEntity, Integer>{
    public Integer getUserForPhone(Integer phoneId);
    public void setSavePhoneForUser(Integer userId, Integer phoneId);
}
