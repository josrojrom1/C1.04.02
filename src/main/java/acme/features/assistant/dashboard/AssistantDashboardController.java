
package acme.features.assistant.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.AssistantDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantDashboardController extends AbstractController<Assistant, AssistantDashboard> {

	@Autowired
	protected AssistantDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
