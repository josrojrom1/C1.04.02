
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
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

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		Tutorial tutorial;
		Audit audit;
		Practicum practicum;
		final Lecturer lecturer;
		Date deadLine;
		deadLine = MomentHelper.getCurrentMoment();

		int tutorialId;
		int auditId;
		int practicumId;
		int lecturerId;

		tutorialId = super.getRequest().getData("tutorial", int.class);
		auditId = super.getRequest().getData("audit", int.class);
		practicumId = super.getRequest().getData("practicum", int.class);
		lecturerId = super.getRequest().getData("lecturer", int.class);

		tutorial = this.repository.findOneTutorialById(tutorialId);
		audit = this.repository.findOneAuditById(auditId);
		practicum = this.repository.findOnePracticumById(practicumId);
		lecturer = this.repository.findOneLecturerById(lecturerId);
		System.out.println("#######" + tutorial);
		System.out.println("#######" + audit);
		System.out.println("#######" + practicum);

		super.bind(object, "code", "title", "abst", "courseType", "retailPrice", "link"/* , "deadLine" */);

		System.out.println("#######" + object.getCode());
		System.out.println("#######" + object.getTitle());
		System.out.println("#######" + object.getAbst());
		System.out.println("#######" + object.getCourseType());
		System.out.println("#######" + object.getRetailPrice());
		System.out.println("#######" + object.getLink());

		object.setDraftMode(true);
		object.setTutorial(tutorial);
		object.setAudit(audit);
		object.setPracticum(practicum);
		object.setLecturer(lecturer);
		object.setDeadLine(deadLine);
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
		final SelectChoices tutorialChoices;
		final SelectChoices auditChoices;
		final SelectChoices practicumChoices;

		final Collection<Tutorial> tutorials;
		final Collection<Audit> audits;
		final Collection<Practicum> practicums;

		tutorials = this.repository.findAllTutorials();
		audits = this.repository.findAllAudits();
		practicums = this.repository.findAllPracticums();

		tutorialChoices = SelectChoices.from(tutorials, "code", object.getTutorial());
		auditChoices = SelectChoices.from(audits, "code", object.getAudit());
		practicumChoices = SelectChoices.from(practicums, "code", object.getPracticum());

		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "draftMode", "deadLine", "tutorial", "audit", "practicum");

		tuple.put("tutorial", tutorialChoices.getSelected().getKey());
		tuple.put("audit", auditChoices.getSelected().getKey());
		tuple.put("practicum", practicumChoices.getSelected().getKey());

		tuple.put("tutorials", tutorialChoices);
		tuple.put("audits", auditChoices);
		tuple.put("practicums", practicumChoices);
		super.getResponse().setData(tuple);
	}

}
