
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityListTest extends TestHarness {

	//TEST POSITIVO

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String abst, final String enrolment) {
		//En este test comprobamos que las actividades se muestran correctamente
		super.signIn("student2", "student2");
		super.clickOnMenu("Student", "Activity");
		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abst);
		super.checkColumnHasValue(recordIndex, 2, enrolment);
		super.signOut();

	}

	//TEST HACKING

	@Test
	public void test200Hacking() {
		//Con este test comprobamos que no podemos acceder a la lista de activities desde un rol no autorizado
		super.checkLinkExists("Sign in");
		super.request("/student/activity/list");
		super.checkPanicExists();
		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/list");
		super.checkPanicExists();
		super.signOut();
	}

}
