
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum practicum;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticum(id);
		status = super.getRequest().getPrincipal().hasRole(Company.class) && practicum != null && practicum.getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;
		int id;
		Practicum practicum;
		boolean showCreate;
		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyPracticumSessionByPracticum(id);

		super.getResponse().setGlobal("masterId", id);
		practicum = this.repository.findOnePracticum(id);
		showCreate = practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setGlobal("hasAddendum", practicum.isHasAddendum());
		super.getResponse().setGlobal("showCreate", showCreate);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Practicum practicum;
		Tuple tuple;
		int id;
		boolean showCreate;

		id = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticum(id);
		showCreate = practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setGlobal("masterId", id);
		super.getResponse().setGlobal("showCreate", showCreate);
		tuple = super.unbind(object, "title");
		tuple.put("practicum", practicum.getCode());
		tuple.put("draftMode", practicum.isDraftMode());
		tuple.put("hasAddendum", practicum.isHasAddendum());
		if (object.isAddendum())
			tuple.put("isAddendum", "✓");
		else
			tuple.put("isAddendum", "✗");
		tuple.put("showCreate", showCreate);
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		super.getResponse().setData(tuple);
	}
}
