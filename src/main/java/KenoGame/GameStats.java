package KenoGame;

import java.util.ArrayList;

public class GameStats
{
    private static float totalCash = 1000;
    private static int numGamesPlayed = 0;
    private static ArrayList<ArrayList<String>> previousMatches = new ArrayList<>();

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

    /* Calculate Winnings
     * Returns a float for the cash prize for the given parameters
     * Information from https://nclottery.com/KenoHow
     */
    public static float calculateWinnings(int numSpots, int numMatches, float wager)
    {
        float earnings = 0.0f;

        if(numSpots == 1)
            if(numMatches == 1)
                earnings = 2;
        else if(numSpots == 2)
            if(numMatches == 2)
                earnings = 11;
        else if(numSpots == 3)
        {
            if(numMatches == 2)
                earnings = 2;
            else if(numMatches == 3)
                earnings = 27;
        }
        else if(numSpots == 4)
        {
            if(numSpots == 2)
                earnings = 1;
            else if(numSpots == 3)
                earnings = 5;
            else if(numSpots == 4)
                earnings = 75;
        }
        else if(numSpots == 5)
        {
            if(numSpots == 3)
                earnings = 2;
            else if(numSpots == 4)
                earnings = 18;
            else if(numSpots == 5)
                earnings = 420;
        }
        else if(numSpots == 6)
        {
            if(numSpots == 3)
                earnings = 1;
            else if(numSpots == 4)
                earnings = 8;
            else if(numSpots == 5)
                earnings = 50;
            else if(numSpots == 6)
                earnings = 1100;
        }
        else if(numSpots == 7)
        {
            if(numSpots == 3)
                earnings = 1;
            else if(numSpots == 4)
                earnings = 3;
            else if(numSpots == 5)
                earnings = 17;
            else if(numSpots == 6)
                earnings = 100;
            else if(numSpots == 7)
                earnings = 4500;
        }
        else if(numSpots == 8)
        {
            if(numSpots == 4)
                earnings = 2;
            else if(numSpots == 5)
                earnings = 12;
            else if(numSpots == 6)
                earnings = 50;
            else if(numSpots == 7)
                earnings = 750;
            else if(numSpots == 8)
                earnings = 10000;
        }
        else if(numSpots == 9)
        {
            if(numSpots == 4)
                earnings = 1;
            else if(numSpots == 5)
                earnings = 6;
            else if(numSpots == 6)
                earnings = 25;
            else if(numSpots == 7)
                earnings = 150;
            else if(numSpots == 8)
                earnings = 3000;
            else if(numSpots == 9)
                earnings = 30000;
        }
        else if(numSpots == 10)
        {
            if(numSpots == 0)
                earnings = 5;
            else if(numSpots == 5)
                earnings = 2;
            else if(numSpots == 6)
                earnings = 15;
            else if(numSpots == 7)
                earnings = 40;
            else if(numSpots == 8)
                earnings = 450;
            else if(numSpots == 9)
                earnings = 4250;
            else if(numSpots == 10)
                earnings = 100000;
        }

        return  earnings * wager;
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
                          "\nStarting cash: $1000" +
                          "\nCurrent  cash: $" + totalCash +
                          "\nWinnings     : $" + (totalCash - 1000) +
                          "\nGames played : "  + numGamesPlayed +
                          "\nMatches\n" + prevMatchesString.toString());
    }
}
