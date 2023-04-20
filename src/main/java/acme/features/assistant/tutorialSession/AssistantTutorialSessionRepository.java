
package acme.features.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("SELECT ts FROM TutorialSession WHERE ts.id = :id")
	public TutorialSession findOneTutorialSession(int id);

	@Query("SELECT t FROM Tutorial t")
	public Collection<Tutorial> findAllTutorials();
}
