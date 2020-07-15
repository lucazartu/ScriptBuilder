package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import exceptions.BaseNaoInformadaException;
import exceptions.ChavePrimariaObrigatoriaException;
import exceptions.NomeTabelaNaoInformadaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import negocio.GerenciadorScript;
import negocio.ValidadorParametros;

public class ScriptBuilderController implements Initializable {
	@FXML
	private TextField txtBaseDados;

	@FXML
	private TextField txtNomeTabela;

	@FXML
	private CheckBox chkbxIdentity;

	@FXML
	private TextField txtChavePrimaria;

	@FXML
	private TextField txtCaminhoArquivoScript;

	@FXML
	private TextField txtDiretorioSaida;

	@FXML
	private ToggleGroup tglGrpTipoScript;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	/**
	 * Método responsável por resetar todos os campos do software e recarregar todas
	 * as informações. Atrelado ao botão "Recarregar".
	 * 
	 * @param event
	 */
	public void regarregar(ActionEvent event) {
		this.txtBaseDados.deleteText(0, txtBaseDados.getLength());
		this.txtNomeTabela.deleteText(0, this.txtNomeTabela.getLength());
		this.txtChavePrimaria.deleteText(0, txtChavePrimaria.getLength());
		this.tglGrpTipoScript.getToggles().get(0).setSelected(true);
	}

	/**
	 * Método responsável por selecionar o arquivo CSV contendo os valores do
	 * script.
	 * 
	 * @param event
	 */
	public void procurarArquivoScript(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File arquivo = fc.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

		if (arquivo != null) {
			this.txtCaminhoArquivoScript.setText(arquivo.getAbsolutePath());
		}

	}

	/**
	 * Método responsável por selecionar o diretório de saída.
	 * 
	 * @param event
	 */
	public void selecionarDiretorioSaida(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		File arquivo = dc.showDialog(((Node) event.getSource()).getScene().getWindow());

		if (arquivo != null) {
			this.txtDiretorioSaida.setText(arquivo.getAbsolutePath());
		}
	}

	@FXML
	/**
	 * Método responsável por gerar o script baseado nos parametros de entrada do
	 * programa. Atrelado ao botão "Gerar Script"
	 * 
	 * @param event
	 */
	public void gerarScript(ActionEvent event) {
		Alert alertaValidacao = new Alert(AlertType.WARNING);
		String baseDeDados = this.txtBaseDados.getText();
		String nomeTabela = this.txtNomeTabela.getText();
		boolean utilizaIdentity = this.chkbxIdentity.isSelected();
		String chavePrimaria = this.txtChavePrimaria.getText().replaceAll("\\s", "").toUpperCase();
		String tipoScript = ((RadioButton) this.tglGrpTipoScript.getSelectedToggle()).getText();
		String caminhoArquivo = this.txtCaminhoArquivoScript.getText();
		String diretorioSaida = this.txtDiretorioSaida.getText();

		try {
			ValidadorParametros.validarParametros(baseDeDados, nomeTabela, chavePrimaria, utilizaIdentity);
			GerenciadorScript.gerarScript(baseDeDados, nomeTabela, utilizaIdentity, chavePrimaria, tipoScript, caminhoArquivo,
					diretorioSaida);
		} catch (BaseNaoInformadaException e) {
			alertaValidacao.setTitle("Base de Dados");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (NomeTabelaNaoInformadaException e) {
			alertaValidacao.setTitle("Nome da Tabela");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (ChavePrimariaObrigatoriaException e) {
			alertaValidacao.setTitle("Chave primária");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (FileNotFoundException e) {
			alertaValidacao.setTitle("Arquivo CSV");
			alertaValidacao.setContentText("Arquivo CSV não encontrado.");
			alertaValidacao.show();
		} catch (Exception e) {
			alertaValidacao.setAlertType(AlertType.ERROR);
			alertaValidacao.setTitle("ERRO Não Tratado");
			alertaValidacao.setContentText("Erro não identificado, favor, verifique os parametros e o arquivo CSV");
			alertaValidacao.show();
		}

	}

}
