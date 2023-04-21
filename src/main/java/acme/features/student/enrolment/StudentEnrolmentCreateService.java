
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
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


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
		super.bind(object, "code", "motivation", "goals", "workTime", "creditCardHolder", "lowerNibble");
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
		if (!super.getBuffer().getErrors().hasErrors("workTime"))
			super.state(object.getWorkTime() >= 0, "workTime", "student.enrolment.form.error.workTime");
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		object.setIsFinalised(true);

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
		tuple = super.unbind(object, "code", "motivation", "goals", "workTime", "creditCardHolder", "lowerNibble");
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("IsFinalised", false);
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);

	}

}
