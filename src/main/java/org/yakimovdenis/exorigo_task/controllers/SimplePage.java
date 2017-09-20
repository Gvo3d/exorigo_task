package org.yakimovdenis.exorigo_task.controllers;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.service.UserService;

public class SimplePage extends WebPage {

    @SpringBean
    private UserService userService;

    public SimplePage(final PageParameters parameters) {
        add(new Label("msg", userService.getAll(null,null,null,false).toString()));
    }

}