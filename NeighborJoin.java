import java.util.ArrayList;
import java.util.HashMap;

public class NeighborJoin
{
    private ArrayList<ArrayList<Double>> distance = new ArrayList<>();
    private HashMap<String, Double> taxonDistance;
    private int intermediateCount;
    private ArrayList<String> nodeNames;

    NeighborJoin(double[][] distanceMatrix)
    {
        for (double[] i: distanceMatrix){
            ArrayList<Double> al = new ArrayList<>();
            for (double j: i)
                al.add(j);
            distance.add(al);
        }
        nodeNames = new ArrayList<>();
        for (int i = 0; i < distance.size(); i++)
            nodeNames.add(String.valueOf((char)(i+97)));
        taxonDistance = new HashMap<>();
        intermediateCount = 1;
    }

    private void calculateQ1()
    {
        while (distance.size() > 1) {
            ArrayList<ArrayList<Double>> q1 = new ArrayList<>();
            for (ArrayList<Double> i : distance) {
                ArrayList<Double> temp = new ArrayList<>();
                for (double j : i) temp.add(j);
                q1.add(temp);
            }
            double min = Double.MAX_VALUE;
            int minRow1 = 0;
            int minRow2 = 0;
            for (int row1 = 0; row1 < q1.size(); row1++) {
                for (int row2 = 0; row2 < q1.size(); row2++) {
                    if (row1 != row2) {
                        double dist = (q1.size() - 2) * distance.get(row1).get(row2) - getRowSum(row1) - getRowSum(row2);
                        q1.get(row1).set(row2, dist);
                        q1.get(row2).set(row1, dist);
                        if (dist < min) {
                            min = dist;
                            minRow1 = row1;
                            minRow2 = row2;
                        }
                    }
                }
            }
            newDistanceMatrix(minRow1, minRow2);
        }
    }

    private HashMap<String, Double> getNeighborJoin() { return taxonDistance; }

    private void newDistanceMatrix(int row1, int row2)
    {
        double u1 = 0.5*distance.get(row1).get(row2) + (getRowSum(row1) - getRowSum(row2))/(2.0 * (distance.size() - 2));
        double u2 = distance.get(row1).get(row2) - u1;
        if (distance.size() == 2) {
            u1 = distance.get(row1).get(row2);
            distance.remove(row1);
            taxonDistance.put(nodeNames.get(row2) + "-u" + (intermediateCount-1), u1);
        }
        else {
            ArrayList<Double> r1 = distance.get(row1);
            ArrayList<Double> dist = new ArrayList<>();
            dist.add(0.0);
            for (int i = 0; i < r1.size(); i++)
                if (i != row1 && i != row2)
                    dist.add(0.5 * (distance.get(row1).get(i) + distance.get(row2).get(i) - distance.get(row1).get(row2)));
            distance.remove(row1);
            distance.remove(row2 - 1);
            for (ArrayList<Double> q : distance) {
                q.remove(row1);
                q.remove(row2 - 1);
            }
            distance.add(0, dist);
            for (int i = 1; i < distance.size(); i++)
                distance.get(i).add(0, dist.get(i));
            taxonDistance.put(nodeNames.get(row1) + "-u" + intermediateCount, u1);
            nodeNames.remove(row1);
            nodeNames.add(row1, "u" + intermediateCount);
            taxonDistance.put(nodeNames.get(row2) + "-u" + intermediateCount++, u2);
            nodeNames.remove(row2);
        }
    }

    private int getRowSum(int row)
    {
        int sum = 0;
        for (double i: distance.get(row)) sum+= i;
        return sum;
    }

    public static void main(String[] args) {
        double[][] dm = {{0,5,9,9,8}, {5,0,10,10,9}, {9,10,0,8,7}, {9,10,8,0,3}, {8,9,7,3,0}};
        NeighborJoin nj = new NeighborJoin(dm);
        nj.calculateQ1();
        System.out.println(nj.getNeighborJoin());

        double[][] dm2 = {{0,0.23,0.16,0.2,0.17}, {0.23,0,0.23,0.17,0.24}, {0.16,0.23,0,0.15,0.11}, {0.20,0.17,0.15,0,0.21}, {0.17,0.24,0.11,0.21,0}};
        NeighborJoin nj2 = new NeighborJoin(dm2);
        nj2.calculateQ1();
        System.out.println(nj2.getNeighborJoin());

        double[][] dm3 = {{0,5,4,7,6,8},{5,0,7,10,9,11},{4,7,0,7,6,8},{7,10,7,0,5,9},{6,9,6,5,0,8},{8,11,8,9,8,0}};
        NeighborJoin nj3 = new NeighborJoin(dm3);
        nj3.calculateQ1();
        System.out.println(nj3.getNeighborJoin());
    }

}
