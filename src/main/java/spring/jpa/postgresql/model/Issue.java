package spring.jpa.postgresql.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tutorials", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = {"circuit", "customer"})})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "protocol", nullable = false)
	private String protocol;
	
	@Column(name = "circuit")
	private String circuit;

	@Column(name = "customer")
	private String customer;
	
	private String description;
	
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;


	public Issue() {

	}
	public Issue(String description) {
		this.description = description;
	}

	public Issue(String protocol, String circuit, String customer) {
		this.protocol = protocol;
		this.circuit = circuit;
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getCircuit() {
		return circuit;
	}

	public void setCircuit(String circuit) {
		this.circuit = circuit;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", protocol=" + protocol + ", circuit=" + circuit + ", customer=" + customer + "]";
	}

}
