
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
import acme.utility.SpamDetector;

@Service
public class CompanyPracticumSessionAddendumService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	protected CompanyPracticumRepository		repository2;

	@Autowired
	protected SpamDetector						textValidator;


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
		Date moment1;
		Date moment2;
		moment1 = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
		moment2 = MomentHelper.deltaFromCurrentMoment(14, ChronoUnit.DAYS);
		practicum = this.repository.findOnePracticum(super.getRequest().getData("masterId", int.class));
		object = new PracticumSession();
		object.setPracticum(practicum);
		object.setTimePeriodStart(moment1);
		object.setTimePeriodEnd(moment2);
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
		if (!super.getBuffer().getErrors().hasErrors("timePeriodStart"))
			try {
				final Date moment = MomentHelper.getCurrentMoment();
				final Duration duration = MomentHelper.computeDuration(moment, object.getTimePeriodStart());
				final Duration d1 = Duration.ofDays(7);
				d1.minus(1, ChronoUnit.MINUTES);
				super.state(duration.compareTo(d1) >= 0, "timePeriodStart", "company.practicumSession.form.error.timePeriodStart");
			} catch (final NullPointerException e) {
				super.state(true, "timePeriodStart", "company.practicumSession.form.error.nullTime");
			}

		if (!super.getBuffer().getErrors().hasErrors("timePeriodEnd"))
			try {
				super.state(object.getTimePeriodStart().before(object.getTimePeriodEnd()), "timePeriodEnd", "company.practicumSession.form.error.timePeriodEnd");
			} catch (final NullPointerException e) {
				super.state(true, "timePeriodEnd", "company.practicumSession.form.error.nullTime");
			}

		if (!super.getBuffer().getErrors().hasErrors("periodFinish"))
			try {
				final Duration duration2 = MomentHelper.computeDuration(object.getTimePeriodStart(), object.getTimePeriodEnd());
				final Duration d2 = Duration.ofDays(7);
				d2.minus(1, ChronoUnit.MINUTES);
				super.state(duration2.compareTo(d2) >= 0, "*", "company.practicumSession.form.error.duration");
			} catch (final AssertionError e) {
				super.state(true, "*", "company.practicumSession.form.error.duration");
			}

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "company.practicum.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("abst")) {
			String validar;
			validar = object.getAbst();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "company.practicum.form.error.spam");
		}

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		Practicum practicum;
		practicum = this.repository.findOnePracticum(super.getRequest().getData("masterId", int.class));
		final Long time = TimeUnit.MILLISECONDS.toSeconds(object.getTimePeriodEnd().getTime() - object.getTimePeriodStart().getTime());
		final double hour_factor = 3600.0;
		final double hours = time.doubleValue() / hour_factor;
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
