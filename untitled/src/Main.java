
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Player pl1 = new RandomPlayer();
        Player pl2 = new GreedyPlayer();
        Player pl3 = new AlmostGreedyPlayer();
        Player pl4 = new Smart1Player();
        Player pl5 = new IlnurMamedbakovCode();

        // Test 1
        // Here test for Random and for Greedy agents
        ArrayList<Player> players = new ArrayList<>();
        players.add(pl1);
        players.add(pl2);
        System.out.println();
        System.out.println("test 1 with Random and greedy");
        ArrayList results = tournament(players, 100);
        System.out.println("Random Agent           = " + results.get(0));
        System.out.println("Greedy Agent           = " + results.get(1));

        // Test 2
        // Here test for almost greedy and for greedy agents
        players.set(0, pl3);
        players.set(1, pl2);
        System.out.println();
        System.out.println("test 2 with almost greedy and greedy");
        results = tournament(players, 100);
        System.out.println("Almost Greedy Agent    = " + results.get(0));
        System.out.println("Greedy Agent           = " + results.get(1));

        // Test 3
        // Here test for smart1 and for greedy agents
        players.set(0, pl4);
        players.set(1, pl2);
        System.out.println();
        System.out.println("test 3 with smart1 and greedy");
        results = tournament(players, 100);
        System.out.println("Smart1 Agent           = " + results.get(0));
        System.out.println("Greedy Agent           = " + results.get(1));

        // Test 4
        // Here test for Ilnur Mamedbakov and for greedy agents
        players.set(0, pl5);
        players.set(1, pl2);
        System.out.println();
        System.out.println("test 4 with Ilnur Mamedbakov and greedy");
        results = tournament(players, 100);
        System.out.println("Ilnur Mamedbakov Agent = " + results.get(0));
        System.out.println("Greedy Agent           = " + results.get(1));

        // Test 5
        // Here test for all agents
        players.set(0, pl1);
        players.set(1, pl2);
        players.add(pl3);
        players.add(pl4);
        players.add(pl5);
        System.out.println();
        System.out.println("test 5 with all agents");
        results = tournament(players, 100);
        System.out.println("Random Agent           = " + results.get(0));
        System.out.println("Greedy Agent           = " + results.get(1));
        System.out.println("Almost Greedy Agent    = " + results.get(2));
        System.out.println("Smart1 Agent           = " + results.get(3));
        System.out.println("Ilnur Mamedbakov Agent = " + results.get(4));

    }

    public static ArrayList<Double> tournament(ArrayList<Player> players, int n_rounds){

        HashMap<Integer, Integer> grassList =  new HashMap<>(); // dictionary with number of grass in each square
        grassList.put(1,1); //xA
        grassList.put(2,1); //xB
        grassList.put(3,1); //xC

        ArrayList<Integer> lastMoves = new ArrayList<>(); // List of last moves of all mooses
        for (int i = 0; i < players.size(); i++) {
            lastMoves.add(0);
        }
        ArrayList<Double> results = new ArrayList<>(); // List with results of each Moose
        for (int i = 0; i < players.size() ; i++)
            results.add((double) 0);

        for (int i = 0; i < players.size() - 1; i++) { // i is index of first moose
            for (int j = i + 1; j < players.size() ; j++) { // j is second moose

                for (int k = 0; k < n_rounds; k++) { // loop for playing number of rounds that we want

                    //System.out.println(" ");
                    // Mooses doing their choice, and we storing their moves
                    //System.out.println(grassList.get(1) + " " + grassList.get(2) + " " + grassList.get(3));

                    int prevMove1 = lastMoves.get(j);
                    int prevMove2 = lastMoves.get(i);
                    lastMoves.set(i, players.get(i).move(prevMove1, grassList.get(1), grassList.get(2), grassList.get(3)));
                    lastMoves.set(j, players.get(j).move(prevMove2, grassList.get(1), grassList.get(2), grassList.get(3)));


                    //System.out.println("First moose: " + lastMoves.get(i));
                    //System.out.println("Second moose: " + lastMoves.get(j));

                    if (lastMoves.get(i) != lastMoves.get(j)) { // if they ate in different squares

                        if (grassList.get(lastMoves.get(i)) != 0) { //if there was at least 1 piece of grass on square
                            Double add_points = 10 * Math.exp(grassList.get(lastMoves.get(i))) / (1 + Math.exp(grassList.get(lastMoves.get(i))));
                            results.set(i, results.get(i) + add_points);
                        }// if in this square no grass, moose will take 0 points

                        if (grassList.get(lastMoves.get(j)) != 0){
                            Double add_points = 10 * Math.exp(grassList.get(lastMoves.get(j))) / (1 + Math.exp(grassList.get(lastMoves.get(j))));
                            results.set(j,results.get(j) + add_points);
                        }

                        // to squares where were mooses are nor adding grass
                        if (grassList.get(lastMoves.get(i)) != 0)
                            grassList.put(lastMoves.get(i), grassList.get(lastMoves.get(i)) - 1);
                        if (grassList.get(lastMoves.get(j)) != 0)
                            grassList.put(lastMoves.get(j), grassList.get(lastMoves.get(j)) - 1);

                        // finding square where wasn't moose and add there grass
                        for (int l = 1; l < grassList.size() + 1; l++) {
                            if (lastMoves.get(i) != l && lastMoves.get(j) != l)
                                grassList.put(l, grassList.get(l) + 1);
                        }

                    } else{ //if both mooses are met in one square
                        if (grassList.get(lastMoves.get(i)) != 0)
                            grassList.put(lastMoves.get(i), grassList.get(lastMoves.get(i)) - 1);

                        for (int l = 1; l < grassList.size() + 1; l++) {
                            if (lastMoves.get(i) != l)
                                grassList.put(l, grassList.get(l) + 1);
                        }
                    }
                }

                players.get(i).reset();
                players.get(j).reset();
                for (int k = 0; k < lastMoves.size(); k++) {
                    lastMoves.set(k, 0);

                }
                grassList.put(1,1); //xA
                grassList.put(2,1); //xB
                grassList.put(3,1); //xC
            }
        }
        return results;
    }

}

