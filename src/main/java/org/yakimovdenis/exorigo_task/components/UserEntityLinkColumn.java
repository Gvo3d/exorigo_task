package org.yakimovdenis.exorigo_task.components;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.editorpages.BasePage;
import org.yakimovdenis.exorigo_task.pages.editorpages.UserActionPanel;

public class UserEntityLinkColumn extends AbstractColumn<UserEntity, Integer> {
    private static final long serialVersionUID = 1L;
    private Class<? extends BasePage> targetPage;

    public UserEntityLinkColumn(IModel<String> displayModel, Class<? extends BasePage> targetPage) {
        super(displayModel);
        this.targetPage = targetPage;
    }

    @Override
    public void populateItem(Item<ICellPopulator<UserEntity>> cellItem, String componentId, IModel<UserEntity> rowModel) {
        cellItem.add(new UserActionPanel(componentId, rowModel, targetPage));
    }
}