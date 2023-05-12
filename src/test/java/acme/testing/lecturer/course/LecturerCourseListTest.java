
package acme.testing.lecturer.course;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String price) {
		//Rol Lecturer;
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my courses");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, price);
		super.signOut();

	}

}
