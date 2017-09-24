package org.yakimovdenis.exorigo_task.pages;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.yakimovdenis.exorigo_task.pages.editorpages.BasePage;

public abstract class NavbarBasePage extends BasePage {
    public NavbarBasePage(PageParameters parameters) {
        super(parameters);
    }

    protected Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(Model.of("EXORIGO"));
        return navbar;
    }
}
