package org.yakimovdenis.exorigo_task.repositories;

import org.yakimovdenis.exorigo_task.model.TelephoneEntity;

import java.util.Set;

public interface TelephoneDao extends Dao<TelephoneEntity, Integer> {
    Set<TelephoneEntity> getPhonesForUser(Integer userId);
}
