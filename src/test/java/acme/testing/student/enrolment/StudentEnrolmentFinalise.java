
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentFinalise extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;

	// Test methods -----------------------------------------------------------


	//TEST POSITIVO
	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String creditCardHolder, final String expiryDate, final String cvc, final String upperNibble, final String lowerNibble) {
		// HINT: En este test nos autenticamos como student escogemos un enrolment y lo finalizamos,
		//para ello tenemos que meter los datos de la tarjeta y que sean correctos
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Finalise");
		super.checkFormExists();
		super.fillInputBoxIn("creditCardHolder", creditCardHolder);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("upperNibble", upperNibble);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.clickOnSubmit("Finalise");
		super.checkNotErrorsExist();
		super.signOut();

	}

	//TEST NEGATIVO

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200negative(final int recordIndex, final String creditCardHolder, final String expiryDate, final String cvc, final String upperNibble, final String lowerNibble) {
		// HINT: En este test nos autenticamos como student escogemos un enrolment y lo finalizamos,
		//para ello tenemos que meter los datos de la tarjeta y que NO sean correctos para ver como no deja finalizar
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Finalise");
		super.checkFormExists();
		super.fillInputBoxIn("creditCardHolder", creditCardHolder);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("upperNibble", upperNibble);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.clickOnSubmit("Finalise");
		super.checkErrorsExist();
		super.signOut();
	}

	//TEST HACKING 1

	@Test
	public void test300Hacking() {
		// HINT: En este test intentamos finalizar un enrolment con un rol distinto a student
		final Collection<Enrolment> enrolments;
		String params;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (!enrolment.getIsFinalised()) {
				params = String.format("id=%d", enrolment.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/student/enrolment/finalise", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

	//TEST HACKING 2

	@Test
	public void test301Hacking() {
		// HINT: En este test intentamos finalizar un enrolment ya finalizado por el student.

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student2", "student2");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.getIsFinalised()) {
				params = String.format("id=%d", enrolment.getId());
				super.request("/student/enrolment/finalise", params);
			}

		super.signOut();
	}

	//TEST HACKING 3

	@Test
	public void test302Hacking() {
		// HINT: En este test intentamos finalizar un enrolment que no pertenece al student

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
		for (final Enrolment enrolment : enrolments) {
			params = String.format("id=%d", enrolment.getId());
			super.request("/student/enrolment/finalise", params);
		}
		super.signOut();
	}

}
