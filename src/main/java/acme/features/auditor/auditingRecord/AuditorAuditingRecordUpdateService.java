
package acme.features.auditor.auditingRecord;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.AuditingRecord;
import acme.entities.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import acme.utility.SpamDetector;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository	repository;

	@Autowired
	protected SpamDetector						textValidator;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		AuditingRecord auditing;
		int id;

		id = super.getRequest().getData("id", int.class);
		auditing = this.repository.findOneAuditingRecord(id);
		status = auditing != null && auditing.getAudit().isDraftMode() && auditing.getAudit().getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecord(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "link");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		Date ahora;
		ahora = MomentHelper.getCurrentMoment();
		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(object.getStartPeriod().before(object.getEndPeriod()), "startPeriod", "auditor.auditingRecord.form.error.date");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(Duration.between(object.getStartPeriod().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), ahora.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).toHours() >= 1, "startPeriod",
				"auditor.auditingRecord.form.error.date");

		if (!super.getBuffer().getErrors().hasErrors("subject")) {
			String validar;
			validar = object.getSubject();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "subject", "auditor.auditingRecord.error.spam");
		}
		if (!super.getBuffer().getErrors().hasErrors("assessment")) {
			String validar;
			validar = object.getAssessment();
			super.getBuffer().getErrors().state(super.getRequest(), !this.textValidator.spamChecker(validar), "assessment", "auditor.auditingRecord.error.spam");
		}
	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		SelectChoices markChoices;

		markChoices = SelectChoices.from(Mark.class, object.getMark());

		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "link");
		tuple.put("draftMode", object.getAudit().isDraftMode());
		tuple.put("markChoices", markChoices);
		tuple.put("id", super.getRequest().getData("id", int.class));

		super.getResponse().setData(tuple);
	}

}
