import static org.junit.jupiter.api.Assertions.*;

import KenoGame.SceneManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SceneManagerTests
{
    @BeforeEach
    void setup()
    {
        SceneManager.ClearScenes(); // to prevent persistent state
    }

    @Test
    void cannotActivateNonLoadedScene()
    {
        var found = SceneManager.StartScene("game");
        Assertions.assertFalse(found);
    }

    @Test
    void canActivateAfterLoadingScenes()
    {
        SceneManager.LoadScenes(null);
        var found = SceneManager.StartScene("game");
        assertTrue(found);
    }

    @Test
    void cannotActivateBadSceneAfterLoading()
    {
        SceneManager.LoadScenes(null);
        var found = SceneManager.StartScene("NOT A SCENE!");
        Assertions.assertFalse(found);
    }

    @Test
    void clearScenesWorks()
    {
        var found = SceneManager.StartScene("landing");
        assertFalse(found);

        SceneManager.LoadScenes(null);
        found = SceneManager.StartScene("landing");
        assertTrue(found);

        SceneManager.ClearScenes();
        found = SceneManager.StartScene("landing");
        assertFalse(found);
    }
}
