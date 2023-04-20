
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	public Tutorial findOneTutorial(int id);

	@Query("SELECT t FROM Tutorial t")
	public Collection<Tutorial> findAllTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	public Collection<Tutorial> findManyTutorialsFromAssistantId(int id);

	@Query("SELECT a FROM Assistant a where a.id = :id")
	public Assistant findOneAssistantById(int id);

	@Query("SELECT c FROM Course c")
	public Collection<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.id=:id")
	public Course findOneCourseById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.code=:code")
	public Tutorial findOneTutorialByCode(String code);

	@Query("SELECT ts FROM TutorialSession ts WHERE ts.tutorial.id=:id")
	public Collection<TutorialSession> findTutorialSessionById(int id);
}
