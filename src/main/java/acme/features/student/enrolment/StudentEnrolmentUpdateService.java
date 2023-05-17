
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
import acme.utility.SpamDetector;

@Service
public class StudentEnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository	repository;

	@Autowired
	protected SpamDetector					textValidator;


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
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "motivation", "goals", "workTime");
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;
			existing = this.repository.findEnrolmentByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "student.enrolment.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("workTime"))
			super.state(object.getWorkTime() >= 0, "workTime", "student.enrolment.form.error.workTime");

		if (!super.getBuffer().getErrors().hasErrors("motivation")) {
			String validar;
			validar = object.getMotivation();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "student.enrolment.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("goals")) {
			String validar;
			validar = object.getGoals();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "student.enrolment.form.error.spam");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;
		Collection<Course> courses;

		courses = this.repository.findAllPublishedCourses();

		SelectChoices courseChoices;

		courseChoices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple;
		tuple = super.unbind(object, "code", "motivation", "goals", "workTime");
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("isFinalised", false);
		tuple.put("courseChoices", courseChoices);
		super.getResponse().setData(tuple);
	}

}
