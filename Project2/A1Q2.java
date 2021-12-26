import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class A1Q2 {
    public static class edge {
        int Node;
        int connectNode;
        int weight;

        public edge(int Node, int connectNode, int weight) {
            this.Node = Node;
            this.connectNode = connectNode;
            this.weight = weight;
        }
    }

    public static class node {
        public int NodeID;
        public int ConnectedNodeID;
        public int Distance;
        public String Coordinates;
        public String Intersection_Name;

        public node(int NodeID, int ConnectedNodeID, int Distance, String Coordinates, String Intersection_Name) {
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
        ArrayList<node> nodes = readNodesFromCSV("/Users/codemaker/IdeaProjects/coder/src/Project2_Input_File1.csv");
        int maxValue = Integer.MAX_VALUE / 10;
        int m = nodes.size();
        int n = nodes.get(m - 1).NodeID + 1;
//        int node1 = 187;
//        int node2 = 68;
        int[][] weights = new int[n][n];

        for(node node: nodes){
            weights[node.NodeID][node.ConnectedNodeID] = node.Distance;
        }

        for(int i = 0; i < weights.length; i++){
            for(int j = 0; j < weights[0].length; j++){
                if(weights[i][j] == 0 && i != j){
                    weights[i][j] = maxValue;
                }
            }
        }

        edge[][] edges = new edge[n][n];
        int[][] touch = new int[n][n];
        int[][] length = new int[n][n];
        int vNear = 0;
        for(int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                touch[k][i] = k;
                length[k][i] = weights[k][i];
            }

            for(int j = 0; j < n ; j++){
                maxValue = Integer.MAX_VALUE / 10;
                for(int i = 0; i < n; i++){
                    if(0 <= length[k][i] && length[k][i] < maxValue){
                        maxValue = length[k][i];
                        vNear = i;
                    }
                }

                edges[k][j] = new edge(vNear, touch[k][vNear], length[k][vNear]);
                for(int i = 0; i < n; i++){
                    if(weights[vNear][i] + length[k][vNear] < length[k][i]){
                        length[k][i] = length[k][vNear] + weights[vNear][i];
                        touch[k][i] = vNear;
                    }
                }
                length[k][vNear] = -1;
            }
        }

        System.out.println("Node\tNode\tDistance");
        int sum = 0;
//        for(int i = 0; i < n; i++){
//            sum += edges[node1][i].weight;
//            System.out.println(edges[node1][i].connectNode + "\t\t" + edges[node1][i].Node + "\t\t" + edges[node1][i].weight);
//        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                Path(i, j, touch);
            }
        }
//        System.out.println(node2);
//        System.out.println("The correct total distance in the minimum-spanning tree is " + sum + " feet.");
        System.out.println();
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

    public static void Path(int q, int r, int[][] touch){
        if(q == r){
            return;
        }
        Path(q, touch[q][r], touch);
        System.out.println(touch[q][r]);
    }

    public static ArrayList<node> readNodesFromCSV(String fileName) {
        ArrayList<node> nodes = new ArrayList<>();
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

                node node = createNode(attributes);

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

    public static node createNode(String[] metadata) {
        int NodeID = Integer.parseInt(metadata[0]);
        int ConnectedNodeID = Integer.parseInt(metadata[1]);
        int Distance = Integer.parseInt(metadata[2]);
        String Coordinates = metadata[3];
        String Intersection_Name = metadata[4];

        return new node(NodeID, ConnectedNodeID, Distance, Coordinates, Intersection_Name);
    }
}