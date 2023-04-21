
package acme.features.lecturer.courseOfLecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseOfLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseOfLectureDeleteService extends AbstractService<Lecturer, CourseOfLecture> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseOfLectureRepository repository;

	//AbstractServiceInterface -------------------------------


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
		final CourseOfLecture col;
		id = super.getRequest().getData("id", int.class);
		col = this.repository.findCourseOfLectureById(id);
		status = col != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseOfLecture object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseOfLectureById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseOfLecture object) {
		super.bind(object, "id");

	}

	@Override
	public void validate(final CourseOfLecture object) {
	}

	@Override
	public void perform(final CourseOfLecture object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final CourseOfLecture object) {
		assert object != null;
		final Tuple tuple;
		tuple = super.unbind(object, "id");
		tuple.put("course", object.getCourse().getTitle());
		tuple.put("lecture", object.getLecture().getTitle());
		super.getResponse().setData(tuple);

	}

}
