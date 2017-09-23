package org.yakimovdenis.exorigo_task.pages.editorpages;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.yakimovdenis.exorigo_task.model.EntityType;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.service.RoleService;
import org.yakimovdenis.exorigo_task.service.TelephoneService;
import org.yakimovdenis.exorigo_task.service.UserService;

public class DeleteEntity extends BasePage {
    private final static String TEXT_MESSAGE = "Do you want to delete ${type} with id ${id}?";

    @SpringBean
    private UserService userService;

    @SpringBean
    private RoleService roleService;

    @SpringBean
    private TelephoneService telephoneService;

    public DeleteEntity(PageParameters parameters) {
        super(parameters);
        EntityType deletableType = EntityType.valueOf(parameters.get("etype").toString());
        Integer id = parameters.get("targetId").toInteger();
        add(newNavbar("cuenavbar"));

        final Label question = new Label("deleteQuestion", Model.of(TEXT_MESSAGE.replace("${type}", deletableType.toString()).replace("${id}",String.valueOf(id))));

        Button escapeButton = new Button("goBack") {
            public void onSubmit() {
                setResponsePage(getReturnPage(deletableType));
            }
            @Override
            public void onError() {
                super.onError();
                setResponsePage(getReturnPage(deletableType));
            }
        };

        Form<?> form = new Form<Void>("deleteForm") {
            @Override
            protected void onSubmit() {
                if (null!=id) {
                    switch (deletableType) {
                        case ROLE: {
                            roleService.delete(id);
                        }
                        break;
                        case USER: {
                            userService.delete(id);
                        }
                        break;
                        case PHONE: {
                            telephoneService.delete(id);
                        }
                    }
                }
                parameters.remove("targetId");
                parameters.remove("etype");
                setResponsePage(getReturnPage(deletableType));
            }
        };

        form.add(question);
        form.add(escapeButton);
        add(form);
    }

    private Class<? extends BasePage> getReturnPage(EntityType deletableType){
        switch (deletableType) {
            case ROLE: {
                return RolePage.class;
            }
            case USER: {
                return UserPage.class;
            }
        }
        return TelephonePage.class;
    }

    public Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(Model.of("EXORIGO"));
        return navbar;
    }
}
