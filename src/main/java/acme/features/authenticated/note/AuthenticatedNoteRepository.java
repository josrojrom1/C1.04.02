
package acme.features.authenticated.note;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Note;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("select n from Note n where n.id = :id")
	Note findOneNoteById(int id);

	@Query("select n from Note n")
	Collection<Note> findAllNotes();

	@Query("SELECT n from Note n where n.moment >= :deadline")
	Collection<Note> findRecentNotes(Date deadline);

}
