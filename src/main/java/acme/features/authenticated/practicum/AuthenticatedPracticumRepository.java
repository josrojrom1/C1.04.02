
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p")
	public Collection<Practicum> findAllPracticums();

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	public Practicum findOnePracticum(int id);

	@Query("SELECT p FROM Practicum p WHERE p.draftMode=false")
	public Collection<Practicum> findAllPublishedPracticums();

	@Query("SELECT c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT p from Practicum p where p.course.id = :id")
	Collection<Practicum> findCoursePracticums(int id);
}
