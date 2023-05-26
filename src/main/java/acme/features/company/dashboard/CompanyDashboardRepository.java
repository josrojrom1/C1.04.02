///*
// * AdministratorDashboardRepository.java
// *
// * Copyright (C) 2012-2023 Rafael Corchuelo.
// *
// * In keeping with the traditional purpose of furthering education and research, it is
// * the policy of the copyright owner to permit non-commercial use and redistribution of
// * this software. It has been tested carefully, but it is not guaranteed for any particular
// * purposes. The copyright owner does not offer any warranties or representations, nor do
// * they accept any liabilities with respect to them.
// */
//
//package acme.features.company.dashboard;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import acme.entities.Practicum;
//import acme.entities.PracticumSession;
//import acme.framework.repositories.AbstractRepository;
//
//@Repository
//public interface CompanyDashboardRepository extends AbstractRepository {
//
//	@Query("select p from Practicum p where p.companyId = :id")
//	List<Practicum> findPracticumsByCompany(int id);
//
//	@Query("select p from PracticumSession p where p.practicum = :practicum")
//	List<PracticumSession> findPracticumSessionsByPracticum(Practicum p);
//
//}
