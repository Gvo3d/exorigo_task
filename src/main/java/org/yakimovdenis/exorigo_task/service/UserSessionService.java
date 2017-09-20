package org.yakimovdenis.exorigo_task.service;

import projectpackage.model.AuthEntities.User;
import projectpackage.model.AuthEntities.UserSession;

/**
 * Created by Lenovo on 17.02.2017.
 */
public interface UserSessionService {
    UserSession findByUserId(Long userId);
    UserSession createUserSession(User user);
    void save(UserSession userSession);
}
