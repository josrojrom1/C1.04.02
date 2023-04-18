
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
	protected LecturerLectureListMineService	listMineService;

	@Autowired
	protected LecturerLectureShowService		showService;

	@Autowired
	protected LecturerLectureCreateService		createService;


	@PostConstruct
	protected void initialise() {

		//BASIC COMMANDS
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);

		//CUSTOM COMMANDS
		super.addCustomCommand("list-mine", "list", this.listMineService);
	}

}
