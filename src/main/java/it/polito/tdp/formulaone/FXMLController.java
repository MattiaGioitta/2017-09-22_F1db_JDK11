package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private Button btnSelezionaStagione;

    @FXML
    private ComboBox<Race> boxGara;

    @FXML
    private Button btnSimulaGara;

    @FXML
    private TextField textInputK;

    @FXML
    private TextField textInputK1;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	this.txtResult.clear();
    	Season chosen = this.boxAnno.getValue();
    	if(chosen == null) {
    		this.txtResult.setText("Choose a season!");
    		return;
    	}
    	this.model.createGraph(chosen);
    	this.txtResult.appendText("Grafo creato con: \n");
    	this.txtResult.appendText(String.format("#Vertici: %d\n#Archi: %d\n", this.model.nVertici(),this.model.nArchi()));
    	List<Adiacenza> massimo = this.model.getMassimi();
    	for(Adiacenza a : massimo) {
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    	this.boxGara.getItems().addAll(this.model.getGare());
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	this.txtResult.clear();
    	Race scelto = this.boxGara.getValue();
    	if(scelto == null) {
    		this.txtResult.setText("Scegli una gara");
    		return;
    	}
    	Double prob;
    	Integer t;
    	try {
    		prob = Double.parseDouble(this.textInputK.getText());
    		t = Integer.parseInt(this.textInputK1.getText());
    		this.model.simula(t,prob,scelto);
    		List<Driver> lista = this.model.getDrivers();
    		for(Driver d : lista) {
    			this.txtResult.appendText(d.getDriverId()+" punti: "+d.getPunti()+"\n");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Valori inseriti in un formato non valido");
    		return;
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getSeasons());
	}
}
