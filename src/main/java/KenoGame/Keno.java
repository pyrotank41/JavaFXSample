/* Keno Game
 * The logic that powers the UI and game states
 */
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
    private boolean finishedDraws;

    public Keno(){
        numSpots = 0;
        numSelected = 0;
        betNums = new ArrayList<>();
        buttonsSelected = new ArrayList<>();
        spotBetButtons = new Button[80];
        spotNumBox = spotBoardBox = drawNumBox = playBox = null;
        numDraws = 1;
        finishedDraws = false;
        matches = new ArrayList<>();
        curDraws = 0;
    }


    // adding the bet numbers to the bet number arrayList when selected by the user
    private Boolean addToBetNumberArray(String num){
        if(betNums.size() == numSpots){
            System.out.println("max spots reached for your given choice");
            return false;
        }
        betNums.add(num);
        return true;
    }

    // removing the bet numbers to the bet number arrayList when deselected by the user
    private void removeFromBetNumberArray(String num)
    {
        betNums.remove(num);
        System.out.println("Removed " + num + " from set numbers.");
    }

    // toggle action of the bet button.
    private void toggleBetNumButton(Button btn){

        // when button is in its default state, change the state to selected.
        if(!btn.getText().equals("*")) {
            if (addToBetNumberArray(btn.getId())){
                buttonsSelected.add(btn);
                System.out.println(btn);
                btn.setText("*");
                btn.setStyle("-fx-background-color: yellow;");
                ++numSelected;
            }
            else return;
        }
        // when the button is selected, change the state to default.
        else{
            btn.setText(btn.getId());
            btn.setStyle(null);
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

    // The function to reset the bet number grid
    // we change the test of the button to default
    // furthermore we reset buttonsSelected and betNums arrayList.
    private void resetBetNumbersArrayList(){

        for (Button btn : buttonsSelected) btn.setText(btn.getId());
        betNums.clear();
        buttonsSelected.clear();
        numSelected = 0;
    }

    /* Add Spot Number Slider Listener
     * To be called by Scene_Game
     * Integrates the slider functionality with the board
     * Sets the slider to call the event listed
     */
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

                        // Changes the board state based on available spots
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

        GameMenuBar.CacheGameScreenElements(spotNumBox, spotBoardBox, drawNumBox, playBox);
    }

    // Generates values for quick pick
    private void generateRandomSpotNumbers(){
        betNums.clear();
        resetSpotButtonsColor();
        Random random = new Random();
        System.out.print("Randomly generated numbers: ");
        while (betNums.size() < numSpots){
            int nextNum = random.nextInt(80) + 1;
            if (!betNums.contains(String.valueOf(nextNum))) {
                betNums.add(String.valueOf(nextNum));
                spotBetButtons[nextNum-1].setText("*");
                spotBetButtons[nextNum-1].setStyle("-fx-background-color: yellow;");
                buttonsSelected.add(spotBetButtons[nextNum-1]);
            }
        }
        System.out.println(betNums);
    }

    // Generates and integrates an event for quick pick
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

    // event listner function for selecting number of draws to play
    public void numDrawToggleListner(ToggleGroup tg){
        tg.selectedToggleProperty().addListener((observableValue, old_toggle, new_toggle) -> {
            if (tg.getSelectedToggle() != null) {
                numDraws = Integer.parseInt(""+tg.getSelectedToggle().getUserData());
                System.out.println("" + tg.getUserData()+ ":" + numDraws);
            }
        });
    }

    // The game starts here!!! this will start roling the dies and get the numbers drawn and to check if u won!
    public void playKenoListner(Button btn)
    {
        EventHandler<ActionEvent> event = e ->
        {
            if(betNums.size() != numSpots)
            {
                System.out.println("Please select more "+ (numSpots-betNums.size())+ " numbers to start the play ");
            }
            else if(finishedDraws)
            {
                btn.setText("Let's Play!");
                finishedDraws = false;
                curDraws = 0;
                resetGame();
            }
            else if(GameStats.getTotalCash() <= 0)
                SceneManager.StartScene("landing");
            else
                startRound(btn);
        };
        //adding event handler
        btn.setOnAction(event);
    }

    /* Reset Game
     * Resets the board characteristics for a fresh game
     */
    private void resetGame()
    {
        resetBetNumbersArrayList();
        resetSpotButtonsColor();

        var slider = (Slider)spotNumBox.getChildren().get(1);
        slider.setValue(0);
        numSpots = 0;

        spotNumBox.setDisable(false);
        spotBoardBox.setDisable(true);
        drawNumBox.setDisable(true);
        playBox.setDisable(true);
    }

    /* Generate Draw
     * Creates a random list of non duplicating numbers as strings
     * Returns said list.
     */
    private ArrayList<String> generateDraw()
    {
        var rand = new Random();
        var randDraws = new ArrayList<String>();

        while(randDraws.size() < 20)
        {
            int nextDraw = rand.nextInt(80) + 1;
            var nextDrawStr = String.valueOf(nextDraw);

            if(!randDraws.contains(nextDrawStr))
                randDraws.add(nextDrawStr);
        }

        return randDraws;
    }

    /* Reset Spot Buttons' Color
     * Checks if each button is currently part of the bet
     * if so, changes it (or keeps it) yellow
     * if not, resets the button to the default characteristics.
     */
    private void resetSpotButtonsColor()
    {
        for(var button : spotBetButtons)
        {
            if(button.getText().equals("*"))
                button.setStyle("-fx-background-color: yellow;");
            else
                button.setStyle(null);
        }
    }

    /* Start Round
     * Handles the gameplay and board characteristics during gameplay
     * Locks inputs that are not allowed to be modified
     * Runs simple animations to color each board piece
     * Modifies game state and the board
     */
    private void startRound(Button button)
    {
        // Clean up in case running second time
        resetSpotButtonsColor();
        button.setDisable(true);
        button.setText("Continue");

        // Disable inputs that are not meant to be used during the round
        drawNumBox.setDisable(true);
        spotBoardBox.setDisable(true);
        spotNumBox.setDisable(true);

        // Generate the list of Spots that are going to be drawn
        var drawList = generateDraw();
        getMatches(drawList);

        // Color Spot Boxes
        // Goes through each Spot that needs to be activated to display draws
        // At the end, determines game state and pops up a modal window for round information
        AtomicInteger i = new AtomicInteger(); // Hold the index for which button is to be modified
        EventHandler<ActionEvent> colorSpotBox = t ->
        {
            // If end of round
            if(i.get() >= 19)
            {
                button.setDisable(false);
                ++curDraws;

                if(curDraws >= numDraws)
                {
                    button.setText("Play Again?");
                    GameStats.incrementNumGamesPlayed();
                    finishedDraws = true;
                }

                // Sets up Game Stats and info, can replace wager with a cached value if added to menu
                var wager = 1f;
                var cost = wager * (float)numDraws;
                var winnings = GameStats.calculateWinnings(numSpots, matches.size(), wager);
                var synopsis = new String("You matched " + matches.size() + " numbers\n" +
                                          "Matches: " + matches + "\n" +
                                          "Winnings: $" + (winnings - (cost / (float)numDraws)));

                GameStats.addCash(winnings - (cost / (float)numDraws));
                GameMenuBar.CreateInfoModal(synopsis);
            }

            // Finds the active draw, and colors the spot accordingly
            var draw = drawList.get(i.getAndIncrement());

            var color = "green"; // success
            if(!matches.contains(draw))
                color = "red"; // missed

            spotBetButtons[Integer.valueOf(draw) - 1]
                    .setStyle("-fx-background-color: " + color + ";");
        };

        // Timeline for spacing out the draws, adds tension to the atmosphere
        var timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.4), colorSpotBox),
                new KeyFrame(Duration.seconds(0.1))
        );

        timeline.setCycleCount(20);
        timeline.play();
    }

    /* Get Matches
     * Intended for the drawList
     * Sets up the matches list with buttons that are activated as well as drawn
     * Sends a copy of the list to GameStats for archiving in the stats window
     */
    private void getMatches(ArrayList<String> drawList)
    {
        matches.clear();

        for(var draw : drawList)
            if(spotBetButtons[Integer.valueOf(draw) - 1].getText().equals("*"))
                matches.add(draw);

        GameStats.addMatchToList((ArrayList<String>) matches.clone());
    }
}
