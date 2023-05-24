
package acme.testing.assistant.session;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionTestRepository extends AbstractRepository {

	@Query("SELECT ts FROM TutorialSession ts WHERE ts.tutorial.assistant.userAccount.username = :username")
	public Collection<TutorialSession> findManyTutorialSessionByAssistant(String username);

}
