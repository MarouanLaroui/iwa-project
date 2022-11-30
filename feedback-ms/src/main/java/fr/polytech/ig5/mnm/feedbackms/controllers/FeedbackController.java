package fr.polytech.ig5.mnm.feedbackms.controllers;

import fr.polytech.ig5.mnm.feedbackms.DTO.FeedbackCreateDTO;
import fr.polytech.ig5.mnm.feedbackms.models.Feedback;
import fr.polytech.ig5.mnm.feedbackms.models.Worker;
import fr.polytech.ig5.mnm.feedbackms.services.FeedbackService;
import fr.polytech.ig5.mnm.feedbackms.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/feedbacks") // 1
public class FeedbackController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FeedbackService service;

    @Autowired
    JwtUtils jwtUtils;

    RestTemplate restTemplate = new RestTemplate();

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
            @Valid @RequestBody FeedbackCreateDTO feedbackDTO,
            @RequestHeader (name="Authorization") String bearerToken) {

        UUID workerId = jwtUtils.extractUUIDFromJWT("workerId", bearerToken);
        UUID companyId = jwtUtils.extractUUIDFromJWT("companyId", bearerToken);

        Feedback newFeedback = modelMapper.map(feedbackDTO, Feedback.class);

        if(workerId != null){
            newFeedback.setSenderId(workerId);
        } else if(companyId != null){
            newFeedback.setSenderId(companyId);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        Optional<Feedback> feedback = this.service.findFeedbackBySenderIdAndReceiverId(
                newFeedback.getSenderId(), newFeedback.getReceiverId()
        );

        if(!feedback.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("You can't send two feedbacks to the same person");
        }

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
