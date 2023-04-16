
package acme.features.lecturer.course;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.LessonType;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		object.setTitle("");
		object.setCode("");
		object.setAbst("");
		object.setCourseType(LessonType.HANDS_ON);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		//		Tutorial tutorial;
		//		Audit audit;
		//		Practicum practicum;
		//		final Lecturer lecturer;
		//		Date deadLine;
		//		deadLine = MomentHelper.getCurrentMoment();
		//
		//		int tutorialId;
		//		int auditId;
		//		int practicumId;
		//		int lecturerId;
		//
		//		tutorialId = super.getRequest().getData("tutorial", int.class);
		//		auditId = super.getRequest().getData("audit", int.class);
		//		practicumId = super.getRequest().getData("practicum", int.class);
		//		lecturerId = super.getRequest().getData("lecturer", int.class);
		//
		//		tutorial = this.repository.findOneTutorialById(tutorialId);
		//		audit = this.repository.findOneAuditById(auditId);
		//		practicum = this.repository.findOnePracticumById(practicumId);
		//		lecturer = this.repository.findOneLecturerById(lecturerId);

		super.bind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "deadLine", "lecturer", "tutorial", "audit", "practicum");

		//		object.setDraftMode(true);
		//		object.setTutorial(tutorial);
		//		object.setAudit(audit);
		//		object.setPracticum(practicum);
		//		object.setLecturer(lecturer);
		//		object.setDeadLine(deadLine);
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("deadLine")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getDeadLine(), minimumDeadline), "deadline", "lecturer.course.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() > 0, "retailPrice", "lecturer.course.form.error.negative-salary");
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
		final SelectChoices tutorialChoices;
		final SelectChoices auditChoices;
		final SelectChoices practicumChoices;
		final SelectChoices courseTypeChoices;

		final Collection<Tutorial> tutorials;
		final Collection<Audit> audits;
		final Collection<Practicum> practicums;

		tutorials = this.repository.findAllTutorials();
		audits = this.repository.findAllAudits();
		practicums = this.repository.findAllPracticums();

		courseTypeChoices = SelectChoices.from(LessonType.class, object.getCourseType());
		tutorialChoices = SelectChoices.from(tutorials, "code", object.getTutorial());
		auditChoices = SelectChoices.from(audits, "code", object.getAudit());
		practicumChoices = SelectChoices.from(practicums, "code", object.getPracticum());

		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "draftMode", "deadLine", "lecturer", "tutorial", "audit", "practicum");

		tuple.put("tutorial", tutorialChoices.getSelected().getKey());
		tuple.put("audit", auditChoices.getSelected().getKey());
		tuple.put("practicum", practicumChoices.getSelected().getKey());
		tuple.put("courseType", courseTypeChoices.getSelected().getKey());

		tuple.put("tutorials", tutorialChoices);
		tuple.put("audits", auditChoices);
		tuple.put("practicums", practicumChoices);
		tuple.put("courseTypes", courseTypeChoices);

		tuple.put("readonly", false);
		super.getResponse().setData(tuple);
	}

}
