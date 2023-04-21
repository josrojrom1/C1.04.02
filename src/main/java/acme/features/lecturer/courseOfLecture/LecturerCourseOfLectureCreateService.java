
package acme.features.lecturer.courseOfLecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseOfLecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseOfLectureCreateService extends AbstractService<Lecturer, CourseOfLecture> {

	@Autowired
	protected LecturerCourseOfLectureRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final CourseOfLecture object;
		final Course course;
		course = this.repository.findCourseById(super.getRequest().getData("id", int.class));
		object = new CourseOfLecture();
		object.setCourse(course);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final CourseOfLecture object) {
		assert object != null;
		super.bind(object, "course", "lecture");
	}

	@Override
	public void validate(final CourseOfLecture object) {

	}

	@Override
	public void perform(final CourseOfLecture object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final CourseOfLecture object) {
		assert object != null;
		Tuple tuple;
		int courseId;
		int lecturerId;

		Course course;
		SelectChoices choices;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		course = this.repository.findCourseById(courseId);
		choices = SelectChoices.from(this.repository.findAvaibleLecturesForCourse(courseId, lecturerId), "title", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");

		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		tuple.put("courseTitle", course.getTitle());
		tuple.put("id", courseId);

		super.getResponse().setData(tuple);
	}

}
