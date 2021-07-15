package spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.jpa.postgresql.model.Issue;
import spring.jpa.postgresql.repository.IssueRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/circuit")
public class IssueController {

	@Autowired
	IssueRepository issueRepository;

	@GetMapping
	public ResponseEntity<List<Issue>> getAllCircuit(@RequestParam(required = false) String protocol){
		try {
			List<Issue> issues = new ArrayList<Issue>();

			if (protocol == null)
				issueRepository.findAll().forEach(issues::add);
			else
				issueRepository.findByProtocol(protocol).forEach(issues::add);
			
			if (issues.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(issues, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Issue> getById(@PathVariable("id") long id) {
		Optional<Issue> circuitData = issueRepository.findById(id);

		if (circuitData.isPresent()) {
			return new ResponseEntity<>(circuitData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	@GetMapping("byCircuit/{circuit}")
	public ResponseEntity<List<Issue>> getByCircuit(@PathVariable("circuit") String circuit) {
		List<Issue> circuitData = new ArrayList<Issue>();

		if (circuit != null) {
			issueRepository.findByProtocol(circuit).forEach(circuitData::add);
			return new ResponseEntity<>(circuitData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("byCustomer/{customer}")
	public ResponseEntity<List<Issue>> getByCustomer(@PathVariable("customer") String customer) {
		List<Issue> circuitData = new ArrayList<Issue>();

		if (customer != null) {
			issueRepository.findByCustomer(customer).forEach(circuitData::add);
			return new ResponseEntity<>(circuitData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Issue> createCircuit(@RequestBody Issue circuit) {
		try {
			Issue _circuit = issueRepository
					.save(new Issue(circuit.getProtocol(), circuit.getCircuit(), circuit.getCustomer()));
			return new ResponseEntity<>(_circuit, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Issue> updateCircuit(@PathVariable("id") long id, @RequestBody Issue circuit) {
		Optional<Issue> circuitData = issueRepository.findById(id);

		if (circuitData.isPresent()) {
			Issue issue = circuitData.get();
			issue.setProtocol(circuit.getProtocol());
			issue.setCircuit(circuit.getCircuit());
			issue.setCustomer(circuit.getCustomer());
			return new ResponseEntity<>(issueRepository.save(issue), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteCircuit(@PathVariable("id") long id) {
		try {
			issueRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllCircuits() {
		try {
			issueRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/protocol")
	public ResponseEntity<List<Issue>> findByProtocol(@PathVariable("protocol") String protocol) {
		try {
			List<Issue> issues = issueRepository.findByProtocol(protocol);

			if (issues.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(issues, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
