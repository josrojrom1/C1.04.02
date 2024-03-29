
package acme.testing.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumTestRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.company.userAccount.username = :username")
	public Collection<Practicum> findManyPracticumsFromUsername(String username);
}
