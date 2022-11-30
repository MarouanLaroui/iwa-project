package fr.polytech.ig5.mnm.feedbackms.models;

import javax.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name="applications")
public class Feedback {

    @Id
    @GeneratedValue()
    private UUID feedbackId;

    @Column(name="sender_id", nullable = false)
    private UUID senderId;

    @Column(name="receiver_id", nullable = false)
    private UUID receiverId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(columnDefinition="TEXT", nullable = false)
    private String message;

    @Column(name="job_label_rated", nullable = false)
    private String jobLabelRated;

    @Column(name="rate", nullable = false)
    private int rate;

}
