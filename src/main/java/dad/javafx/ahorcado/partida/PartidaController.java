package dad.javafx.ahorcado.partida;

import java.io.IOException;
import java.util.ArrayList;

import dad.javafx.ahorcado.DialogoRegistro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PartidaController {
	
	   @FXML
    private VBox contenedorBox;

    @FXML
    private HBox encabezadoBox;

    @FXML
    private ImageView ahorcadoImage;

    @FXML
    private HBox puntuacionBox;

    @FXML
    private Label puntuacionLabel;

    @FXML
    private Label puntuacionNumberLabel;

    @FXML
    private Label palabraLabel;

    @FXML
    private HBox letrasBox;

    @FXML
    private Label aciertosLabel;

    @FXML
    private Label fallosLabel;

    @FXML
    private HBox introducirBox;

    @FXML
    private TextField introText;

    @FXML
    private Button letterButton;

    @FXML
    private Button resolveButton;

	
	
	PartidaModel model = new PartidaModel();
	
	
	Partida partida = new Partida();
	
	ArrayList<Character> palabra;
	ArrayList<Character> letras;
	
	String palabraAdivinar = "";
	String palabraOculta = "";
	int fallos = 0;
	
	String nombre = "";
	
	public PartidaController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PartidaView.fxml"));
 		loader.setController(this);
		loader.load();
		
		pedirNombre();
		
		nuevaRonda();
		
		
		model.introducidoProperty().bind(introText.textProperty());
		puntuacionNumberLabel.textProperty().bind(model.puntuacionObtenidaProperty().asString());
		model.palabraOcultaProperty().bindBidirectional(palabraLabel.textProperty());
		model.palabraOcultaProperty().addListener((o, ov, nv) -> comprobarVictoria(nv));
		
		resolveButton.setOnAction(e -> onResolveAction());
                letterButton.setOnAction(e -> onLetraAction());
		
	}
	
	public void pistasIniciales() {
		char pista = partida.generaConsonante();
		ArrayList<String> comprobacion = partida.comprobar(palabra, palabraOculta, pista);
		
		if (partida.noEstaRepetidaLetra(pista, letras)) {
			letras.add(pista);
			if (Integer.parseInt(comprobacion.get(0)) > 0) {
				palabraOculta = comprobacion.get(1);
				palabraLabel.setText(palabraOculta);
				aciertosLabel.setText(aciertosLabel.getText() + pista + " ");
			}else {
				fallosLabel.setText(fallosLabel.getText() + pista + " ");
			}
		}else {
			pistasIniciales();
		}
	}
	
	public void pistasInicialesV() {
		char pista = partida.generaVocal();
		ArrayList<String> comprobacion = partida.comprobar(palabra, palabraOculta, pista);
		
		if (partida.noEstaRepetidaLetra(pista, letras)) {
			letras.add(pista);
			if (Integer.parseInt(comprobacion.get(0)) > 0) {
				palabraOculta = comprobacion.get(1);
				palabraLabel.setText(palabraOculta);
				fallosLabel.setText(aciertosLabel.getText() + pista + " ");
			}else {
				fallosLabel.setText(fallosLabel.getText() + pista + " ");
			}
		}else {
			pistasInicialesV();
		}
	}
	
	public void cambiarImagen() {
		ahorcadoImage.setImage(new Image(getClass().getResource("/imagenes/" + (fallos+1) + ".png").toString()));
	}

	private void onLetraAction() {
		if (model.getIntroducido().length() == 1) {
			char introducido = model.getIntroducido().charAt(0);
			if (!comprobarLetras(introducido)) {
			letras.add(model.getIntroducido().charAt(0));
			ArrayList<String> comprobacion = partida.comprobar(palabra, palabraOculta, introducido);
			if (Integer.parseInt(comprobacion.get(0)) > 0) {
				palabraOculta = comprobacion.get(1);
				palabraLabel.setText(palabraOculta);
				aciertosLabel.setText(aciertosLabel.getText() + introducido + " ");
				model.setPuntuacionObtenida(model.getPuntuacionObtenida() + Integer.parseInt(comprobacion.get(0)));
			}else {
				fallosLabel.setText(fallosLabel.getText() + introducido + " ");
				fallos();
				}
			}
		}
		introText.clear();
	}
	
	private boolean comprobarLetras(char letra) {
		for (int i = 0; i < letras.size(); i++) {
			if (letras.get(i) == letra) {
				return true;
			}
		}
		return false;
	}
	private void comprobarVictoria(String nv) {
		if (nv.equals(palabraAdivinar)) {
			siguienteRonda();
		}
	}
	private void onResolveAction() {
		if (model.getIntroducido().length() > 1) {
			String introducido = model.getIntroducido();
			if (introducido.equals(palabraAdivinar)) {
				model.setPuntuacionObtenida(model.getPuntuacionObtenida() + 10);
				siguienteRonda();
			}else {
				fallosLabel.setText(fallosLabel.getText() + introducido + " ");
				fallos();
				fallos();
			}
		}
		introText.clear();
	}
	
	private void fallos() {
		fallos++;
		cambiarImagen();
		if (fallos >= 8) {
			fin();
		}
	}
        private void siguienteRonda() {
		
		Alert ganado = new Alert(AlertType.CONFIRMATION);
		ganado.setHeaderText("Has acertado");
		ganado.showAndWait();
		
		nuevaRonda();
		
	}
	
	private void fin() {
		Alert fin = new Alert(AlertType.ERROR);
		fin.setHeaderText("Ha terminado la partida ");
		fin.setContentText("Tu puntuación es de " + model.getPuntuacionObtenida() );
		fin.showAndWait();
		
		partida.guardarPuntuacion(model.getPuntuacionObtenida(), nombre);
		
		model.setPuntuacionObtenida(0);
		fallos = 0;
		nombre = "";
		cambiarImagen();
		pedirNombre();
		nuevaRonda();
	}
	

	
	
	
	private void pedirNombre() {
		DialogoRegistro registro = new DialogoRegistro();
		
		nombre = registro.getNombre();
	}
	
	private void nuevaRonda() {
		
		palabra = new ArrayList<Character>();
		letras = new ArrayList<Character>();
		palabraOculta = "";
		
		aciertosLabel.setText("Letras acertadas" + '\n');
		fallosLabel.setText("Letras fallidas" + '\n');
		
		palabraAdivinar = partida.palabraAleatoria();
		
		for (int i = 0; i < palabraAdivinar.length(); i++) {
			palabra.add(palabraAdivinar.charAt(i));
		}
		
		for (int i = 0; i < palabraAdivinar.length(); i++) {
			if (palabraAdivinar.charAt(i) == ' ') {
				palabraOculta += " ";
			}else {
				palabraOculta += "_";
			}
		}
		
		palabraLabel.setText(palabraOculta);
		
		pistasIniciales();
		pistasIniciales();
		pistasIniciales();
		pistasInicialesV();
		
	}

	public VBox getRoot() {
		return contenedorBox;
	}
	
}
