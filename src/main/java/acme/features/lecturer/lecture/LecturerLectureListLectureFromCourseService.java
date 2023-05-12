
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListLectureFromCourseService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Lecture> lectures = this.repository.findAllLecturesByCourse(super.getRequest().getData("id", int.class));
		super.getBuffer().setData(lectures);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "abst", "learningTime", "body", "lectureType", "link");
		super.getResponse().setData(tuple);
	}

}
