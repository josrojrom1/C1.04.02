
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	public Tutorial findOneTutorial(int id);

	@Query("SELECT t FROM Tutorial t")
	public Collection<Tutorial> findAllTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	public Collection<Tutorial> findManyTutorialsFromAssistantId(int id);

}
