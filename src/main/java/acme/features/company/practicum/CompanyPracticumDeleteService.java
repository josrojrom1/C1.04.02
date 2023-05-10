
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumDeleteService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {

		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Practicum practicum;

		id = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticum(id);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Practicum object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticum(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		super.bind(object, "code", "title", "abst", "goals", "totalTime");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		Collection<PracticumSession> sessions;
		sessions = this.repository.findPracticumSessionsById(object.getId());
		for (final PracticumSession session : sessions)
			this.repository.delete(session);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		courses = this.repository.findAllPublishedCourses();
		SelectChoices courseChoices;
		courseChoices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("course", courseChoices.getSelected());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("addendum", object.isAddendum());
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);
	}
}
