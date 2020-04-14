package com.company;

public class Smart1Player implements Player {


    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] arr = {xA, xB, xC};
        int[] arrI = {1, 2, 3};
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr[j] < arr[j + 1]) {
                    int tmp = arr[j];
                    int tmpI = arrI[j];

                    arr[j] = arr[j + 1];
                    arrI[j] = arrI[j + 1];

                    arr[j + 1] = tmp;
                    arrI[j + 1] = tmpI;
                }
            }
        }

        if(Math.random() > 0.5)
            return arrI[1];
        else
            return arrI[0];
    }

    @Override
    public String getEmail() {
        return null;
    }
}
