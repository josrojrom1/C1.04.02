
package acme.features.administrator.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.controllers.AbstractController;

@Controller
public class AdministratorConfigurationController extends AbstractController<Administrator, Configuration> {

	@Autowired
	protected AdministratorConfigurationShowService		showService;

	@Autowired
	protected AdministratorConfigurationListService		listService;

	@Autowired
	protected AdministratorConfigurationUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
	}
}
