
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.id = :id")
	public AuditingRecord findOneAuditingRecord(int id);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :id")
	public Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);

	@Query("SELECT a FROM Audit a")
	public Collection<Audit> findAllAudits();

	@Query("SELECT a FROM Audit a WHERE a.id = :id")
	public Audit findAuditById(int id);

}
