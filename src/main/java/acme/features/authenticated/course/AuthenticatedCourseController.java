
package acme.features.authenticated.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedCourseController extends AbstractController<Authenticated, Course> {

	@Autowired
	protected AuthenticatedCourseListService	listService;

	@Autowired
	protected AuthenticatedCourseShowService	showService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
