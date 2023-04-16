
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.framework.components.jsp.SelectChoices;
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
		final SelectChoices tutorialChoices;
		final SelectChoices auditChoices;
		final SelectChoices practicumChoices;

		final Collection<Tutorial> tutorials;
		final Collection<Audit> audits;
		final Collection<Practicum> practicums;

		tutorials = this.repository.findAllTutorials();
		audits = this.repository.findAllAudits();
		practicums = this.repository.findAllPracticums();

		tutorialChoices = SelectChoices.from(tutorials, "code", object.getTutorial());
		auditChoices = SelectChoices.from(audits, "code", object.getAudit());
		practicumChoices = SelectChoices.from(practicums, "code", object.getPracticum());

		tuple = super.unbind(object, "code", "title", "abst", "courseType", "retailPrice", "link"/* , "draftMode", "deadLine", "tutorial", "audit", "practicum" */);
		tuple.put("tutorial", tutorialChoices.getSelected().getKey());
		tuple.put("audit", auditChoices.getSelected().getKey());
		tuple.put("practicum", practicumChoices.getSelected().getKey());

		tuple.put("tutorials", tutorialChoices);
		tuple.put("audits", auditChoices);
		tuple.put("practicums", practicumChoices);

		tuple.put("readonly", false);
		super.getResponse().setData(tuple);
	}

}
