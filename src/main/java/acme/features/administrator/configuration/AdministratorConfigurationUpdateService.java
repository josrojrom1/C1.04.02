
package acme.features.administrator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorConfigurationUpdateService extends AbstractService<Administrator, Configuration> {

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
		Configuration object;

		object = this.repository.findConfiguration();
		object.setSystemCurrency("");
		object.setAcceptedCurrencies("");
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Configuration object) {
		assert object != null;

		super.bind(object, "systemCurrency", "acceptedCurrencies", "spamThreshold", "spamTerms");

	}

	@Override
	public void validate(final Configuration object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("systemCurrency"))
			super.state(object.getAcceptedCurrencies().contains(object.getSystemCurrency()), "systemCurrency", "administrator.configuration.form.error.currency");
		if (!super.getBuffer().getErrors().hasErrors("spamThreshold"))
			super.state(object.getSpamThreshold() >= 0 && object.getSpamThreshold() <= 1, "spamThreshold", "administrator.configuration.form.error.threshold");

	}

	@Override
	public void perform(final Configuration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Configuration object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies", "spamThreshold", "spamTerms");
		super.getResponse().setData(tuple);
	}
}
