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
import org.yakimovdenis.exorigo_task.providers.RoleProvider;
import org.yakimovdenis.exorigo_task.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@MountPath("role")
public class RolePage extends BasePage {

    @SpringBean
    private RoleService roleService;

    RoleProvider dataProvider;

    public RolePage(PageParameters parameters) {
        super(parameters);
        this.dataProvider = new RoleProvider(roleService);

        Form form = new Form("createR") {
            @Override
            protected void onSubmit() {
                setResponsePage(CreateRole.class);
            }
        };
        queue(form);

        List<IColumn> columnsList = new ArrayList<>();
        columnsList.add(new PropertyColumn(new StringResourceModel("rln", this, null), "roleName", "roleName"));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Edit"), CreateRole.class,null));
        columnsList.add(new CustomEntityLinkColumn(new Model<String>("Delete"), DeleteEntity.class, EntityType.ROLE.name()));

        DefaultDataTable table = new DefaultDataTable("datatable", columnsList, dataProvider, 3);

        add(table);
    }
}