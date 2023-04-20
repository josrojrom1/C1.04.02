
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


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
		super.bind(object, "code", "conclusion", "weakPoints", "strongPoints", "mark");
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
		//validar marks
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
