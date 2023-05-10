
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferDeleteService extends AbstractService<Administrator, Offer> {

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
		Offer offer;
		int id;

		id = super.getRequest().getData("id", int.class);
		offer = this.repository.findOneOfferById(id);
		status = offer != null;
		super.getResponse().setAuthorised(status && super.getRequest().getPrincipal().hasRole(Administrator.class));
	}

	@Override
	public void load() {
		int id;
		Offer object;

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
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "moment", "heading", "summary", "timePeriodStart", "timePeriodEnd", "retailPrice", "link");

		super.getResponse().setData(tuple);
	}
}
