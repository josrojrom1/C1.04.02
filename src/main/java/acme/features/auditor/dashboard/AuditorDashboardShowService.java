/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


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
		AuditorDashboard dashboard;
		int id;

		id = super.getRequest().getPrincipal().getActiveRoleId();

		int totalNumOfTheoryAudits;
		int totalNumOfHandsonAudits;
		Double auditRAverage;

		//AUDITS
		totalNumOfTheoryAudits = this.repository.totalNumOfTheoryAudits(id);
		totalNumOfHandsonAudits = this.repository.totalNumOfHandsonAudits(id);
		//AUDITING RECORDS
		auditRAverage = this.repository.averageNumberOfAuditingRecords(id);

		dashboard = new AuditorDashboard();

		dashboard.setTotalNumOfTheoryAudits(totalNumOfTheoryAudits);
		dashboard.setTotalNumOfHandsonAudits(totalNumOfHandsonAudits);
		dashboard.setAuditRAverage(auditRAverage);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumOfTheoryAudits", "totalNumOfHandsonAudits", "auditRAverage");

		super.getResponse().setData(tuple);
	}

}
