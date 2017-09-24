package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.yakimovdenis.exorigo_task.repositories.AuthDao;


@Service
public class AuthServiceImpl implements UserDetailsService {

    @Autowired
    private AuthDao authDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return authDao.loadUserByUsername(s);
    }
}
