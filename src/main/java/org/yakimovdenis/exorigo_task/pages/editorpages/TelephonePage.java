package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import org.yakimovdenis.exorigo_task.components.CustomEntityLinkColumn;
import org.yakimovdenis.exorigo_task.model.EntityType;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.providers.TelephoneProvider;
import org.yakimovdenis.exorigo_task.service.TelephoneService;
import org.yakimovdenis.exorigo_task.service.UserService;

import java.util.ArrayList;
import java.util.List;

@MountPath("telephone")
public class TelephonePage extends BasePage {
    private final String USERLIST = "List of ${username} phones.";
    private Integer searcheableUser;

    @SpringBean
    private TelephoneService telephoneService;

    @SpringBean
    private UserService userService;

    private TelephoneProvider dataProvider;

    public TelephonePage(PageParameters parameters) {
        super(parameters);
        this.dataProvider = new TelephoneProvider(telephoneService);
        UserEntity user=null;

        try {
            searcheableUser = parameters.get("targetId").toInteger();
        } catch (Exception e) {
            searcheableUser = null;
        }
        List<TelephoneEntity> phones;
        if (null==searcheableUser){
            phones = telephoneService.getAll(null,null,null,false);
        } else {
            phones = new ArrayList<>();
            phones.addAll(telephoneService.getPhonesForUser(searcheableUser));
            user = userService.getOne(searcheableUser);
        }
        dataProvider.populateTelephoneList(phones);

        Form form = new Form("createP") {
            @Override
            protected void onSubmit() {
                PageParameters parameters = new PageParameters();
                if (null!=searcheableUser) {
                    parameters.add("holderId", searcheableUser);
                }
                setResponsePage(CreatePhone.class,parameters);
            }
        };
        queue(form);

        String userLabelText;
        if (user!=null){
            userLabelText = USERLIST.replace("${username}",user.getSurname()+" "+user.getName());
        } else {
            userLabelText="";
        }

        Label userLabel = new Label("userLabel", Model.of(userLabelText));
        if (user==null){
            userLabel.setVisible(false);
        }

        List<IColumn> columnsList = new ArrayList<>();
        columnsList.add(new PropertyColumn(new StringResourceModel("pnm", this, null), "phoneNumber", "phoneNumber"));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Edit"), CreatePhone.class,null));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Delete"), DeleteEntity.class, EntityType.PHONE.name()));
        DefaultDataTable table = new DefaultDataTable("datatableP", columnsList, dataProvider, 3);
        add(table);
        add(userLabel);
    }
}
