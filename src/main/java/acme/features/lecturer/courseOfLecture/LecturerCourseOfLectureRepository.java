
package acme.features.lecturer.courseOfLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseOfLectureRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select distinct(l) from Lecture l left join CourseOfLecture col on l.id = col.lecture.id where col.course.id != :courseId AND l.lecturer.id = :lecturerId")
	Collection<Lecture> findAvaibleLecturesForCourse(int courseId, int lecturerId);

}
