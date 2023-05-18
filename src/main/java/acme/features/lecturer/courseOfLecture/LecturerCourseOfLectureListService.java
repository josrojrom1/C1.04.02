
package acme.features.lecturer.courseOfLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.CourseOfLecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseOfLectureListService extends AbstractService<Lecturer, CourseOfLecture> {

	// Internal State ------------------------------------------
	@Autowired
	protected LecturerCourseOfLectureRepository repository;

	//AbstractServiceInterface -------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		final CourseOfLecture col = this.repository.findCourseOfLectureById(super.getRequest().getData("id", int.class));
		status = col != null && //
			super.getRequest().getPrincipal().hasRole(Lecturer.class) &&//
			col.getCourse().getLecturer().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CourseOfLecture> objects;
		objects = this.repository.findCourseOfLectureByCourseId(super.getRequest().getData("courseId", int.class));
		super.getBuffer().setData(objects);
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
