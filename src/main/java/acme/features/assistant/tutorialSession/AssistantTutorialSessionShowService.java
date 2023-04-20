
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
public class AssistantTutorialSessionShowService extends AbstractService<Assistant, TutorialSession> {

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
		super.getResponse().setAuthorised(true);
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
		tuple.put("lessonType", lessonChoices.getSelected().getKey());
		tuple.put("lessonChoices", lessonChoices);
		super.getResponse().setData(tuple);
	}
}
