
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerLectureListTest extends TestHarness {

	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String learningTime) {
		//En este test comprobamos que las lectures se muestran correctamente
		super.signIn("lecturer1", "lecturer1");
		super.clickOnMenu("Lecturer", "List my lectures");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, learningTime);
		super.signOut();

	}

	//TEST HACKING
	@Test
	public void test200Hacking() {
		//Con este test comprobamos que no podemos acceder a la lista de lecturas desde un rol no autorizado
		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/list");
		super.checkPanicExists();
		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/list");
		super.checkPanicExists();
		super.signOut();
	}

}
