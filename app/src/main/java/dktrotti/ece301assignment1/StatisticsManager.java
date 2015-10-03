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

    public Long GetMedianReaction() {
        return GetMedianofNReactions(SingleplayerReactionTimes.size());
    }

    public Long GetMedianofNReactions(int count) {
        int size = SingleplayerReactionTimes.size();
        if (count > size) {
            count = size;
        }
        ArrayList<Long> sublist = new ArrayList<>(SingleplayerReactionTimes.subList(size - count, size));
        Collections.sort(sublist);

        if(sublist.size() % 2 == 1) {
            return sublist.get((sublist.size() - 1) / 2 + 1);
        } else {
            Long upper = sublist.get(sublist.size() / 2);
            Long lower = sublist.get(sublist.size() / 2 - 1);
            return (upper + lower) / 2;
        }
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

    public void ClearStats() {
        SingleplayerReactionTimes.clear();
        TwoPlayerCounts = new ArrayList<>(Arrays.asList(0, 0));
        ThreePlayerCounts = new ArrayList<>(Arrays.asList(0, 0, 0));
        FourPlayerCounts = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        SaveData();
    }

    public String GetFormattedStats() {
        StringBuilder builder = new StringBuilder();
        builder.append("Singleplayer: \n")
                .append("All Time: \n")
                .append("Minimum: " + GetMinimumReaction() + "\n")
                .append("Maximum: " + GetMaximumReaction() + "\n")
                .append("Median: " + GetMedianReaction() + "\n")
                .append("Average: " + GetAverageReaction() + "\n")

                .append("Last 100: \n")
                .append("Minimum: " + GetMinimumOfNReactions(100) + "\n")
                .append("Maximum: " + GetMaximumOfNReactions(100) + "\n")
                .append("Median: " + GetMedianofNReactions(100) +"\n")
                .append("Average: " + GetAverageOfNReactions(100) + "\n")

                .append("Last 10: \n")
                .append("Minimum: " + GetMinimumOfNReactions(10) + "\n")
                .append("Maximum: " + GetMaximumOfNReactions(10) + "\n")
                .append("Median: " + GetMedianofNReactions(10) + "\n")
                .append("Average: " + GetAverageOfNReactions(10) + "\n")
                .append("\n")

                .append("Multiplayer: \n")
                .append("2 Player: \n")
                .append("Player 1 wins: " + GetTwoPlayerCounts().get(0) + "\n")
                .append("Player 2 wins: " + GetTwoPlayerCounts().get(1) + "\n")

                .append("3 Player: \n")
                .append("Player 1 wins: " + GetThreePlayerCounts().get(0) + "\n")
                .append("Player 2 wins: " + GetThreePlayerCounts().get(1) + "\n")
                .append("Player 3 wins: " + GetThreePlayerCounts().get(2) + "\n")

                .append("4 Player: \n")
                .append("Player 1 wins: " + GetFourPlayerCounts().get(0) + "\n")
                .append("Player 2 wins: " + GetFourPlayerCounts().get(1) + "\n")
                .append("Player 3 wins: " + GetFourPlayerCounts().get(2) + "\n")
                .append("Player 4 wins: " + GetFourPlayerCounts().get(3) + "\n");
        return builder.toString();
    }
}
