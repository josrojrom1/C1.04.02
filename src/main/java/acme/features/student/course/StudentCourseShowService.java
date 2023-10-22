
package acme.features.student.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.features.lecturer.course.LecturerCoursePublishService;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	@Autowired
	protected StudentCourseRepository		repository;

	@Autowired
	protected LecturerCoursePublishService	servicecourse;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findCourseById(courseId);
		status = course != null && super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		int id;
		Collection<Lecture> lectures;

		id = super.getRequest().getData("id", int.class);
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("lecturer", object.getLecturer().getUserAccount().getUsername());

		lectures = this.repository.findLecturesByCourseId(id);
		tuple.put("lectures", lectures);
		tuple.put("courseType", this.servicecourse.courseType(lectures));

		super.getResponse().setData(tuple);
	}

}
