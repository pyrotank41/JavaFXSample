package KenoGame;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMenuBar
{
    private static String ODDS = "Overall Odds \n" +
                                 "1  spot : 1 in 4.00 \n" +
                                 "4  spot : 1 in 3.86 \n" +
                                 "8  spot : 1 in 9.77 \n" +
                                 "10 spot : 1 in 9.05 \n";

    private static String RULES = "Rules \n" +
                                  "1. Select how many numbers to match from \n" +
                                  "   1 to 10 these are called Spots. \n" +
                                  "2. Pick as many numbers as you did Spots.  \n" +
                                  "   Select from 1 to 80, \n" +
                                  "   or choose quick pick! \n" +
                                  "3. Select consecutive draws to play\n" +
                                  "   up to 4. \n" +
                                  "4. Try your luck! \n";

    /* Create Menu
     * Creates a menu that serves the purpose of providing rules, odds, and a means of exiting.
     * Does not modify anything outside of its scope
     * Returns the new menu bar.
     */
    public static MenuBar CreateMenu(Text description)
    {
        var menuBar = new MenuBar();
        var helpTab = new Menu("Help");
        var rulesButton = new MenuItem("Rules");
        var oddsButton = new MenuItem("Odds");
        var statsButton = new MenuItem("Stats");
        var exitButton = new MenuItem("Exit");

        rulesButton.setOnAction(t ->
                                {
                                    if(description != null)
                                        description.setText(RULES);
                                    else
                                        CreateInfoModal(RULES);
                                });
        oddsButton.setOnAction(t ->
                               {
                                   if(description != null)
                                       description.setText(ODDS);
                                   else
                                       CreateInfoModal(ODDS);
                               });
        statsButton.setOnAction(t ->
                               {
                                   var stats = GameStats.generateStatsString();

                                   if(description != null)
                                       description.setText(stats);
                                   else
                                       CreateInfoModal(stats);
                               });
        exitButton.setOnAction(t -> System.exit(0));

        menuBar.getMenus().add(helpTab);

        if(description == null) // implies in game scene, requires look change button
        {
            var lookChangeButton = new MenuItem("New Look");
            lookChangeButton.setOnAction(t -> System.out.println("Make the New Look button do something! Dancing Spiderman Gif?"));
            helpTab.getItems().addAll(lookChangeButton, statsButton);
        }

        helpTab.getItems().addAll(rulesButton, oddsButton, exitButton);

        return menuBar;
    }

    /* Create Info Modal Window
     * Creates a modal window to display the information submitted
     */
    public static void CreateInfoModal(String info)
    {
        var modalStage = new Stage();
        var infoText = new Text(info);
        var infoBox = new VBox(infoText);
        var modalScene = new Scene(infoBox, 250, 200);

        modalStage.setTitle("Help");
        modalStage.setScene(modalScene);
        modalStage.show();
    }
}
