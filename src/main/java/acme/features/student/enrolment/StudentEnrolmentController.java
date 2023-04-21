
package acme.features.student.enrolment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Enrolment;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentEnrolmentController extends AbstractController<Student, Enrolment> {

	@Autowired
	StudentEnrolmentListMineService				listservice;

	@Autowired
	StudentEnrolmentShowService					showservice;

	@Autowired
	StudentEnrolmentCreateService				createService;

	@Autowired
	StudentEnrolmentUpdateService				updateService;

	@Autowired
	StudentEnrolmentDeleteService				deleteService;

	@Autowired
	protected StudentEnrolmentFinaliseService	finaliseService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listservice);
		super.addBasicCommand("show", this.showservice);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		//super.addCustomCommand("register", "create", this.registerService);
		super.addCustomCommand("finalise", "update", this.finaliseService);
	}
}
