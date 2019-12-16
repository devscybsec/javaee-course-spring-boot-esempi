package it.cybsec.models;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the corsi database table.
 * 
 */
@Entity
@Table(name="corsi")
public class Corso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String descrizione;

	private String nome;

	//bi-directional many-to-one association to Professore
	@ManyToOne
	@JoinColumn(name="professore")
	@JsonIgnoreProperties({"corsi"})
	private Professore professore;

	//bi-directional many-to-many association to Studente
	@ManyToMany(mappedBy="corsi", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"corsi"})
	private List<Studente> studenti;

	public Corso() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Professore getProfessore() {
		return this.professore;
	}

	public void setProfessore(Professore professore) {
		this.professore = professore;
	}

	public List<Studente> getStudenti() {
		return this.studenti;
	}

	public void setStudenti(List<Studente> studenti) {
		this.studenti = studenti;
	}

}