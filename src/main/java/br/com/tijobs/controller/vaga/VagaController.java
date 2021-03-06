package br.com.tijobs.controller.vaga;

import static br.com.tijobs.util.Message.addDetailMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.tijobs.model.Candidato;
import br.com.tijobs.model.Usuario;
import br.com.tijobs.model.Vaga;
import br.com.tijobs.model.habilidade.ListaHabilidade;
import br.com.tijobs.repository.VagaRepository;
import br.com.tijobs.repository.habilidade.ListaHabilidadeRepository;
import br.com.tijobs.service.VagaService;
import br.com.tijobs.util.UtilService;

@Named
@ViewScoped
public class VagaController {
	
	private List<Vaga> vagas;

	private Vaga vagaSelecionada;
	
	private static String habilidade;

	private List<String> distritos;

	private String distritoSelecionado;

	private List<ListaHabilidade> habilidades;
	
	private ListaHabilidade habilidadeSelecionada;

	private Usuario usuarioLogado;

	private Candidato candidatoLogado;

	@Autowired
	private VagaRepository vagaRepository;

	@Autowired
	private ListaHabilidadeRepository habilidadeRepository;

	@Autowired
	private UtilService utilService;

	@Autowired
	private VagaService vagaService;
	

	@PostConstruct
	public void init() {

		if (vagas == null) {
			vagas = vagaService.buscaTodasVagas();
		}

		habilidades = habilidadeRepository.findAll();

		distritos = utilService.buscaDistritosSP();

		usuarioLogado = utilService.usuarioLogado();

		candidatoLogado = utilService.perfilCandidato();
	}
	
	public String iconBeneficio(String beneficio) {
		return vagaService.iconeDoBenefício(beneficio);
	}

	public boolean getVerificaCandidatura() {
		if (vagaSelecionada != null && candidatoLogado != null) {

			Vaga candidatura = vagaRepository.buscaVagaPorIdECandidato(vagaSelecionada.getId(),
					candidatoLogado.getId());

			if (candidatura != null) {
				return true;
			}
		}

		return false;
	}

	public void candidatar() {

		List<Candidato> candidatos = vagaSelecionada.getCandidatos();

		if (!getVerificaCandidatura()) {

			candidatos.add(candidatoLogado);
			vagaSelecionada.setCandidatos(candidatos);

			vagaRepository.save(vagaSelecionada);

			addDetailMessage("Candidatura realizada", FacesMessage.SEVERITY_INFO);
		}

	}

	public void cancelarCandidatura() {

		List<Candidato> candidatos = vagaSelecionada.getCandidatos();

		List<Candidato> candidadosForeach = candidatos;

		for (Candidato candidato : candidadosForeach) {
			if (candidato.getId() == candidatoLogado.getId()) {
				candidatos.remove(candidato);
				break;
			}
		}

		vagaSelecionada.setCandidatos(candidatos);

		vagaRepository.save(vagaSelecionada);

		addDetailMessage("Candidatura cancelada", FacesMessage.SEVERITY_INFO);
	}

	public void cadastrar() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/cadastre.xhtml");
	}
	
	// ------------ FILTROS ------------------

	public void filtroTodas() {
		vagas = vagaRepository.findAll();
		habilidade = null;
	}

	public void filtroCLT() {
		vagas = vagaService.buscaVagasPeloTipoContrato("CLT", habilidade);
	}

	public void filtroPJ() {
		vagas = vagaService.buscaVagasPeloTipoContrato("PJ", habilidade);
	}

	public void filtroEstagio() {
		vagas = vagaService.buscaVagasPeloTipoContrato("Estágio", habilidade);
	}

	public void filtroJunior() {
		vagas = vagaService.buscaVagasPeloNivelExperiencia("Júnior", habilidade);
	}

	public void filtroPleno() {
		vagas = vagaService.buscaVagasPeloNivelExperiencia("Pleno", habilidade);
	}

	public void filtroSenior() {
		vagas = vagaService.buscaVagasPeloNivelExperiencia("Sênior", habilidade);
	}

	public void filtroStartup() {
		vagas = vagaService.buscaVagasPeloTamanhoEmpresa("Startup", habilidade);
	}

	public void filtroPequenaMedia() {
		vagas = vagaService.buscaVagasPeloTamanhoEmpresa("Pequeno Porte", "Médio Porte", habilidade);
	}

	public void filtroGrande() {
		vagas = vagaService.buscaVagasPeloTamanhoEmpresa("Grande Empresa", habilidade);
	}

	public void filtroRemoto() {
		vagas = vagaService.buscaVagasPeloTipoTrabalho("Remoto", habilidade);
	}

	public void filtroAceitaCandidatoFora() {
		vagas = vagaService.buscaVagasPeloAceitaCandidatoFora("Sim", habilidade);
	}

	public void filtroDistrito() {
		if (distritoSelecionado != null) {
			vagas = vagaService.buscaVagasPelaLocalidade(distritoSelecionado, habilidade);
		}
	}
	
	public void filtroHabilidade() {
		if (habilidadeSelecionada != null) {
			habilidade = "%" + habilidadeSelecionada.getNome() + "%";
			vagas = vagaService.buscaVagasPelaHabilidade(habilidade);
		} else {
			habilidade = null;
		}
	}
	
	public List<Vaga> getVagas() {
		Collections.sort(vagas);

		vagas = vagas.stream().sorted(Comparator.comparing(Vaga::getDesativada)).collect(Collectors.toList());
		return vagas;
	}
	
	public Vaga getVagaSelecionada() {
		return vagaSelecionada;
	}

	public void setVagaSelecionada(Vaga vagaSelecionada) {
		if(vagaSelecionada != null) {
			Vaga vaga = vagaService.buscaVagaAtivada(vagaSelecionada.getId());
			
			if (vaga == null) {
				PrimeFaces current = PrimeFaces.current();

				current.executeScript("PF('vagaDialogo').hide();");

				addDetailMessage("Vaga desativada", FacesMessage.SEVERITY_WARN);
				current.ajax().update("growl");
			}
			this.vagaSelecionada = vaga;
		}
	}

	public List<ListaHabilidade> getHabilidades() {
		return habilidades;
	}

	public List<String> getDistritos() {
		return distritos;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public Candidato getCandidatoLogado() {
		return candidatoLogado;
	}

	public String getDistritoSelecionado() {
		return distritoSelecionado;
	}

	public void setDistritoSelecionado(String distritoSelecionado) {
		this.distritoSelecionado = distritoSelecionado;
	}

	public ListaHabilidade getHabilidadeSelecionada() {
		return habilidadeSelecionada;
	}

	public void setHabilidadeSelecionada(ListaHabilidade habilidadeSelecionada) {
		this.habilidadeSelecionada = habilidadeSelecionada;
	}
}