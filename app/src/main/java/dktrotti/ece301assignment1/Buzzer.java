/*The MIT License (MIT)

Copyright (c) 2015 Dominic Trottier

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/

package dktrotti.ece301assignment1;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Dominic on 15-09-22.
 *
 * Buzzer is a button that allows itself to be primed and disabled like a quiz show buzzer and
 * keeps track of the time that it has been primed for.
 */
public class Buzzer extends Button {
    private long primedTime;
    private Boolean isPrimed = Boolean.FALSE;
    private int primedColor = Color.GREEN;
    private int disabledColor = Color.RED;

    public Buzzer(Context context) {
        super(context);
        this.setText("");
    }

    public Buzzer(Context context, String text) {
        super(context);
        this.setText(text);

    }

    public Buzzer(Context context, String text, int primedColor, int disabledColor) {
        super(context);
        this.setText(text);
        this.primedColor = primedColor;
        this.disabledColor = disabledColor;
    }

    public Buzzer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Buzzer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        this.setBackgroundColor(disabledColor);
    }

    public int getPrimedColor() {
        return primedColor;
    }

    public void setPrimedColor(int primedColor) {
        this.primedColor = primedColor;
    }

    public int getDisabledColor() {
        return disabledColor;
    }

    public void setDisabledColor(int disabledColor) {
        this.disabledColor = disabledColor;
    }

    public void primeBuzzer() {
        isPrimed = Boolean.TRUE;
        this.setBackgroundColor(primedColor);
        primedTime = System.currentTimeMillis();
    }

    public void disableBuzzer() {
        this.setBackgroundColor(disabledColor);
        isPrimed = Boolean.FALSE;
    }

    public Boolean isBuzzerPrimed() {
        return isPrimed;
    }

    public long getTime() {
        if (isPrimed) {
            return System.currentTimeMillis() - primedTime;
        }
        //Consider throwing an exception here instead
        return Long.MAX_VALUE;
    }
}
