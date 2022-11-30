package fr.polytech.ig5.mnm.feedbackms.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackCreateDTO {

    private UUID feedbackId;

    @NotNull(message = "a feedback must have a sender id")
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
