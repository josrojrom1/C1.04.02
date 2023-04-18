
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Lecture;
import acme.entities.LessonType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

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
		int id;
		id = super.getRequest().getData("id", int.class);
		Lecture lecture;
		lecture = this.repository.findLectureById(id);
		status = lecture != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findLectureById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final Tuple tuple;
		SelectChoices lectureTypeChoices;
		lectureTypeChoices = SelectChoices.from(LessonType.class, object.getLectureType());

		tuple = super.unbind(object, "title", "abst", "learningTime", "body", "lectureType", "link");
		tuple.put("lectureTypes", lectureTypeChoices);
		tuple.put("draftMode", object.isDraftMode());
		super.getResponse().setData(tuple);

	}
}
