
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	//TEST POSITIVO

	@ParameterizedTest

	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String activityType, final String startTimePeriod, final String endTimePeriod, final String link, final String enrolment) {
		//HINT: Este test muestra como se crea una activity correctamente
		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Activity");
		super.checkListingExists();
		super.clickOnButton("Create");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startTimePeriod", startTimePeriod);
		super.fillInputBoxIn("endTimePeriod", endTimePeriod);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("enrolment", enrolment);
		super.clickOnSubmit("Create");
		super.clickOnMenu("Student", "Activity");
		super.checkListingExists();
		super.sortListing(0, "desc"); //Según el orden de la lista la nueva entrada es la primera
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

	//TEST NEGATIVO

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String activityType, final String startTimePeriod, final String endTimePeriod, final String link, final String enrolment) {
		//HINT: Este test muestro como no se crea un activity al meter parámetros incorrectos
		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Activity");
		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("startTimePeriod", startTimePeriod);
		super.fillInputBoxIn("endTimePeriod", endTimePeriod);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("enrolment", enrolment);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.signOut();
	}

	//TEST HACKING

	@Test
	public void test300Hacking() {
		// HINT: Este test intenta crear una activity usando roles inapropiados

		super.checkLinkExists("Sign in");
		super.request("/student/activity/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/enrolment/create");
		super.checkPanicExists();
		super.signOut();
	}

}
