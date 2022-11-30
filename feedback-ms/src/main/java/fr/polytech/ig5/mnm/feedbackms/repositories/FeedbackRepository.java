package fr.polytech.ig5.mnm.feedbackms.repositories;

import fr.polytech.ig5.mnm.feedbackms.models.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, UUID> {

    List<Feedback> findFeedbacksBySenderId(UUID senderId);

    List<Feedback> findFeedbacksByReceiverId(UUID senderId);

}
