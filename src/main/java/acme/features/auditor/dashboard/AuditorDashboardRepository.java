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

package acme.features.auditor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	//@Query("select count(a) from Audit a where a.course.courseType = 'THEORY'")
	//Integer totalNumOfTheoryAudits();

	//@Query("select count(a) from Audit a where a.course.courseType = 'HANDS_ON'")
	//Integer totalNumOfHandsonAudits();

	@Query("select avg(a) from AuditingRecord a where a.audit.id = :id")
	Double averageNumberOfAuditingRecords(int id);

	@Query("select stddev(a) from AuditingRecord a where a.audit.id = :id")
	Double deviationNumberOfAuditingRecords(int id);

	@Query("select min(a) from AuditingRecord a where a.audit.id = :id")
	Double minNumberOfAuditingRecords(int id);

	@Query("select max(a) from AuditingRecord a where a.audit.id = :id")
	Double maxNumberOfAuditingRecords(int id);

	@Query("select avg(TIME_TO_SEC(TIMEDIFF(a.startPeriod, a.endPeriod)) / 3600) from AuditingRecord a")
	Double averagePeriodLenghtOfAuditingRecords();

	@Query("select stddev(TIME_TO_SEC(TIMEDIFF(a.startPeriod, a.endPeriod)) / 3600) from AuditingRecord a")
	Double deviationPeriodLenghtOfAuditingRecords();

	@Query("select min(TIME_TO_SEC(TIMEDIFF(a.startPeriod, a.endPeriod)) / 3600) from AuditingRecord a")
	Double minPeriodLenghtOfAuditingRecords();

	@Query("select max(TIME_TO_SEC(TIMEDIFF(a.startPeriod, a.endPeriod)) / 3600) from AuditingRecord a")
	Double maxPeriodLenghtOfAuditingRecords();

}
