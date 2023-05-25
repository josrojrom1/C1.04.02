
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Activity;
import acme.entities.Course;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.student.id = :studentId")
	Collection<Enrolment> findEnrolmentsByStudentId(int studentId);

	@Query("SELECT e.course FROM Enrolment e WHERE e.id = :id")
	Course findCourseByEnrolmnetId(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT s FROM Student s WHERE s.id = :id")
	Student findStudentById(int id);

	@Query("SELECT c FROM Course c WHERE c.draftMode=false")
	public Collection<Course> findAllPublishedCourses();

	@Query("SELECT e FROM Enrolment e WHERE e.code= :code")
	public Enrolment findEnrolmentByCode(String code);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findAllActivitiesFromEnrolmentId(int id);

	@Query("select sum(TIME_TO_SEC(TIMEDIFF(a.endTimePeriod, a.startTimePeriod)) / 3600) from Activity a where a.enrolment.id=:id")
	Double findWorkTimeByEnrolmentId(int id);
}
