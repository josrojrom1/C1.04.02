
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialListMineService extends AbstractService<Assistant, Tutorial> {

	@Autowired
	protected AssistantTutorialRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Tutorial> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyTutorialsFromAssistantId(principal.getActiveRoleId());
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abst", "goals", "totalTime");

		super.getResponse().setData(tuple);
	}
}
