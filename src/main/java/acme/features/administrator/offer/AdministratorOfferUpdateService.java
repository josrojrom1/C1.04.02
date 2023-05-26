
package acme.features.administrator.offer;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Offer offer;

		masterId = super.getRequest().getData("id", int.class);
		offer = this.repository.findOneOfferById(masterId);
		status = offer != null;

		super.getResponse().setAuthorised(status && super.getRequest().getPrincipal().hasRole(Administrator.class));
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);
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
		//Checks if start moment is at least one day after instance moment
		if (!super.getBuffer().getErrors().hasErrors("moment") && !super.getBuffer().getErrors().hasErrors("timePeriodStart")) {
			final Calendar start = Calendar.getInstance();
			start.setTime(object.getTimePeriodStart());

			final Calendar momento = Calendar.getInstance();
			momento.setTime(object.getMoment());
			momento.add(Calendar.DATE, 1);

			super.state(momento.before(start), "moment", "administrator.offer.form.error.instanceMoment");
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
