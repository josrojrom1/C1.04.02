
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Activity;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String activityType, final String startTimePeriod, final String endTimePeriod, final String link, final String enrolment) {
		//HINT: En este test comprobamos que mostramos la vista de una activity correctamente
		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Activity");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("activityType_proxy", activityType);
		super.checkInputBoxHasValue("startTimePeriod", startTimePeriod);
		super.checkInputBoxHasValue("endTimePeriod", endTimePeriod);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("enrolment_proxy", enrolment);
		super.signOut();
	}

	//TEST HACKING

	@Test
	public void test300Hacking() {
		// HINT: en este test intentamos ver un enrolment desde un estudiante que no tiene asiganada esa actividad

		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student2");
		for (final Activity activity : activities) {

			param = String.format("id=%d", activity.getId());
			super.checkLinkExists("Sign in");
			super.request("/student/activity/show", param);
			super.checkPanicExists();
			super.signIn("student1", "student1");
			super.request("/student/activity/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
