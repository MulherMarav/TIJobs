package br.com.tijobs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "vaga")
public class Vaga implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5058507194333298243L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String anonima;
	
	private String aceitaCandidatoFora;
	
	@Lob
	private String apresentacao;
	
	private String tipoTrabalho;
	
	private String profissao;
	
	private String nivelExperiencia;
	
	private String salario;
	
	private String tipoContrato;
	
	private String principalTecnologia;
	
	@Lob
	private String responsabilidades;
	
	@Lob
	private String skillsDesejaveis;
	
	@Lob
	private String skillsObrigatorias;
	
	@ManyToOne
	private Empresa empresa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnonima() {
		return anonima;
	}

	public void setAnonima(String anonima) {
		this.anonima = anonima;
	}

	public String getAceitaCandidatoFora() {
		return aceitaCandidatoFora;
	}

	public void setAceitaCandidatoFora(String aceitaCandidatoFora) {
		this.aceitaCandidatoFora = aceitaCandidatoFora;
	}

	public String getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}

	public String getTipoTrabalho() {
		return tipoTrabalho;
	}

	public void setTipoTrabalho(String tipoTrabalho) {
		this.tipoTrabalho = tipoTrabalho;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getNivelExperiencia() {
		return nivelExperiencia;
	}

	public void setNivelExperiencia(String nivelExperiencia) {
		this.nivelExperiencia = nivelExperiencia;
	}

	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getPrincipalTecnologia() {
		return principalTecnologia;
	}

	public void setPrincipalTecnologia(String principalTecnologia) {
		this.principalTecnologia = principalTecnologia;
	}

	public String getResponsabilidades() {
		return responsabilidades;
	}

	public void setResponsabilidades(String responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	public String getSkillsDesejaveis() {
		return skillsDesejaveis;
	}

	public void setSkillsDesejaveis(String skillsDesejaveis) {
		this.skillsDesejaveis = skillsDesejaveis;
	}

	public String getSkillsObrigatorias() {
		return skillsObrigatorias;
	}

	public void setSkillsObrigatorias(String skillsObrigatorias) {
		this.skillsObrigatorias = skillsObrigatorias;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}