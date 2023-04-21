
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.id = :id")
	public PracticumSession findOnePracticumSession(int id);

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.company.id = :id")
	public Collection<PracticumSession> findManyPracticumSessionByCompany(int id);

	@Query("SELECT p FROM Practicum p")
	public Collection<Practicum> findAllPracticums();

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	public Practicum findOnePracticum(int id);

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.id = :id")
	public Collection<PracticumSession> findManyPracticumSessionByPracticum(int id);
}
