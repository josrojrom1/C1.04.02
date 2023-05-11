
package acme.features.administrator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.forms.MoneyExchange;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferShowService extends AbstractService<Administrator, Offer> {

	@Autowired
	protected AdministratorOfferRepository				repository;

	@Autowired
	protected AuthenticatedMoneyExchangePerformService	moneyExchangeService;


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

		final Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);

	}

	//Unbind method transforms data into tuples that are fed into the response
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
