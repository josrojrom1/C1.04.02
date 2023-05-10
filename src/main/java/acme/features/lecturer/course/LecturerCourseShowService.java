
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.LessonType;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseShowService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository					repository;

	@Autowired
	protected AuthenticatedMoneyExchangePerformService	moneyExchangeService;


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
		final Course course;
		final Lecturer lecturer;
		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		lecturer = course == null ? null : course.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer) || course != null;
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

	public LessonType courseType(final Collection<Lecture> lecturesFromACourse) {
		int theory = 0;//..........En este método calculamos el courseType de un curso.
		int handson = 0;//.........Recordemos que el sistema rechaza cursos puros teóricos.
		LessonType res = LessonType.THEORY;
		for (final Lecture l : lecturesFromACourse)
			if (l.getLectureType().equals(LessonType.THEORY))
				theory += 1;
			else if (l.getLectureType().equals(LessonType.HANDS_ON))
				handson += 1;
		if (theory > handson)
			res = LessonType.THEORY;
		else
			res = LessonType.HANDS_ON;
		return res;
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final String systemCurrency = this.repository.findConfiguration().getSystemCurrency();
		final Collection<Lecture> lecturesByCourse = this.repository.findAllLecturesByCourse(object.getId());
		final Boolean hasLectures;
		MoneyExchange moneyExchange;
		moneyExchange = this.moneyExchangeService.computeMoneyExchange(object.getRetailPrice(), systemCurrency);
		if (lecturesByCourse.isEmpty())
			hasLectures = false;
		else
			hasLectures = true;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("courseType", this.courseType(this.repository.findAllLecturesByCourse(object.getId())));
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("moneyExchange", moneyExchange.getTarget());
		tuple.put("hasLectures", hasLectures);

		super.getResponse().setData(tuple);
	}

}
