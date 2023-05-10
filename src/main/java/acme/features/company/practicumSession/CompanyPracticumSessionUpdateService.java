
package acme.features.company.practicumSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	CompanyPracticumSessionRepository repository;


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
		PracticumSession session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOnePracticumSession(id);
		status = session != null && session.getPracticum().isDraftMode() && super.getRequest().getPrincipal().hasRole(Company.class) && session.getPracticum().getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSession(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;
		super.bind(object, "title", "abst", "timePeriodStart", "timePeriodEnd", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;
		//Validación de las fechas
		if (!super.getBuffer().getErrors().hasErrors("timePeriodStart")) {
			final Date moment = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(object.getTimePeriodStart().after(moment), "timePeriodStart", "company.practicumSession.form.error.timePeriodStart");
		}

		if (!super.getBuffer().getErrors().hasErrors("timePeriodEnd"))
			super.state(object.getTimePeriodStart().before(object.getTimePeriodEnd()), "timePeriodStart, timePeriodEnd", "company.practicumSession.form.error.timePeriodEnd");

		if (!super.getBuffer().getErrors().hasErrors("periodFinish")) {
			final Duration duration = MomentHelper.computeDuration(object.getTimePeriodStart(), object.getTimePeriodEnd());
			final Duration d1 = Duration.ofDays(7);
			super.state(d1.compareTo(duration) >= 0, "*", "company.practicumSession.form.error.duration");
		}
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Collection<Practicum> Practicums;
		SelectChoices choices;

		Practicums = this.repository.findAllPracticums();
		choices = SelectChoices.from(Practicums, "code", object.getPracticum());
		Tuple tuple;
		tuple = super.unbind(object, "title", "abst", "timePeriodStart", "timePeriodEnd", "link");
		tuple.put("Practicum", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("draftMode", object.getPracticum().isDraftMode());
		tuple.put("readPracticum", true);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		super.getResponse().setData(tuple);
	}

}
