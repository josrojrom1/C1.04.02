
package acme.features.student.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.CourseOfLecture;
import acme.entities.Lecture;
import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentCourseRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT c FROM Course c WHERE c.draftMode= false")
	Collection<Course> findAllCoursesPublished();

	@Query("SELECT col FROM CourseOfLecture col WHERE col.course.id = :id")
	Collection<CourseOfLecture> findCourseOfLecturesByCourseId(int id);

	@Query("SELECT col.lecture FROM CourseOfLecture col WHERE col.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("SELECT c FROM Configuration c")
	Configuration findConfiguration();

}
