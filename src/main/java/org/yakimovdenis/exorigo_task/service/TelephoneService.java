package org.yakimovdenis.exorigo_task.service;

import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

import java.util.Set;

public interface TelephoneService extends EntityCRUDService<TelephoneEntity, Integer>{
    Integer getUserForPhone(Integer phoneId);
    void setSavePhoneForUser(Integer userId, Integer phoneId);
    Set<TelephoneEntity> getPhonesForUser(Integer userId);
    Integer getPhoneIdWirhPhonenum(String phoneNum);
}
