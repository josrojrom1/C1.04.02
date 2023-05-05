
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.LessonType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

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
		Course course;
		int id;
		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		status = course != null;
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

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abst", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() >= 0, "retailPrice", "lecturer.lecture.form.error.retailPrice.positiveOrZero");

		//if (!super.getBuffer().getErrors().hasErrors("code"))
		//	super.state(!this.repository.findAllCodesFromCourses().contains(object.getCode()), "code", "lecturer.lecture.form.error.course.code.duplicated");

		assert object != null;

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		final Collection<Lecture> cl = this.repository.findAllLecturesByCourse(object.getId());
		final List<Boolean> lb = cl.stream().map(x -> x.isDraftMode()).collect(Collectors.toList());
		if (!lb.contains(true) && !lb.isEmpty())
			object.setDraftMode(false);
		else
			object.setDraftMode(true);
		this.repository.save(object);
	}

	public LessonType courseType(final Collection<Lecture> lecturesCourse) {
		int theory = 0;
		int handsOn = 0;
		LessonType res = LessonType.THEORY;
		for (final Lecture l : lecturesCourse)
			if (l.getLectureType().equals(LessonType.THEORY))
				theory += 1;
			else if (l.getLectureType().equals(LessonType.HANDS_ON))
				handsOn += 1;
		if (theory < handsOn)
			res = LessonType.HANDS_ON;
		else if (theory == handsOn)
			res = LessonType.HANDS_ON;

		return res;
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("courseType", this.courseType(this.repository.findAllLecturesByCourse(object.getId())));
		super.getResponse().setData(tuple);
	}

}
