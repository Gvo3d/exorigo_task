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
import org.yakimovdenis.exorigo_task.components.UserEntityLinkColumn;
import org.yakimovdenis.exorigo_task.providers.TelephoneProvider;
import org.yakimovdenis.exorigo_task.service.TelephoneService;

import java.util.ArrayList;
import java.util.List;

@MountPath("telephone")
public class TelephonePage extends BasePage {

    @SpringBean
    private TelephoneService telephoneService;

    TelephoneProvider dataProvider;

    public TelephonePage(PageParameters parameters) {
        super(parameters);
        this.dataProvider = new TelephoneProvider(telephoneService);

        Form form = new Form("createP") {
            @Override
            protected void onSubmit() {
                setResponsePage(CreatePhone.class);
            }
        };
        queue(form);

        List<IColumn> columnsList = new ArrayList<>();
        columnsList.add(new PropertyColumn(new StringResourceModel("pnm", this, null), "phoneNumber", "phoneNumber"));
        columnsList.add(new UserEntityLinkColumn(new Model<String>("Edit"), CreatePhone.class));
        DefaultDataTable table = new DefaultDataTable("datatableP", columnsList, dataProvider, 3);
        add(table);
    }
}
