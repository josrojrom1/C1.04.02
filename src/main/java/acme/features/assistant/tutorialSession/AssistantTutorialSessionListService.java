
package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.TutorialSession;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, TutorialSession> {

	@Autowired
	protected AssistantTutorialSessionRepository repository;

}
