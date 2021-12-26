import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class project421 {
    public static class Node {
        public int NodeID;
        public int ConnectedNodeID;
        public int Distance;

        public Node(int NodeID, int ConnectedNodeID, int Distance) {
            this.NodeID = NodeID;
            this.ConnectedNodeID = ConnectedNodeID;
            this.Distance = Distance;
        }
    }

    public static void main(String... args) {
        ArrayList<Node> nodes = readNodesFromCSV("/Users/linyang/IdeaProjects/project3/src/Project 4_Problem 2_InputData.csv");
        int inFinite = Integer.MAX_VALUE / 10;
        int m = nodes.size();
        int n = nodes.get(m - 1).NodeID + 1;
        int[][] W = new int[n][n];

        for(Node node: nodes){
            W[node.NodeID][node.ConnectedNodeID] = node.Distance;
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(W[i][j] == 0 && i != j){
                    W[i][j] = inFinite;
                }
            }
        }

        int subsets = 1;
        for(int i = 0; i < n - 1; i++){
            subsets = subsets << 1;
        }

        int[][] D = new int[n][subsets];
        for(int i = 0; i < D.length; i++){
            for(int j = 0; j < D[0].length; j++){
                D[i][j] = Integer.MAX_VALUE / 10;
            }
        }

        int[][] P = new int[n][subsets];
        for(int i = 1; i < n; i++){
            D[i][0] = W[i][0];
        }

        for(int k = 1; k < n - 1; k++){
            ArrayList<Integer> subset = subsets(k, n - 1);
            for(Integer integer: subset){
                for(int i = 0; i < n - 1; i++){
                    int num1 = 1 << i;
                    if((num1 & integer) == 0){
                        for(int j = 0; j < n - 1; j++){
                            int num2 = 1 << j;
                            if((num2 & integer) != 0){
                                int num3 = integer ^ (1 << j);
                                if(W[i + 1][j + 1] + D[j + 1][num3] < D[i + 1][integer]){
                                    D[i + 1][integer] = W[i + 1][j + 1] + D[j + 1][num3];
                                    P[i + 1][integer] = j + 1;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int j = 0; j < n - 1; j++){
            int num4 = (subsets - 1) ^ (1 << j);
            if(W[0][j + 1] + D[j + 1][num4] < D[0][subsets - 1]){
                D[0][subsets - 1] = W[0][j + 1] + D[j + 1][num4];
                P[0][subsets - 1] = j + 1;
            }
        }

        System.out.println("NodeID\t\tConnectedNodeID\t\tDistance");
        int a = 0;
        int b = subsets - 1;
        while(b != 0){
            System.out.printf("%-12d",a);
            System.out.printf("%-20d",P[a][b]);
            System.out.printf("%-12d",W[a][P[a][b]]);
            System.out.println();
            a = P[a][b];
            b = b ^ (1 << (a - 1));
        }
        System.out.printf("%-12d",a);
        System.out.printf("%-20d",P[a][b]);
        System.out.printf("%-12d",W[a][P[a][b]]);
        System.out.println();
        System.out.println("The minimum distance for the salesperson to travel is " + D[0][subsets - 1]);
    }

    public static ArrayList<Integer> subsets(int k, int n){
        ArrayList<Integer> subsets = new ArrayList<>();
        subsetNum(0, 0, k, n, subsets);
        return subsets;
    }

    public static void subsetNum(int subset, int index, int k, int n, ArrayList<Integer> subsets){
        if (n - index < k) return;

        if (k == 0) {
            subsets.add(subset);
        } else {
            for (int i = index; i < n; i++) {

                subset |= (1 << i);

                subsetNum(subset, i + 1, k - 1, n, subsets);

                subset ^= (1 << i);
            }
        }
    }



    public static ArrayList<Node> readNodesFromCSV(String fileName) {
        ArrayList<Node> nodes = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();
            line = br.readLine();

            // loop until all lines are read
            while (line != null) {
                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Node node = createNode(attributes);

                // adding book into ArrayList
                nodes.add(node);

                // read next line before looping
                //if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return nodes;
    }

    public static Node createNode(String[] metadata) {
        int NodeID = Integer.parseInt(metadata[0]);
        int ConnectedNodeID = Integer.parseInt(metadata[1]);
        int Distance = Integer.parseInt(metadata[2]);

        return new Node(NodeID, ConnectedNodeID, Distance);
    }
}