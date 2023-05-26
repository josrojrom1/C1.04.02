
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.entities.LessonType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;
import acme.utility.SpamDetector;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository	repository;

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
		int id;
		Activity activity;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);
		enrolment = this.repository.findEnrolmentByActivityId(id);
		status = activity != null && enrolment != null && enrolment.getIsFinalised() && super.getRequest().getPrincipal().hasRole(Student.class) && activity.getEnrolment().getStudent().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		final Enrolment enrolment;
		int enrolmentId;

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
		boolean isFinalised;
		enrolmentId = super.getRequest().getPrincipal().getActiveRoleId();
		enrolments = this.repository.findAllEnrolmentsFinalisedFromStudentId(enrolmentId);
		choices = SelectChoices.from(enrolments, "code", object.getEnrolment());
		lessonChoices = SelectChoices.from(LessonType.class, object.getActivityType());

		isFinalised = object.getEnrolment() != null ? object.getEnrolment().getIsFinalised() : true;
		tuple = super.unbind(object, "title", "abst", "startTimePeriod", "endTimePeriod", "link");
		tuple.put("enrolment", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("isFinalised", isFinalised);
		tuple.put("activityType", lessonChoices.getSelected().getKey());
		tuple.put("lessonChoices", lessonChoices);
		tuple.put("readEnrolment", false);
		super.getResponse().setData(tuple);
	}

}
