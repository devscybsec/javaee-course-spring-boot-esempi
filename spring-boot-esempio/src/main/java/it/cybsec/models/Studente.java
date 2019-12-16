package it.cybsec.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


/**
 * The persistent class for the studenti database table.
 * 
 */
@Entity
@Table(name="studenti")
public class Studente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String cognome;

	@Column(name="data_nascita")
	@JsonFormat(pattern="dd-MM-yyyy", timezone="UTC")
	private LocalDate dataNascita;
	
	@JsonInclude
	@Transient
	public Integer getEtà() {
		return Period.between(dataNascita, LocalDate.now()).getYears();
	}

	private String nome;

	//bi-directional many-to-many association to Corso
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="frequenta"
		, joinColumns={
			@JoinColumn(name="studente")
			}
		, inverseJoinColumns={
			@JoinColumn(name="corso")
			}
		)
	@JsonIgnoreProperties({"studenti"})
	private List<Corso> corsi;

	public Studente() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Corso> getCorsi() {
		return this.corsi;
	}

	public void setCorsi(List<Corso> corsi) {
		this.corsi = corsi;
	}

}