package org.yakimovdenis.exorigo_task.pages.editorpages;

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.components.StringRegexValidator;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.service.TelephoneService;
import org.yakimovdenis.exorigo_task.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class CreatePhone extends BasePage {
    private String selected;
    private TelephoneEntity selectedPhone;
    private Map<Integer, String> usersList;

    @SpringBean
    private TelephoneService phoneService;

    @SpringBean
    private UserService userService;

    public CreatePhone(final PageParameters parameters) {
        super(parameters);
        add(newNavbar("cuenavbar"));
        Set<UserEntity> usersFromDb = new TreeSet<UserEntity>(new UserComparator());
        usersFromDb.addAll(userService.getAll(null, null, null, false));
        usersList = usersFromDb.stream().collect(Collectors.toMap(UserEntity::getId, x-> x.getSurname()+" "+x.getName()));
        usersList.put(null, "NONE");
        List<String> userNamesList = new ArrayList<>();
        userNamesList.addAll(usersList.values());

        Integer searcheablePhone;
        try{
            searcheablePhone = parameters.get("targetId").toInteger();
        } catch (Exception e){
            searcheablePhone  =null;
        }
        String sphoneNum = "";
        String submitString="Create new phone";

        if (null == searcheablePhone) {
            selected = usersList.get(null);
        } else {
            selectedPhone = phoneService.getOne(searcheablePhone);
            sphoneNum = selectedPhone.getPhoneNumber();
            selected = usersList.get(phoneService.getUserForPhone(searcheablePhone));
            submitString = "Update existing phone";
        }

        add(new FeedbackPanel("feedback"));

        final TextField<String> phoneNum = new TextField<String>("name", Model.of(sphoneNum));
        final DropDownChoice<String> listSites = new DropDownChoice<String>(
                "users", new PropertyModel<String>(this, "selected"), userNamesList);
        final Button submitLabel = new Button("submitb", Model.of(submitString));


        phoneNum.setRequired(true);
        phoneNum.add(new StringRegexValidator(TelephoneEntity.PHONENUM_REGEX));

        Form<?> form = new Form<Void>("phoneForm") {
            @Override
            protected void onSubmit() {
                if (null == selectedPhone) {
                    TelephoneEntity newPhone = new TelephoneEntity();
                    newPhone.setPhoneNumber(phoneNum.getModelObject());
                    if (!selected.equals("NONE")){
                        Integer key = null;
                        for (Map.Entry<Integer, String> entry: usersList.entrySet()){
                            if (entry.getValue().equals(selected)){
                                key = entry.getKey();
                            }
                        }
                        phoneService.setSavePhoneForUser(key, );
                    }
                    phoneService.create(newUser);
                    setResponsePage(UserPage.class);
                } else {
                    selectedUser.setName(phoneNum.getModelObject());
                    selectedUser.setSurname(surname.getModelObject());
                    selectedUser.setLogin(login.getModelObject());
                    selectedUser.setEnabled(enabled.getModelObject());
                    if (!selectedUser.getRole().getRoleName().equals(selected)) {
                        selectedUser.setRole(roleService.getEntityByRolename(selected));
                    }
                    phoneService.update(selectedUser);
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
        form.add(phoneNum);
        form.add(surname);
        form.add(login);
        form.add(password);
        form.add(enabled);
        form.add(passLabel);
        form.add(listSites);
        form.add(submitLabel);
    }

    protected Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(Model.of("EXORIGO"));
        return navbar;
    }

    private class UserComparator implements Comparator<UserEntity> {
        @Override
        public int compare(UserEntity o1, UserEntity o2) {
            return -1*o1.getId().compareTo(o2.getId());
        }
    }

    class ExactErrorLevelFilter implements IFeedbackMessageFilter {
        private int errorLevel;

        public ExactErrorLevelFilter(int errorLevel) {
            this.errorLevel = errorLevel;
        }

        public boolean accept(FeedbackMessage message) {
            return message.getLevel() == errorLevel;
        }
    }
}
