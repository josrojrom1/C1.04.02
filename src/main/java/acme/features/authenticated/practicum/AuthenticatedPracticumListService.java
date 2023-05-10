
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Collection<Practicum> objects;
		objects = this.repository.findAllPublishedPracticums();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		Tuple tuple;

		String company;
		company = object.getCompany().getUserAccount().getIdentity().getName();
		company = company + " " + object.getCompany().getUserAccount().getIdentity().getSurname();

		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("company", company);
		super.getResponse().setData(tuple);
	}
}
