
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

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
		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent()) && enrolment.getStudent().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
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
		tuple.put("isFinalised", object.getIsFinalised());
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);
	}

}
