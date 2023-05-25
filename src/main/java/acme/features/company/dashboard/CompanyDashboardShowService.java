///*
// * AdministratorDashboardShowService.java
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
//import java.beans.Transient;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import acme.entities.Practicum;
//import acme.entities.PracticumSession;
//import acme.forms.CompanyDashboard;
//import acme.forms.LecturerDashboard;
//import acme.framework.components.accounts.Principal;
//import acme.framework.components.models.Tuple;
//import acme.framework.helpers.MomentHelper;
//import acme.framework.services.AbstractService;
//import acme.roles.Lecturer;
//
//@Service
//public class CompanyDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected CompanyDashboardRepository repository;
//
//	// AbstractService interface ----------------------------------------------
//
//
//	@Override
//	public void check() {
//		super.getResponse().setChecked(true);
//	}
//
//	@Override
//	public void authorise() {
//		super.getResponse().setAuthorised(true);
//	}
//
//	@Override
//	public void load() {
//		final CompanyDashboard dashboard;
//		Principal principal;
//		int id;
//		principal = super.getRequest().getPrincipal();
//		id = principal.getActiveRoleId();
//
//		//  Me traigo los Practicums
//		final List<Practicum> companyPracticums;
//		companyPracticums = this.repository.findPracticumsByCompany(id);
//
//		//Me traigo las sessiones
//		final List<PracticumSession> companyPracticumSessions;
//		for (final Practicum p : companyPracticums) {
//			final List<PracticumSession> practicumSessions = this.repository.findPracticumSessionsByPracticum(p);
//			companyPracticumSessions.addAll(practicumSessions);
//		}
//
//		//Clasificación de sesiones por mes del año pasado
//		//Definimos el año pasado
//		Date moment;
//		moment = MomentHelper.getCurrentMoment();
//		final Calendar fechaActual = Calendar.getInstance();
//		fechaActual.setTime(moment);
//		fechaActual.add(Calendar.YEAR, -1);
//		final int anioPasado = fechaActual.get(Calendar.YEAR);
//
//		//Definimos nuestro contendor para las sesiones
//		final Map<String, List<Practicum>> practicasPorMes = new HashMap<>();
//
//		// Itera sobre las sesiones
//		for (final Practicum practica : companyPracticums) {
//			//Cogemos su año y mes
//			final Calendar fecha = Calendar.getInstance();
//			fecha.setTime(practica.getPublishTime());
//			final int anioSession = fecha.get(Calendar.YEAR);
//			final int mesSession = fecha.get(Calendar.MONTH);
//
//			if (anioSession == anioPasado) //Si son del año pasado
//				//Las claves son "Mes-TipoDeCourse" al que pertenece la session
//				if (practicasPorMes.containsKey(Integer.toString(mesSession) + "-" + practica.getCourse().getCourseType().toString()))
//					practicasPorMes.get(mesSession).add(practica);
//				else {//Sino creamos la entrada
//					final List<Practicum> listaMes;
//					listaMes.add(practica);
//					practicasPorMes.put(Integer.toString(mesSession) + "-" + practica.getCourse().getCourseType().toString(), listaMes);
//				}
//		}
//
//		//Prácticas por mes de cursos de teoría
//		//-------------------------------------
//		final int totalNumOfTheoryPracticaJan;
//		if (practicasPorMes.containsKey(Integer.toString(1) + "-THEORY"))
//			totalNumOfTheoryPracticaJan = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaJan = 0;
//
//		final int totalNumOfTheoryPracticaFeb;
//		if (practicasPorMes.containsKey(Integer.toString(2) + "-THEORY"))
//			totalNumOfTheoryPracticaFeb = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaFeb = 0;
//
//		final int totalNumOfTheoryPracticaMar;
//		if (practicasPorMes.containsKey(Integer.toString(3) + "-THEORY"))
//			totalNumOfTheoryPracticaMar = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaMar = 0;
//
//		final int totalNumOfTheoryPracticaApr;
//		if (practicasPorMes.containsKey(Integer.toString(4) + "-THEORY"))
//			totalNumOfTheoryPracticaApr = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaApr = 0;
//
//		final int totalNumOfTheoryPracticaMay;
//		if (practicasPorMes.containsKey(Integer.toString(5) + "-THEORY"))
//			totalNumOfTheoryPracticaMay = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaMay = 0;
//
//		final int totalNumOfTheoryPracticaJun;
//		if (practicasPorMes.containsKey(Integer.toString(6) + "-THEORY"))
//			totalNumOfTheoryPracticaJun = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaJun = 0;
//
//		final int totalNumOfTheoryPracticaJul;
//		if (practicasPorMes.containsKey(Integer.toString(7) + "-THEORY"))
//			totalNumOfTheoryPracticaJul = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaJul = 0;
//
//		final int totalNumOfTheoryPracticaAug;
//		if (practicasPorMes.containsKey(Integer.toString(8) + "-THEORY"))
//			totalNumOfTheoryPracticaAug = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaAug = 0;
//
//		final int totalNumOfTheoryPracticaSep;
//		if (practicasPorMes.containsKey(Integer.toString(9) + "-THEORY"))
//			totalNumOfTheoryPracticaSep = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaSep = 0;
//
//		final int totalNumOfTheoryPracticaOct;
//		if (practicasPorMes.containsKey(Integer.toString(10) + "-THEORY"))
//			totalNumOfTheoryPracticaOct = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaOct = 0;
//
//		final int totalNumOfTheoryPracticaNov;
//		if (practicasPorMes.containsKey(Integer.toString(11) + "-THEORY"))
//			totalNumOfTheoryPracticaNov = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaNov = 0;
//
//		final int totalNumOfTheoryPracticaDec;
//		if (practicasPorMes.containsKey(Integer.toString(12) + "-THEORY"))
//			totalNumOfTheoryPracticaDec = practicasPorMes.get(Integer.toString(1) + "-THEORY").size();
//		else
//			totalNumOfTheoryPracticaDec = 0;
//
//		//Prácticas por mes de cursos de práctica
//		//---------------------------------------
//		final int totalNumOfHandsOnPracticaJan;
//		if (practicasPorMes.containsKey(Integer.toString(1) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaJan = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaJan = 0;
//
//		final int totalNumOfHandsOnPracticaFeb;
//		if (practicasPorMes.containsKey(Integer.toString(2) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaFeb = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaFeb = 0;
//
//		final int totalNumOfHandsOnPracticaMar;
//		if (practicasPorMes.containsKey(Integer.toString(3) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaMar = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaMar = 0;
//
//		final int totalNumOfHandsOnPracticaApr;
//		if (practicasPorMes.containsKey(Integer.toString(4) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaApr = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaApr = 0;
//
//		final int totalNumOfHandsOnPracticaMay;
//		if (practicasPorMes.containsKey(Integer.toString(5) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaMay = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaMay = 0;
//
//		final int totalNumOfHandsOnPracticaJun;
//		if (practicasPorMes.containsKey(Integer.toString(6) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaJun = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaJun = 0;
//
//		final int totalNumOfHandsOnPracticaJul;
//		if (practicasPorMes.containsKey(Integer.toString(7) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaJul = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaJul = 0;
//
//		final int totalNumOfHandsOnPracticaAug;
//		if (practicasPorMes.containsKey(Integer.toString(8) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaAug = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaAug = 0;
//
//		final int totalNumOfHandsOnPracticaSep;
//		if (practicasPorMes.containsKey(Integer.toString(9) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaSep = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaSep = 0;
//
//		final int totalNumOfHandsOnPracticaOct;
//		if (practicasPorMes.containsKey(Integer.toString(10) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaOct = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaOct = 0;
//
//		final int totalNumOfHandsOnPracticaNov;
//		if (practicasPorMes.containsKey(Integer.toString(11) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaNov = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaNov = 0;
//
//		final int totalNumOfHandsOnPracticaDec;
//		if (practicasPorMes.containsKey(Integer.toString(12) + "-HANDS_ON"))
//			totalNumOfHandsOnPracticaDec = practicasPorMes.get(Integer.toString(1) + "-HANDS_ON").size();
//		else
//			totalNumOfHandsOnPracticaDec = 0;
//
//		final Double averageLearningTimeOfLectures;
//		final Double deviationLearningTimeOfLectures;
//		final Double minimumLearningTimeOfLectures;
//		final Double maximumLearningTimeOfLectures;
//
//		final Double averageLearningTimeOfCourses;
//		final Double deviationLearningTimeOfCourses;
//		final Double minimumLearningTimeOfCourses;
//		final Double maximumLearningTimeOfCourses;
//
//		//PRACTICUM BY MONTH AND TYPE
//
//		//LEARNING TIME
//		averageLearningTimeOfLectures = this.repository.averageLearningTimeOfLectures(id);
//		deviationLearningTimeOfLectures = this.repository.deviationLearningTimeOfLectures(id);
//		minimumLearningTimeOfLectures = this.repository.minimumLearningTimeOfLectures(id);
//		maximumLearningTimeOfLectures = this.repository.maximumLearningTimeOfLectures(id);
//		//COURSES
//		averageLearningTimeOfCourses = this.calculateAverageLearningTimeOfCourses(learningTimeOfCourses);
//		deviationLearningTimeOfCourses = this.calculateDeviationLearningTimeOfCourses(learningTimeOfCourses);
//		maximumLearningTimeOfCourses = this.calculateMaximumLearningTimeOfCourses(learningTimeOfCourses);
//		minimumLearningTimeOfCourses = this.calculateMinimumLearningTimeOfCourses(learningTimeOfCourses);
//
//		dashboard = new CompanyDashboard();
//		dashboard.setTotalNumOfHandsOnLectures(totalNumOfTheoryLectures);
//		dashboard.setTotalNumOfHandsonLectures(totalNumOfHandsonLectures);
//		dashboard.setLectureLearningTimeAverage(averageLearningTimeOfLectures);
//		dashboard.setLectureLearningTimeDeviation(deviationLearningTimeOfLectures);
//		dashboard.setLectureLearningTimeMinimum(minimumLearningTimeOfLectures);
//		dashboard.setLectureLearningTimeMaximum(maximumLearningTimeOfLectures);
//		dashboard.setCourseLearningTimeAverage(averageLearningTimeOfCourses);
//		dashboard.setCourseLearningTimeDeviation(deviationLearningTimeOfCourses);
//		dashboard.setCourseLearningTimeMinimum(minimumLearningTimeOfCourses);
//		dashboard.setCourseLearningTimeMaximum(maximumLearningTimeOfCourses);
//		super.getBuffer().setData(dashboard);
//	}
//
//	@Override
//	public void unbind(final LecturerDashboard object) {
//		Tuple tuple;
//
//		tuple = super.unbind(object, //
//			"totalNumOfTheoryLectures", "totalNumOfHandsonLectures", // 
//			"lectureLearningTimeAverage", "lectureLearningTimeDeviation", // 
//			"lectureLearningTimeMinimum", "lectureLearningTimeMaximum", //
//			"courseLearningTimeAverage", "courseLearningTimeDeviation", //
//			"courseLearningTimeMaximum", "courseLearningTimeMinimum" //
//		); //
//
//		super.getResponse().setData(tuple);
//	}
//
//	//Métodos para calcular tiempos de aprendizaje de un curso teniendo en cuenta las lectures
//	@Transient
//	public Double calculateAverageLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
//		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty()) {
//			double res = 0.;
//			for (final Double d : learningTimeOfCourse)
//				res += d;
//			return res / learningTimeOfCourse.size();
//		} else
//			return null;
//	}
//
//	@Transient
//	public Double calculateDeviationLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
//		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty()) {
//			double x = 0.;
//			for (final Double t : learningTimeOfCourse)
//				x += t;
//			x /= learningTimeOfCourse.size();
//			double sumaDesviacionesCuadrado = 0.;
//			for (final Double t : learningTimeOfCourse) {
//				final double desviacion = t - x;
//				sumaDesviacionesCuadrado += desviacion * desviacion;
//			}
//			return Math.sqrt(sumaDesviacionesCuadrado / learningTimeOfCourse.size());
//		} else
//			return null;
//	}
//
//	@Transient
//	public Double calculateMinimumLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
//		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty())
//			return Collections.min(learningTimeOfCourse);
//		else
//			return null;
//	}
//
//	@Transient
//	public Double calculateMaximumLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
//		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty())
//			return Collections.max(learningTimeOfCourse);
//		else
//			return null;
//	}
//
//}
