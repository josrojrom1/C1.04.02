
package acme.features.lecturer.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.LectureType;
import acme.entities.LessonType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.utility.SpamDetector;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected SpamDetector				textValidator;


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
		status = course != null && course.isDraftMode() && //
			super.getRequest().getPrincipal().hasRole(course.getLecturer()) && //
			course.getLecturer().getId() == super.getRequest().getPrincipal().getActiveRoleId();
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
			super.state(object.getRetailPrice().getAmount() <= 999, "retailPrice", "lecturer.lecture.form.error.retailPrice.max");
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.lecture.form.error.retailPrice.negative");

			String currencies;
			boolean b = false;
			currencies = this.repository.findConfigurationAcceptedCurrencies();
			final List<String> listCurrencies;
			final String[] aux = currencies.replace(" ", "").replace("[", "").replace("]", "").split(",");
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

		Course course;
		final Collection<Lecture> lectures;
		course = this.repository.findOneCourseByCode(object.getCode());
		final int id = course.getId();
		lectures = this.repository.findAllLecturesByCourse(id);
		final List<LectureType> lectureTypeList = new ArrayList<>();
		for (final Lecture l : lectures)
			lectureTypeList.add(l.getLectureType());
		super.state(lectureTypeList.contains(LectureType.HANDS_ON), "*", "lecturer.course.form.courseType.reject-pure-theoretical");

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "title", "assistant.tutorial.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("abst")) {
			String validar;
			validar = object.getAbst();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "abst", "assistant.tutorial.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("link")) {
			String validar;
			validar = object.getLink();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "link", "assistant.tutorial.form.error.spam");
		}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		final Collection<Boolean> lecturesDraftModeByCourse = this.repository.findAllLecturesDraftModeByCourse(object.getId());
		final Collection<LessonType> lecturesLessonTypeByCourse = this.repository.findAllLecturesLessonTypeByCourse(object.getId());

		if (lecturesDraftModeByCourse.contains(true) || lecturesDraftModeByCourse.isEmpty() || !lecturesLessonTypeByCourse.contains(LessonType.HANDS_ON))
			object.setDraftMode(true);//En caso de que alguna lecture no este publicada, la lista del curso este vacia, o no existan lectures de practica entonces no se publica
		else {
			object.setDraftMode(false);//En caso contrario podemos publicar el curso correctamente
			object.setCourseType(this.courseType(this.repository.findAllLecturesByCourse(object.getId())));

			this.repository.save(object);

		}
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
		if (theory > handsOn && handsOn > 0)
			res = LessonType.THEORY;
		else if (handsOn > theory)
			res = LessonType.HANDS_ON;
		else if (handsOn == theory && handsOn > 0)
			res = LessonType.BALANCED;

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
