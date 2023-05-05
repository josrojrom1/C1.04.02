
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.CourseOfLecture;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l")
	Collection<Lecture> findAllLectures();

	@Query("select l from Lecture l where l.lecturer.id = :lecturerId")
	Collection<Lecture> findAllLecturesByLecturerId(int lecturerId);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findLecturerById(int id);

	@Query("select col from CourseOfLecture col where col.lecture = :lecture")
	Collection<CourseOfLecture> findCourseOfLecturesByLecture(Lecture lecture);

	@Query("select col.lecture from CourseOfLecture col where col.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

}