interface Player {
    void reset();
    int move(int opponentLastMove, int xA, int xB, int xC);
}

class Smart1Player implements Player {


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
}

class AlmostGreedyPlayer implements Player {
    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] arr = {xA, xB, xC};
        int[] arrI = {1, 2, 3};
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    int tmpI = arrI[j];

                    arr[j] = arr[j + 1];
                    arrI[j] = arrI[j + 1];

                    arr[j + 1] = tmp;
                    arrI[j + 1] = tmpI;
                }
            }
        }

        return arrI[1];
    }
}

class RandomPlayer implements Player {

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return (int) (1 + Math.random()*3);
    }
}

class GreedyPlayer implements Player {
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

class IlnurMamedbakovCode implements Player {
    private int lastxA, lastxB, lastxC; // previous x's on squares
    private int badM, goodM, countM; // counters of bad and kind moves of opponent
    private int numberGames; // counter of all games

    @Override
    public void reset() {
        lastxA = 0;
        lastxB = 0;
        lastxC = 0;
        badM   = 0;
        goodM  = 0;
        countM = 0;
        numberGames = 0;
    }


    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int result = -1; // here will be result

        //If its a first game between mooses
        //We don't care which square take, because they all equal
        if (opponentLastMove == 0) {//If its a first game between mooses
            goodM  = 0; // number of kind moves of opponent
            badM   = 0; // number of bad moves of opponent
            countM = 0; // number of total kind or bad moves
            lastxA = xA;
            lastxB = xB;
            lastxC = xC;
            numberGames = 1;
            //System.out.println(" первый раз");
            return (int) (1 + Math.random()*3);
        }

        //if its second round
        //we can choose best square here, but we still don't care about moves of opponent
        //because he still didn't made any logical moves
        if (numberGames == 1){
            ArrayList<Integer> listPresentSquares = new ArrayList<>();
            listPresentSquares.add(xA);
            listPresentSquares.add(xB);
            listPresentSquares.add(xC);
            ArrayList<Integer> sortedPresentSquares = sort(listPresentSquares);

            lastxA = xA;
            lastxB = xB;
            lastxC = xC;
            numberGames = numberGames + 1;
            //System.out.println(" второй раз");
            return sortedPresentSquares.get(0);
        }

