
package acme.features.company.practicumSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.PracticumSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticumSessionController extends AbstractController<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionShowService		showService;

	@Autowired
	protected CompanyPracticumSessionListService		listService;

	@Autowired
	protected CompanyPracticumSessionCreateService		createService;

	@Autowired
	protected CompanyPracticumSessionAddendumService	addendumService;

	@Autowired
	protected CompanyPracticumSessionDeleteService		deleteService;

	@Autowired
	protected CompanyPracticumSessionUpdateService		updateService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("addendum", "create", this.addendumService);
	}
}
