package fr.polytech.ig5.mnm.feedbackms.controllers;

import fr.polytech.ig5.mnm.feedbackms.DTO.FeedbackCreateDTO;
import fr.polytech.ig5.mnm.feedbackms.models.Feedback;
import fr.polytech.ig5.mnm.feedbackms.services.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedbacks") // 1
public class FeedbackController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @GetMapping("/bySenderId/{id}")
    public ResponseEntity<Object> getBySenderId(@PathVariable("id") UUID senderId) {

        List<Feedback> feedbacks = this.service.findBySenderId(senderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(feedbacks);
    }

    @GetMapping("/byReceiverId/{id}")
    public ResponseEntity<Object> getByReceiverId(@PathVariable("id") UUID receiverId) {

        List<Feedback> feedbacks = this.service.findByReceiverId(receiverId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(feedbacks);
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(
            @Valid @RequestBody FeedbackCreateDTO feedbackDTO) {

        Feedback newFeedback = modelMapper.map(feedbackDTO, Feedback.class);
        //newFeedback.setSenderId();

        Feedback createdFeedback = this.service.create(newFeedback);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdFeedback);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {

        var isRemoved = this.service.delete(id);

        if (!isRemoved) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }


}
