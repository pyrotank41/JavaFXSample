import static org.junit.jupiter.api.Assertions.*;

import KenoGame.GameStats;
import KenoGame.SceneManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameStatsTests
{
    @BeforeEach
    void setup()
    {
        GameStats.resetCharacteristics();
    }

    @Test
    void resetWorks()
    {
        var startCash = GameStats.getTotalCash();
        GameStats.addCash(100);
        GameStats.resetCharacteristics();
        assertEquals(startCash, GameStats.getTotalCash());
    }

    @Test
    void noWinningsOnLoss()
    {
        var winnings = GameStats.calculateWinnings(10, 1, 1);
        assertEquals(winnings, 0f);
    }

    @Test
    void winningsOnVictory()
    {
        var winnings = GameStats.calculateWinnings(10, 0, 1);
        assertEquals(winnings, 5f);
    }

    @Test
    void noPrevMatchesBeforeMatches()
    {
        assertEquals(0, GameStats.getPreviousMatches().size());
    }

    @Test
    void matchesMatchAfterInsert()
    {
        var arrayList = new ArrayList<String>();
        arrayList.add("test");
        GameStats.addMatchToList((ArrayList<String>) arrayList.clone());

        assertEquals(arrayList.size(), GameStats.getPreviousMatches().size());
    }

    @Test
    void noGamesPlayedBeforePlay()
    {
        assertEquals(0, GameStats.getNumGamesPlayed());
    }

    @Test
    void numGamesPlayedIncrements()
    {
        GameStats.incrementNumGamesPlayed();
        assertEquals(1, GameStats.getNumGamesPlayed());
    }

    @Test
    void generatesStatsString()
    {
        assertNotNull(GameStats.generateStatsString());
    }
}