
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerLectureCreateTest extends TestHarness {

	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String learningTime, final String body, final String lectureType, final String link) {
		// HINT: Nos autenticamos como Lecturer, mostramos las lectures y hacemos click en crear una lecture para ver que todo resulta como esperamos
		super.signIn("lecturer3", "lecturer3");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.clickOnButton("Create new lecture");
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	//TEST NEGATIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String learningTime, final String body, final String lectureType, final String link) {
		// HINT: En este test intentamos crear cursos con datos incorrectos.

		super.signIn("lecturer3", "lecturer3");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.clickOnButton("Create new lecture");
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.signOut();
	}

	//TEST HACKING
	@Test
	public void test300Hacking() {
		// HINT: Este test intenta crear un curso usando principals con roles inapropiados

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();
	}

}
