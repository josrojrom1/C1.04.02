
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
public class CompanyPracticumCreateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


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
		final Practicum object;
		final Company company;
		company = this.repository.findOneCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Practicum();
		object.setCompany(company);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		super.bind(object, "code", "title", "abst", "goals", "totalTime");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;
			existing = this.repository.findOnePracticumByCode(object.getCode());
			super.state(existing == null, "code", "Company.Practicum.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("totalTime"))
			super.state(object.getTotalTime() >= 0, "totalTime", "Company.Practicum.form.error.totalTime");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		object.setDraftMode(true);
		object.setAddendum(false);
		this.repository.save(object);
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
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("draftMode", true);
		tuple.put("addendum", false);
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);
	}
}
