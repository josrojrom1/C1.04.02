
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.LessonType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseShowService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		final Course course;
		final Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer) || course != null;
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		super.getBuffer().setData(object);

	}

	public LessonType courseType(final Collection<Lecture> lecturesFromACourse) {
		int theory = 0;
		int handson = 0;
		LessonType res = LessonType.THEORY;
		for (final Lecture l : lecturesFromACourse)
			if (l.getLectureType().equals(LessonType.THEORY))
				theory += 1;
			else if (l.getLectureType().equals(LessonType.HANDS_ON))
				handson += 1;
		if (theory < handson || theory == handson)
			res = LessonType.HANDS_ON;
		return res;
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("courseType", this.courseType(this.repository.findAllLecturesByCourse(object.getId())));
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
