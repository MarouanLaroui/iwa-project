package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashMap;
import java.util.UUID;

@Data
@Entity
@Table(name="applications")
public class Application {

    @Id
    @GeneratedValue()
    private UUID applicationId;

    @Column(name="worker_id", nullable = false)
    private UUID workerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Column(name="message", nullable = false)
    private String message;

    @Column(name="is_validated_by_company", nullable = false)
    private Boolean isValidatedByCompany;

    @Column(name="is_validated_by_worker", nullable = false)
    private Boolean isValidatedByWorker;

}
