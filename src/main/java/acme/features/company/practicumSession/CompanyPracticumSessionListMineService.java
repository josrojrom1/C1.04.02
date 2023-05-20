
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListMineService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum Practicum;
		int id;

		id = super.getRequest().getData("id", int.class);
		Practicum = this.repository.findOnePracticum(id);
		status = super.getRequest().getPrincipal().hasRole(Company.class) && Practicum != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyPracticumSessionByCompany(principal.getActiveRoleId());
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Tuple tuple;
		int id;
		id = super.getRequest().getData("id", int.class);
		tuple = super.unbind(object, "title");
		tuple.put("id", id);
		tuple.put("practicum", object.getPracticum().getCode());
		super.getResponse().setData(tuple);
	}
}
