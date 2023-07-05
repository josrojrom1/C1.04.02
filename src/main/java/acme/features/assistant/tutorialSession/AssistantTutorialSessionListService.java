
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Tutorial tutorial;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorial(id);
		status = tutorial != null && super.getRequest().getPrincipal().getActiveRoleId() == tutorial.getAssistant().getId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TutorialSession> objects;
		int id;
		Tutorial tutorial;
		boolean showCreate;
		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyTutorialSessionByTutorial(id);

		super.getResponse().setGlobal("masterId", id);
		tutorial = this.repository.findOneTutorial(id);
		showCreate = tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(Assistant.class);
		super.getResponse().setGlobal("showCreate", showCreate);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;
		Tuple tuple;
		int id;
		boolean showCreate;

		id = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorial(id);
		showCreate = tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(Assistant.class);
		super.getResponse().setGlobal("masterId", id);
		super.getResponse().setGlobal("showCreate", showCreate);
		tuple = super.unbind(object, "title");
		tuple.put("tutorial", tutorial.getCode());
		tuple.put("showCreate", showCreate);
		super.getResponse().setData(tuple);
	}
}
