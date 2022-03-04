package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;
	
	// index
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}
	
	//show
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer customer = customerService.getCustomer(customerId);
		if(customer == null) {
			throw new CustomerNotFoundException("Id not found.");
		}
		return customer;
	}
	
	//create
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		//0 means "empty" for hibernate
		//it will create a new id for the table
		customer.setId(0);
		customerService.saveCustomer(customer);
		return customer;
	}
	
	//update
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		//our service uses .saveOrUpdate() with its
		//.saveCustomer method
		customerService.saveCustomer(customer);
		return customer;
	}
	
	//delete
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		Customer customer = customerService.getCustomer(customerId);
		if(customer == null) {
			throw new CustomerNotFoundException("Id not found.");
		}
		customerService.deleteCustomer(customerId);
		return "Deleted customer successfully.";
	}
	
}


















