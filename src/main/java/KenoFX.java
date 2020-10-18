import KenoGame.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class KenoFX extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		SceneManager.LoadScenes(primaryStage);
		SceneManager.StartScene("landing");
	}
}
