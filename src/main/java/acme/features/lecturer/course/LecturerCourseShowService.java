
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.LectureType;
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
		int id;
		final Course course;
		final Lecturer lecturer;
		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		lecturer = course == null ? null : course.getLecturer();
		status = course.getLecturer().getId() == super.getRequest().getPrincipal().getActiveRoleId();
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

	public LessonType courseType(final Collection<Lecture> lecturesCourse) {
		int theory = 0;
		int handsOn = 0;
		LessonType res = null;
		for (final Lecture l : lecturesCourse)
			if (l.getLectureType().equals(LectureType.THEORY))
				theory += 1;
			else if (l.getLectureType().equals(LectureType.HANDS_ON))
				handsOn += 1;
		if (theory > handsOn && handsOn > 0)
			res = LessonType.THEORY;
		else if (handsOn > theory)
			res = LessonType.HANDS_ON;
		else if (theory == 0)
			res = LessonType.HANDS_ON;
		else if (handsOn == theory && handsOn > 0)
			res = LessonType.BALANCED;

		return res;
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final Collection<Lecture> lecturesByCourse = this.repository.findAllLecturesByCourse(object.getId());
		final Boolean hasLectures;
		if (lecturesByCourse.isEmpty())
			hasLectures = false;
		else
			hasLectures = true;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("courseType", this.courseType(this.repository.findAllLecturesByCourse(object.getId())));
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("hasLectures", hasLectures);

		super.getResponse().setData(tuple);
	}

}
