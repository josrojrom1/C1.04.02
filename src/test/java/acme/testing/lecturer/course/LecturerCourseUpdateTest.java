
package acme.testing.lecturer.course;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class LecturerCourseUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abst, final String retailPrice, final String link) {
		// HINT: En este test nos autenticamos como Lecturer, listamos los courses, seleccionamos uno de ellos y comprobamos que el update se hace correctamente 
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abst", abst);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, retailPrice);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abst", abst);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/employer/job/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test200Negative(final int recordIndex, final String reference, final String contractor, final String title, final String deadline, final String salary, final String score, final String moreInfo, final String description) {
	//		// HINT: this test attempts to update a job with wrong data.
	//
	//		super.signIn("employer1", "employer1");
	//
	//		super.clickOnMenu("Employer", "List my courses");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//
	//		super.checkColumnHasValue(recordIndex, 0, reference);
	//		super.clickOnListingRecord(recordIndex);
	//		super.checkFormExists();
	//		super.fillInputBoxIn("reference", reference);
	//		super.fillInputBoxIn("contractor", contractor);
	//		super.fillInputBoxIn("title", title);
	//		super.fillInputBoxIn("deadline", deadline);
	//		super.fillInputBoxIn("salary", salary);
	//		super.fillInputBoxIn("score", score);
	//		super.fillInputBoxIn("moreInfo", moreInfo);
	//		super.fillInputBoxIn("description", description);
	//		super.clickOnSubmit("Update");
	//
	//		super.checkErrorsExist();
	//
	//		super.signOut();
	//	}

	//	@Test
	//	public void test300Hacking() {
	//		// HINT: this test tries to update a job with a role other than "Employer",
	//		// HINT+ or using an employer who is not the owner.
	//
	//		Collection<Course> courses;
	//		String param;
	//
	//		courses = this.repository.findManyCoursesByLecturerUsername("lecturer1");
	//		for (final Course course : courses) {
	//			param = String.format("id=%d", course.getId());
	//
	//			super.checkLinkExists("Sign in");
	//			super.request("/lecturer/course/update", param);
	//			super.checkPanicExists();
	//
	//			super.signIn("administrator", "administrator");
	//			super.request("/lecturer/course/update", param);
	//			super.checkPanicExists();
	//			super.signOut();
	//
	//			super.checkLinkExists("Sign in");
	//			super.signIn("lecturer2", "lecturer");
	//			super.request("/lecturer/course/update", param);
	//			super.checkPanicExists();
	//			super.signOut();
	//		}
	//	}

}
