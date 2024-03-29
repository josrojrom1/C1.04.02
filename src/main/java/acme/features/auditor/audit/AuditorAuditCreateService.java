
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import acme.utility.SpamDetector;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository	repository;

	@Autowired
	SpamDetector						textValidator;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		Boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		Auditor auditor;
		auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Audit();
		object.setAuditor(auditor);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "code", "conclusion", "weakPoints", "strongPoints");
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;
			existing = this.repository.findOneAuditByCode(object.getCode());
			super.state(existing == null, "code", "auditor.audit.form.error.duplicated");
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
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Collection<Course> courses;

		courses = this.repository.findPublishedCourses();

		SelectChoices courseChoices;
		//SelectChoices markChoices;

		courseChoices = SelectChoices.from(courses, "title", object.getCourse());
		//markChoices = SelectChoices.from(Mark.class, object.getMark());

		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "weakPoints", "strongPoints");
		tuple.put("course", courseChoices.getSelected().getKey());
		//tuple.put("mark", markChoices.getSelected().getKey());
		tuple.put("draftMode", true);
		tuple.put("courseChoices", courseChoices);
		//tuple.put("markChoices", markChoices);

		super.getResponse().setData(tuple);
	}

}
