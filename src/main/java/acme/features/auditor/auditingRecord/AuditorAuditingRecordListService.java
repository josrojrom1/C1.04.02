
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

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
		Audit audit;
		int id;

		id = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(id);
		status = super.getRequest().getPrincipal().hasRole(Auditor.class) && audit != null && super.getRequest().getPrincipal().getActiveRoleId() == audit.getAuditor().getId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;

		int id;
		id = super.getRequest().getData("id", int.class);
		objects = this.repository.findAuditingRecordsByAuditId(id);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;

		String auditor;
		auditor = object.getAudit().getAuditor().getUserAccount().getIdentity().getName();
		auditor = auditor + " " + object.getAudit().getAuditor().getUserAccount().getIdentity().getSurname();
		tuple = super.unbind(object, "subject", "assessment", "startPeriod", "endPeriod", "mark", "link", "correction");
		tuple.put("auditor", auditor);
		tuple.put("audit", object.getAudit().getCode());
		int id;
		id = super.getRequest().getData("id", int.class);
		super.getResponse().setGlobal("id", id);
		super.getResponse().setData(tuple);
	}

}
