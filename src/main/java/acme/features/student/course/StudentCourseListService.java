
package acme.features.student.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseListService extends AbstractService<Student, Course> {

	@Autowired
	protected StudentCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Course> courses;
		courses = this.repository.findAllCoursesPublished();

		super.getBuffer().setData(courses);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title");

		super.getResponse().setData(tuple);
	}

}
