package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    @Column(name="message")
    private String message;

    @Column(name="is_validated_by_company")
    private String isValidatedByCompany;

    @Column(name="is_validated_by_worker")
    private String isValidatedByWorker;

    /*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Offer offer;
     */
}
