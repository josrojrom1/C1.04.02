
package acme.features.any.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.LectureType;
import acme.entities.LessonType;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Course course;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		status = course != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Course object;
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

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("principal", super.getRequest().getPrincipal().isAuthenticated());
		tuple.put("courseType", this.courseType(this.repository.findAllLecturesByCourse(object.getId())));

		super.getResponse().setData(tuple);
	}
}
