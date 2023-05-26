
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Lecture;
import acme.entities.LessonType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.utility.SpamDetector;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository	repository;

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
		Lecture lecture;
		int id;
		id = super.getRequest().getData("id", int.class);
		lecture = this.repository.findLectureById(id);
		status = lecture != null && lecture.isDraftMode() && //
			super.getRequest().getPrincipal().hasRole(lecture.getLecturer()) && //
			lecture.getLecturer().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findLectureById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "abst", "learningTime", "body", "lectureType", "link");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("learningTime"))
			super.state(object.getLearningTime() >= 0.01, "learningTime", "lecturer.lecture.form.error.learningTime");

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

		if (!super.getBuffer().getErrors().hasErrors("body")) {
			String validar;
			validar = object.getBody();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "body", "assistant.tutorial.form.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("link")) {
			String validar;
			validar = object.getLink();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "link", "assistant.tutorial.form.error.spam");
		}
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(LessonType.class, object.getLectureType());
		tuple = super.unbind(object, "title", "abst", "learningTime", "body", "lectureType", "link");

		tuple.put("id", object.getId());
		tuple.put("lectureTypes", choices);
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
