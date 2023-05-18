
package acme.features.lecturer.courseOfLecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseOfLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseOfLectureShowService extends AbstractService<Lecturer, CourseOfLecture> {

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
		final boolean status;
		final CourseOfLecture col = this.repository.findCourseOfLectureById(super.getRequest().getData("id", int.class));
		status = col != null && //
			super.getRequest().getPrincipal().hasRole(Lecturer.class) &&//
			col.getCourse().getLecturer().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CourseOfLecture object;
		object = this.repository.findCourseOfLectureById(super.getRequest().getData("id", int.class));
		super.getBuffer().setData(object);
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
