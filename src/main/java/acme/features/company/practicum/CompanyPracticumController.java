
package acme.features.company.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Practicum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticumController extends AbstractController<Company, Practicum> {

	@Autowired
	protected CompanyPracticumListMineService	listService;

	@Autowired
	protected CompanyPracticumShowService		showService;

	@Autowired
	protected CompanyPracticumUpdateService		updateService;

	@Autowired
	protected CompanyPracticumDeleteService		deleteService;

	@Autowired
	protected CompanyPracticumCreateService		createService;

	@Autowired
	protected CompanyPracticumPublishService	publishService;

	@Autowired
	protected CompanyPracticumListMineService	listMineService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
