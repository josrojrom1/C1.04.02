
package acme.features.any.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository {

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.draftMode = false")
	Collection<Course> findAllCoursesNoDraftMode();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT c FROM Configuration c")
	Configuration findConfiguration();

	@Query("select col.lecture from CourseOfLecture col where col.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

}
