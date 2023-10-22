
package acme.testing.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Audit;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.auditor.userAccount.username = :username")
	public Collection<Audit> findManyAuditsFromUsername(String username);

}
