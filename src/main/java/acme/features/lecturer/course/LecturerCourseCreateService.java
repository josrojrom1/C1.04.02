
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course object;
		final Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "draftMode", "deadLine", "lecturer", "tutorial", "audit", "practicum");

	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		//
		//		if (!super.getBuffer().getErrors().hasErrors("reference")) {
		//			Job existing;
		//
		//			existing = this.repository.findOneJobByReference(object.getReference());
		//			super.state(existing == null, "reference", "employer.job.form.error.duplicated");
		//		}
		//
		//		if (!super.getBuffer().getErrors().hasErrors("deadline")) {
		//			Date minimumDeadline;
		//
		//			minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
		//			super.state(MomentHelper.isAfter(object.getDeadline(), minimumDeadline), "deadline", "employer.job.form.error.too-close");
		//		}
		//
		//		if (!super.getBuffer().getErrors().hasErrors("salary"))
		//			super.state(object.getSalary().getAmount() > 0, "salary", "employer.job.form.error.negative-salary");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "draftMode", "deadLine", "lecturer", "tutorial", "audit", "practicum");

		super.getResponse().setData(tuple);
	}

}
