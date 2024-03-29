
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.CourseOfLecture;
import acme.entities.Lecture;
import acme.entities.LectureType;
import acme.entities.Practicum;
import acme.entities.Tutorial;
import acme.entities.configuration.Configuration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.lecturer.id = :lecturerId")
	Collection<Course> findAllCoursesByLecturerId(int lecturerId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select t from Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("select a from Audit a")
	Collection<Audit> findAllAudits();

	@Query("select p from Practicum p")
	Collection<Practicum> findAllPracticums();

	@Query("select t from Tutorial t where t.id = :tutorialId")
	Tutorial findOneTutorialById(int tutorialId);

	@Query("select a from Audit a where a.id = :auditId")
	Audit findOneAuditById(int auditId);

	@Query("select p from Practicum p where p.id = :practicumId")
	Practicum findOnePracticumById(int practicumId);

	@Query("select c from Course c where c.code = :code")
	Course findOneCourseByCode(String code);

	@Query("select col.lecture.draftMode from CourseOfLecture col where col.course.id = :id")
	Collection<Boolean> findAllLecturesDraftModeByCourse(int id);

	@Query("select col.lecture.lectureType from CourseOfLecture col where col.course.id = :id")
	Collection<LectureType> findAllLecturesLessonTypeByCourse(int id);

	@Query("select col.lecture from CourseOfLecture col where col.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

	@Query("select col from CourseOfLecture col where col.course = :course")
	Collection<CourseOfLecture> findCourseOfLecturesByCourse(Course course);

	@Query("select c.acceptedCurrencies from Configuration c")
	String findConfigurationAcceptedCurrencies();

	@Query("select c.code from Course c")
	Collection<String> findAllCodesFromCourses();

	@Query("SELECT c FROM Configuration c")
	Configuration findConfiguration();

}
