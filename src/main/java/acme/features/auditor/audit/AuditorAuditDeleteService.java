
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

@Service
public class AuditorAuditDeleteService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {

		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Audit audit;
		int id;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAudit(id);
		status = audit != null && audit.isDraftMode() && audit.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Audit object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAudit(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "weakPoints", "strongPoints", "mark");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		Collection<AuditingRecord> records;
		records = this.repository.findAuditingRecordById(object.getId());
		for (final AuditingRecord record : records)
			this.repository.delete(record);
		this.repository.delete(object);
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
