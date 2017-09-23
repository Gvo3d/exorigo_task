package org.yakimovdenis.exorigo_task.service;

import org.yakimovdenis.exorigo_task.model.UserEntity;

public interface UserService extends EntityCRUDService<UserEntity, Integer> {
    void updateUserPass(Integer id, String newPassword);
}
