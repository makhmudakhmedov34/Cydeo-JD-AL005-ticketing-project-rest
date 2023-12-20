package com.cydeo.controller;


import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO> taskList = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskList, HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){
        TaskDTO taskDTO = taskService.findById(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",taskDTO,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is successfully created",HttpStatus.CREATED));
    }

    @DeleteMapping("/{taskId}")
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted",HttpStatus.OK));
    }

    @PutMapping
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> pendingTaskList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",pendingTaskList, HttpStatus.OK));

    }

    @PutMapping("/employee/edit")
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully updated",HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Manager")
    private ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
    List<TaskDTO> taskList = taskService.listAllTasksByStatus(Status.COMPLETE);
    return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskList, HttpStatus.OK));

    }
}
