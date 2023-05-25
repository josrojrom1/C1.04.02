
package acme.features.company.practicumSession;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.features.company.practicum.CompanyPracticumRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionDeleteService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	protected CompanyPracticumRepository		repository2;


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
		PracticumSession session;

		id = super.getRequest().getData("id", int.class);
		session = this.repository.findOnePracticumSession(id);
		status = session != null && session.getPracticum().isDraftMode() && super.getRequest().getPrincipal().hasRole(Company.class) && session.getPracticum().getCompany().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOnePracticumSession(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;
		super.bind(object, "title", "abst", "timePeriodStart", "timePeriodFinish", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;
	}

	@Override
	public void perform(final PracticumSession object) {
		Practicum practicum;
		practicum = object.getPracticum();
		final Long time = TimeUnit.MILLISECONDS.toSeconds(object.getTimePeriodEnd().getTime() - object.getTimePeriodStart().getTime());
		final double hours = time.doubleValue() / 3600;
		practicum.setTotalTime(practicum.getTotalTime() - hours);
		this.repository2.save(practicum);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		final Collection<Practicum> practicums;
		SelectChoices choices;

		practicums = this.repository.findAllPracticums();
		choices = SelectChoices.from(practicums, "code", object.getPracticum());

		Tuple tuple;
		tuple = super.unbind(object, "title", "abst", "timePeriodStart", "timePeriodEnd", "link");
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("choices", choices);
		tuple.put("draftMode", object.getPracticum().isDraftMode());
		tuple.put("hasAddendum", object.getPracticum().isHasAddendum());
		tuple.put("readPracticum", true);
		super.getResponse().setData(tuple);
	}
}
