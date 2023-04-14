
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findAllCoursesByLecturerId(int lecturerId);

}
