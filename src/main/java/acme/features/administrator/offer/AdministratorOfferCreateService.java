
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		Date moment;
		Date moment1;
		Date moment2;
		moment = MomentHelper.getCurrentMoment();
		moment1 = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.DAYS);
		moment2 = MomentHelper.deltaFromCurrentMoment(8, ChronoUnit.DAYS);

		object = new Offer();
		object.setMoment(moment);
		object.setHeading("");
		object.setSummary("");
		object.setLink("");
		object.setTimePeriodStart(moment1);
		object.setTimePeriodEnd(moment2);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "moment", "heading", "summary", "timePeriodStart", "timePeriodEnd", "retailPrice", "link");

	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		//Checks if start moment is at least one day after instance moment
		if (!super.getBuffer().getErrors().hasErrors("moment") && !super.getBuffer().getErrors().hasErrors("timePeriodStart")) {
			final Calendar start = Calendar.getInstance();
			start.setTime(object.getTimePeriodStart());

			final Calendar momento = Calendar.getInstance();
			momento.setTime(object.getMoment());
			momento.add(Calendar.DATE, 1);
			momento.add(Calendar.MINUTE, -1);
			super.state(momento.before(start), "moment", "administrator.offer.form.error.instanceMoment");
		}

		//Checks if start moment is at least a week prior to end moment
		if (!super.getBuffer().getErrors().hasErrors("timePeriodStart") && !super.getBuffer().getErrors().hasErrors("timePeriodEnd")) {
			final Calendar start = Calendar.getInstance();
			final Calendar end = Calendar.getInstance();

			start.setTime(object.getTimePeriodStart());
			end.setTime(object.getTimePeriodEnd());

			final Calendar inicio = Calendar.getInstance();
			inicio.setTime(object.getTimePeriodStart());
			inicio.add(Calendar.DATE, 7);
			inicio.add(Calendar.MINUTE, -1);
			super.state(end.after(inicio), "timePeriodEnd", "administrator.offer.form.error.totalTime");
		}

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "heading", "summary", "timePeriodStart", "timePeriodEnd", "retailPrice", "link");

		super.getResponse().setData(tuple);
	}

}
