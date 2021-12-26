import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class project422 {
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
        int N = nodes.get(m - 1).NodeID + 1;
        int lowerTri = N * (N - 1) / 2;
        int[] W = new int[lowerTri];

        Arrays.fill(W, inFinite);

        for(Node node: nodes){
            int a = node.NodeID;
            int b = node.ConnectedNodeID;
            int c = node.Distance;
            if(a > b){
                int d = a * (a - 1) / 2 + b;
                W[d] = c;
            }
        }

        int subsets = 1;
        for(int i = 0; i < N - 1; i++){
            subsets = subsets << 1;
        }

        int[][] D = new int[N][subsets];
        for(int i = 0; i < D.length; i++){
            for(int j = 0; j < D[0].length; j++){
                D[i][j] = Integer.MAX_VALUE / 10;
            }
        }

        int[][] P = new int[N][subsets];
        for(int i = 1; i < N; i++){
            int index = i * (i - 1) / 2;
            D[i][0] = W[index];
        }

        for(int k = 1; k < N - 1; k++){
            ArrayList<Integer> subset = subsets(k, N - 1);
            for(Integer integer: subset){
                for(int i = 0; i < N - 1; i++){
                    int num1 = 1 << i;
                    if((num1 & integer) == 0){
                        for(int j = 0; j < N - 1; j++){
                            int num2 = 1 << j;
                            if((num2 & integer) != 0){
                                int num3 = integer ^ (1 << j);
                                int index;
                                if(i >= j){
                                    index = (i + 1) * i / 2 + (j + 1);
                                } else {
                                    index = (j + 1) * j / 2 + (i + 1);
                                }
                                if(W[index] + D[j + 1][num3] < D[i + 1][integer]){
                                    D[i + 1][integer] = W[index] + D[j + 1][num3];
                                    P[i + 1][integer] = j + 1;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int j = 0; j < N - 1; j++){
            int num4 = (subsets - 1) ^ (1 << j);
            int index = (j + 1) * j / 2;
            if(W[index] + D[j + 1][num4] < D[0][subsets - 1]){
                D[0][subsets - 1] = W[index] + D[j + 1][num4];
                P[0][subsets - 1] = j + 1;
            }
        }

        System.out.println("NodeID\t\tConnectedNodeID\t\tDistance");
        int a = 0;
        int b = subsets - 1;
        while(b != 0){
            System.out.printf("%-12d",a);
            System.out.printf("%-20d",P[a][b]);
            int index;
            if(a >= P[a][b]){
                index = a * (a - 1) / 2 + P[a][b];
            } else {
                index = P[a][b] * (P[a][b] - 1) / 2 + a;
            }
            System.out.printf("%-12d",W[index]);
            System.out.println();
            a = P[a][b];
            b = b ^ (1 << (a - 1));
        }
        System.out.printf("%-12d",a);
        System.out.printf("%-20d",P[a][b]);
        int index = a * (a - 1) / 2 + P[a][b];
        System.out.printf("%-12d",W[index]);
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