
package acme.testing.lecturer.course;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseDeleteTest extends TestHarness {

	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abst, final String retailPrice, final String link) {
		// HINT: Nos autenticamos como Lecturer, mostramos los cursos y hacemos click en borrar un curso aún no publicado
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my courses");
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Delete");
		super.signOut();

	}

}
