
package acme.features.lecturer.course;

import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	//	@Autowired
	//	protected LecturerCourseRepository repository;
	//
	//
	//	@Override
	//	public void check() {
	//		super.getResponse().setChecked(true);
	//	}
	//
	//	@Override
	//	public void authorise() {
	//		super.getResponse().setAuthorised(true);
	//	}
	//
	//	@Override
	//	public void load() {
	//		Course object;
	//		final Lecturer lecturer;
	//		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
	//		object = new Course();
	//		object.setDraftMode(true);
	//		object.setLecturer(lecturer);
	//		super.getBuffer().setData(object);
	//	}
	//
	//	@Override
	//	public void bind(final Course object) {
	//		assert object != null;
	//
	//		super.bind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "deadLine", "lecturer", "tutorial", "audit", "practicum");
	//
	//	}
	//
	//	@Override
	//	public void validate(final Course object) {
	//		assert object != null;
	//		//
	//		//		if (!super.getBuffer().getErrors().hasErrors("code")) {
	//		//			Course existing;
	//		//
	//		//			existing = this.repository.findOneCourseByCode(object.getCode());
	//		//			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
	//		//		}
	//		//
	//		//		if (!super.getBuffer().getErrors().hasErrors("deadLine")) {
	//		//			Date minimumDeadline;
	//		//
	//		//			minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
	//		//			super.state(MomentHelper.isAfter(object.getDeadLine(), minimumDeadline), "deadLine", "lecturer.course.form.error.too-close");
	//		//		}
	//		//
	//		//		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
	//		//			super.state(object.getRetailPrice().getAmount() > 0, "retailPrice", "lecturer.course.form.error.negative-salary");
	//	}
	//
	//	@Override
	//	public void perform(final Course object) {
	//		assert object != null;
	//
	//		this.repository.save(object);
	//	}
	//
	//	@Override
	//	public void unbind(final Course object) {
	//		assert object != null;
	//		Tuple tuple;
	//		final SelectChoices tutorialChoices;
	//		final SelectChoices auditChoices;
	//		final SelectChoices practicumChoices;
	//		final SelectChoices courseTypeChoices;
	//
	//		final Collection<Tutorial> tutorials;
	//		final Collection<Audit> audits;
	//		final Collection<Practicum> practicums;
	//
	//		tutorials = this.repository.findAllTutorials();
	//		audits = this.repository.findAllAudits();
	//		practicums = this.repository.findAllPracticums();
	//
	//		courseTypeChoices = SelectChoices.from(LessonType.class, object.getCourseType());
	//		tutorialChoices = SelectChoices.from(tutorials, "code", object.getTutorial());
	//		auditChoices = SelectChoices.from(audits, "code", object.getAudit());
	//		practicumChoices = SelectChoices.from(practicums, "code", object.getPracticum());
	//
	//		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "draftMode", "deadLine", "lecturer", "tutorial", "audit", "practicum");
	//
	//		tuple.put("tutorial", tutorialChoices.getSelected().getKey());
	//		tuple.put("audit", auditChoices.getSelected().getKey());
	//		tuple.put("practicum", practicumChoices.getSelected().getKey());
	//		tuple.put("courseType", courseTypeChoices.getSelected().getKey());
	//
	//		tuple.put("tutorials", tutorialChoices);
	//		tuple.put("audits", auditChoices);
	//		tuple.put("practicums", practicumChoices);
	//		tuple.put("courseTypes", courseTypeChoices);
	//
	//		tuple.put("readonly", false);
	//		super.getResponse().setData(tuple);
	//	}

}
