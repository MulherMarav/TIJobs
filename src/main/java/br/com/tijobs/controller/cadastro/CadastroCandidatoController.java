package br.com.tijobs.controller.cadastro;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.tijobs.model.Candidato;
import br.com.tijobs.model.Habilidade;
import br.com.tijobs.repository.CandidatoRepository;
import br.com.tijobs.repository.HabilidadeRepository;
import br.com.tijobs.util.UtilService;

@Named
@ViewScoped
public class CadastroCandidatoController {

	private List<String> distritos;
	
	private Candidato candidato;
	
	@Autowired
	private CandidatoRepository candidatoRepository;

	private List<Habilidade> habilidades;

	@Autowired
	private HabilidadeRepository habilidadeRepository;

	private UploadedFile foto;

	private UploadedFile curriculo;
	
	private List<String> tamanhoEmpresa;

	private List<String> tipoContrato;
	
	@Autowired
	private UtilService utilService;

	@PostConstruct
	public void init() {

		habilidades = habilidadeRepository.buscaTodasHabilidades();

		if (candidato == null) {
			candidato = new Candidato();
		}
		
		distritos = utilService.buscaDistritosSP();
	}

	public void salvar() {
		candidatoRepository.save(candidato);
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile file) {
		this.foto = file;
		if (file != null) {
			candidato.setFoto(file.getContent());
		}
	}

	public UploadedFile getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(UploadedFile file) {
		this.curriculo = file;
		if (file != null) {
			candidato.setCurriculo(file.getContent());
		}
	}

	public List<String> getTamanhoEmpresa() {
		return tamanhoEmpresa;
	}

	public void setTamanhoEmpresa(List<String> tamanhoEmpresa) {
		this.tamanhoEmpresa = tamanhoEmpresa;
		if(tamanhoEmpresa != null && !tamanhoEmpresa.isEmpty()) {
			candidato.setTamanhoEmpresa(tamanhoEmpresa.stream()
			        .map(String::valueOf)
			        .collect(Collectors.joining(",")));
		}	
	}
	
	public List<String> getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(List<String> tipoContrato) {
		this.tipoContrato = tipoContrato;
		if(tipoContrato != null && !tipoContrato.isEmpty()) {
			candidato.setTipoContrato(tipoContrato.stream()
			        .map(String::valueOf)
			        .collect(Collectors.joining(",")));
		}
	}

	public List<String> getDistritos() {
		return distritos;
	}

	public List<Habilidade> getHabilidades() {
		return habilidades;
	}

}