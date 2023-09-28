
package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Anonymous;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.utility.SpamDetector;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository	repository;

	@Autowired
	protected SpamDetector		textValidator;


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
		Peep object;
		Date moment;
		Principal principal;
		principal = super.getRequest().getPrincipal();

		moment = MomentHelper.getCurrentMoment();

		object = new Peep();
		object.setTitle("");
		object.setMoment(moment);
		object.setMessage("");

		if (principal.hasRole(Anonymous.class))
			object.setNick("");
		else
			object.setNick(principal.getUsername());

		object.setEmail("");
		object.setLink("");

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "moment", "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "title", "any.peep.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("nick")) {
			String validar;
			validar = object.getNick();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "nick", "any.peep.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("message")) {
			String validar;
			validar = object.getMessage();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "message", "any.peep.form.error.spam");
		}
	}
	@Override
	public void perform(final Peep object) {
		assert object != null;

		Date moment;

		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "nick", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}
