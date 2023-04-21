
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

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		id = super.getRequest().getData("id", int.class);
		Audit audit;
		audit = this.repository.findAuditById(id);
		status = audit != null && super.getRequest().getPrincipal().hasRole(Auditor.class);
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
		System.out.println(audit.getCode());
		super.getBuffer().setData(object);
		System.out.println(object.getAudit().getCode());
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

		//validar marks
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

		tuple.put("markChoices", markChoices);
		tuple.put("id", super.getRequest().getData("id", int.class));

		super.getResponse().setData(tuple);
	}

}
