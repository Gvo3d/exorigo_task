package org.yakimovdenis.exorigo_task.repositories;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthDao {
    UserDetails loadUserByUsername(String s);
}
