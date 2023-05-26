
package acme.features.company.practicum;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticum(masterId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

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
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "title", "abst", "goals", "totalTime");
		object.setCourse(course);
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setPublishTime(moment);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;
			existing = this.repository.findOnePracticumByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "company.practicum.form.error.duplicated");
		}
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		object.setDraftMode(false);
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
		tuple.put("publishTime", object.getPublishTime());
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("addendum", object.isHasAddendum());
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);
	}
}
