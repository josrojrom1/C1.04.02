
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("SELECT t FROM Practicum t")
	public Collection<Practicum> findAllPracticums();

	@Query("SELECT t FROM Practicum t WHERE t.id = :id")
	public Practicum findOnePracticum(int id);

	@Query("SELECT t FROM Practicum t WHERE t.draftMode=false")
	public Collection<Practicum> findAllPublishedPracticums();
}
