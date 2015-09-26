package dktrotti.ece301assignment1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Dominic on 15-09-25.
 * StatisticsManager tracks statistics, offers public methods to access them, and handles saving
 * and loading of statistics.
 */
public class StatisticsManager implements Serializable{
    private static final String filename = "data/data/dktrotti.ece301assignment1/statistics.bin";

    private static StatisticsManager ourInstance = new StatisticsManager();
    private ArrayList<Long> SingleplayerReactionTimes = new ArrayList<>();
    private ArrayList<Integer> TwoPlayerCounts = new ArrayList<>(Arrays.asList(0, 0));
    private ArrayList<Integer> ThreePlayerCounts = new ArrayList<>(Arrays.asList(0, 0, 0));
    private ArrayList<Integer> FourPlayerCounts = new ArrayList<>(Arrays.asList(0, 0, 0, 0));


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
        TwoPlayerCounts.set(player, TwoPlayerCounts.get(player) + 1);
    }

    public void AddThreePlayerWin(int player) {
        ThreePlayerCounts.set(player, ThreePlayerCounts.get(player) + 1);
    }

    public void AddFourPlayerWin(int player){
        FourPlayerCounts.set(player, FourPlayerCounts.get(player) + 1);
    }

    public void AddMultiplayerWin(int player, int playercount) {
        switch (playercount) {
            case 2: AddTwoPlayerWin(player);
                break;
            case 3: AddThreePlayerWin(player);
                break;
            case 4: AddFourPlayerWin(player);
                break;
        }
    }

    public Double GetAverageReaction() {
        return GetAverageOfNReactions(SingleplayerReactionTimes.size());
    }

    public Double GetAverageOfNReactions(int count) {
        int size = SingleplayerReactionTimes.size();
        if (count > size) {
            count = size;
        }
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
        if (count > size) {
            count = size;
        }
        ArrayList<Long> sublist = new ArrayList<>(SingleplayerReactionTimes.subList(size - count, size));

        return Collections.min(sublist);
    }

    public Long GetMaximumReaction() {
        return GetMaximumOfNReactions(SingleplayerReactionTimes.size());
    }

    public Long GetMaximumOfNReactions(int count) {
        int size = SingleplayerReactionTimes.size();
        if (count > size) {
            count = size;
        }
        ArrayList<Long> sublist = new ArrayList<>(SingleplayerReactionTimes.subList(size - count, size));

        return Collections.max(sublist);
    }

    public ArrayList<Integer> GetTwoPlayerCounts() {
        return TwoPlayerCounts;
    }

    public ArrayList<Integer> GetThreePlayerCounts() {
        return ThreePlayerCounts;
    }

    public ArrayList<Integer> GetFourPlayerCounts() {
        return FourPlayerCounts;
    }
}
