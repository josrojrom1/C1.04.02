
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.TutorialSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListMineService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;


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
		Collection<TutorialSession> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyTutorialSessionByAssistant(principal.getActiveRoleId());
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title");
		tuple.put("tutorial", object.getTutorial().getCode());
		tuple.put("showCreate", false);
		super.getResponse().setData(tuple);
	}
}
