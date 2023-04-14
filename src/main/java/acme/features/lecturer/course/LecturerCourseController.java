
package acme.features.lecturer.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseController extends AbstractController<Lecturer, Course> {

	@Autowired
	protected LecturerCourseListMineService	listMineService;

	@Autowired
	protected LecturerCourseShowService		showService;

	@Autowired
	protected LecturerCourseCreateService	createService;


	@PostConstruct
	public void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);

		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}

}
