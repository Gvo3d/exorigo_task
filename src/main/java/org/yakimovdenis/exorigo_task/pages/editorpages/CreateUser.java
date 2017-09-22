package org.yakimovdenis.exorigo_task.pages.editorpages;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.ErrorPage;
import org.yakimovdenis.exorigo_task.service.RoleService;
import org.yakimovdenis.exorigo_task.service.UserService;
import org.yakimovdenis.exorigo_task.support.StringRegexValidator;
import org.yakimovdenis.exorigo_task.support.StringVariableLengthValidator;

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
        List<String> roleList=null;
        StringValue sv = parameters.get("userId");
        Integer seracheable = sv.toInteger();

        String sname="";
        String ssurname="";
        String slogin="";
        String spassword="";

        if (null==seracheable){
        add(newNavbar("cuenavbar"));
        roleList = roleService.getAll(null,null,null,false).stream().map(role -> role.getRoleName()).collect(Collectors.toList());
        selected =  roleList.get(0);
        } else {
            selectedUser = userService.getOne(seracheable);
            sname=selectedUser.getName();
            ssurname=selectedUser.getSurname();
            slogin=selectedUser.getLogin();
            spassword=selectedUser.getPassword();
            selected=selectedUser.getRole().getRoleName();
        }

        add(new FeedbackPanel("feedback"));

        final TextField<String> name = new TextField<String>("name", Model.of(sname));
        final TextField<String> surname = new TextField<String>("surname", Model.of(ssurname));
        final TextField<String> login = new TextField<String>("login", Model.of(slogin));
        final TextField<String> password = new TextField<String>("password", Model.of(spassword));
        final DropDownChoice<String> listSites = new DropDownChoice<String>(
                "roles", new PropertyModel<String>(this, "selected"), roleList);


        name.setRequired(true);
        surname.setRequired(true);
        login.setRequired(true);
        password.setRequired(true);

        name.add(new StringRegexValidator(UserEntity.NAME_REGEX));
        surname.add(new StringRegexValidator(UserEntity.SURNAME_REGEX));
        login.add(new StringRegexValidator(UserEntity.LOGIN_REGEX));
        password.add(new StringRegexValidator(UserEntity.PASSWORD_REGEX));

        Form<?> form = new Form<Void>("userForm") {
            @Override
            protected void onSubmit() {
                if (null==selectedUser) {
                    final String nameValue = name.getModelObject();
                    final String surnameValue = surname.getModelObject();
                    final String loginValue = login.getModelObject();
                    final String passwordValue = password.getModelObject();

                    UserEntity newUser = new UserEntity();
                    newUser.setName(name.getModelObject());
                    newUser.setSurname(surname.getModelObject());
                    newUser.setLogin(login.getModelObject());
                    newUser.setPassword(password.getModelObject());
                    newUser.setEnabled(true);
                    newUser.setRole(roleService.getEntityByRolename(selected));
                    userService.create(newUser);
                    setResponsePage(UserPage.class);
                } else {
                    selectedUser.setName(name.getModelObject());
                    selectedUser.setSurname(surname.getModelObject());
                    selectedUser.setLogin(login.getModelObject());
                    selectedUser.setPassword(password.getModelObject());
                    userService.update(selectedUser);
                }
            }
        };


        Button button1 = new Button("goBack") {
            public void onSubmit() {
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
        form.add(listSites);
    }

    protected Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(Model.of("EXORIGO"));
        return navbar;
    }

    class ExactErrorLevelFilter implements IFeedbackMessageFilter {
        private int errorLevel;

        public ExactErrorLevelFilter(int errorLevel){
            this.errorLevel = errorLevel;
        }

        public boolean accept(FeedbackMessage message) {
            return message.getLevel() == errorLevel;
        }
    }
}

