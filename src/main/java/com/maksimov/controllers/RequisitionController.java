package com.maksimov.controllers;

import com.maksimov.dto.ResponseResult;
import com.maksimov.entities.MyUserDetails;
import com.maksimov.entities.Requisition;
import com.maksimov.services.RequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequisitionController {
    private RequisitionService requisitionService;

    @Autowired
    public void setRequisitionService(RequisitionService requisitionService) {
        this.requisitionService = requisitionService;
    }

    //BY USER!!!

    /**
     * Создание заявки авторизованным пользователем(USER)
     *
     * @param txt-текст заявки
     * @return
     */
    @PostMapping("/createRequisition")
    public ResponseEntity<ResponseResult<Requisition>> createRequisition(@RequestParam String txt,
                                                                         Authentication authentication) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.createRequisition(txt,
                    ((MyUserDetails) authentication.getPrincipal()).getId())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * просматривать созданные им заявки с возможностью сортировки по дате создания в оба направления
     * (как от самой старой к самой новой, так и наоборот)(USER)
     *
     * @param typeOfSort-параметр сортировки 1-прямая, 2 обратная
     * @return
     */
    @GetMapping("/listOfRequisitionsForUser")
    public ResponseEntity<ResponseResult<List<Requisition>>> listOfRequisitionsForUser(@RequestParam int typeOfSort,
                                                                                       Authentication authentication) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.listOfRequisitionsForUser(
                    ((MyUserDetails) authentication.getPrincipal()).getId(), typeOfSort)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * редактировать созданные им заявки в статусе «черновик»(USER)
     *
     * @param idR-id  заявки
     * @param txtEdit
     * @return
     */
    @PutMapping("/editRequisition")
    public ResponseEntity<ResponseResult<Requisition>> editRequisition(@RequestParam long idR, @RequestParam String txtEdit,
                                                                       Authentication authentication) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.editRequisition(idR, txtEdit,
                    ((MyUserDetails) authentication.getPrincipal()).getId())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * отправлять заявки на рассмотрение оператору.(USER)
     *
     * @param idR-id заявки
     * @return
     */
    @PutMapping("/sendRequisition")
    public ResponseEntity<ResponseResult<Requisition>> sendRequisition(@RequestParam long idR,
                                                                       Authentication authentication) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.sendRequisition(idR,
                    ((MyUserDetails) authentication.getPrincipal()).getId())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    //BY OPERATOR

    /**
     * Просматривать все отправленные на рассмотрение заявки с возможностью сортировки
     * по дате создания в оба направления (как от самой старой к самой новой, так и наоборот)(OPERATOR)
     *
     * @param typeOfSort-параметр сортировки 1-прямая, 2 обратная
     * @return
     */
    @GetMapping("/listRequisitionsForOperator")
    public ResponseEntity<ResponseResult<List<Requisition>>> listRequisitionsForOperator(@RequestParam int typeOfSort) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.
                    listRequisitionsForOperator(typeOfSort)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Просматривать отправленные заявки только конкретного пользователя по его
     * имени/части имени (у пользователя, соотетственно, должно быть поле name) с
     * возможностью сортировки по дате создания в оба направления (как от самой старой к
     * самой новой, так и наоборот)(OPERATOR)
     *
     * @param nameOrSubName-имя   или часть имени
     * @param typeOfSort-параметр сортировки 1-прямая, 2 обратная
     * @return
     */
    @GetMapping("/listRequisitionsByNameOrSubNameForOperator")
    public ResponseEntity<ResponseResult<List<Requisition>>> listRequisitionsByNameOrSubNameForOperator(@RequestParam String nameOrSubName,
                                                                                                        @RequestParam int typeOfSort) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.listRequisitionsByNameOrSubNameForOperator(
                    nameOrSubName, typeOfSort)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Принимать заявки(OPERATOR)
     *
     * @param id-id     заявки
     * @param idUser-id пользовтеля
     * @return
     */
    @PutMapping("/approveRequisition")
    public ResponseEntity<ResponseResult<Requisition>> approveRequisition(@RequestParam long id, @RequestParam long idUser) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.approveRequisition(
                    id, idUser)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Отклонять заявки(OPERATOR)
     *
     * @param id-id     заявки
     * @param idUser-id пользовтеля
     * @return
     */
    @PutMapping("/rejectRequisition")
    public ResponseEntity<ResponseResult<Requisition>> rejectRequisition(@RequestParam long id, @RequestParam long idUser) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.requisitionService.rejectRequisition(
                    id, idUser)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
