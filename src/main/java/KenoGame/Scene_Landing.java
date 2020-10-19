/* Scene - Landing
 * Landing scene for when the application starts or the player loses.
 */

package KenoGame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.*;

public class Scene_Landing implements SceneHolder
{
    private Scene scene = null;
    private Text description = null;
    private Stage stage;

    Scene_Landing(Stage stage)
    {
        this.stage = stage;
    }

    /* Activate
     * Checks if the scene already exists, if not, calls for it to be created
     * Changes stage elements to reflect which scene is presently up.
     */
    public void Activate()
    {
        if(stage == null)
        {
            System.out.println("Need a valid stage!");
            return;
        }

        if(scene == null)
            scene = CreateScene();

        stage.setTitle("Welcome to Keno!");
        stage.setScene(scene);
        stage.show();
    }

    /* Create Scene
     * Takes the elements for the scene and assembles them.
     * Modifies scene.
     */
    private Scene CreateScene()
    {
        var vbox = new VBox();

        var content = new VBox(50);
        content.setAlignment(Pos.CENTER);

        var title = new Text("KENO!");
        title.setStyle("-fx-font-size: 96; ");
        title.setFill(Color.RED);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(1);

        var playButton = new Button("Play!");
        playButton.setScaleX(2);
        playButton.setScaleY(1.8);
        playButton.setStyle("-fx-background-color: linear-gradient(lime, limegreen);" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 5, 0, 0, 1);" +
                            "-fx-font-size: 24;");
        playButton.setOnAction(t -> SceneManager.StartScene("game"));

        description = new Text("");
        description.setStyle("-fx-font-size: 18");

        var menuBar = GameMenuBar.CreateMenu(description);

        content.getChildren().addAll(title, playButton, description);
        vbox.getChildren().addAll(menuBar, content);

        return new Scene(vbox, 355, 690);
    }
}
