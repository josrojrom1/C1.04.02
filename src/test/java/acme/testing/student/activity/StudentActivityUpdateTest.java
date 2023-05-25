
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Activity;
import acme.testing.TestHarness;

public class StudentActivityUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;

	// Test methods ------------------------------------------------------------

	//TEST POSITIVO


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String activityType, final String startTimePeriod, final String endTimePeriod, final String link, final String enrolment) {
		//HINT: En este test mostramos como se actualiza un activity correctamente
		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Activity");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startTimePeriod", startTimePeriod);
		super.fillInputBoxIn("endTimePeriod", endTimePeriod);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("enrolment", enrolment);
		super.clickOnSubmit("Update");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
		//super.checkInputBoxHasValue("title", title);
		//super.checkInputBoxHasValue("abst", abst);
		//super.checkInputBoxHasValue("activityType", activityType);
		//super.checkInputBoxHasValue("startTimePeriod", startTimePeriod);
		//super.checkInputBoxHasValue("endTimePeriod", endTimePeriod);
		//super.checkInputBoxHasValue("link", link);
		//super.checkInputBoxHasValue("enrolment", enrolment);
		super.signOut();
	}
	//TEST NEGATIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String activityType, final String startTimePeriod, final String endTimePeriod, final String link, final String enrolment) {
		//HINT: En este test mostramos como no se actualiza la activity al meter datos incorrectos
		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Activity");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startTimePeriod", startTimePeriod);
		super.fillInputBoxIn("endTimePeriod", endTimePeriod);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("enrolment", enrolment);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();
	}
	//TEST HACKING
	@Test
	public void test300Hacking() {
		//HINT: en este test probamos a actualizar la activity desde un usuario no logueado
		Collection<Activity> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student2");
		for (final Activity activity : activities) {
			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/update", param);
			super.checkPanicExists();

			//Probamos como un usuario que no está logueado
			super.request("/student/activity/update", param);
			super.checkPanicExists();

			super.checkLinkExists("Sign in");
			super.signIn("student1", "student1");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
