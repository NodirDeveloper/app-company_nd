package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.service.DepartmentService;
import uz.pdp.appcompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;


    @GetMapping
    public ResponseEntity<?> getWorkerListMapping(){
        List<Worker> workerList= workerService.getWorkerList();
        return ResponseEntity.ok(workerList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkerMapping(@PathVariable Integer id){
        Worker worker=workerService.getWorker(id);
        return ResponseEntity.ok(worker);
    }

    @PostMapping
    public ResponseEntity<?> addWorkerMapping(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse=workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> editWorkerMapping(@RequestBody WorkerDto workerDto,@PathVariable Integer id){
        ApiResponse apiResponse=workerService.editWorker(workerDto,id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkerMapping(@PathVariable Integer id){
        ApiResponse apiResponse= workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
