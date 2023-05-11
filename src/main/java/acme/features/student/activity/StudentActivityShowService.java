
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

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repository;


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

		id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);
		status = activity != null && super.getRequest().getPrincipal().hasRole(Student.class) && activity.getEnrolment().getStudent().getId() == super.getRequest().getPrincipal().getActiveRoleId();
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
		tuple.put("isFinalised", object.getEnrolment().getIsFinalised());
		tuple.put("activityType", lessonChoices.getSelected().getKey());
		tuple.put("lessonChoices", lessonChoices);
		tuple.put("readEnrolment", false);
		tuple.put("masterId", object.getEnrolment().getId());
		super.getResponse().setData(tuple);

	}

}
