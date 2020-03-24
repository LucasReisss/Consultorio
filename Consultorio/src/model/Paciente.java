package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Paciente extends DefaultEntity<Paciente> {

	private static final long serialVersionUID = 8808843944013495869L;

	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String sexo;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataAniversario;
	
	@Column(length = 11)
	private Integer cpf;
	
	@Column(length = 100)
	private Integer idade;
	
	@Column(length = 100)
	private String estadoCivil;
	
	@Column(length = 7)
	private Integer rg;
	
	@Column(length = 100)
	private String naturalidade;
	
	@Column(length = 100)
	private String emissor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public Date getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(Date dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public int getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Integer getRg() {
		return rg;
	}

	public void setRg(Integer rg) {
		this.rg = rg;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}

}
