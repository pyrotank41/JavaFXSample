package KenoGame;

import java.util.ArrayList;

public class GameStats
{
    private static float totalCash = 0, startingCash = 0;
    private static int numGamesPlayed = 0;
    private static ArrayList<ArrayList<String>> previousMatches = new ArrayList<>();

    public static void setStartingCash(float amount)
    {
        startingCash = totalCash = amount;
    }

    public static void addCash(float amount)
    {
        totalCash += amount;
    }

    public static float getTotalCash()
    {
        return totalCash;
    }

    public static void incrementNumGamesPlayed()
    {
        ++numGamesPlayed;
    }

    public static void addMatchToList(ArrayList<String> match)
    {
        previousMatches.add(match);
    }

    public static ArrayList<ArrayList<String>> getPreviousMatches()
    {
        return previousMatches;
    }

    public static String generateStatsString()
    {
        var prevMatchesString = new StringBuilder();
        for(var match : previousMatches)
        {
            prevMatchesString.append(match);
            prevMatchesString.append('\n');
        }

        return new String("Stats" +
                          "\nStarting cash: $" + startingCash +
                          "\nCurrent  cash: $" + totalCash +
                          "\nWinnings     : $" + (totalCash - startingCash) +
                          "\nGames played : "  + numGamesPlayed +
                          "\nMatches\n" + prevMatchesString.toString());
    }
}
