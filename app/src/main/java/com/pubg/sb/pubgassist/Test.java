package com.pubg.sb.pubgassist;

import android.widget.Toast;

/**
 * Created by XY on 2019/2/23.
 */
public class Test {

    public static void main(String[] s) {
        Value value = new Value(5);
        setValue(value);
        System.out.print("value = " + value.value);
    }

    private static void setValue(Value value) {
        value.value = 6;
    }

    public void test() {
        int s = 6;
        setValue(s);
        System.out.print("s = " + s);
    }

    private void setValue(int s) {
        s += 1;
    }

    static class Value {
        int value;

        public Value(int value) {
            this.value = value;
        }
    }
}
