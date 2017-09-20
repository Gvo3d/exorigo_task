package org.yakimovdenis.exorigo_task.repositories;

import org.yakimovdenis.exorigo_task.model.UserEntity;

public interface UserDao extends Dao<UserEntity, Integer> {
    void updateUserPass(Integer id, String newPassword);
}
