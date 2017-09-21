package org.yakimovdenis.exorigo_task.pages.annotationscan;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("user")
public class UserPage extends BasePage {
    public UserPage(PageParameters parameters) {
        super(parameters);
    }
}
