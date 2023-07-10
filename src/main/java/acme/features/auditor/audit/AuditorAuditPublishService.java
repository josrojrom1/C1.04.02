
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.entities.Course;
import acme.entities.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import acme.utility.SpamDetector;

@Service
public class AuditorAuditPublishService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository	repository;

	@Autowired
	protected SpamDetector				textValidator;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit audit;

		masterId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAudit(masterId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAudit(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "conclusion", "weakPoints", "strongPoints", "mark");
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;
			existing = this.repository.findOneAuditByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "auditor.audit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("conclusion")) {
			String conclusion;
			conclusion = object.getConclusion();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(conclusion), "conclusion", "auditor.audit.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("weakPoints")) {
			String weak;
			weak = object.getWeakPoints();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(weak), "weakPoints", "auditor.audit.error.spam");
		}

		if (!super.getBuffer().getErrors().hasErrors("strongPoints")) {
			String strong;
			strong = object.getStrongPoints();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(strong), "strongPoints", "auditor.audit.error.spam");
		}

		Collection<AuditingRecord> sessions;
		sessions = this.repository.findAuditingRecordById(object.getId());
		super.state(sessions != null && !sessions.isEmpty(), "*", "auditor.audit.form.error.publish");

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Collection<Course> courses;

		courses = this.repository.findPublishedCourses();

		SelectChoices courseChoices;
		SelectChoices markChoices;

		courseChoices = SelectChoices.from(courses, "title", object.getCourse());
		markChoices = SelectChoices.from(Mark.class, object.getMark());

		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "weakPoints", "strongPoints", "mark");
		tuple.put("course", courseChoices.getSelected().getKey());
		//tuple.put("mark", markChoices.getSelected().getKey());
		tuple.put("draftMode", true);
		tuple.put("courseChoices", courseChoices);
		tuple.put("markChoices", markChoices);

		super.getResponse().setData(tuple);
	}

}
