
package acme.features.assistant.tutorialSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.TutorialSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialSessionController extends AbstractController<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionShowService		showService;

	@Autowired
	protected AssistantTutorialSessionListService		listService;

	@Autowired
	protected AssistantTutorialSessionListMineService	listMineService;

	@Autowired
	protected AssistantTutorialSessionCreateService		createService;

	@Autowired
	protected AssistantTutorialSessionDeleteService		deleteService;

	@Autowired
	protected AssistantTutorialSessionUpdateService		updateService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
	}
}
