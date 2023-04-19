
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
	protected LecturerCourseOfLectureCreateService createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
	}
}
