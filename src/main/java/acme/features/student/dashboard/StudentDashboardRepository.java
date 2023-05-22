
package acme.features.student.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Activity a where a.activityType = 'THEORY'")
	Integer totalNumOfTheoryActivities();

	@Query("select count(a) from Activity a where a.activityType = 'HANDS_ON'")
	Integer totalNumOfHandsonActivities();

	@Query("select avg(TIME_TO_SEC(TIMEDIFF(a.endTimePeriod, a.startTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double averageTimeOfActivityWorkbook(int id);

	@Query("select stddev(TIME_TO_SEC(TIMEDIFF(a.endTimePeriod, a.startTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double deviationTimeOfActivityWorkbook(int id);

	@Query("select min(TIME_TO_SEC(TIMEDIFF(a.endTimePeriod, a.startTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double minimumTimeOfActivityWorkbook(int id);

	@Query("select max(TIME_TO_SEC(TIMEDIFF(a.endTimePeriod, a.startTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double maximumTimeOfActivityWorkbook(int id);

	@Query("select avg(select sum(l.learningTime) from CourseOfLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id= :id")
	Double averageLearningTimeAOfCoursesEnrolled(int id);

	@Query("select stddev((select sum(l.learningTime) from CourseOfLecture cl join cl.lecture l where cl.course=c)) from Enrolment e join e.course c where e.student.id= :id")
	Double deviationLearningTimeAOfCoursesEnrolled(int id);

	@Query("select min(select sum(l.learningTime) from CourseOfLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id= :id")
	Double minimumLearningTimeAOfCoursesEnrolled(int id);

	@Query("select max(select sum(l.learningTime) from CourseOfLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id= :id")
	Double maximumLearningTimeAOfCoursesEnrolled(int id);
}