        //sorting previous squares for understanding which was best
        ArrayList<Integer> listPreviousSquares = new ArrayList<>();
        listPreviousSquares.add(lastxA);
        listPreviousSquares.add(lastxB);
        listPreviousSquares.add(lastxC);
        ArrayList<Integer> sortedPrevSquares =  sort(listPreviousSquares); //this method return ID's of top squares

        //sorting present squares for understanding which is the best
        ArrayList<Integer> listPresentSquares = new ArrayList<>();
        listPresentSquares.add(xA);
        listPresentSquares.add(xB);
        listPresentSquares.add(xC);
        ArrayList<Integer> sortedPresentSquares = sort(listPresentSquares);
        listPresentSquares = usualSort(listPresentSquares); //this method just sort x's of each squares

        //I am calculating coefficient of how opponent are kind or evil
        //If he made greedy choice I'm adding to badM, because he stole my best move
        //if he didn't take the best move, I add goodM, cause he left for me best square
        if (opponentLastMove == sortedPrevSquares.get(0)){
            // if in previous game opponent made greedy decision
            badM = badM + 1;
            countM = countM + 1;
        } else {
            // if in previous game opponent made NOT greedy decision
            goodM = goodM + 1;
            countM = countM + 1;
        }

        //Here I am doing actual calculation of coefficient
        //This coefficient located within the borders between -1 to +1
        //If coef = 1 -> opponent is really kind and always leave for me best square
        //if coef = -1-> opponent are greedy and always trying to take best square
        //if -1 < coef < 1 -> opponent not greedy and not too kind
        double evilCoef = Double.valueOf((goodM - badM)) / Double.valueOf(countM); // if coef = 1 its a kind opponent, if its -1 its greedy opponent, if coef =0 its strange opponent

        //1. If opponent are greedy
        //1.1 I will take second after best square
        //1.2 But if second square after best have x = 0, I will go to best square and will fight with opponent
        //2. If opponent are too kind
        //2.1 I will take best square
        //3. If opponent are not greedy and not too kind
        //3.1 I will take best or second square with probability 0.5
        //3.2 But if in second square x = 0, I will go to best square
        if (evilCoef == -1) { // if moose are greedy
            if (listPresentSquares.get(1) == 0){ // if second square are empty
                result = sortedPresentSquares.get(0); // go to fight with another moose on best square
            } else {
                result = sortedPresentSquares.get(1); // if second square are not empty go there
            }
        } else if (evilCoef == 1){ // if opponent moose are kind
            result = sortedPresentSquares.get(0); // go and eat on best square
        } else {//if opponent not greedy or too kind
            if(Math.random() > 0.5) { //eat randomly on best or second square
                result = sortedPresentSquares.get(0);
            } else {
                if(listPresentSquares.get(1) != 0) // eat second square only if its not empty
                    result = sortedPresentSquares.get(1);
                else
                    result = sortedPresentSquares.get(0);
            }
        }

        lastxA = xA;
        lastxB = xB;
        lastxC = xC;
        numberGames = numberGames + 1;
        return result;

    }

    // This bubble sort returning indexes of sorted list
    private ArrayList<Integer> sort(ArrayList<Integer> arr){
        ArrayList<Integer> arrI = new ArrayList<>();
        arrI.add(1);
        arrI.add(2);
        arrI.add(3);

        for (int i = arr.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr.get(j) < arr.get(j + 1)) {
                    int tmp = arr.get(j);
                    int tmpI = arrI.get(j);

                    arr.set(j, arr.get(j + 1));
                    arrI.set(j,arrI.get(j + 1));

                    arr.set(j + 1, tmp);
                    arrI.set(j + 1, tmpI);

                }
            }
        }
        return arrI;
    }
    //This is usual bubble sort that just returning sorted list
    private ArrayList<Integer> usualSort(ArrayList<Integer> arr){
        for (int i = arr.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr.get(j) < arr.get(j + 1)) {
                    int tmp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, tmp);

                }
            }
        }
        return arr;
    }
}
