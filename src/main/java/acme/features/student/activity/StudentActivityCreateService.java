
package acme.features.student.activity;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.entities.LessonType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import acme.utility.SpamDetector;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository	repository;

	@Autowired
	protected SpamDetector				textValidator;


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
		Activity object;

		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Activity();
		object.setStartTimePeriod(moment);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		int enrolmentId;
		final Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolment_proxy", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		super.bind(object, "title", "abst", "activityType", "startTimePeriod", "endTimePeriod", "link");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
		//validación de la fechas

		if (!super.getBuffer().getErrors().hasErrors("endTimePeriod")) {
			super.state(object.getStartTimePeriod().before(object.getEndTimePeriod()), "endTimePeriod", "student.activity.form.error.endTimePeriod");
			super.state(object.getStartTimePeriod() != object.getEndTimePeriod(), "endTimePeriod", "student.activity.form.error.endTimePeriod");
		}
		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "student.activity.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("abst")) {
			String validar;
			validar = object.getAbst();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "student.activity.form.error.spam");
		}

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Collection<Enrolment> enrolments;
		SelectChoices choices;
		SelectChoices lessonChoices;
		Tuple tuple;
		int enrolmentId;

		enrolmentId = super.getRequest().getPrincipal().getActiveRoleId();
		enrolments = this.repository.findAllEnrolmentsFinalisedFromStudentId(enrolmentId);
		choices = SelectChoices.from(enrolments, "code", object.getEnrolment());
		lessonChoices = SelectChoices.from(LessonType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "abst", "startTimePeriod", "endTimePeriod", "link");
		tuple.put("enrolment", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("activityType", lessonChoices.getSelected().getKey());
		tuple.put("lessonChoices", lessonChoices);
		tuple.put("readEnrolment", false);
		super.getResponse().setData(tuple);
	}
}
