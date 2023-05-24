
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentShowTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String course) {
		//HINT: En este test comprobamos que mostramos la vista de un enrolment correctamente
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "Enrolment");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);
		super.signOut();
	}

	//TEST HACKING
	@Test
	public void test300Hacking() {
		// HINT: en este test intentamos ver un enrolment desde un rol quue no lo tiene asignado

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {

			param = String.format("id=%d", enrolment.getId());
			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/show", param);
			super.checkPanicExists();
			super.signIn("administrator", "administrator");
			super.request("/student/enrolment/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
