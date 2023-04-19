
package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureListService	listService;

	@Autowired
	protected LecturerLectureShowService	showService;

	@Autowired
	protected LecturerLectureCreateService	createService;

	@Autowired
	protected LecturerLectureDeleteService	deleteService;

	@Autowired
	protected LecturerLecturePublishService	publishService;

	@Autowired
	protected LecturerLectureUpdateService	updateService;


	@PostConstruct
	protected void initialise() {

		//BASIC COMMANDS
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("update", this.updateService);

		//CUSTOM COMMANDS
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
