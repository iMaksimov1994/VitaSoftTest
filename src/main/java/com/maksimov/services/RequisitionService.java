package com.maksimov.services;

import com.maksimov.entities.Requisition;

import java.util.List;

public interface RequisitionService {
    //by user
    Requisition createRequisition(String txt, long idUser);

    List<Requisition> listOfRequisitionsForUser(long idUser, int typeOfSort);

    Requisition editRequisition(long id, String txtEdit, long idUser);

    Requisition sendRequisition(long id, long idUser);

    // by operator
    List<Requisition> listRequisitionsForOperator(int typeOfSort);

    List<Requisition> listRequisitionsByNameOrSubNameForOperator(String nameOrSubName, int typeOfSort);

    Requisition approveRequisition(long id, long idUser);

    Requisition rejectRequisition(long id, long idUser);
}
