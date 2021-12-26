import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class A1Q1 {
    public static int sum = 0;
    public static int lastNode = 0;

    public static class Node {
        public int NodeID;
        public int ConnectedNodeID;
        public int Distance;
        public String Coordinates;
        public String Intersection_Name;

        public Node(int NodeID, int ConnectedNodeID, int Distance, String Coordinates, String Intersection_Name) {
            this.NodeID = NodeID;
            this.ConnectedNodeID = ConnectedNodeID;
            this.Distance = Distance;
            this.Coordinates = Coordinates;
            this.Intersection_Name = Intersection_Name;
        }
    }

    public static void main(String... args) {
        Runtime run = Runtime.getRuntime();

// System.out.println("memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + (run.totalMemory()-run.freeMemory()) );
        run.gc();
        long startMem = run.totalMemory()-run.freeMemory();
        System.out.println("memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + startMem );
        long startTime=System.currentTimeMillis();
        Runtime.getRuntime().gc();
        long initM = Runtime.getRuntime().freeMemory();
        ArrayList<Node> nodes = readNodesFromCSV("/Users/codemaker/IdeaProjects/coder/src/Project2_Input_File3.csv");
        int inFinite = Integer.MAX_VALUE / 10;
        int m = nodes.size();
        int n = nodes.get(m - 1).NodeID + 1;
        int node1 =65;
        int node2 =280;
        int[][] weights = new int[n][n];

        for(Node node: nodes){
            weights[node.NodeID][node.ConnectedNodeID] = node.Distance;
        }

        int[][] shortestPath = new int[n][n];
        for(int i = 0; i < shortestPath.length; i++){
            for(int j = 0; j < shortestPath[0].length; j++){
                if(weights[i][j] == 0 && i != j){
                    weights[i][j] = inFinite;
                }
                shortestPath[i][j] = weights[i][j];
            }
        }

        int[][] highestNode = new int[n][n];
        for(int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(shortestPath[i][k] + shortestPath[k][j] < shortestPath[i][j]){
                        highestNode[i][j] = k;
                        shortestPath[i][j] = shortestPath[i][k] + shortestPath[k][j];
                    }
                }
            }
        }

        System.out.println("Start: " + node1);
        lastNode = node1;
        Path(node1, node2, highestNode, shortestPath);
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                Path(i, j, highestNode, shortestPath);
//            }
//        }
        sum += shortestPath[lastNode][node2];
        System.out.println("End: " + node2);
        System.out.println();
        Runtime.getRuntime().gc();
        long endM = Runtime.getRuntime().freeMemory();
        System.out.println();
        long endTime=System.currentTimeMillis();
        System.out.println("Run time is ： " + (endTime - startTime)+" ms");
        System.out.println("Total memory is " + Runtime.getRuntime().totalMemory() + " bytes");
        long endMem = run.totalMemory()-run.freeMemory();
        System.out.println("memory > total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + endMem );
        System.out.println("memory difference:" + (endMem-startMem));
    }

    public static void Path(int q, int r, int[][] highestNode, int[][] shortestPath){
        if(highestNode[q][r] != 0){
            Path(q, highestNode[q][r], highestNode, shortestPath);
            System.out.println(highestNode[q][r]);
            sum += shortestPath[lastNode][highestNode[q][r]];
            lastNode = highestNode[q][r];
            Path(highestNode[q][r], r, highestNode, shortestPath);
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
        String Coordinates = metadata[3];
        String Intersection_Name = metadata[4];

        return new Node(NodeID, ConnectedNodeID, Distance, Coordinates, Intersection_Name);
    }
}