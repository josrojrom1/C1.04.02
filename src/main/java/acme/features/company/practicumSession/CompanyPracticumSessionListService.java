
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
		Practicum Practicum;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		Practicum = this.repository.findOnePracticum(id);
		status = super.getRequest().getPrincipal().hasRole(Company.class) && Practicum != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyPracticumSessionByPracticum(id);
		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("masterId", id);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Practicum practicum;
		Tuple tuple;
		int id;
		boolean showCreate;
		tuple = super.unbind(object, "title");

		id = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findOnePracticum(id);
		showCreate = practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		tuple.put("practicum", practicum.getCode());
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setData(tuple);
	}
}
