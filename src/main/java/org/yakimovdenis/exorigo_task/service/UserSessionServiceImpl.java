package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import projectpackage.model.AuthEntities.User;
import projectpackage.model.AuthEntities.UserSession;
import projectpackage.repositories.AuthRepositories.UserSessionRepository;

/**
 * Created by Lenovo on 17.02.2017.
 */
@Service
public class UserSessionServiceImpl implements UserSessionService {

    @Value("${server.defaultLocale}")
    private String locale;

    @Autowired
    UserSessionRepository userSessionRepository;

    @Override
    public UserSession findByUserId(Long userId) {
        return userSessionRepository.findByUserId(userId);
    }

    @Override
    public UserSession createUserSession(User user) {
        UserSession session = new UserSession();
        session.setUserId(user.getId());
        session.setUsername(user.getUsername());
        session.setFullname(user.getFullname());
        session.setLocale(locale);
        session.setFilesQuantity(10);
        session.setFilesSortParameter("uploadDate");
        session.setFilesAscend(true);
        return userSessionRepository.save(session);
    }

    @Override
    public void save(UserSession userSession) {
        userSessionRepository.save(userSession);
    }
}
