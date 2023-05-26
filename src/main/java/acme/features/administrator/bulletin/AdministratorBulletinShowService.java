
package acme.features.administrator.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.entities.FlagType;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinShowService extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinRepository repository;


	//Check method determines if the request has the required input data
	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);

	}

	//Authorise method determines if the request is legal or not
	@Override
	public void authorise() {

		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	//Load method stores in a buffer the retrieved request
	@Override
	public void load() {

		final Bulletin object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBulletinById(id);

		super.getBuffer().setData(object);

	}

	//Unbind method transforms data into tuples that are fed into the response
	@Override
	public void unbind(final Bulletin object) {

		assert object != null;
		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(FlagType.class, object.getFlag());

		tuple = super.unbind(object, "moment", "title", "message", "flag", "link");
		tuple.put("confirmation", false);
		tuple.put("readonly", false);
		tuple.put("flagType", choices);
		super.getResponse().setData(tuple);

	}

}
