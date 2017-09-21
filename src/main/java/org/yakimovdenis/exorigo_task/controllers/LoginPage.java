package org.yakimovdenis.exorigo_task.controllers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.yakimovdenis.exorigo_task.service.AuthService;

public class LoginPage extends WebPage{

    @Autowired
    private AuthService authService;

    public LoginPage(PageParameters parameters) {
        super(parameters);
        add(new Label("msg", "Hi there mfucker"));
    }
}
