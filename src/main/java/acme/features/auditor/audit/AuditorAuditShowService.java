
package acme.features.auditor.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.entities.Course;
import acme.entities.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Audit object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAudit(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Collection<Course> courses;

		courses = this.repository.findPublishedCourses();

		SelectChoices courseChoices;
		//SelectChoices markChoices;

		courseChoices = SelectChoices.from(courses, "title", object.getCourse());
		//markChoices = SelectChoices.from(Mark.class, object.getMark());
		Collection<AuditingRecord> ls;
		int id;
		id = super.getRequest().getData("id", int.class);
		ls = this.repository.findAuditingRecordById(id);
		Mark mark;
		Collection<Mark> lsm;
		lsm = new ArrayList<>();
		for (final AuditingRecord ar : ls)
			lsm.add(ar.getMark());
		if (lsm.size() == 0)
			mark = Mark.F_MINUS;
		else {
			Map<Mark, Integer> markCount;
			markCount = new HashMap<>();
			for (final Mark m : lsm) {
				Integer count = markCount.get(m);
				if (count == null)
					count = 0;
				markCount.put(m, count + 1);
			}

			int maxValue;
			maxValue = Collections.max(markCount.values());
			List<Mark> maxMarks;
			maxMarks = new ArrayList<>();
			for (final Map.Entry<Mark, Integer> entry : markCount.entrySet())
				if (entry.getValue() == maxValue)
					maxMarks.add(entry.getKey());

			Mark result;
			if (maxMarks.size() == 1)
				result = maxMarks.get(0);
			else {
				int randomIndex;
				randomIndex = new Random().nextInt(maxMarks.size());
				result = maxMarks.get(randomIndex);
			}
			mark = result;
		}
		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "weakPoints", "strongPoints");
		tuple.put("course", courseChoices.getSelected().getKey());
		//tuple.put("mark", markChoices.getSelected().getKey());
		tuple.put("draftMode", object.isDraftMode());
		tuple.put("courseChoices", courseChoices);
		//tuple.put("markChoices", markChoices);
		tuple.put("mark", mark);

		super.getResponse().setData(tuple);
	}

}
