
package acme.features.lecturer.courseOfLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseOfLecture;
import acme.entities.Lecture;
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
		boolean status;
		status = this.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
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
		Lecture lecture;
		Integer lectureId;
		final int id = super.getRequest().getData("id", int.class);
		final Course course = this.repository.findCourseById(id);
		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findLectureById(lectureId);

		super.bind(object, "course");

		object.setLecture(lecture);
		object.setCourse(course);
	}

	@Override
	public void validate(final CourseOfLecture object) {
		final int courseId = super.getRequest().getData("id", int.class);
		final Collection<Lecture> lectures = this.repository.findAllLecturesFromCourseOfLecture(courseId);
		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.repository.findLectureById(lectureId);
		if (!super.getBuffer().getErrors().hasErrors("lecture"))
			super.state(!lectures.contains(lecture), "lecture", "lecturer.courseOfLecture.form.error.lecture");

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

		courseId = super.getRequest().getData("id", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		course = this.repository.findCourseById(courseId);
		choices = SelectChoices.from(this.repository.findPublishedLecturesFromLecturer(lecturerId), "title", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");

		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		tuple.put("courseTitle", course.getTitle());
		tuple.put("id", courseId);

		tuple.put("colId", object.getId());

		super.getResponse().setData(tuple);
	}

}
