package org.yakimovdenis.exorigo_task.pages.editorpages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.service.TelephoneService;

public class UserToPhonesList extends BasePage {

    @SpringBean
    private TelephoneService telephoneService;

    public UserToPhonesList(PageParameters parameters) {
        super(parameters);

        Integer searcheable;
        try{
            searcheable = parameters.get("userId").toInteger();
        } catch (Exception e){
            searcheable  =null;
        }

    }
}
