package dad.javafx.ahorcado;

import dad.javafx.ahorcado.palabras.PalabrasController;
import dad.javafx.ahorcado.partida.PartidaController;
import dad.javafx.ahorcado.puntuacion.PuntuacionController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;

public class App extends Application {
	
	private PartidaController partidaController;
	private PalabrasController palabrasController;
	private PuntuacionController puntuacionController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		partidaController = new PartidaController();
		palabrasController = new PalabrasController();
		puntuacionController = new PuntuacionController();
		Tab partidaTab = new Tab("Partida");
		partidaTab.setContent(partidaController.getRoot());
		Tab palabrasTab = new Tab("Palabras");
		palabrasTab.setContent(palabrasController.getRoot());
		Tab puntuacionTab = new Tab("Puntuación");
		puntuacionTab.setContent(puntuacionController.getRoot());
		TabPane tabs = new TabPane(partidaTab, palabrasTab, puntuacionTab);
		tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		Scene scene = new Scene(tabs);
		
		primaryStage.setTitle("Ahorcado");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		
		launch(args);

	}

}
