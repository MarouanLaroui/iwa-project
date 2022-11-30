package fr.polytech.ig5.mnm.feedbackms.services;

import fr.polytech.ig5.mnm.feedbackms.models.Feedback;
import fr.polytech.ig5.mnm.feedbackms.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public List<Feedback> findByReceiverId(final UUID id) {
        return repository.findFeedbacksByReceiverId(id);
    }

    public List<Feedback> findBySenderId(final UUID id) {
        return repository.findFeedbacksByReceiverId(id);
    }

    public Optional<Feedback> find(final UUID id) {
        return repository.findById(id);
    }

    public Feedback create(Feedback criteria) {
        return this.repository.save(criteria);
    }

    public Boolean delete(final UUID id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
