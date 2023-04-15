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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Lecture a where a.lectureType = 'THEORY'")
	Integer totalNumOfTheoryLectures();

	@Query("select count(a) from Lecture a where a.lectureType = 'HANDS_ON'")
	Integer totalNumOfHandsonLectures();

	//@Query("select avg(select count(a.learningTime) from Lecture a) from Lecture e")
	//Double averageNumberOfLecturesLearningTime();

}
