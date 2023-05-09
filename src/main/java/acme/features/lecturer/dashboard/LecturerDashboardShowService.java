/*
 * AdministratorDashboardShowService.java
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

import java.beans.Transient;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.LecturerDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final LecturerDashboard dashboard;
		Principal principal;
		int id;
		principal = super.getRequest().getPrincipal();
		id = principal.getActiveRoleId();
		List<Double> learningTimeOfCourses;
		learningTimeOfCourses = this.repository.learningTimeOfCourses(id);

		final Integer totalNumOfTheoryLectures;
		final Integer totalNumOfHandsonLectures;
		final Double averageLearningTimeOfLectures;
		final Double deviationLearningTimeOfLectures;
		final Double minimumLearningTimeOfLectures;
		final Double maximumLearningTimeOfLectures;

		final Double averageLearningTimeOfCourses;
		final Double deviationLearningTimeOfCourses;
		final Double minimumLearningTimeOfCourses;
		final Double maximumLearningTimeOfCourses;

		//LECTURES
		totalNumOfTheoryLectures = this.repository.totalNumOfTheoryLectures();
		totalNumOfHandsonLectures = this.repository.totalNumOfHandsonLectures();
		//LEARNING TIME
		averageLearningTimeOfLectures = this.repository.averageLearningTimeOfLectures(id);
		deviationLearningTimeOfLectures = this.repository.deviationLearningTimeOfLectures(id);
		minimumLearningTimeOfLectures = this.repository.minimumLearningTimeOfLectures(id);
		maximumLearningTimeOfLectures = this.repository.maximumLearningTimeOfLectures(id);
		//COURSES
		averageLearningTimeOfCourses = this.calculateAverageLearningTimeOfCourses(learningTimeOfCourses);
		deviationLearningTimeOfCourses = this.calculateDeviationLearningTimeOfCourses(learningTimeOfCourses);
		maximumLearningTimeOfCourses = this.calculateMaximumLearningTimeOfCourses(learningTimeOfCourses);
		minimumLearningTimeOfCourses = this.calculateMinimumLearningTimeOfCourses(learningTimeOfCourses);

		dashboard = new LecturerDashboard();
		dashboard.setTotalNumOfTheoryLectures(totalNumOfTheoryLectures);
		dashboard.setTotalNumOfHandsonLectures(totalNumOfHandsonLectures);
		dashboard.setLectureLearningTimeAverage(averageLearningTimeOfLectures);
		dashboard.setLectureLearningTimeDeviation(deviationLearningTimeOfLectures);
		dashboard.setLectureLearningTimeMinimum(minimumLearningTimeOfLectures);
		dashboard.setLectureLearningTimeMaximum(maximumLearningTimeOfLectures);
		dashboard.setCourseLearningTimeAverage(averageLearningTimeOfCourses);
		dashboard.setCourseLearningTimeDeviation(deviationLearningTimeOfCourses);
		dashboard.setCourseLearningTimeMinimum(minimumLearningTimeOfCourses);
		dashboard.setCourseLearningTimeMaximum(maximumLearningTimeOfCourses);
		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"totalNumOfTheoryLectures", "totalNumOfHandsonLectures", // 
			"lectureLearningTimeAverage", "lectureLearningTimeDeviation", // 
			"lectureLearningTimeMinimum", "lectureLearningTimeMaximum", //
			"courseLearningTimeAverage", "courseLearningTimeDeviation", //
			"courseLearningTimeMaximum", "courseLearningTimeMinimum" //
		); //

		super.getResponse().setData(tuple);
	}

	//Métodos para calcular tiempos de aprendizaje de un curso teniendo en cuenta las lectures
	@Transient
	public Double calculateAverageLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty()) {
			double res = 0.;
			for (final Double d : learningTimeOfCourse)
				res += d;
			return res / learningTimeOfCourse.size();
		} else
			return null;
	}

	@Transient
	public Double calculateDeviationLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty()) {
			double x = 0.;
			for (final Double t : learningTimeOfCourse)
				x += t;
			x /= learningTimeOfCourse.size();
			double sumaDesviacionesCuadrado = 0.;
			for (final Double t : learningTimeOfCourse) {
				final double desviacion = t - x;
				sumaDesviacionesCuadrado += desviacion * desviacion;
			}
			return Math.sqrt(sumaDesviacionesCuadrado / learningTimeOfCourse.size());
		} else
			return null;
	}

	@Transient
	public Double calculateMinimumLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty())
			return Collections.min(learningTimeOfCourse);
		else
			return null;
	}

	@Transient
	public Double calculateMaximumLearningTimeOfCourses(final List<Double> learningTimeOfCourse) {
		if (learningTimeOfCourse != null && !learningTimeOfCourse.isEmpty())
			return Collections.max(learningTimeOfCourse);
		else
			return null;
	}

}
