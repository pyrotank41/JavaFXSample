/* Scene - Game
 * Sets up and controls the Keno gameplay
 */
package KenoGame;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scene_Game implements SceneHolder
{
    private Scene scene;
    private Stage stage;

    Scene_Game(Stage stage)
    {
        this.stage = stage;
    }

    /* Activate
     * Checks if the scene already exists, if not, calls for it to be created
     * Changes stage elements to reflect which scene is presently up.
     */
    @Override
    public void Activate()
    {
        if(stage == null)
        {
            System.out.println("Need a valid stage!");
            return;
        }
        if(scene == null)
            scene = CreateScene(new Keno());

        stage.setTitle("Keno!");
        stage.setScene(scene);
        stage.show();
    }

    /* Create Scene
     * Takes the elements for the scene and assembles them.
     * Modifies scene.
     */
    private Scene CreateScene(Keno k){
        //declaring main vbox
        VBox mainVB = new VBox();

        // creating various layouts to the main VB
        var menuBar = GameMenuBar.CreateMenu(null);
        VBox spotCardsVB = createSpotNumberSlider(k);
        VBox betCardVB = createBetCard(k);
        VBox drawingVB = createDrawingVB(k);
        VBox playKenoVB = playKenoVB(k);

        // adding various layouts to the main VB
        mainVB.getChildren().addAll(menuBar, spotCardsVB, betCardVB, drawingVB, playKenoVB);

        k.setPanels(spotCardsVB, betCardVB, drawingVB, playKenoVB);

        return new Scene(mainVB);
    }

    /* Create Spot number slider
     * Creates and hooks a number slider into the Keno board
     * Returns the VBox containing the slider
     */
    private VBox createSpotNumberSlider(Keno keno)
    {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 20, 20, 20));
        Label directions = new Label("How many Spots do you wish to play?");

        var slider = new Slider(0, 10, 0);
        slider.setSnapToTicks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setUserData("NumSpots");

        keno.addSpotNumberSliderListener(slider);

        vbox.getChildren().addAll(directions, slider);
        return vbox;
    }

    /* Create Bet Card
     * Creates and hooks the Spot buttons into the Keno board
     * Returns the VBox containing the the buttons
     */
    private VBox createBetCard(Keno k){

        // create a VBox
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 20, 20, 20));
        Label l2 = new Label("Pick your own numbers, or choose quick pick!");
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
        Button b = new Button(" Quick Pick!");
        k.generateRandomSpotNumbersButtonListner(b);
        hbox.getChildren().addAll(b);
        vbox.getChildren().add(hbox);

        return vbox;
    }

    /* Create Drawing
     * Creates and hooks the drawing toggle group into the Keno board
     * Returns the VBox containing the toggle group
     */
    private VBox createDrawingVB(Keno k){
        // create a VBox
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 0, 20, 20));
        vbox.setAlignment(Pos.CENTER);
        Label l2 = new Label("How many consecutive draws?");
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

    /* Play Keno create play button
     * Creates and hooks a play button into the Keno board
     * Returns the VBox containing the play button.
     */
    private VBox playKenoVB(Keno k){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 20, 0));
        vbox.setAlignment(Pos.CENTER);
        Button btn = new Button("Let's Play!");
        k.playKenoListner(btn);
        vbox.getChildren().add(btn);
        return vbox;
    }
}