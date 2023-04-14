
package acme.features.any.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository repository;


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
		final Lecturer lecturer = object.getLecturer();
		final Tutorial tutorial = object.getTutorial();
		final Audit audit = object.getAudit();
		final Practicum practicum = object.getPracticum();
		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link"/* , "lecturer" , "tutorial", "audit", "practicum" */);
		tuple.put("tutorial", tutorial.getTitle());
		tuple.put("audit", audit.getCode());
		tuple.put("practicum", practicum.getCode());
		super.getResponse().setData(tuple);
	}
}
