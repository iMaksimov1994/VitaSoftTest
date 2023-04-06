package com.maksimov.entities;

import com.maksimov.enums.RequisitionStates;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requisition")
public class Requisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String txt;
    @NonNull
    private LocalDateTime localDateTime;
    @NonNull
    RequisitionStates requisitionStates;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Requisition(Requisition requisition) {
        this.id = requisition.getId();
        this.txt = requisition.getTxt();
        this.localDateTime = requisition.getLocalDateTime();
        this.requisitionStates = requisition.getRequisitionStates();
        this.user = requisition.getUser();
    }
}
