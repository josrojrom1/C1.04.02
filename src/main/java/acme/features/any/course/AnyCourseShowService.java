
package acme.features.any.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.features.authenticated.moneyExchange.AuthenticatedMoneyExchangePerformService;
import acme.forms.MoneyExchange;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {

	@Autowired
	protected AnyCourseRepository						repository;

	@Autowired
	protected AuthenticatedMoneyExchangePerformService	moneyExchangeService;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Course course;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		status = course != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Course object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "abst", "retailPrice", "link");
		tuple.put("principal", super.getRequest().getPrincipal().isAuthenticated());
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
