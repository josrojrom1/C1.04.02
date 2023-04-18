
package acme.features.lecturer.lecture;

import org.springframework.stereotype.Service;

import acme.entities.Lecture;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

}
