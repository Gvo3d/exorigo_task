package org.yakimovdenis.exorigo_task.pages;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.model.AuthCredentials;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.editorpages.UserPage;

@WicketHomePage
public class HomePage extends WebPage {

	public HomePage() {
		final TextField<String> login = new TextField<String>("login", Model.of(""));
		final TextField<String> password = new TextField<String>("password", Model.of(""));

		Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				setResponsePage(UserPage.class);
			}

			@Override
			protected void onError() {
				super.onError();
				setResponsePage(ErrorPage.class);
			}
		};
		form.add(login);
		form.add(password);
		queue(form);
	}
}
