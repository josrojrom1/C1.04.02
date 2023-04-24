
package acme.features.lecturer.course;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abst", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() >= 0, "retailPrice", "lecturer.lecture.form.error.retailPrice.positiveOrZero");

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(object.getRetailPrice().getAmount() <= 99999, "retailPrice", "lecturer.lecture.form.error.retailPrice.max");

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(!object.getRetailPrice().toString().contains("-"), "retailPrice", "lecturer.lecture.form.error.retailPrice.negative");

		if (!super.getBuffer().getErrors().hasErrors("retailPrice"))
			super.state(this.repository.findConfigurationAcceptedCurrencies().stream().collect(Collectors.toList()).contains(object.getRetailPrice().getCurrency()), "retailPrice", "lecturer.lecture.form.error.retailPrice.currency");

		assert object != null;
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
		tuple.put("id", object.getId());
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
