package dktrotti.ece301assignment1;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dominic on 15-09-25.
 * StatisticsManager tracks statistics, offers public methods to access them, and handles saving
 * and loading of statistics.
 */
public class StatisticsManager implements Serializable{
    private static final String filename = "statistics.bin";

    private static StatisticsManager ourInstance = new StatisticsManager();
    private ArrayList<Long> SingleplayerReactionTimes = new ArrayList<>();
    private ArrayList<Integer> TwoPlayerCounts = new ArrayList<>(2);
    private ArrayList<Integer> ThreePlayerCounts = new ArrayList<>(3);
    private ArrayList<Integer> FourPlayerCounts = new ArrayList<>(4);


    public static StatisticsManager getInstance() {
        return ourInstance;
    }

    private StatisticsManager() {
        LoadData();
    }

    public void SaveData() {
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to open stats file.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to stats file.");
        }
    }

    public void LoadData() {
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ourInstance = (StatisticsManager)objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            //Swallowing exception because file will be created on save.
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stats file.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found, the stats file may be corrupt.");
        }
    }

    public void AddReactionTime(long time) {
        SingleplayerReactionTimes.add(time);
    }

    public void AddTwoPlayerWin(int player) {
        TwoPlayerCounts.set(player - 1, TwoPlayerCounts.get(player - 1));
    }

    public void AddThreePlayerWin(int player) {
        ThreePlayerCounts.set(player - 1, ThreePlayerCounts.get(player - 1));
    }

    public void AddFourPlayerWin(int player){
        FourPlayerCounts.set(player - 1, FourPlayerCounts.get(player - 1));
    }

    public Double GetAverageReaction() {
        return GetAverageOfNReactions(SingleplayerReactionTimes.size());
    }

    public Double GetAverageOfNReactions(int count) {
        int size = SingleplayerReactionTimes.size();
        ArrayList<Long> sublist = new ArrayList<>(SingleplayerReactionTimes.subList(size - count, size));

        Double sum = 0.0;
        for (Long i: sublist) {
            sum += i;
        }

        return sum / count;
    }

    public Long GetMinimumReaction() {
        return GetMinimumOfNReactions(SingleplayerReactionTimes.size());
    }

    public Long GetMinimumOfNReactions(int count) {
        int size = SingleplayerReactionTimes.size();
        ArrayList<Long> sublist = new ArrayList<>(SingleplayerReactionTimes.subList(size - count, size));

        return Collections.min(sublist);
    }

    public Long GetMaximumReaction() {
        return GetMaximimOfNReactions(SingleplayerReactionTimes.size());
    }

    public Long GetMaximimOfNReactions(int count) {
        int size = SingleplayerReactionTimes.size();
        ArrayList<Long> sublist = new ArrayList<>(SingleplayerReactionTimes.subList(size - count, size));

        return Collections.max(sublist);
    }

    public ArrayList<Integer> GetTwoPlayerCounts() {
        return TwoPlayerCounts;
    }

    public ArrayList<Integer> getThreePlayerCounts() {
        return ThreePlayerCounts;
    }

    public ArrayList<Integer> getFourPlayerCounts() {
        return FourPlayerCounts;
    }
}
