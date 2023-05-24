
package acme.features.auditor.auditingRecord;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.entities.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;
import acme.utility.SpamDetector;

@Service
public class AuditorAuditingRecordCorrectService extends AbstractService<Auditor, AuditingRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository	repository;

	@Autowired
	protected SpamDetector						textValidator;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Audit audit;
		int id;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(id);
		status = audit != null && !audit.isDraftMode() && audit.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		Audit audit;
		int id;
		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(id);
		object = new AuditingRecord();
		object.setAudit(audit);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "link");

		//object.setDraftMode(false);

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

		Boolean confirm;
		confirm = super.getRequest().getData("confirm", boolean.class);
		super.state(confirm, "confirm", "javax.validation.constraints.AssertTrue.message");

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
		object.setCorrection(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		SelectChoices markChoices;

		markChoices = SelectChoices.from(Mark.class, object.getMark());

		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "link");
		tuple.put("confirm", false);
		tuple.put("markChoices", markChoices);
		tuple.put("id", super.getRequest().getData("id", int.class));

		super.getResponse().setData(tuple);
	}

}
