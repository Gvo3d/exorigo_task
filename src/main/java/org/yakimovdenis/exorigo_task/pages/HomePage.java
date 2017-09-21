package org.yakimovdenis.exorigo_task.pages;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.yakimovdenis.exorigo_task.pages.editorpages.UserPage;

@WicketHomePage
public class HomePage extends WebPage {
	
	public HomePage() {
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
		queue(form);
	}
}
