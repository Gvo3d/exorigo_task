package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import org.yakimovdenis.exorigo_task.components.CustomEntityLinkColumn;
import org.yakimovdenis.exorigo_task.model.EntityType;
import org.yakimovdenis.exorigo_task.providers.UserProvider;
import org.yakimovdenis.exorigo_task.service.UserService;

import java.util.ArrayList;
import java.util.List;

@MountPath("user")
public class UserPage extends BasePage {

    @SpringBean
    private UserService userService;

    private UserProvider dataProvider;

    public UserPage(PageParameters parameters) {
        super(parameters);
        this.dataProvider = new UserProvider(userService);

        Form form = new Form("create") {
            @Override
            protected void onSubmit() {
                setResponsePage(CreateUser.class);
            }
        };
        queue(form);

        List<IColumn> columnsList = new ArrayList<>();
        columnsList.add(new PropertyColumn(new StringResourceModel("fin", this, null), "name", "name"));
        columnsList.add(new PropertyColumn(new StringResourceModel("lan", this, null), "surname", "surname"));
        columnsList.add(new PropertyColumn(new StringResourceModel("log", this, null), "login", "login"));
        columnsList.add(new PropertyColumn(new StringResourceModel("pas", this, null), "password", "password"));
        columnsList.add(new PropertyColumn(new StringResourceModel("rol", this, null), "role.roleName", "role.roleName"));
        columnsList.add(new PropertyColumn(new StringResourceModel("enb", this, null), "enabled", "enabled"));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Edit"), CreateUser.class,null));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Phones"), TelephonePage.class,null));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Delete"), DeleteEntity.class,EntityType.USER.name()));

        DefaultDataTable table = new DefaultDataTable("datatable", columnsList, dataProvider, 3);

        add(table);
    }
}

