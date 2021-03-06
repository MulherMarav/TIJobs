package br.com.tijobs.controller.empresa;

import static br.com.tijobs.util.Message.addDetailMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.tijobs.model.Empresa;
import br.com.tijobs.model.Vaga;
import br.com.tijobs.service.VagaService;
import br.com.tijobs.util.UtilService;

@Named
@ViewScoped
public class DashboardEmpresaController {

	private List<Vaga> vagas;

	private Vaga vagaSelecionada;

	private Empresa empresa;

	@Autowired
	private VagaService vagaService;

	@Autowired
	private UtilService utilService;
	

	@PostConstruct
	public void init() {

		empresa = utilService.perfilEmpresa();

		if (empresa != null) {
			vagas = vagaService.buscaVagasPeloIdEmpresa(empresa);
		} else {
			vagas = new ArrayList<Vaga>();
		}
		
	}

	public String tecnologiasCores() {

		List<String> backgroundColors = utilService.getBackgroundColors();

		List<String> colors = utilService.getColors();

		Random random = new Random();

		int numero = random.nextInt(5);

		return "border-radius: 16px; background-color: " + backgroundColors.get(numero)
				+ "; padding: 0 0.5rem; font-size: 15px; color: " + colors.get(numero) + "; font-weight: 600;";
	}

	public String avatarCores() {

		List<String> colors = utilService.getColors();

		Random random = new Random();

		int numero = random.nextInt(5);

		return "vertical-align: middle; border-radius: 50px; border: 2px solid " + colors.get(numero)
				+ "; margin-right: 5px;";
	}

	public void desativarVaga(Vaga vaga) {

		vagaService.desativaVaga(vaga);
		vagas = vagaService.buscaVagasPeloIdEmpresa(empresa);

		addDetailMessage("Vaga desativada", FacesMessage.SEVERITY_INFO);
	}

	public void ativarVaga(Vaga vaga) {
		
		vagaService.ativaVaga(vaga);
		vagas = vagaService.buscaVagasPeloIdEmpresa(empresa);

		addDetailMessage("Vaga ativada", FacesMessage.SEVERITY_INFO);
	}

	public String getFotoStr() {
		return utilService.fotoUsuarioLogado();
	}

	public List<Vaga> getVagas() {
		Collections.sort(vagas);

		vagas = vagas.stream().sorted(Comparator.comparing(Vaga::getDesativada)).collect(Collectors.toList());
		return vagas;
	}

	public Integer quantidadeDeCandidatos() {
		if (empresa != null) {
			List<Integer> quantidade = vagaService.buscaQuantidadeDeCandidatosPorEmpresa(empresa.getId());
			return quantidade.size();
		} else {
			return 0;
		}
	}

	public Vaga getVagaSelecionada() {
		return vagaSelecionada;
	}

	public void setVagaSelecionada(Vaga vagaSelecionada) {
		this.vagaSelecionada = vagaSelecionada;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
}
