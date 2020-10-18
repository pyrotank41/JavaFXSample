package KenoGame;

import javafx.stage.Stage;

import java.util.HashMap;

public class SceneManager
{
    private static HashMap<String, SceneHolder> sceneMap = new HashMap<>();

    /* Start Scene
     * Checks if the requestedScene is in the sceneMap
     * If so, calls Activate on it. If not, prints an error to the console.
     */
    public static void StartScene(String requestedScene)
    {
        if(sceneMap.containsKey(requestedScene))
            sceneMap.get(requestedScene).Activate();
        else
            System.out.println("Invalid Scene Change! " + requestedScene + " does not exist in the Scene Manager!");
    }

    /* Load Scenes
     * Enter primaryStage scenes in here in order to start via StartScene
     */
    public static void LoadScenes(Stage stage)
    {
        var landingMenu = new Scene_Landing(stage);
        var gameScene = new Scene_Game(stage);

        sceneMap.put("landing", landingMenu);
        sceneMap.put("game", gameScene);
    }
}
