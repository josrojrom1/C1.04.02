
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.LessonType;
import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumPublishService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	//Locurita
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int masterId;
		Practicum practicum;

		masterId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticum(masterId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticum(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);

		super.bind(object, "code", "title", "abst", "goals", "totalTime");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Practicum existing;
			existing = this.repository.findOnePracticumByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "company.practicum.form.error.duplicated");
		}

		//Comprobar que el curso no sea nulo al publicarla
		try {
			//Comprobar que se ha seleccionado un curso
			final Course curso = this.repository.findOneCourseById(object.getCourse().getId());

			if (curso != null) {
				//Comprobar que el curso ya está publicado
				super.state(!curso.isDraftMode(), "*", "company.practicum.form.error.unpublishedCourse");

				//Comprobar que el curso sea de prácticas
				final LessonType tipo = curso.getCourseType();
				super.state(tipo == LessonType.HANDS_ON, "*", "company.practicum.form.error.wrongCourseType");
			}
		} catch (final NullPointerException e) {
			super.state(false, "*", "company.practicum.form.error.nullCourse");
		}
		
		//Comprobar que tenga sesiones
				Collection<PracticumSession> sessions;
				sessions = this.repository.findPracticumSessionsById(object.getId());
				super.state(!sessions.isEmpty(), "*", "company.practicum.form.error.noSessions");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		Collection<Course> courses;

		courses = this.repository.findAllHandsOnCourses();

		SelectChoices courseChoices;

		courseChoices = SelectChoices.from(courses, "title", object.getCourse());

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("totalTimePlus", object.getTotalTime() + 0.1 * object.getTotalTime());
		tuple.put("totalTimeLess", object.getTotalTime() - 0.1 * object.getTotalTime());
		tuple.put("course", courseChoices.getSelected().getKey());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("addendum", object.isHasAddendum());
		tuple.put("courseChoices", courseChoices);

		super.getResponse().setData(tuple);
	}
}
