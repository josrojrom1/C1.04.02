
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListMineService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Audit> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyAuditsFromAuditorId(principal.getActiveRoleId());
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Tuple tuple;

		String auditor;
		auditor = object.getAuditor().getUserAccount().getIdentity().getName();
		auditor = auditor + " " + object.getAuditor().getUserAccount().getIdentity().getSurname();
		tuple = super.unbind(object, "code", "conclusion", "weakPoints", "strongPoints");
		tuple.put("auditor", auditor);
		tuple.put("course", object.getCourse().getTitle());
		super.getResponse().setData(tuple);
	}

}
