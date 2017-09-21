package org.yakimovdenis.exorigo_task;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import com.mkyong.user.SimplePage;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.controllers.EditorPage;
import org.yakimovdenis.exorigo_task.controllers.LoginPage;

@Component
public class WicketApplication extends WebApplication {

	public Class<EditorPage> getEditorPage() {
		return EditorPage.class;
	}

	@Override
	public Class<LoginPage> getHomePage() {
		return LoginPage.class;
	}



	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));
		 
	}

}
