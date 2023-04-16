
package acme.features.authenticated.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferCreateService extends AbstractService<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		Date moment;
		Date start;
		final Date end;

		moment = MomentHelper.getCurrentMoment();
		start = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.DAYS);
		end = MomentHelper.deltaFromCurrentMoment(8, ChronoUnit.DAYS);

		object = new Offer();
		object.setMoment(moment);
		object.setHeading("");
		object.setSummary("");
		object.setTimePeriodStart(start);
		object.setTimePeriodEnd(end);
		object.setLink("");

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

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "heading", "summary", "timePeriodStart", "timePeriodEnd", "retailPrice", "link");
		tuple.put("confirmation", false);
		tuple.put("readonly", false);

		super.getResponse().setData(tuple);
	}

}
