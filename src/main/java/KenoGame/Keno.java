package KenoGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Keno {

    private int numSpots;
    private int numSelected;
    private ArrayList<String> betNums, matches;
    private ArrayList<Button> buttonsSelected;
    private Button[] spotBetButtons;
    private int numDraws, curDraws;
    private VBox spotNumBox, spotBoardBox, drawNumBox, playBox;
    private boolean activeGame, finishedDraws;

    public Keno(){
        numSpots = 0;
        numSelected = 0;
        betNums = new ArrayList<String>();
        buttonsSelected = new ArrayList<Button>();
        spotBetButtons = new Button[80];
        spotNumBox = spotBoardBox = drawNumBox = playBox = null;
        numDraws = 1;
        activeGame = finishedDraws = false;
        matches = new ArrayList<>();
        curDraws = 0;
    }


    private Boolean addToBetNumberArray(String num){
        if(betNums.size() == numSpots){
            System.out.println("max spots reached for your given choice");
            return false;
        }
        betNums.add(num);
        return true;
    }
    private void removeFromBetNumberArray(String num)
    {
        betNums.remove(num);
        System.out.println("Removed " + num + " from set numbers.");
    }

    // toggle action of the bet button.
    private void toggleBetNumButton(Button btn){

        if(!btn.getText().equals("*")) {
            if (addToBetNumberArray(btn.getId())){
                buttonsSelected.add(btn);
                System.out.println(btn);
                btn.setText("*");
                ++numSelected;
            }
            else return;
        }
        else{
            btn.setText(btn.getId());
            buttonsSelected.remove(btn);
            removeFromBetNumberArray(btn.getId());
            --numSelected;
        }

        var enableLastStep = numSpots == numSelected;
        drawNumBox.setDisable(!enableLastStep);
        playBox.setDisable(!enableLastStep);
    }

    // function for adding event listner to the bet number button.
    public void addBetNumButtonsListner(Button btn){
        //saving the button object in our button array
        spotBetButtons[Integer.parseInt(btn.getId())-1] = btn;

        //adding event listener to the button
        EventHandler<ActionEvent> event = e -> toggleBetNumButton(btn);

        //adding event handler
        btn.setOnAction(event);
    }

    private void resetBetNumbersArrayList(){

        for (int i = 0; i < buttonsSelected.size(); i++) {
            Button  btn = buttonsSelected.get(i);
            btn.setText(btn.getId());
        }
        betNums.clear();
        buttonsSelected.clear();
        numSelected = 0;
    }

    public void addSpotNumberSliderListener(Slider slider)
    {
        slider.valueProperty().addListener(
                (observable, oldVal, newVal) ->
                {
                    var newInt = newVal.intValue();

                    if(newInt != numSpots)
                    {
                        numSpots = newInt;
                        resetBetNumbersArrayList();

                        var enableOtherInputs = numSpots > 0;
                        spotBoardBox.setDisable(!enableOtherInputs);
                        drawNumBox.setDisable(true);
                        playBox.setDisable(true);

                        System.out.println(numSpots + " Spots is selected.");
                    }
                }
        );
    }

    /* Set Panels
     * Takes in the panels that can be disabled depending on game state.
     * Implies initialization, disables spotBoardBox and drawNumBox until
     * spotNumBox has a value other than 0 and the latter
     * until the number selected is equal to the number of spots
     */
    public void setPanels(VBox spotNum, VBox drawBoard, VBox drawNum, VBox playKenoVB)
    {
        spotNumBox = spotNum;
        spotBoardBox = drawBoard;
        drawNumBox = drawNum;
        playBox = playKenoVB;

        spotBoardBox.setDisable(true);
        drawNumBox.setDisable(true);
        playBox.setDisable(true);
    }

    private void generateRandomSpotNumbers(){
        betNums.clear();
        Random random = new Random();
        System.out.print("Randomly generated numbers: ");
        while (betNums.size() < numSpots){
            int nextNum = random.nextInt(80) + 1;
            if (!betNums.contains(nextNum)) {
                betNums.add(""+nextNum);
                spotBetButtons[nextNum-1].setText("*");
                buttonsSelected.add(spotBetButtons[nextNum-1]);
            }
        }
        System.out.println(betNums);
    }

    public void generateRandomSpotNumbersButtonListner(Button btn){
        EventHandler<ActionEvent> event = e -> {
            resetBetNumbersArrayList();
            generateRandomSpotNumbers();
            numSelected = numSpots;
            drawNumBox.setDisable(false);
            playBox.setDisable(false);
        };
        //adding event handler
        btn.setOnAction(event);
    }

    public void numDrawToggleListner(ToggleGroup tg){
        tg.selectedToggleProperty().addListener((observableValue, old_toggle, new_toggle) -> {
            if (tg.getSelectedToggle() != null) {
                numDraws = Integer.parseInt(""+tg.getSelectedToggle().getUserData());
                System.out.println("" + tg.getUserData()+ ":" + numDraws);
            }
        });
    }

    public void playKenoListner(Button btn)
    {
        EventHandler<ActionEvent> event = e ->
        {
            if(betNums.size() != numSpots)
            {
                System.out.println("Please select more "+ (numSpots-betNums.size())+ " numbers to start the play ");
                return;
            }
            else if(finishedDraws)
            {
                btn.setText("Let's Play!");
                resetGame();
            }
            else
                startRound(btn);

        };
        //adding event handler
        btn.setOnAction(event);
    }

    private void resetGame()
    {
        resetSpotButtonsColor();
        resetBetNumbersArrayList();
        numSpots = 0;
        var slider = (Slider)spotNumBox.getChildren().get(1);
        slider.setValue(0);
        spotNumBox.setDisable(false);
        spotBoardBox.setDisable(true);
        drawNumBox.setDisable(true);
        playBox.setDisable(true);
    }

    private ArrayList<String> generateDraw()
    {
        var rand = new Random();
        var randDraws = new ArrayList<String>();

        while(randDraws.size() < 20)
        {
            int nextDraw = rand.nextInt(80) + 1;

            if(!randDraws.contains(nextDraw))
                randDraws.add(String.valueOf(nextDraw));
        }

        return randDraws;
    }

    private void resetSpotButtonsColor()
    {
        for(var button : spotBetButtons)
            button.setStyle(null);
    }

    private void startRound(Button button)
    {
        resetSpotButtonsColor();
        button.setDisable(true);
        button.setText("Continue");

        drawNumBox.setDisable(true);
        spotBoardBox.setDisable(true);
        spotNumBox.setDisable(true);

        var drawList = generateDraw();
        getMatches(drawList);

        AtomicInteger i = new AtomicInteger();
        EventHandler<ActionEvent> colorSpotBox = t ->
        {
            if(i.get() >= 19)
            {
                button.setDisable(false);
                ++curDraws;

                if(curDraws >= numDraws)
                {
                    button.setText("Play Again?");
                    finishedDraws = true;
                }
            }
            String draw = drawList.get(i.getAndIncrement());

            var color = "green"; // success
            if(!matches.contains(draw))
                color = "red"; // missed

            spotBetButtons[Integer.valueOf(draw) - 1]
                    .setStyle("-fx-background-color: " + color + ";");
        };

        var timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), colorSpotBox),
                new KeyFrame(Duration.seconds(0.1))
        );

        timeline.setCycleCount(20);
        timeline.play();
    }

    private void getMatches(ArrayList<String> drawList)
    {
        matches.clear();

        for(var draw : drawList)
            if(spotBetButtons[Integer.valueOf(draw) - 1].getText() == "*")
                matches.add(draw);
    }
}
