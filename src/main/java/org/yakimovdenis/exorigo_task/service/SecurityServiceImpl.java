package org.yakimovdenis.exorigo_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yakimovdenis.exorigo_task.repositories.AuthDao;

import java.util.Collection;

@Service
public class SecurityServiceImpl {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthDao authDao;

    public Boolean autologin(String username, String password) {
        UserDetails userDetails = authService.loadUserByUsername(username);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Collection<? extends GrantedAuthority> authorities;
        if (userDetails != null && null != userDetails.getAuthorities()) {
            authorities = userDetails.getAuthorities();
        } else authorities = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (userDetails, password, authorities);
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return false;
        }
        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
