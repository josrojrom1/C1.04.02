
package acme.features.administrator.configuration;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorConfigurationListService extends AbstractService<Administrator, Configuration> {

	@Autowired
	protected AdministratorConfigurationRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Configuration> objects;
		objects = this.repository.findAllConfiguration();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Configuration object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().setData(tuple);

	}
}
