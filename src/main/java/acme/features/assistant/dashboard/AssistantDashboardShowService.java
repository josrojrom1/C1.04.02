
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
		double sessionAvg;
		double sessionDev;
		double sessionMin;
		double sessionMax;
		double tutorialAvg;
		double tutorialDev;
		double tutorialMin;
		double tutorialMax;

		totalNumHandsOn = this.repository.getTotalNumTutorialHandsOn(id);
		totalNumTheory = this.repository.getTotalNumTutorialTheory(id);
		sessionAvg = this.repository.getAverageTimeTutorialSession(id);
		sessionDev = this.repository.getDeviationTimeTutorialSession(id);
		sessionMin = this.repository.getMinTimeTutorialSession(id);
		sessionMax = this.repository.getMaxTimeTutorialSession(id);

		tutorialAvg = this.repository.getAverageTimeTutorial(id);
		tutorialDev = this.repository.getDeviationTimeTutorial(id);
		tutorialMin = this.repository.getMinTimeTutorial(id);
		tutorialMax = this.repository.getMaxTimeTutorial(id);

		dashboard = new AssistantDashboard();
		dashboard.setTotalNumOfHandsonTutorials(totalNumHandsOn);
		dashboard.setTotalNumOfTheoryTutorials(totalNumTheory);
		dashboard.setTutorialAverage(tutorialAvg);
		dashboard.setTutorialDeviation(tutorialDev);
		dashboard.setTutorialMaximun(tutorialMax);
		dashboard.setTutorialMinimun(tutorialMin);
		dashboard.setSessionsAverage(sessionAvg);
		dashboard.setSessionsDeviation(sessionDev);
		dashboard.setSessionsMinimun(sessionMin);
		dashboard.setSessionsMaximun(sessionMax);

		super.getBuffer().setData(dashboard);
	}
	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumOfHandsonTutorials", "totalNumOfTheoryTutorials", "tutorialAverage", "tutorialDeviation", "tutorialMinimun", "tutorialMaximun", "sessionsAverage", "sessionsDeviation", "sessionsMinimun", "sessionsMaximun");
		super.getResponse().setData(tuple);
	}
}
