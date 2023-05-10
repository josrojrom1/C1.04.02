
package acme.features.lecturer.courseOfLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.CourseOfLecture;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseOfLectureRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select col from CourseOfLecture col where col.id = :id")
	CourseOfLecture findCourseOfLectureById(int id);

	@Query("select col from CourseOfLecture col where col.course.id = :courseId ")
	Collection<CourseOfLecture> findCourseOfLectureByCourseId(int courseId);

	@Query("select l from Lecture l where l.id = :lectureId")
	Lecture findLectureById(Integer lectureId);

	@Query("select col.lecture from CourseOfLecture col where col.course.id = :id")
	Collection<Lecture> findAllLecturesFromCourseOfLectureByCourseId(int id);

	@Query("select l from Lecture l where l.draftMode = false AND l.lecturer.id = :lecturerId")
	Collection<Lecture> findPublishedLecturesFromLecturer(int lecturerId);

}
