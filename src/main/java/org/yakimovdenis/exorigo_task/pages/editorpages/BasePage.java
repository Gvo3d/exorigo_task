package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;

public class BasePage extends GenericWebPage<Void> {

    public BasePage(final PageParameters parameters) {
        super(parameters);
        add(newNavbar("navbar"));
    }

    protected Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(Model.of("EXORIGO"));
        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT,
                new NavbarButton<Void>(UserPage.class, Model.of("Users"))
                        .setIconType(GlyphIconType.user),
                new NavbarButton<Void>(RolePage.class, Model.of("Roles"))
                        .setIconType(GlyphIconType.tower),
                new NavbarButton<Void>(TelephonePage.class, Model.of("Telephones"))
                        .setIconType(GlyphIconType.phone)));
        return navbar;
    }

}
