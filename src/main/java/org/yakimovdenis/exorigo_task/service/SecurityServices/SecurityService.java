package org.yakimovdenis.exorigo_task.service.SecurityServices;

/**
 * Created by Gvozd on 07.01.2017.
 */
public interface SecurityService {
    String findLoggedInUsername();
    Boolean autologin(String username, String password);
}
