package fr.polytech.ig5.mnm.feedbackms.controllers;

import fr.polytech.ig5.mnm.feedbackms.models.Feedback;
import fr.polytech.ig5.mnm.feedbackms.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    // TODO : implémenter service
    @Autowired
    FeedbackRepository repository;

    @GetMapping("/")
    public Flux<Feedback> getAll(){
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Feedback> getOne(@PathVariable Long id){
        return this.repository.findById(id);
    }

    @PostMapping("/")
    public Mono<Feedback> create(@RequestBody Feedback feedback){
        return this.repository.save(feedback);
    }

    @PutMapping("/{id}")
    public Mono<Feedback> update(@PathVariable Long id, @RequestBody Feedback feedback){
        return this.repository.findById(id)
                .map(f -> {
                    f.setMessage(feedback.getMessage());
                    return f;
                }).flatMap( f -> this.repository.save(f));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return this.repository.deleteById(id);
    }


}

