package dad.javafx.ahorcado;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogoRegistro {

	Stage dialog;

	
	TextField nameText;
	Button send;

	Long position;

	public DialogoRegistro() {

		dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
                dialog.setTitle("Introduce tu nombre");

		nameText = new TextField();
		nameText.setPromptText("Nombre");

		send = new Button("Enviar");
		send.setDefaultButton(true);
		send.setOnAction(e -> onSendAction());

		HBox contenidoBox = new HBox( nameText, send);

		Scene scene = new Scene(new Group(contenidoBox));
		dialog.setScene(scene);
		dialog.setOnCloseRequest(e -> onCloseAction());
		dialog.showAndWait();

	}

	private void onCloseAction() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Por favor introduce un nombre.");
		alert.showAndWait();
		dialog.showAndWait();
	}

	private void onSendAction() {
		dialog.close();
	}

	public String getNombre() {
		return nameText.getText();
	}

}
