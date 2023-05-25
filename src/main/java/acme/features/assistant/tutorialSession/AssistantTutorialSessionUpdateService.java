
package acme.features.assistant.tutorialSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.LessonType;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;
import acme.utility.SpamDetector;

@Service
public class AssistantTutorialSessionUpdateService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	AssistantTutorialSessionRepository	repository;

	@Autowired
	protected SpamDetector				textValidator;


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
		status = session != null && session.getTutorial().isDraftMode() && super.getRequest().getPrincipal().hasRole(Assistant.class) && session.getTutorial().getAssistant().getId() == super.getRequest().getPrincipal().getActiveRoleId();

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
		//validación de la fechas
		if (!super.getBuffer().getErrors().hasErrors("periodStart")) {
			final Date moment = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.DAYS);
			super.state(object.getPeriodStart().after(moment), "periodStart", "assistant.tutorialSession.form.error.periodStart");
		}
		if (!super.getBuffer().getErrors().hasErrors("periodFinish"))
			super.state(object.getPeriodStart().before(object.getPeriodFinish()), "periodFinish", "assistant.tutorialSession.form.error.periodFinish");

		if (!super.getBuffer().getErrors().hasErrors("periodFinish")) {
			final Duration duration = MomentHelper.computeDuration(object.getPeriodStart(), object.getPeriodFinish());
			final Duration d1 = Duration.ofHours(1);
			final Duration d2 = Duration.ofHours(5);
			super.state(d1.compareTo(duration) <= 0 && d2.compareTo(duration) >= 0, "*", "assistant.tutorialSession.form.error.duration");
		}
		if (!super.getBuffer().getErrors().hasErrors("title")) {
			String validar;
			validar = object.getTitle();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "assistant.tutorialSession.form.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("abst")) {
			String validar;
			validar = object.getAbst();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "*", "assistant.tutorialSession.form.error.spam");
		}

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.save(object);
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
		tuple = super.unbind(object, "title", "abst", "sessionType", "periodStart", "periodFinish", "link");
		tuple.put("tutorial", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		tuple.put("lessonChoices", lessonChoices);
		tuple.put("readTutorial", true);
		tuple.put("masterId", super.getRequest().getData("id", int.class));
		super.getResponse().setData(tuple);
	}

}
