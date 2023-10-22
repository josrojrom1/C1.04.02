
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String subject, final String assessment, final String startPeriod, final String endPeriod, final String mark, final String link) {

		super.signIn("auditor2", "auditor2");
		super.clickOnMenu("Auditor", "List my audits");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

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
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();

			super.signIn("auditor1", "auditor1");
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/auditor/auditing-record/show", param);
			super.checkPanicExists();
			super.signOut();
		}

	}
}
