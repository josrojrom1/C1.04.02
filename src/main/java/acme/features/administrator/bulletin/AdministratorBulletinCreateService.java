
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.entities.FlagType;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Bulletin object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Bulletin();
		object.setTitle("");
		object.setMoment(moment);
		object.setMessage("");
		object.setFlag(FlagType.CRITICAL);
		object.setLink("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;

		super.bind(object, "moment", "title", "message", "flag", "link");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(FlagType.class, object.getFlag());

		tuple = super.unbind(object, "moment", "title", "message", "flag", "link");
		tuple.put("confirmation", false);
		tuple.put("readonly", false);
		tuple.put("flagType", choices);

		super.getResponse().setData(tuple);
	}

}
