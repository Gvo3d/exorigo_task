package org.yakimovdenis.exorigo_task.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ErrorPage extends NavbarBasePage {

    public ErrorPage(final PageParameters pageParameters) {
        super(pageParameters);
        add(newNavbar("cuenavbar"));
    }

}

