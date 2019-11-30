package dad.javafx.ahorcado;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InsertDialog {
	
	Stage dialog;
	
	Label wordLabel;
	TextField wordText;
	Button send;
	
	Long position;
	
	public InsertDialog(Long pos) {
		
		position = pos;
		
		dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
		
		wordLabel = new Label("Inserte la palabra");
		
		wordText = new TextField();
		wordText.setPromptText("Palabra");
		
		send = new Button("Enviar");
		send.setDefaultButton(true);
		send.setOnAction(e -> onEnviarAction());
		
		HBox contenidoBox = new HBox(wordLabel, wordText, send);
		
		Scene scene = new Scene(new Group(contenidoBox));
		dialog.setScene(scene);
		dialog.showAndWait();
		
	}
	
	private void onEnviarAction() {
		
		String word = wordText.getText();
		
		if (word.length() > 0) {
			File palabras = new File("palabras.dat");
			RandomAccessFile random;
			try {
				random = new RandomAccessFile(palabras, "rw");
				random.seek(position);
				
				random.writeUTF(word);
				
				dialog.close();
			} catch (IOException e) {
			}
		}
	}
	
}
