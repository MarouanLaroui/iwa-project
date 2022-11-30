package fr.polytech.ig5.mnm.feedbackms.DTO;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
public class FeedbackCreateDTO {

    private UUID feedbackId;

    private UUID senderId;

    @NotNull(message = "a feedback must have a receiver id")
    private UUID receiverId;

    @NotEmpty(message = "a feedback must have a title")
    private String title;

    @NotEmpty(message = "a feedback must have a message")
    private String message;

    @NotEmpty(message = "a feedback must have a job label rated")
    private String jobLabelRated;

    @NotNull(message = "a feedback must have a rate")
    @Min(0)
    @Max(5)
    private int rate;
}
