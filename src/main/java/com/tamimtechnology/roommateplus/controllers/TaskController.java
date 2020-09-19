package com.tamimtechnology.roommateplus.controllers;

import com.tamimtechnology.roommateplus.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/trash")
    public ResponseEntity<?> recordTrashOut(HttpServletRequest httpServletRequest){
        taskService.takeOutTrash(httpServletRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
