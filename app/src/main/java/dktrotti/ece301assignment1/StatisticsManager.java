package dktrotti.ece301assignment1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dominic on 15-09-25.
 */
public class StatisticsManager implements Serializable{
    private static StatisticsManager ourInstance = new StatisticsManager();
    ArrayList<long> SingleplayerReactionTimes = new ArrayList<>();
    ArrayList<Integer> TwoPlayerCounts = new ArrayList<>(2);
    ArrayList<Integer> ThreePlayerCounts = new ArrayList<>(3);
    ArrayList<Integer> FourPlayerCounts = new ArrayList<>(4);


    public static StatisticsManager getInstance() {
        return ourInstance;
    }

    private StatisticsManager() {
        LoadData();
    }

    public void SaveData() {

    }

    public void LoadData() {

    }
}
