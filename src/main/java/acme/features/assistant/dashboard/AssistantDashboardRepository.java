
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

	//tutorialSession
	@Query("select avg(TIME_TO_SEC(TIMEDIFF(ts.periodFinish, ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	public Double getAverageTimeTutorialSession(int id);

	@Query("select stddev(TIME_TO_SEC(TIMEDIFF(ts.periodFinish, ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	public Double getDeviationTimeTutorialSession(int id);

	@Query("select min(TIME_TO_SEC(TIMEDIFF(ts.periodFinish, ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	public Double getMinTimeTutorialSession(int id);

	@Query("select max(TIME_TO_SEC(TIMEDIFF(ts.periodFinish, ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.assistant.id = :id")
	public Double getMaxTimeTutorialSession(int id);

	//tutorial
	@Query("select avg(time_to_sec(timediff(ts.periodFinish,ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :id)")
	public Double getAverageTimeTutorial(int id);

	@Query("select stddev(time_to_sec(timediff(ts.periodFinish,ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :id)")
	public Double getDeviationTimeTutorial(int id);

	@Query("select min(time_to_sec(timediff(ts.periodFinish,ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :id)")
	public Double getMinTimeTutorial(int id);

	@Query("select max(time_to_sec(timediff(ts.periodFinish,ts.periodStart)) / 3600) from TutorialSession ts where ts.tutorial.id in (select t.id from Tutorial t where t.assistant.id = :id)")
	public Double getMaxTimeTutorial(int id);

}
