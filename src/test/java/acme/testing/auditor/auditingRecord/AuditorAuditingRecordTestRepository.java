
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditingRecordTestRepository extends AbstractRepository {

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.auditor.userAccount.username = :username")
	public Collection<AuditingRecord> findManyAuditingRecordByAuditor(String username);

	@Query("SELECT a FROM Audit a WHERE a.auditor.userAccount.username = :username")
	public Collection<Audit> findManyAuditsByAuditor(String username);
}
