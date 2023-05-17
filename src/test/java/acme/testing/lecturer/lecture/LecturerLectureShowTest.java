
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureShowTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String learningTime, final String body, final String lectureType, final String link) {
		// HINT: En este test nos autenticamos como Lecturer, listamos sus lectures y despues hacemos click en una de ellas y comprobamos que contiene los datos esperados  
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("learningTime", learningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	//TEST HACKING
	@Test
	public void test300Hacking() {
		// HINT: this test tries to show an unpublished job by someone who is not the principal.

		Collection<Lecture> lectures;
		String param;

		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer1");
		for (final Lecture lecture : lectures) {

			param = String.format("id=%d", lecture.getId());
			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/show", param);
			super.checkPanicExists();
			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
