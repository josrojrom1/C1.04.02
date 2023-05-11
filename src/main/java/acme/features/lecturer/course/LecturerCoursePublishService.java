
package acme.features.lecturer.course;

import java.util.Arrays;
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
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			super.state(object.getRetailPrice().getAmount() >= 0, "retailPrice", "lecturer.lecture.form.error.retailPrice.positiveOrZero");
			super.state(object.getRetailPrice().getAmount() <= 99999, "retailPrice", "lecturer.lecture.form.error.retailPrice.max");
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.lecture.form.error.retailPrice.negative");

			String currencies;
			boolean b = false;
			currencies = this.repository.findConfigurationAcceptedCurrencies();
			final List<String> listCurrencies;
			final String[] aux = currencies.replace("[", "").replace("]", "").split(",");
			listCurrencies = Arrays.asList(aux);
			for (final String c : listCurrencies)
				if (c.equals(object.getRetailPrice().getCurrency()))
					b = true;

			super.state(b != false, "retailPrice", "lecturer.lecture.form.error.retailPrice.currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;
			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "lecturer.lecture.form.error.course.code.duplicated");
		}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		final Collection<Lecture> lecturesByCourse = this.repository.findAllLecturesByCourse(object.getId());
		final List<Boolean> drafModeList = lecturesByCourse.stream().map(x -> x.isDraftMode()).collect(Collectors.toList());
		final List<LessonType> lectureTypes = lecturesByCourse.stream().map(x -> x.getLectureType()).collect(Collectors.toList());
		//Comprobamos que no existan lectures no publicadas y que tengan al menos una de practicas para que no sea puramente teorica
		if (!drafModeList.contains(true) && !drafModeList.isEmpty() && lectureTypes.contains(LessonType.HANDS_ON))
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
		if (theory > handsOn)
			res = LessonType.THEORY;
		else
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
