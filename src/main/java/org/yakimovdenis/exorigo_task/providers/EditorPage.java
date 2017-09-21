package org.yakimovdenis.exorigo_task.providers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class EditorPage extends WebPage {

    UserProvider dataProvider = new UserProvider();

    public EditorPage() {
        List<IColumn> columnsList = new ArrayList<>();
        IColumn column1 = new PropertyColumn(new StringResourceModel("firstNameTableHeaderLabel", this, null), "name.first", "name.first");
        IColumn column2 = new PropertyColumn(new Model("Last Name"), "name.last", "name.last");
        columnsList.add(column1);
        columnsList.add(column2);

        DefaultDataTable table = new DefaultDataTable("datatable", columnsList, dataProvider, 10);

        add(table);
    }

}