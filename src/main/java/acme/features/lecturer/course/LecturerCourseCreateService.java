
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
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		final Lecturer lecturer;
		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);
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

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(this.repository.findConfigurationAcceptedCurrencies().contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.lecture.form.error.retailPrice.currency");

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(!this.repository.findAllCodesFromCourses().contains(object.getCode()), "code", "lecturer.lecture.form.error.course.code.duplicated");

		assert object != null;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
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
		tuple.put("draftMode", true);
		super.getResponse().setData(tuple);
	}

}
