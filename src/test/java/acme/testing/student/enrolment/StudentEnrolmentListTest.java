
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentListTest extends TestHarness {

	//TEST POSITIVO

	@ParameterizedTest

	@CsvFileSource(resources = "/student/enrolment/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String course, final String code, final String motivation) {
		//En este test comprobamos que las lectures se muestran correctamente
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, course);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, motivation);
		super.signOut();

	}

	//TEST HACKING

	@Test
	public void test200Hacking() {
		//Con este test comprobamos que no podemos acceder a la lista de lecturas desde un rol no autorizado
		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/list");
		super.checkPanicExists();
		super.signIn("lecturer1", "lecturer1");
		super.request("/student/enrolment/list");
		super.checkPanicExists();
		super.signOut();
	}

}
