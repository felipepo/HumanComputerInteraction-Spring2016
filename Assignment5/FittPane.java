package application;

import java.awt.MouseInfo;
import java.awt.Point;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FittPane extends GridPane{
	Integer attempts;
	Integer corrects;
	ArrayList<Target> targetList;
	EventHandler<MouseEvent> eventHandler;
	
	public FittPane() {
		super();
		targetList = new ArrayList<Target>();
		attempts = 0; 
		corrects = 0;
		eventHandler = null;
		
		setAlignment(Pos.CENTER);
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(15, 15, 15, 15));
		
		Text startText = new Text("Fitt's Law Simulator");
		startText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(startText, 0, 0);
		Label attemptsLabel = new Label("Enter number of attempts:");
		add(attemptsLabel, 0, 1);
		TextField attemptsField = new TextField();
		add(attemptsField, 1, 1);
		Button button = new Button("Start");
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		hbox.getChildren().add(button);
		add(hbox, 1, 4);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent e) {
		        attempts = Integer.parseInt(attemptsField.getText());
		        if(attempts > 0) {
		        	attempt(1);
		        }
		        else {
		        	Alert alert = new Alert(AlertType.ERROR, "At least 1 attempt", ButtonType.OK);
		        	alert.showAndWait();
		        	if(alert.getResult() == ButtonType.OK) {
		        		alert.close();
		        	}
		        }
		    }
		});
	}

	public void attempt(int number){
		if(eventHandler != null) {
			removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler);
		}
		setHgap(0);
		setVgap(0);
		setPadding(new Insets(0, 0, 0, 0));
		setAlignment(Pos.TOP_LEFT);
		getChildren().clear();
		Pane pane = new Pane();
		Label label = new Label("Attempt " + number + " of " + attempts);
		add(label, 0, 0);
		Point startPoint = MouseInfo.getPointerInfo().getLocation();
		Instant startTime = Instant.now();
		Target target = new Target(getScene().getWidth(), getScene().getHeight());
		Circle circle = new Circle(target.getWidth());
		circle.setCenterX(target.getX());
		circle.setCenterY(target.getY());
		circle.setFill(new Color(Math.random(), Math.random(), Math.random(), 1 ) );
		pane.getChildren().add(circle);
		addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandler = new EventHandler<MouseEvent>() {
			@Override
		    public void handle(MouseEvent event) {
				Point point = MouseInfo.getPointerInfo().getLocation();
				Instant time = Instant.now();
				target.setID( point.distance(startPoint) );
				target.setMT( Duration.between(startTime, time).toNanos() );
				if(event.getTarget().equals(circle)) {
					corrects ++;
					target.setSuccess(true);
				}
				targetList.add(target);
				if(number < attempts) {
					attempt(number+1);
				}
				else {
					endSimulation();
				}
			}
		});
		add(pane, 0, 1);
	}
	
	public void endSimulation() {
		getChildren().clear();
		Pane pane = new Pane();
		NumberAxis scX = new NumberAxis();
		NumberAxis scY = new NumberAxis();
		scX.setLabel("ID");
		scY.setLabel("MT");
		LineChart<Number,Number> lineChart = new LineChart<>( scX, scY );
		final XYChart.Series<Number,Number> lcSeries = new XYChart.Series<>();
		final XYChart.Series<Number,Number> scSeries = new XYChart.Series<>();
		LinearRegression linearRegression = new LinearRegression();
		for(int i = 0; i < targetList.size(); i++) {
			if(targetList.get(i).isSuccess()) {
				scSeries.getData().add( new XYChart.Data<Number,Number>( targetList.get(i).getID(), targetList.get(i).getMT() ) );
				linearRegression.addPoint(targetList.get(i).getID(), targetList.get(i).getMT());
			}
		}
		if(linearRegression.getSize() > 0) {
			linearRegression.calculateParameters();
			lcSeries.getData().add(new XYChart.Data<Number,Number>(linearRegression.getFirstPointX(), linearRegression.getFirstPointY()));
			lcSeries.getData().add(new XYChart.Data<Number,Number>(linearRegression.getLastPointX(), linearRegression.getLastPointY()));
			lineChart.setLegendVisible( false );
			lineChart.setAnimated(false);
		    lineChart.setCreateSymbols(true);
		    lineChart.getData().addAll(scSeries, lcSeries);
			lineChart.setPrefSize(getScene().getWidth(), getScene().getHeight());
			pane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			pane.getChildren().add(lineChart);
		}
		Label label = new Label("Percentage of correct attempts: " + (double)corrects/attempts * 100 + " %");
		add(label,0, 0);
		add(pane, 0, 1);
	}
}
