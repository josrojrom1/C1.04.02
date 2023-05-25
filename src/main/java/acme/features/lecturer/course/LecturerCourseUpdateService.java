
package acme.features.lecturer.course;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.utility.SpamDetector;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected SpamDetector				textValidator;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Course course;
		int id;
		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		status = course.isDraftMode() && super.getRequest().getPrincipal().hasRole(Lecturer.class) &&//
			course.getLecturer().getId() == super.getRequest().getPrincipal().getActiveRoleId();
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
			final String[] aux = currencies.replace("[", "").replace("]", "").split(",");
			listCurrencies = Arrays.asList(aux);
			for (final String c : listCurrencies)
				if (c.equals(object.getRetailPrice().getCurrency()))
					b = true;
			super.state(b != false, "retailPrice", "lecturer.lecture.form.error.retailPrice.currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;
			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "lecturer.lecture.form.error.course.code.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "title", "assistant.tutorial.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("abst")) {
			String validar;
			validar = object.getAbst();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "abst", "assistant.tutorial.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("link")) {
			String validar;
			validar = object.getLink();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "link", "assistant.tutorial.form.error.spam");
		}
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
