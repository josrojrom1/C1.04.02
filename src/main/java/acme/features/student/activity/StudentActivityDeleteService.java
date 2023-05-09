
package acme.features.student.activity;

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
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Activity activity;

		id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);
		status = activity != null && super.getRequest().getPrincipal().hasRole(activity.getEnrolment().getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Activity activity = this.repository.findActivityById(id);
		super.getBuffer().setData(activity);
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
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.delete(object);
	}
	@Override
	public void unbind(final Activity object) {
		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(LessonType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "abst", "activityType", "startTimePeriod", "endTimePeriod", "link");
		tuple.put("enrolment", object.getEnrolment().getId());
		tuple.put("isFinalised", object.getEnrolment().getIsFinalised());
		tuple.put("activityTypes", choices);
		super.getResponse().setData(tuple);
	}

}
