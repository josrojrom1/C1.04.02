
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumListMineService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Practicum> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyPracticumsFromCompanyId(principal.getActiveRoleId());
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		Tuple tuple;

		String Company;
		Company = object.getCompany().getUserAccount().getIdentity().getName();
		Company = Company + " " + object.getCompany().getUserAccount().getIdentity().getSurname();
		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("company", Company);
		super.getResponse().setData(tuple);
	}
}
