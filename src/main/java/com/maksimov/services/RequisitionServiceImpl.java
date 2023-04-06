package com.maksimov.services;

import com.maksimov.entities.Requisition;
import com.maksimov.entities.User;
import com.maksimov.enums.RequisitionStates;
import com.maksimov.repositories.RequisitionRepository;
import com.maksimov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RequisitionServiceImpl implements RequisitionService {

    private RequisitionRepository requisitionRepository;
    private UserRepository userRepository;

    @Autowired
    public void setRequisitionRepository(RequisitionRepository requisitionRepository) {
        this.requisitionRepository = requisitionRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //BY USER
    @Override
    public Requisition createRequisition(String txt, long idUser) {
        User user = this.userRepository.findById(idUser).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return this.requisitionRepository.save(new Requisition(txt, LocalDateTime.now(), RequisitionStates.DRAFT, user));
    }

    @Override
    public List<Requisition> listOfRequisitionsForUser(long idUser, int typeOfSort) {
        User user = this.userRepository.findById(idUser).orElse(null);
        List<Requisition> requisitions = null;
        switch (typeOfSort) {
            case 1:
                requisitions = this.requisitionRepository.findRequisitionsByUserOrderByLocalDateTimeAsc(user);
                break;
            case 2:
                requisitions = this.requisitionRepository.findRequisitionsByUserOrderByLocalDateTimeDesc(user);
        }
        return requisitions;
    }

    @Override
    public Requisition editRequisition(long id, String txtEdit, long idUser) {
        User user = this.userRepository.findById(idUser).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Requisition requisition = this.requisitionRepository.
                findRequisitionByIdAndRequisitionStatesAndUser(id, RequisitionStates.DRAFT, user).orElse(null);
        if (requisition == null) {
            throw new IllegalArgumentException("Requisition not found");
        }
        requisition.setTxt(txtEdit);
        return this.requisitionRepository.save(requisition);
    }

    @Override
    public Requisition sendRequisition(long id, long idUser) {
        Requisition requisition = findRequisition(id, idUser);
        requisition.setRequisitionStates(RequisitionStates.SENT);
        return this.requisitionRepository.save(requisition);
    }

    //BY OPERATOR
    @Override
    public List<Requisition> listRequisitionsForOperator(int typeOfSort) {
        List<Requisition> requisitions = null;
        switch (typeOfSort) {
            case 1:
                requisitions = this.requisitionRepository.
                        findRequisitionsByRequisitionStatesOrderByLocalDateTimeAsc(RequisitionStates.SENT);
                break;
            case 2:
                requisitions = this.requisitionRepository.
                        findRequisitionsByRequisitionStatesOrderByLocalDateTimeDesc(RequisitionStates.SENT);
        }
        if (requisitions != null && requisitions.size() != 0) {
            modListRequisition(requisitions);
        }
        return requisitions;
    }

    @Override
    public List<Requisition> listRequisitionsByNameOrSubNameForOperator(String nameOrSubName, int typeOfSort) {
        User user = this.userRepository.findUserByUserNameIsContainingIgnoreCaseAndRoles(nameOrSubName, "ROLE_USER")
                .orElse(null);
        List<Requisition> requisitions = null;
        switch (typeOfSort) {
            case 1:
                requisitions = this.requisitionRepository.
                        findRequisitionsByUserAndRequisitionStatesOrderByLocalDateTimeAsc(user, RequisitionStates.SENT);
                break;
            case 2:
                requisitions = this.requisitionRepository.
                        findRequisitionsByUserAndRequisitionStatesOrderByLocalDateTimeDesc(user, RequisitionStates.SENT);
        }
        if (requisitions != null && requisitions.size() != 0) {
            modListRequisition(requisitions);
        }
        return requisitions;
    }


    @Override
    public Requisition approveRequisition(long id, long idUser) {
        Requisition requisition = findRequisition(id, idUser);
        requisition.setRequisitionStates(RequisitionStates.ACCEPTED);
        return this.requisitionRepository.save(requisition);
    }

    @Override
    public Requisition rejectRequisition(long id, long idUser) {
        Requisition requisition = findRequisition(id, idUser);
        requisition.setRequisitionStates(RequisitionStates.REJECTED);
        return this.requisitionRepository.save(requisition);
    }

    private Requisition findRequisition(long id, long idUser) {
        User user = this.userRepository.findById(idUser).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Requisition requisition = this.requisitionRepository.findRequisitionByIdAndUser(id, user).orElse(null);
        if (requisition == null) {
            throw new IllegalArgumentException("Requisition not found");
        }
        return requisition;
    }

    private void modListRequisition(List<Requisition> requisitions) {
        for (Requisition requisition : requisitions) {
            requisition.setTxt(modTxt(requisition.getTxt()));
        }

    }

    private String modTxt(String txt) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            if (txt.charAt(i) != txt.charAt(txt.length() - 1)) {
                if (txt.charAt(i) == ' ') {
                    stringBuilder.append("-");
                } else {
                    stringBuilder.append(txt.charAt(i)).append("-");
                }
            } else {
                stringBuilder.append(txt.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
}
