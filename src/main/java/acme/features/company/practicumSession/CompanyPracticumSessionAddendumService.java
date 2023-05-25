
package acme.features.company.practicumSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.features.company.practicum.CompanyPracticumRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionAddendumService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	protected CompanyPracticumRepository		repository2;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Practicum practicum;
		id = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticum(id);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(Company.class) && super.getRequest().getPrincipal().getActiveRoleId() == practicum.getCompany().getId() && !practicum.isDraftMode() && !practicum.isHasAddendum();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		Practicum practicum;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		practicum = this.repository.findOnePracticum(super.getRequest().getData("masterId", int.class));
		object = new PracticumSession();
		object.setPracticum(practicum);
		object.setTimePeriodStart(moment);
		object.setTimePeriodEnd(moment);
		object.setAddendum(true);
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
		//Validación de la fechas
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
		Practicum practicum;
		practicum = this.repository.findOnePracticum(super.getRequest().getData("masterId", int.class));
		final Long time = TimeUnit.MILLISECONDS.toSeconds(object.getTimePeriodEnd().getTime() - object.getTimePeriodStart().getTime());
		final double hours = time.doubleValue() / 3600;
		practicum.setTotalTime(practicum.getTotalTime() + hours);
		practicum.setHasAddendum(true);
		this.repository2.save(practicum);
		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Collection<Practicum> practicums;
		SelectChoices choices;

		practicums = this.repository.findAllPracticums();
		choices = SelectChoices.from(practicums, "code", object.getPracticum());
		Tuple tuple;
		tuple = super.unbind(object, "title", "abst", "timePeriodStart", "timePeriodEnd", "link");
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("hasAddendum", object.getPracticum().isHasAddendum());
		tuple.put("draftMode", object.getPracticum().isDraftMode());
		tuple.put("readPracticum", true);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		super.getResponse().setData(tuple);
	}
}
