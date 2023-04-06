package com.maksimov.repositories;

import com.maksimov.entities.Requisition;
import com.maksimov.entities.User;
import com.maksimov.enums.RequisitionStates;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequisitionRepository extends JpaRepository<Requisition, Long> {

    List<Requisition> findRequisitionByUser(User user);

    List<Requisition> findRequisitionsByUserOrderByLocalDateTimeAsc(User user);

    List<Requisition> findRequisitionsByUserOrderByLocalDateTimeDesc(User user);

    List<Requisition> findRequisitionsByUserAndRequisitionStatesOrderByLocalDateTimeAsc(User user,
                                                                                        RequisitionStates requisitionStates);

    List<Requisition> findRequisitionsByUserAndRequisitionStatesOrderByLocalDateTimeDesc(User user,
                                                                                         RequisitionStates requisitionStates);

    List<Requisition> findRequisitionsByRequisitionStatesOrderByLocalDateTimeAsc(RequisitionStates requisitionStates);

    List<Requisition> findRequisitionsByRequisitionStatesOrderByLocalDateTimeDesc(RequisitionStates requisitionStates);

    Optional<Requisition> findRequisitionByIdAndRequisitionStatesAndUser(long id, RequisitionStates requisitionStates,
                                                                         User user);

    Optional<Requisition> findRequisitionByIdAndUser(long id, User user);

}
