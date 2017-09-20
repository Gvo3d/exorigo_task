package org.yakimovdenis.exorigo_task.repositories;

import org.yakimovdenis.exorigo_task.model.UserEntity;

public interface AuthDao {
    UserEntity authorize(String login, String password);
}
