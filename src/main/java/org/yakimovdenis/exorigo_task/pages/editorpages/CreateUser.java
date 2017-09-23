package org.yakimovdenis.exorigo_task.pages.editorpages;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.yakimovdenis.exorigo_task.components.ExactErrorLevelFilter;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.service.RoleService;
import org.yakimovdenis.exorigo_task.service.UserService;
import org.yakimovdenis.exorigo_task.components.StringRegexValidator;

import java.util.List;
import java.util.stream.Collectors;

public class CreateUser extends BasePage {
    private String selected;
    private UserEntity selectedUser;

    @SpringBean
    private UserService userService;

    @SpringBean
    private RoleService roleService;

    public CreateUser(final PageParameters parameters) {
        super(parameters);
        add(newNavbar("cuenavbar"));
        List<String> roleList = roleService.getAll(null, null, null, false).stream().map(role -> role.getRoleName()).collect(Collectors.toList());

        Integer searcheable;
        try{
            searcheable = parameters.get("targetId").toInteger();
        } catch (Exception e){
            searcheable  =null;
        }
        String sname = "";
        String ssurname = "";
        String slogin = "";
        String spassword = "";
        String submitString="Create new user";
        Boolean benabled=Boolean.FALSE;


        if (null == searcheable) {
            selected = roleList.get(0);
        } else {
            selectedUser = userService.getOne(searcheable);
            sname = selectedUser.getName();
            ssurname = selectedUser.getSurname();
            slogin = selectedUser.getLogin();
            spassword = selectedUser.getPassword();
            selected = selectedUser.getRole().getRoleName();
            benabled = selectedUser.isEnabled();
            submitString = "Update existing user";
        }

        add(new FeedbackPanel("feedback"));

        final TextField<String> name = new TextField<String>("name", Model.of(sname));
        final TextField<String> surname = new TextField<String>("surname", Model.of(ssurname));
        final TextField<String> login = new TextField<String>("login", Model.of(slogin));
        final TextField<String> password = new TextField<String>("password", Model.of(spassword));
        final Label passLabel = new Label("passLabel", Model.of("Password"));
        final CheckBox enabled = new CheckBox("enabled", Model.of(benabled));
        final Button submitLabel = new Button("submitb", Model.of(submitString));
        final DropDownChoice<String> listSites = new DropDownChoice<String>(
                "roles", new PropertyModel<String>(this, "selected"), roleList);


        name.setRequired(true);
        name.add(new StringRegexValidator(UserEntity.NAME_REGEX));

        surname.setRequired(true);
        surname.add(new StringRegexValidator(UserEntity.SURNAME_REGEX));

        login.setRequired(true);
        login.add(new StringRegexValidator(UserEntity.LOGIN_REGEX));

        if (null!=searcheable){
            password.setVisible(false);
            passLabel.setVisible(false);
        } else {
            password.setRequired(true);
            password.add(new StringRegexValidator(UserEntity.PASSWORD_REGEX));
        }

        Form<?> form = new Form<Void>("userForm") {
            @Override
            protected void onSubmit() {
                if (null == selectedUser) {
                    UserEntity newUser = new UserEntity();
                    newUser.setName(name.getModelObject());
                    newUser.setSurname(surname.getModelObject());
                    newUser.setLogin(login.getModelObject());
                    newUser.setPassword(password.getModelObject());
                    newUser.setEnabled(enabled.getModelObject());
                    newUser.setRole(roleService.getEntityByRolename(selected));
                    userService.create(newUser);
                    setResponsePage(UserPage.class);
                } else {
                    selectedUser.setName(name.getModelObject());
                    selectedUser.setSurname(surname.getModelObject());
                    selectedUser.setLogin(login.getModelObject());
                    selectedUser.setEnabled(enabled.getModelObject());
                    if (!selectedUser.getRole().getRoleName().equals(selected)) {
                        selectedUser.setRole(roleService.getEntityByRolename(selected));
                    }
                    userService.update(selectedUser);
                }
                setResponsePage(UserPage.class);
            }
        };

        Button button1 = new Button("goBack") {
            public void onSubmit() {
                setResponsePage(UserPage.class);
            }

            @Override
            public void onError() {
                super.onError();
                setResponsePage(UserPage.class);
            }
        };
        form.add(button1);

        add(new FeedbackPanel("feedbackMessage", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));
        add(form);
        form.add(name);
        form.add(surname);
        form.add(login);
        form.add(password);
        form.add(enabled);
        form.add(passLabel);
        form.add(listSites);
        form.add(submitLabel);
    }

    public Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(Model.of("EXORIGO"));
        return navbar;
    }
}

