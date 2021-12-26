import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class project41 {

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

    public static class Vertex{
        public int vertexID;
        public int visitedTime;

        public Vertex(int vertexID){
            this.vertexID = vertexID;
            visitedTime = 0;
        }
    }

    public static void main(String... args) {
        ArrayList<Node> nodes = readNodesFromCSV("/Users/linyang/IdeaProjects/project3/src/Project 4_Problem 1_InputData.csv");
        int inFinite = Integer.MAX_VALUE / 10;
        int m = nodes.size();
        int n = nodes.get(m - 1).NodeID + 1;
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

        Vertex[] vertices = new Vertex[n];
        for(int i = 0; i < n; i++){
            vertices[i] = new Vertex(i);
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                Path(i, j, highestNode, vertices);
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 1; j < n; j++){
                if(vertices[j].visitedTime < vertices[j - 1].visitedTime){
                    Vertex vertex = vertices[j];
                    vertices[j] = vertices[j - 1];
                    vertices[j - 1] = vertex;
                }
            }
        }

        System.out.println("Top 20 betweenness centrality vertices");
        System.out.println("VertexID\tvisitedTime");
        for(int i = n - 1; i >= n - 20; i--){
            System.out.printf("%-12d", vertices[i].vertexID);
            System.out.printf("%-12d", vertices[i].visitedTime);
            System.out.println();
        }
    }

    public static void Path(int q, int r, int[][] highestNode, Vertex[] vertices){
        if(highestNode[q][r] != 0){
            Path(q, highestNode[q][r], highestNode, vertices);
            vertices[highestNode[q][r]].visitedTime++;
            Path(highestNode[q][r], r, highestNode, vertices);
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