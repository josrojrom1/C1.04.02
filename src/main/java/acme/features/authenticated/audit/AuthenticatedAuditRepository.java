
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findOneAuditById(int id);

	@Query("select a from Audit a")
	Collection<Audit> findAllAudits();

	@Query("SELECT a from Audit a where a.course.id = :id")
	Collection<Audit> findCourseAudits(int id);

	@Query("SELECT c from Course c where c.id = :id")
	Course findCourseById(int id);

}
