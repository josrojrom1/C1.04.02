
package acme.features.lecturer.courseOfLecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.CourseOfLecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class CourseOfLectureController extends AbstractController<Lecturer, CourseOfLecture> {

	@Autowired
	protected LecturerCourseOfLectureCreateService	createService;

	@Autowired
	protected LecturerCourseOfLectureListService	listService;

	@Autowired
	protected LecturerCourseOfLectureShowService	showService;

	@Autowired
	protected LecturerCourseOfLectureDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);

	}
}
