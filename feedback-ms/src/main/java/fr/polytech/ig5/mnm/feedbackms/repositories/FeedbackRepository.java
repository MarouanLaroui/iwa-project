package fr.polytech.ig5.mnm.feedbackms.repositories;

import fr.polytech.ig5.mnm.feedbackms.models.Feedback;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface FeedbackRepository extends ReactiveCrudRepository<Feedback, Long> {
}
