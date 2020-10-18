package KenoGame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

import java.util.ArrayList;
import java.util.Random;

public class Keno {

    private int numSpots;
    private int numSelected;
    private ArrayList<String> betNums;
    private ArrayList<Button> buttonsSelected;
    private Button[] spotBetButtons;
    private int numDraws;

    public Keno(){
        numSpots = 0;
        numSelected = 0;
        betNums = new ArrayList<String>();
        buttonsSelected = new ArrayList<Button>();
        spotBetButtons = new Button[80];
        numDraws = 1;
    }


    private Boolean addToBetNumberArray(String num){
        if(betNums.size() == numSpots){
            System.out.println("max spots reached for your given choice");
            return false;
        }
        betNums.add(num);
        return true;
    }
    private void removeFromBetNumberArray(String num){
        betNums.remove(num);
    }

    // toggle action of the bet button.
    private void toggleBetNumButton(Button btn){

        if(!btn.getText().equals("*")) {
            if (addToBetNumberArray(btn.getId())){
                buttonsSelected.add(btn);
                System.out.println(btn);
                btn.setText("*");
            }
            else return;
        }
        else{
            btn.setText(btn.getId());
            buttonsSelected.remove(btn);
        }

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
                        System.out.println(numSpots + " Spots is selected.");
                    }
                }
        );
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

    public void playKenoListner(Button btn){
        EventHandler<ActionEvent> event = e -> {
            if(betNums.size() != numSpots){
                System.out.println("Please select more "+ (numSpots-betNums.size())+ " numbers to start the play ");
                return;
            }


        };
        //adding event handler
        btn.setOnAction(event);
    }



}
