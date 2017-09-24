package org.yakimovdenis.exorigo_task.pages;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.yakimovdenis.exorigo_task.components.StringRegexValidator;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.pages.editorpages.UserPage;
import org.yakimovdenis.exorigo_task.service.SecurityServiceImpl;

@WicketHomePage
public class HomePage extends NavbarBasePage {

	@SpringBean
	private SecurityServiceImpl securityService;

	public HomePage(final PageParameters pageParameters) {
		super(pageParameters);
		add(newNavbar("cuenavbar"));

		final TextField<String> login = new TextField<String>("login", Model.of(""));
		final TextField<String> password = new TextField<String>("password", Model.of(""));

		login.setRequired(true);
		login.add(new StringRegexValidator(UserEntity.NAME_REGEX));
		password.setRequired(true);
		password.add(new StringRegexValidator(UserEntity.NAME_REGEX));

		Form form = new Form("loginForm"){
			@Override
			protected void onSubmit() {
				if (securityService.autologin(login.getModelObject(), password.getModelObject())){
					setResponsePage(UserPage.class);
				} else {
					setResponsePage(ErrorPage.class);
				}
			}

			@Override
			protected void onError() {
				super.onError();
				setResponsePage(ErrorPage.class);
			}
		};
		form.add(login);
		form.add(password);
		add(form);
	}
}
