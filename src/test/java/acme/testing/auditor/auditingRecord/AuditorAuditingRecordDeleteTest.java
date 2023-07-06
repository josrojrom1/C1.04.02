
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@Test
	public void test100Positive() {
		super.signIn("auditor2", "auditor2");
		super.clickOnMenu("Auditor", "List my audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Auditing records");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.signOut();
	}
	@Test
	public void test300Hacking() {
		Collection<AuditingRecord> records;
		String param;
		records = this.repository.findManyAuditingRecordByAuditor("auditor2");
		for (final AuditingRecord record : records) {
			param = String.format("id=%d", record.getId());
			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();

			super.signIn("auditor1", "auditor1");
			super.request("/assistant/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
