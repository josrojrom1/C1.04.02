
package acme.features.assistant.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("SELECT count(t) FROM Tutorial t WHERE t.assistant.id=:id AND  t.course.courseType = '1' ")
	public int getTotalNumTutorialHandsOn(int id);

	@Query("SELECT count(t) FROM Tutorial t WHERE t.assistant.id=:id AND t.course.courseType = '0' ")
	public int getTotalNumTutorialTheory(int id);

	//	@Query("SELECT avg(t.learningTime) FROM Tutorial t WHERE t.assistant.id=:id ")
	//	public Double
}
