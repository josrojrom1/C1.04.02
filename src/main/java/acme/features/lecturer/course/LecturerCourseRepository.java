
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findAllCoursesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("select a from Audit a")
	Collection<Audit> findAllAudits();

	@Query("select p from Practicum p")
	Collection<Practicum> findAllPracticums();

	@Query("select t from Tutorial t where t.id = :tutorialId")
	Tutorial findOneTutorialById(int tutorialId);

	@Query("select a from Audit a where a.id = :auditId")
	Audit findOneAuditById(int auditId);

	@Query("select p from Practicum p where p.id = :practicumId")
	Practicum findOnePracticumById(int practicumId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

}
