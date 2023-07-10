
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditListService extends AbstractService<Authenticated, Audit> {

	@Autowired
	protected AuthenticatedAuditRepository repository;


	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		Collection<Audit> objects;
		objects = this.repository.findAllAuditsPublish();
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {

		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "weakPoints", "strongPoints", "mark");
		tuple.put("auditor", object.getAuditor().getFirm());
		tuple.put("course", object.getCourse().getTitle());
		super.getResponse().setData(tuple);

	}
}
