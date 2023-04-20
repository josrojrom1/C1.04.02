
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.entities.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.id = :id")
	public Audit findOneAudit(int id);

	@Query("SELECT a FROM Audit a")
	public Collection<Audit> findAllAudits();

	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :id")
	public Collection<Audit> findManyAuditsFromAuditorId(int id);

	@Query("SELECT a FROM Auditor a where a.id = :id")
	public Auditor findOneAuditorById(int id);

	@Query("SELECT c FROM Course c WHERE c.draftMode = false")
	public Collection<Course> findPublishedCourses();

	@Query("SELECT c FROM Course c WHERE c.id=:id")
	public Course findOneCourseById(int id);

	@Query("SELECT a FROM Audit a WHERE a.code=:code")
	public Audit findOneAuditByCode(String code);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id=:id")
	public Collection<AuditingRecord> findAuditingRecordById(int id);

}
