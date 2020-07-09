package application;

import java.net.URL;
import java.util.ResourceBundle;

import exceptions.BaseNaoInformadaException;
import exceptions.IdentificadorInvalidoException;
import exceptions.IndentificadorNaoInformadoException;
import exceptions.NomeTabelaNaoInformadaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import negocio.GerenciadorParametros;
import negocio.GerenciadorScript;

public class ScriptBuilderController implements Initializable {
	@FXML
	private ComboBox<String> drpDwnBaseDados;

	@FXML
	private TextField txtNomeTabela;

	@FXML
	private TextArea txtIdentificadoresUnicos;

	@FXML
	private ToggleGroup tglGrpTipoScript;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.drpDwnBaseDados.getItems().addAll(GerenciadorParametros.popularBaseDados());
	}

	@FXML
	/**
	 * Método responsável por resetar todos os campos do software e recarregar todas
	 * as informações.
	 * 
	 * @param event
	 */
	public void regarregar(ActionEvent event) {
		this.drpDwnBaseDados.setValue(null);
		this.txtIdentificadoresUnicos.deleteText(0, txtIdentificadoresUnicos.getLength());
		this.tglGrpTipoScript.getToggles().get(0).setSelected(true);
	}

	@FXML
	/**
	 * Método responsável por gerar o script baseado nos parametros de entrada do
	 * programa.
	 * 
	 * @param event
	 */
	public void gerarScript(ActionEvent event) {
		Alert alertaValidacao = new Alert(AlertType.WARNING);
		String baseDeDados = this.drpDwnBaseDados.getValue();
		String nomeTabela = this.txtNomeTabela.getText();
		String identificadores = this.txtIdentificadoresUnicos.getText().replaceAll("\\s", "").toUpperCase();
		String tipoScript = ((RadioButton) this.tglGrpTipoScript.getSelectedToggle()).getText();

		try {
			GerenciadorParametros.validarParametros(baseDeDados, nomeTabela, identificadores);
			GerenciadorScript.gerarScript(baseDeDados, nomeTabela, identificadores, tipoScript);
		} catch (BaseNaoInformadaException e) {
			alertaValidacao.setTitle("Base de Dados");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (NomeTabelaNaoInformadaException e) {
			alertaValidacao.setTitle("Nome da Tabela");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (IndentificadorNaoInformadoException e) {
			alertaValidacao.setTitle("Identificador");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		} catch (IdentificadorInvalidoException e) {
			alertaValidacao.setTitle("Identificador Inválido");
			alertaValidacao.setContentText(e.getMessage());
			alertaValidacao.show();
		}
	}

}
