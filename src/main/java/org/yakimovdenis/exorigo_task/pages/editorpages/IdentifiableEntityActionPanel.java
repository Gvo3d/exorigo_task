package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.yakimovdenis.exorigo_task.model.IdentifiableEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.editorpages.CreateUser;

public class IdentifiableEntityActionPanel extends Panel {
    private static final long serialVersionUID = 1L;

    public IdentifiableEntityActionPanel(String id, IModel<IdentifiableEntity> model, Class<? extends BasePage> redirect, String optional) {
        super(id, model);
        add(new Link<IdentifiableEntity>(id,model) {
            @Override
            public void onClick() {
                PageParameters pageParameters = new PageParameters();
                pageParameters.add("targetId",model.getObject().getId());
                if (null!=optional){
                    pageParameters.add("etype", optional);
                }
                setResponsePage(redirect,pageParameters);
            }
        });
    }
}
