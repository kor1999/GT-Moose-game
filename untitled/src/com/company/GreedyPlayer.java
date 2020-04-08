package com.company;

import java.util.Arrays;
import java.util.OptionalInt;

public class GreedyPlayer implements Player {
    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] a = {xA, xB, xC};
        int max = Integer.MIN_VALUE;
        int imax = -1;
        for (int i = 0; i < a.length ; i++) {
            if(a[i] > max){
                imax = i;
                max = a[i];
            }
        }
        return (imax + 1);
    }
}
