
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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = !enrolment.getIsFinalised();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(id);
		super.getBuffer().setData(enrolment);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "student", "workTime", "course", "creditCardHolder", "lowerNibble");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		final int id = object.getId();
		final Collection<Activity> activities = this.repository.findActivitiesByEnrolmentId(id);

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
		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "creditCardHolder", "lowerNibble");
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("IsFinalised", false);
		tuple.put("courseChoices", courseChoices);
	}

}
