import com.company.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Player pl1 = new RandomPlayer();
        Player pl2 = new GreedyPlayer();
        Player pl3 = new AlmostGreedyPlayer();
        Player pl4 = new Smart1Player();
        Player pl5 = new PutinPlayer();

        ArrayList<Player> players = new ArrayList<>();
        //players.add(pl1);
        //players.add(pl2);
        //players.add(pl3);
        //players.add(pl4);
        players.add(pl5);

        ArrayList results = tournament(players, 10);
        for (int i = 0; i <results.size() ; i++) {
            System.out.println(results.get(i));
        }


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

                    System.out.println(" ");
                    // Mooses doing their choice, and we storing their moves
                    System.out.println(grassList.get(1) + " " + grassList.get(2) + " " + grassList.get(3));

                    int prevMove1 = lastMoves.get(j);
                    int prevMove2 = lastMoves.get(i);
                    lastMoves.set(i, players.get(i).move(prevMove1, grassList.get(1), grassList.get(2), grassList.get(3)));
                    lastMoves.set(j, players.get(j).move(prevMove2, grassList.get(1), grassList.get(2), grassList.get(3)));


                    System.out.println("First moose: " + lastMoves.get(i));
                    System.out.println("Second moose: " + lastMoves.get(j));

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
