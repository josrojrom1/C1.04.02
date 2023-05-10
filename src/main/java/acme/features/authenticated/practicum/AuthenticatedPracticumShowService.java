
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


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
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticum(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;
		Tuple tuple;

		String company;
		String course;
		company = object.getCompany().getUserAccount().getIdentity().getName();
		company = company + " " + object.getCompany().getUserAccount().getIdentity().getSurname();
		course = object.getCourse().getTitle();
		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("company", company);
		tuple.put("course", course);
		super.getResponse().setData(tuple);
	}
}
