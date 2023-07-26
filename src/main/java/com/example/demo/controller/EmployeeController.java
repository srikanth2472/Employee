package com.example.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.EmloyeeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import  com.example.demo.model.Employee;


@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmloyeeRepository repo;
	
	
	//get all the employee detalis
	
	@GetMapping("/emp")
	public List<Employee> getAll(){
		return repo.findAll();	
	}
 
	//create a new employee
	@PostMapping("/emp/create")
	public Employee createEmp(@RequestBody Employee emp) {
	 return repo.save(emp);	
	}
	
	//get the emp details by the id 
	@GetMapping("/emp/{id}")
	public ResponseEntity<Employee> getEmplybyId(@PathVariable Long id) {
		
		Employee e= repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("The emp id nort exixts in the system "+id));
		
		return ResponseEntity.ok(e);
		
	}
	
	//upadting the emp details 
	
	@PutMapping("/emp/{id}")
	public ResponseEntity<Employee> updateEmp(@PathVariable Long id,@RequestBody Employee emp){
		Employee emp1= repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("The emp id nort exixts in the system "+id));
		
		emp1.setFirstName(emp.getFirstName());
		emp1.setLastName(emp.getLastName());
		emp1.setEmail(emp.getEmail());
		emp1.setDate(emp.getDate());
		 Employee e=   repo.save(emp1);
		 
		return ResponseEntity.ok(e);
		
	}
	//delete the employee
	@DeleteMapping("/emp/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmp(@PathVariable long  id){
		
		Employee e= repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("the id is not exixting the sysyem"));
		repo.delete(e);
		
		Map<String, Boolean> m= new HashMap<>();
		m.put("deleted", true);
		return ResponseEntity.ok(m);
		
	}
	
	
	
}
