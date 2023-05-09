/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Lecture a where a.lectureType = 'THEORY'")
	Integer totalNumOfTheoryLectures();

	@Query("select count(a) from Lecture a where a.lectureType = 'HANDS_ON'")
	Integer totalNumOfHandsonLectures();

	@Query("select avg(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double averageLearningTimeOfLectures(int id);

	@Query("select stddev(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double deviationLearningTimeOfLectures(int id);

	@Query("select min(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double minimumLearningTimeOfLectures(int id);

	@Query("select max(l.learningTime) from Lecture l where l.lecturer.id = :id")
	Double maximumLearningTimeOfLectures(int id);

	@Query("SELECT sum(l.learningTime) FROM Lecture l JOIN CourseOfLecture col ON l = col.lecture JOIN Course c ON col.course = c WHERE c.lecturer.id = :id group by c")
	List<Double> learningTimeOfCourses(int id);

}
