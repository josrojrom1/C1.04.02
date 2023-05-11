
package acme.features.authenticated.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.forms.MoneyExchange;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferShowService extends AbstractService<Authenticated, Offer> {

	@Autowired
	protected AuthenticatedOfferRepository				repository;

	@Autowired
	protected AuthenticatedMoneyExchangePerformService	moneyExchangeService;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);

	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "moment", "heading", "summary", "timePeriodStart", "timePeriodEnd", "retailPrice", "link");
		final String systemCurrency = this.repository.findConfiguration().getSystemCurrency();
		if (!systemCurrency.equals(object.getRetailPrice().getCurrency())) {
			MoneyExchange moneyExchange;
			moneyExchange = this.moneyExchangeService.computeMoneyExchange(object.getRetailPrice(), systemCurrency);
			tuple.put("moneyExchange", moneyExchange.getTarget());
			tuple.put("showExchange", true);
		} else
			tuple.put("showExchange", false);

		super.getResponse().setData(tuple);
	}
}
