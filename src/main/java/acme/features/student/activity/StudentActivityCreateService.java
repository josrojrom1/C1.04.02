
package acme.features.student.activity;

import java.time.temporal.ChronoUnit;
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

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


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
		object.setEndTimePeriod(moment);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Activity object) {
		assert object != null;
		int enrolmentId;
		final Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolment", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		super.bind(object, "title", "abst", "activityType", "startTimePeriod", "endTimePeriod", "link");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
		//validación de la fechas
		if (!super.getBuffer().getErrors().hasErrors("startTimePeriod")) {
			final Date moment = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.DAYS);
			super.state(object.getStartTimePeriod().after(moment), "startTimePeriod", "student.activity.form.error.startTimePeriod");
		}

		if (!super.getBuffer().getErrors().hasErrors("endTimePeriod"))
			super.state(object.getStartTimePeriod().before(object.getEndTimePeriod()), "startTimePeriod, endTimePeriod", "student.activity.form.error.endTimePeriod");

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
