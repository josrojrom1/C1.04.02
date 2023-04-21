
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCoursePracticumListService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Course course;
		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findCourseById(masterId);
		status = course != null;
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Collection<Practicum> objects;
		final int id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findCoursePracticums(id);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {

		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("company", object.getCompany().getName());
		tuple.put("course", object.getCourse().getTitle());
		super.getResponse().setData(tuple);

	}
}
