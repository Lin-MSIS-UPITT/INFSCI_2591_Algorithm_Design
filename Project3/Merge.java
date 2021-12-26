import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

class Node {
    public int NodeID;
    public int ConnectedNodeID;
    public int Distance;
    public String Coordinates;

    public Node(int NodeID, int ConnectedNodeID, int Distance, String Coordinates) {
        this.NodeID = NodeID;
        this.ConnectedNodeID = ConnectedNodeID;
        this.Distance = Distance;
        this.Coordinates = Coordinates;
    }
}

public class Merge {

    static ArrayList<Node> nodes1 = readNodesFromCSV("/Users/linyang/IdeaProjects/project3/src/Project3_G1_Data.csv");
    static ArrayList<Node> nodes2 = readNodesFromCSV("/Users/linyang/IdeaProjects/project3/src/Project3_G2_Data.csv");

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
        String Coordinates1 = metadata[3];
        String Coordinates2 = metadata[4];
        String Coordinates = Coordinates1 + Coordinates2;

        return new Node(NodeID, ConnectedNodeID, Distance, Coordinates);
    }

    public static int[][] adjMatrix(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<Integer, Integer> hashMap1 = new HashMap<>();
        int n = nodes1.size();
        int m = nodes2.size();
        int n1 = nodes1.get(n-1).NodeID + 1;
        for(Node node: nodes2){
            node.NodeID += n1;
            node.ConnectedNodeID += n1;
        }

        for(int i = 0; i < n; i++){
            Node node = nodes1.get(i);
            if(!hashMap.containsKey(node.Coordinates)){
                hashMap.put(node.Coordinates,node.NodeID);
                hashMap1.put(node.NodeID, node.NodeID);
            }
        }

        for(int i = 0; i < m; i++){
            Node node = nodes2.get(i);
            int size = hashMap.size();
            if(!hashMap.containsKey(node.Coordinates)) {
                hashMap.put(node.Coordinates, size);
                hashMap1.put(node.NodeID, size);
            } else if(hashMap.get(node.Coordinates) != size - 1){
                hashMap1.put(node.NodeID, hashMap.get(node.Coordinates));
            }
        }

        int length = hashMap.size();
        int[][] matrix = new int[length][length];
        for(Node node : nodes1){
            int a = hashMap1.get(node.NodeID);
            int b = hashMap1.get(node.ConnectedNodeID);
            matrix[a][b] = node.Distance;
        }

        for(Node node : nodes2){
            int a = hashMap1.get(node.NodeID);
            int b = hashMap1.get(node.ConnectedNodeID);
            matrix[a][b] = node.Distance;
        }
        return matrix;
    }
}