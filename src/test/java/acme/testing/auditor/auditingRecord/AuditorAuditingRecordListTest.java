
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordListTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100positive(final int recordIndex, final String subject, final String mark) {
		//este test comprueba el listado de los registros de una auditoria de un auditor
		super.signIn("auditor2", "auditor2");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.clickOnButton("Auditing records");

		super.checkListingExists();
		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Audit> audits;
		String param;
		audits = this.repository.findManyAuditsByAssistant("auditor2");
		for (final Audit audit : audits) {
			param = String.format("id=%d", audit.getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/list", param);
			super.checkPanicExists();

			super.signIn("auditor1", "auditor1");
			super.request("/auditor/auditing-record/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditing-record/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
