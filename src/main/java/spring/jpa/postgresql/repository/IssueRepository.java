package spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.jpa.postgresql.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

  List<Issue> findByCircuitContaining(String circuit);
  
  List<Issue> findByCircuit(String circuit);
  
  List<Issue> findByCustomer(String customer);

  List<Issue> findByProtocol(String protocol);  

}
