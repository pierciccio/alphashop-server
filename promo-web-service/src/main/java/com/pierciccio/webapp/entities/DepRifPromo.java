package com.pierciccio.webapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "deprifpromo")
@Data
public class DepRifPromo  implements Serializable
{
private static final long serialVersionUID = 1436206967746080890L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "IDDEPOSITO")
	private int idDeposito;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "IDPROMO", referencedColumnName = "idPromo")
	@JsonBackReference 
	private Promo promo;
	
	
	
	 
}
