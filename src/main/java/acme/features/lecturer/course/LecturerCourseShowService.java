
package acme.features.lecturer.course;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseShowService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		final Course course;
		final Lecturer lecturer;
		Date currentMoment;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		lecturer = course == null ? null : course.getLecturer();
		currentMoment = MomentHelper.getCurrentMoment();
		status = super.getRequest().getPrincipal().hasRole(lecturer) || course != null && !course.isDraftMode() && MomentHelper.isAfter(course.getDeadLine(), currentMoment);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		final Tutorial tutorial = object.getTutorial();
		final Audit audit = object.getAudit();
		final Practicum practicum = object.getPracticum();

		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link"/* , "draftMode", "deadLine", "tutorial", "audit", "practicum" */);
		tuple.put("tutorial", tutorial.getTitle());
		tuple.put("audit", audit.getCode());
		tuple.put("practicum", practicum.getCode());
		super.getResponse().setData(tuple);
	}

}
