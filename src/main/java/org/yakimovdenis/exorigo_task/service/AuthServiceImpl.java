package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yakimovdenis.exorigo_task.model.AuthCredentials;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.repositories.AuthDao;

import java.util.Collection;

@Service
public class AuthServiceImpl implements UserDetailsService {

    @Autowired
    private AuthDao authDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return authDao.loadUserByUsername(s);
    }
}
