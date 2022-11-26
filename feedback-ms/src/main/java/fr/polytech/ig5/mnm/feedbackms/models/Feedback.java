package fr.polytech.ig5.mnm.feedbackms.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name="feedbacks")
@Data
public class Feedback {

    @Id
    private Long feedbackId;

    @Column
    private String message;
}
