package com.pierciccio.webapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "dettpromo")
@Data
public class DettPromo implements Serializable
{
	private static final long serialVersionUID = 7444232363326102441L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false)
	private int riga;
	
	@Basic(optional = false)
	private String codart;
	
	@Transient
	private String descrizione;
	
	@Transient
	private double prezzo;
	
	@Basic
	private String codfid;
		
	@Column(name = "INIZIO")
	@Temporal(TemporalType.DATE)
	private Date inizio;
	
	@Column(name = "FINE")
	@Temporal(TemporalType.DATE)
	private Date fine;
	
	@Basic(optional = false)
	private String oggetto;
	
	@Basic
	private String isfid;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "IDPROMO", referencedColumnName = "idPromo")
	@JsonBackReference 
	private Promo promo;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "IDTIPOPROMO", referencedColumnName = "idTipoPromo")
	private TipoPromo tipoPromo;

}
