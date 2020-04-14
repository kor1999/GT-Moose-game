package com.company;
import java.util.*;

public class RandomPlayer implements Player {

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return (int) (1 + Math.random()*3);
    }

    @Override
    public String getEmail() {
        return null;
    }
}
