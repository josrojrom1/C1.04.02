
package acme.testing.authenticated.note;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuthenticatedNoteListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/note/list-all-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String moment, final String title, final String author) {
		//Este test se autentica como lecturer y comprueba que puede listar las notas correctamente

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Authenticated", "List notes");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, author);

		super.signOut();
	}
	//No se puede hacer test negativo en los tests

	@Test
	public void test200Hacking() {
		//This test tries to request the list of notes as anonymous principal

		super.checkLinkExists("Sign in");
		super.request("/authenticated/notes/list");
		super.checkPanicExists();
	}

}
