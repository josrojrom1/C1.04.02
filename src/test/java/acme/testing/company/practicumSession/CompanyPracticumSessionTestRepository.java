
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.company.userAccount.username = :username")
	public Collection<PracticumSession> findManyPracticumSessionByCompany(String username);

	@Query("SELECT p FROM Practicum p WHERE p.company.userAccount.username = :username")
	public Collection<Practicum> findManyPracticumsByCompany(String username);
}
