package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;
import org.yakimovdenis.exorigo_task.providers.UserProvider;

import java.util.ArrayList;
import java.util.List;

@MountPath("user")
public class UserPage extends BasePage {

    UserProvider dataProvider = new UserProvider();

    public UserPage(PageParameters parameters) {
        super(parameters);

        List<IColumn> columnsList = new ArrayList<>();
        IColumn column1 = new PropertyColumn(new StringResourceModel("label", this, null), "name", "name");
        IColumn column2 = new PropertyColumn(new Model("Last Name"), "surname", "surname");
        columnsList.add(column1);
        columnsList.add(column2);

        DefaultDataTable table = new DefaultDataTable("datatable", columnsList, dataProvider, 10);

        add(table);
    }
}
