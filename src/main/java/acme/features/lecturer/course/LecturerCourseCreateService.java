
package acme.features.lecturer.course;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {//Autorizamos solo si somos rol Lecturer
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		final Lecturer lecturer;
		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "abst", "retailPrice", "link");

	}

	@Override
	public void validate(final Course object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			super.state(object.getRetailPrice().getAmount() >= 0, "retailPrice", "lecturer.lecture.form.error.retailPrice.positiveOrZero");
			super.state(object.getRetailPrice().getAmount() <= 99999, "retailPrice", "lecturer.lecture.form.error.retailPrice.max");
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.lecture.form.error.retailPrice.negative");

			String currencies;
			boolean b = false;
			currencies = this.repository.findConfigurationAcceptedCurrencies();
			final List<String> listCurrencies;
			final String[] aux = currencies.replace(" ", "").replace("[", "").replace("]", "").split(",");
			listCurrencies = Arrays.asList(aux);
			for (final String c : listCurrencies)
				if (c.equals(object.getRetailPrice().getCurrency()))
					b = true;
			super.state(b != false, "retailPrice", "lecturer.lecture.form.error.retailPrice.currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(!this.repository.findAllCodesFromCourses().contains(object.getCode()), "code", "lecturer.lecture.form.error.course.code.duplicated");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		super.getResponse().setData(tuple);
	}

}
