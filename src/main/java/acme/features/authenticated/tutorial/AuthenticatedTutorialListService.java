
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedTutorialListService extends AbstractService<Authenticated, Tutorial> {

	@Autowired
	protected AuthenticatedTutorialRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Assistant.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Collection<Tutorial> objects;
		objects = this.repository.findAllPublishedTutorials();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Tuple tuple;

		String assistant;
		assistant = object.getAssistant().getUserAccount().getIdentity().getName();
		assistant = assistant + " " + object.getAssistant().getUserAccount().getIdentity().getSurname();

		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("assistant", assistant);
		super.getResponse().setData(tuple);
	}
}
