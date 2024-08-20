package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController{


    @Autowired
    StudentRepo studRepo;

    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(studRepo.save(student), HttpStatus.CREATED);
    }

    @GetMapping("/api/students")
    public ResponseEntity<List<Student>> getStudents(){
        return new ResponseEntity<>(studRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id){
        Optional<Student> student = studRepo.findById(id);
        if(student.isPresent()){
            return new ResponseEntity<>(student.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id , @RequestBody Student stud){
        Optional<Student> student=studRepo.findById(id);
        if(student.isPresent()){
            student.get().setEmail(stud.getEmail());
            student.get().setName(stud.getName());
            return new ResponseEntity<>(studRepo.save(student.get()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
     }

     @DeleteMapping("/api/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
         Optional<Student> student = studRepo.findById(id);
         if (student.isPresent()) {
             studRepo.deleteById(id);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);

         } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     }


}
