package org.yakimovdenis.exorigo_task.pages.editorpages;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.components.ExactErrorLevelFilter;
import org.yakimovdenis.exorigo_task.components.StringRegexValidator;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.pages.NavbarBasePage;
import org.yakimovdenis.exorigo_task.service.RoleService;

public class CreateRole extends NavbarBasePage {
    private RoleEntity selectedRole;

    @SpringBean
    private RoleService roleService;

    public CreateRole(final PageParameters parameters) {
        super(parameters);
        add(newNavbar("cuenavbar"));

        Integer searcheable;
        try{
            searcheable = parameters.get("targetId").toInteger();
        } catch (Exception e){
            searcheable  =null;
        }
        String srolename = "";
        String submitString="Create new role";

        if (null == searcheable) {
            selectedRole = null;
        } else {
            selectedRole = roleService.getOne(searcheable);
            srolename = selectedRole.getRoleName();
            submitString = "Update existing role";
        }

        add(new FeedbackPanel("feedback"));

        final TextField<String> rolename = new TextField<String>("rolename", Model.of(srolename));
        final Button submitLabel = new Button("submitb", Model.of(submitString));

        rolename.setRequired(true);
        rolename.add(new StringRegexValidator(RoleEntity.ROLE_REGEX));

        Form<?> form = new Form<Void>("roleForm") {
            @Override
            protected void onSubmit() {
                if (null == selectedRole) {
                    RoleEntity newRole = new RoleEntity();
                    newRole.setRoleName(rolename.getModelObject());
                    roleService.create(newRole);
                } else {
                    selectedRole.setRoleName(rolename.getModelObject());
                    roleService.update(selectedRole);
                }
                setResponsePage(RolePage.class);
            }
        };

        Button button1 = new Button("goBack") {
            public void onSubmit() {
                setResponsePage(RolePage.class);
            }

            @Override
            public void onError() {
                super.onError();
                setResponsePage(RolePage.class);
            }
        };
        form.add(button1);

        add(new FeedbackPanel("feedbackMessage", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));
        add(form);
        form.add(rolename);
        form.add(submitLabel);
    }
}

