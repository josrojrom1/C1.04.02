
package acme.features.any.course;

import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.accounts.Any;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	//	@Autowired
	//	protected AnyCourseRepository repository;
	//
	//
	//	@Override
	//	public void check() {
	//		//boolean status;
	//		//status = super.getRequest().hasData("id", int.class);
	//		super.getResponse().setChecked(true);
	//	}
	//
	//	@Override
	//	public void authorise() {
	//		super.getResponse().setAuthorised(true);
	//	}
	//
	//	@Override
	//	public void load() {
	//		final Course object;
	//		int id;
	//		id = super.getRequest().getData("id", int.class);
	//		object = this.repository.findOneCourseById(id);
	//		super.getBuffer().setData(object);
	//	}
	//
	//	@Override
	//	public void unbind(final Course object) {
	//		Tuple tuple;
	//		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link", "lecturer", "tutorial", "audit", "practicum");
	//		super.getResponse().setData(tuple);
	//	}
}
