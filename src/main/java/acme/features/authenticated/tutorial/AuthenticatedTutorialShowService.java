
package acme.features.authenticated.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	@Autowired
	protected AuthenticatedTutorialRepository repository;


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
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorial(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Tuple tuple;
		final Assistant assistant = object.getAssistant();

		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");
		tuple.put("assistant", assistant.getUserAccount().getUsername());
		super.getResponse().setData(tuple);
	}
}
