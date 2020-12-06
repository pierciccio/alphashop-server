package com.pierciccio.webapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "dettlistini")
@Data
public class DettListini implements Serializable
{
	private static final long serialVersionUID = 8777751177774522519L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Size(min = 5, max = 20, message = "{Size.DettListini.codArt.Validation}")
	@NotNull(message = "{NotNull.DettListini.codArt.Validation}")
	@Column(name = "CODART")
	private String codArt;
	
	@Min(value = (long) 0.01, message = "{Min.DettListini.prezzo.Validation}")
	@Column(name = "PREZZO")
	private Double prezzo;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "IDLIST", referencedColumnName = "id")
	@JsonBackReference
	private Listini listino;
	
	public DettListini() {}
	
	public DettListini(String CodArt, Double Prezzo, Listini Listino)
	{
		this.codArt = CodArt;
		this.prezzo = Prezzo;
		this.listino = Listino;
	}
}
