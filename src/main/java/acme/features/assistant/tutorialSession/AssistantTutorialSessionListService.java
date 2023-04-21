
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
		status = super.getRequest().getPrincipal().hasRole(Assistant.class) && tutorial != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TutorialSession> objects;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyTutorialSessionByTutorial(id);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tutorial tutorial;
		Tuple tuple;
		int id;
		boolean showCreate;
		tuple = super.unbind(object, "title");

		id = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorial(id);
		showCreate = tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		tuple.put("tutorial", tutorial.getCode());
		super.getResponse().setData(tuple);
		super.getResponse().setGlobal("masterId", id);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
