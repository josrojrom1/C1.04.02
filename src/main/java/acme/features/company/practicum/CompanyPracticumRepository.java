
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	public Practicum findOnePracticum(int id);

	@Query("SELECT p FROM Practicum p")
	public Collection<Practicum> findAllPracticums();

	@Query("SELECT p FROM Practicum p WHERE p.company.id = :id")
	public Collection<Practicum> findManyPracticumsFromCompanyId(int id);

	@Query("SELECT c FROM Company c where c.id = :id")
	public Company findOneCompanyById(int id);

	@Query("SELECT c FROM Course c WHERE c.draftMode = false")
	public Collection<Course> findAllPublishedCourses();

	@Query("SELECT c FROM Course c WHERE c.id=:id")
	public Course findOneCourseById(int id);

	@Query("SELECT p FROM Practicum p WHERE p.code=:code")
	public Practicum findOnePracticumByCode(String code);

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.id=:id")
	public Collection<PracticumSession> findPracticumSessionsById(int id);
}
