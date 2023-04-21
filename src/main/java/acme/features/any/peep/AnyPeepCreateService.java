
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

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


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
		Principal principal;
		principal = super.getRequest().getPrincipal();

		tuple = super.unbind(object, "moment", "title", "nick", "message", "email", "link");

		if (principal.hasRole(Anonymous.class))
			tuple.put("readonly1", true);
		else
			tuple.put("readonly1", false);

		super.getResponse().setData(tuple);
	}

}
