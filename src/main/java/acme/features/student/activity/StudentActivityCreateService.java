
package acme.features.student.activity;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.entities.LessonType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;

		id = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = enrolment != null && enrolment.getIsFinalised() != true && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Activity object;
		final int id;
		Enrolment enrolment;

		enrolment = this.repository.findEnrolmentById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new Activity();
		object.setEnrolment(enrolment);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		assert object != null;
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolment", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		super.bind(object, "title", "abst", "activityType", "startTimePeriod", "endTimePeriod", "link");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		final Date startTime = (Date) object.getStartTimePeriod();
		final Date endTime = (Date) object.getEndTimePeriod();
		super.state(startTime.before(endTime), "*", "student.activity.form.error.invalidDuration");

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(LessonType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "abst", "activityType", "startTimePeriod", "endTimePeriod", "link");
		tuple.put("masterId", object.getEnrolment().getId());
		tuple.put("isFinalised", object.getEnrolment().getIsFinalised());
		tuple.put("activityTypes", choices);
		super.getResponse().setData(tuple);
	}
}
