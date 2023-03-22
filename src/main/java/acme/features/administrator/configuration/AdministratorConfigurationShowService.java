
package acme.features.administrator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorConfigurationShowService extends AbstractService<Administrator, Configuration> {

	@Autowired
	protected AdministratorConfigurationRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Configuration object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneConfigurationById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Configuration object) {
		if (object != null) {
			Tuple tuple;

			tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");
			super.getResponse().setData(tuple);
		}

	}
}
