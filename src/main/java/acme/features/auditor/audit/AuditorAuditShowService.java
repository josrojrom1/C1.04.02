
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
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

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
		super.getResponse().setAuthorised(true);
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
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("courseChoices", courseChoices);
		tuple.put("markChoices", markChoices);

		super.getResponse().setData(tuple);
	}

}
