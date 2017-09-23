package org.yakimovdenis.exorigo_task.components;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.yakimovdenis.exorigo_task.model.IdentifiableEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.editorpages.BasePage;
import org.yakimovdenis.exorigo_task.pages.editorpages.IdentifiableEntityActionPanel;

public class CustomEntityLinkColumn extends AbstractColumn<IdentifiableEntity, Integer> {
    private static final long serialVersionUID = 1L;
    private Class<? extends BasePage> targetPage;
    private String optional;

    public CustomEntityLinkColumn(IModel<String> displayModel, Class<? extends BasePage> targetPage, String optionalParameter) {
        super(displayModel);
        this.targetPage = targetPage;
        this.optional = optionalParameter;
    }

    @Override
    public void populateItem(Item<ICellPopulator<IdentifiableEntity>> cellItem, String componentId, IModel<IdentifiableEntity> rowModel) {
        cellItem.add(new IdentifiableEntityActionPanel(componentId, rowModel, targetPage, optional));
    }
}