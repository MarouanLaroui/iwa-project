package fr.polytech.ig5.mnm.offerms.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Data
@Entity
@Table(name="applications")
public class Application {

    @Id
    @GeneratedValue()
    private UUID applicationId;

    @Column(name="message")
    private String message;

    @Column(name="is_validated_by_company")
    private Boolean isValidatedByCompany;

    @Column(name="is_validated_by_worker")
    private Boolean isValidatedByWorker;

    @Column(name="worker_id")
    private UUID workerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    // add :  @OnDelete(action = OnDeleteAction.CASCADE) ?
    // add : @JsonIgnore ?

}
