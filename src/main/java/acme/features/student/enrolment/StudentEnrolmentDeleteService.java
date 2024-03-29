
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentDeleteService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


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
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = enrolment != null && !enrolment.getIsFinalised() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent()) && enrolment.getStudent().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Enrolment object;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "student", "creditCardHolder", "lowerNibble", "isFinalised");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		int id;
		Collection<Activity> activities;

		id = object.getId();
		activities = this.repository.findAllActivitiesFromEnrolmentId(id);

		this.repository.deleteAll(activities);
		this.repository.delete(object);
	}
	@Override
	public void unbind(final Enrolment object) {
		assert object != null;
		Collection<Course> courses;

		courses = this.repository.findAllPublishedCourses();

		SelectChoices courseChoices;

		courseChoices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple;
		tuple = super.unbind(object, "code", "motivation", "goals", "creditCardHolder", "lowerNibble");
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("isFinalised", false);
		tuple.put("courseChoices", courseChoices);
		super.getResponse().setData(tuple);
	}

}
