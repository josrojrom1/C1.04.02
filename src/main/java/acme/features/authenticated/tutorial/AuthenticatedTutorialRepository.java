
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t")
	public Collection<Tutorial> findAllTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	public Tutorial findOneTutorial(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.draftMode=false")
	public Collection<Tutorial> findAllPublishedTutorials();
}
