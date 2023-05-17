
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentActivityRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	public Enrolment findEnrolmentById(int id);

	@Query("SELECT e FROM Enrolment e")
	public Collection<Enrolment> findAllEnrolments();

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	public Collection<Activity> findActivitiesByEnrolmentId(int id);

	@Query("SELECT a FROM Activity a WHERE a.id = :id")
	public Activity findActivityById(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.student.id = :id")
	public Collection<Activity> findAllActivitiesFromStudentId(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.isFinalised = true AND e.student.id = :id")
	public Collection<Enrolment> findAllEnrolmentsFinalisedFromStudentId(int id);

	@Query("SELECT s FROM Student s WHERE s.id= :id")
	public Student findStudentById(int id);

	@Query("SELECT a.enrolment FROM Activity a WHERE a.id= :id")
	Enrolment findEnrolmentByActivityId(@Param("id") Integer Id);

}
