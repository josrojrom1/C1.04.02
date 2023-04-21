
package acme.features.authenticated.moneyExchange;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.MoneyExchange;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedMoneyExchangeController extends AbstractController<Authenticated, MoneyExchange> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedMoneyExchangePerformService exchangeService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("perform", this.exchangeService);
	}

}
