import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class KenoFX extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Keno");


		Scene scene = createKenoBoard(new Keno());


		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Scene createKenoBoard(Keno k){
		//declaring main vbox
		VBox mainVB = new VBox();

		// creating various layouts to the main VB
		VBox spotCardsVB = createSpotNumbersCard(k);
		VBox betCardVB = createBetCard(k);
		VBox drawingVB = createDrawingVB(k);
		VBox playKenoVB = playKenoVB(k);

		// adding various layouts to the main VB
		mainVB.getChildren().addAll(spotCardsVB, betCardVB, drawingVB, playKenoVB);

		return new Scene(mainVB);
	}

	public VBox createSpotNumbersCard(Keno k){
		// create a VBox
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10, 0, 20, 20));
		Label l1 = new Label("Step 1. How many numbers(spots) so u want to play?");
		vbox.getChildren().add(l1);


		// creating a ned hbox for toggles to reside in
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);

		// create a toggle group
		ToggleGroup tg = new ToggleGroup();
		tg.setUserData("NumSpots");

		// add buttons to VBox
		int i = 0;
		int[] spots = {1,4,8, 10};

		while(i < spots.length) {
			RadioButton r = new RadioButton(""+spots[i]);
			r.setUserData(spots[i]);
			r.setToggleGroup(tg);
			if(spots[i] == 1) r.setSelected(true);
			hbox.getChildren().add(r);
			i++;
		}

		//adding the listner the the toggle group
		k.addSpotNumbersToggleGroupListner(tg);

		vbox.getChildren().add(hbox);

		return vbox;
	}

	public VBox createBetCard(Keno k){

		// create a VBox
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10, 20, 20, 20));
		Label l2 = new Label("Step 2. Pick your own numbers, or chose quick pick");
		vbox.getChildren().add(l2);


		// add buttons to VBox
		int num = 1;
		for (int i = 1; i <= 10; i++)
		{
			HBox hbox = new HBox(10);
			hbox.setAlignment(Pos.CENTER);

			for ( int j = 1; j <=8; j++){
				Button btn = new Button(""+ num);
				btn.setId(""+ num);
				k.addBetNumButtonsListner(btn);

				// adding button styling
				btn.setMinWidth(30);
//				btn.setStyle("-fx-background-color: white; -fx-text-fill: gray;");

				//adding the button to the hbox
				hbox.getChildren().add(btn);
				num++;
			}

			vbox.getChildren().add(hbox);
		}
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10, 10, 0, 0));
		hbox.setAlignment(Pos.CENTER);
		Button b = new Button(" For quick pic Click Me!");
		k.generateRandomSpotNumbersButtonListner(b);
		hbox.getChildren().addAll(b);
		vbox.getChildren().add(hbox);


		return vbox;
	}

	public VBox createDrawingVB(Keno k){
		// create a VBox
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10, 0, 20, 20));
		vbox.setAlignment(Pos.CENTER);
		Label l2 = new Label("Step 3. How many consecutive draws do you want to play?");
		vbox.getChildren().add(l2);

		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER);
		// create a toggle group
		ToggleGroup tg = new ToggleGroup();
		tg.setUserData("DrawingNumber");
		for(int i = 1; i <=4 ; i++) {
			RadioButton r = new RadioButton(""+i);
			r.setUserData(i);
			r.setToggleGroup(tg);
			if(i == 1) r.setSelected(true);
			hbox.getChildren().add(r);
		}
		k.numDrawToggleListner(tg);
		vbox.getChildren().add(hbox);


		return vbox;
	}

	public VBox playKenoVB(Keno k){
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10, 10, 20, 0));
		vbox.setAlignment(Pos.CENTER);
		Button btn = new Button("Lets Play!");
		k.playKenoListner(btn);
		vbox.getChildren().add(btn);
		return vbox;
	}



}
