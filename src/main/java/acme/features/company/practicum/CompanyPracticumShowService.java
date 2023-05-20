
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

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
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticum(masterId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(Company.class) && practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticum(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		courses = this.repository.findAllPublishedCourses();
		SelectChoices courseChoices;
		courseChoices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "goals");
		tuple.put("totalTime", object.getTotalTime() + object.getTotalTime() * (1.0 / 10.0));
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("addendum", object.isHasAddendum());
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);
	}
}
