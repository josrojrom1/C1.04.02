
package acme.features.assistant.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	@Autowired
	protected AssistantDashboardRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Assistant.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AssistantDashboard dashboard;
		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();
		int totalNumHandsOn;
		int totalNumTheory;
		totalNumHandsOn = this.repository.getTotalNumTutorialHandsOn(id);
		totalNumTheory = this.repository.getTotalNumTutorialTheory(id);
		dashboard = new AssistantDashboard();
		dashboard.setTotalNumOfHandsonTutorials(totalNumHandsOn);
		dashboard.setTotalNumOfTheoryTutorials(totalNumTheory);
		System.out.println(totalNumHandsOn);
		super.getBuffer().setData(dashboard);
	}
	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumOfHandsonTutorials", "totalNumOfTheoryTutorials");
		super.getResponse().setData(tuple);
	}
}
