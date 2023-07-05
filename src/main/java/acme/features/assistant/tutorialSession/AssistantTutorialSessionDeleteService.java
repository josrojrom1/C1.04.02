
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.LessonType;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		TutorialSession session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOneTutorialSession(id);
		status = session != null && session.getTutorial().getAssistant().getId() == super.getRequest().getPrincipal().getActiveRoleId() && session.getTutorial().isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSession(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "title", "abst", "sessionType", "periodStart", "periodFinish", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;
	}

	@Override
	public void perform(final TutorialSession object) {
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Collection<Tutorial> tutorials;
		SelectChoices choices;
		SelectChoices lessonChoices;

		tutorials = this.repository.findAllTutorials();
		choices = SelectChoices.from(tutorials, "code", object.getTutorial());
		lessonChoices = SelectChoices.from(LessonType.class, object.getSessionType());
		Tuple tuple;
		tuple = super.unbind(object, "title", "abst", "periodStart", "periodFinish", "link");
		tuple.put("tutorial", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		tuple.put("lessonType", lessonChoices.getSelected().getKey());
		tuple.put("lessonChoices", lessonChoices);
		tuple.put("readTutorial", true);
		super.getResponse().setData(tuple);
	}
}
