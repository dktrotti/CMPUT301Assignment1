package dktrotti.ece301assignment1;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Dominic on 15-09-22.
 *
 */
public class BuzzerManager {
    private Integer buzzerCount;
    private ArrayList<Buzzer> buzzers;

    public BuzzerManager(Context context, Integer buzzerCount) {
        this.buzzerCount = buzzerCount;
        buzzers = new ArrayList<Buzzer>(buzzerCount);
        for (Integer i = 0; i < buzzerCount; i++) {
            buzzers.add(new Buzzer(context, "P" + i.toString()));
        }
    }

    public void primeBuzzers() {
        for (Buzzer buzzer : buzzers) {
            buzzer.primeBuzzer();
        }
    }

    private void disableBuzzers() {
        for (Buzzer buzzer : buzzers) {
            buzzer.disableBuzzer();
        }
    }
}
