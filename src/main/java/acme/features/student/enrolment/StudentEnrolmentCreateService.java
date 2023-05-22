
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
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository	repository;

	@Autowired
	protected SpamDetector					textValidator;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Student.class));
	}

	@Override
	public void load() {
		Enrolment object;
		Student student;
		student = this.repository.findStudentById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Enrolment();
		object.setStudent(student);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "code", "motivation", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Enrolment existing;
			existing = this.repository.findEnrolmentByCode(object.getCode());
			super.state(existing == null, "code", "student.enrolment.form.error.duplicated");
		}

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
		object.setIsFinalised(false);

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
		tuple = super.unbind(object, "code", "motivation", "goals");
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("isFinalised", false);
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);

	}

}
