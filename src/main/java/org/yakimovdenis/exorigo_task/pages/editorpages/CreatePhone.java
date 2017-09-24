package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.components.ExactErrorLevelFilter;
import org.yakimovdenis.exorigo_task.components.StringRegexValidator;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.NavbarBasePage;
import org.yakimovdenis.exorigo_task.service.TelephoneService;
import org.yakimovdenis.exorigo_task.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class CreatePhone extends NavbarBasePage {
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
        Set<UserEntity> usersFromDb = new TreeSet<UserEntity>(new BackwardUserComparator());
        usersFromDb.addAll(userService.getAll(null, null, null, false));
        usersList = usersFromDb.stream().collect(Collectors.
                toMap(UserEntity::getId, x -> x.getSurname() + " " + x.getName()));
        usersList.put(null, "NONE");
        List<String> userNamesList = new ArrayList<>();
        userNamesList.addAll(usersList.values());

        Integer searcheablePhone;
        try {
            searcheablePhone = parameters.get("targetId").toInteger();
        } catch (Exception e) {
            searcheablePhone = null;
        }
        Integer holderId;
        try {
            holderId = parameters.get("holderId").toInteger();
            parameters.remove("holderId");
        } catch (Exception e) {
            holderId = null;
        }

        String phoneNumText = "";
        String submitString = "Create new phone";

        if (null == searcheablePhone) {
            selected = usersList.get(null);
        } else {
            selectedPhone = phoneService.getOne(searcheablePhone);
            phoneNumText = selectedPhone.getPhoneNumber();
            try {
                selected = usersList.get(phoneService.getUserForPhone(searcheablePhone));
            } catch (Exception e){
                selected = usersList.get(null);
            }
            submitString = "Update existing phone";
        }

        if (null!=holderId){
            selected = usersList.get(holderId);
        }

        add(new FeedbackPanel("feedback"));

        final TextField<String> phoneNum = new TextField<String>("phoneNum", Model.of(phoneNumText));
        final DropDownChoice<String> usersForPhones = new DropDownChoice<String>(
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
                    phoneService.create(newPhone);
                    if (!selected.equals("NONE")) {
                        newPhone.setId(phoneService.getPhoneIdWirhPhonenum(newPhone.getPhoneNumber()));
                        savePhoneForUser(newPhone);
                    }
                } else {
                    selectedPhone.setPhoneNumber(phoneNum.getModelObject());
                    phoneService.update(selectedPhone);
                    if (!selected.equals("NONE")) {
                        savePhoneForUser(selectedPhone);
                    }
                }
                setResponsePage(TelephonePage.class);
            }
        };

        Button button1 = new Button("goBack") {
            public void onSubmit() {
                setResponsePage(TelephonePage.class);
            }
            @Override
            public void onError() {
                super.onError();
                setResponsePage(TelephonePage.class);
            }
        };
        form.add(button1);

        add(new FeedbackPanel("feedbackMessage", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));
        add(form);
        form.add(phoneNum);
        form.add(usersForPhones);
        form.add(submitLabel);
    }

    private void savePhoneForUser(TelephoneEntity newPhone) {
        Integer key = null;
        for (Map.Entry<Integer, String> entry : usersList.entrySet()) {
            if (entry.getValue().equals(selected)) {
                key = entry.getKey();
            }
        }
        phoneService.setSavePhoneForUser(key, newPhone.getId());
    }


    private class BackwardUserComparator implements Comparator<UserEntity> {
        @Override
        public int compare(UserEntity o1, UserEntity o2) {
            return -1 * o1.getId().compareTo(o2.getId());
        }
    }

}
