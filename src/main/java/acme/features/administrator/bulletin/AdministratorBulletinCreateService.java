
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
import acme.utility.SpamDetector;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinRepository	repository;

	@Autowired
	protected SpamDetector						textValidator;

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
		Bulletin object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Bulletin();
		object.setMoment(moment);
		object.setTitle("");
		object.setMessage("");
		object.setFlag(FlagType.NOT_CRITICAL);
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
		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "title", "administrator.bulletin.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("message")) {
			String validar;
			validar = object.getMessage();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "message", "administrator.bulletin.form.error.spam");
		}
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

		tuple.put("flag", choices.getSelected().getKey());
		tuple.put("flagType", choices);

		super.getResponse().setData(tuple);
	}

}
