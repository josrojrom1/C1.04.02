
package acme.features.authenticated.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCourseShowService extends AbstractService<Authenticated, Course> {

	@Autowired
	protected AuthenticatedCourseRepository repository;


	@Override
	public void check() {
		//boolean status;
		//status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "lecturer", "tutorial", "audit", "practicum");
		super.getResponse().setData(tuple);
	}
}
