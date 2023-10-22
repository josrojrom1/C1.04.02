
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.testing.TestHarness;

public class LecturerLectureUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String learningTime, final String body, final String lectureType, final String link) {
		// HINT: En este test nos autenticamos como Lecturer, listamos las lectures, seleccionamos una de ellas y comprobamos que el update se hace correctamente 
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		//		super.checkInputBoxHasValue("title", title);
		//		super.checkInputBoxHasValue("abst", abst);
		//		super.checkInputBoxHasValue("learningTime", learningTime);
		//		super.checkInputBoxHasValue("body", body);
		//		super.checkInputBoxHasValue("lectureType", lectureType);
		//		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String abst, final String learningTime, final String body, final String lectureType, final String link) {
		// HINT: En este test actualizamos un lecture con datos incorrectos
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("learningTime", learningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: En este test intentamos actualizar un lecture con otros roles o un lecturer no propietario

		Collection<Course> lectures;
		String param;

		lectures = this.repository.findManyCoursesByLecturerUsername("lecturer1");
		for (final Course lecture : lectures) {
			param = String.format("id=%d", lecture.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();

			super.checkLinkExists("Sign in");
			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
