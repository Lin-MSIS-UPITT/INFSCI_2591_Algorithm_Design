import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class Node {
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

public class A1Q3 {
    public static class node{
        public int NodeID;
        public int weight = 0;
        public int highestIndex = 0;
        public node next;
        public node pre;
        public node(int NodeID, int weight){
            this.NodeID = NodeID;
            this.weight = weight;
        }

        public node(int NodeID){
            this.NodeID = NodeID;
        }
    }

    public static void addNode(node lastNode, int connectedNode, int weight){
        node node = new node(connectedNode, weight);
        node pre = lastNode.pre;
        lastNode.pre = node;
        node.next = lastNode;
        pre.next = node;
        node.pre = pre;
    }

    public static void main(String... args) {
        Runtime run = Runtime.getRuntime();

// System.out.println("memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + (run.totalMemory()-run.freeMemory()) );
        run.gc();
        long startMem = run.totalMemory()-run.freeMemory();
        System.out.println("memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + startMem );

        long startTime=System.currentTimeMillis();
        ArrayList<Node> Nodes = readNodesFromCSV("/Users/codemaker/IdeaProjects/coder/src/Project2_Input_File7.csv");
        int inFinite = Integer.MAX_VALUE / 10;
        int m = Nodes.size();
        int n = Nodes.get(m - 1).NodeID + 1;
//        int node1 = 187;
//        int node2 = 68;

        node[] nodeHeads = new node[n];
        node[] nodeTails = new node[n];
        for(int i = 0; i < n; i++){
            nodeHeads[i] = new node(i);
            nodeTails[i] = new node(-1);
            nodeHeads[i].next = nodeTails[i];
            nodeTails[i].pre =  nodeHeads[i];
        }

        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if(i != j){
                    addNode(nodeTails[i], j, inFinite);
                }
            }
        }

        for(Node Node : Nodes){
            changeNode(nodeHeads[Node.NodeID], Node.ConnectedNodeID, Node.Distance);
        }

        for(int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    node nodeA = getNode(nodeHeads[i], j);
                    node nodeB = getNode(nodeHeads[i], k);
                    node nodeF = getNode(nodeHeads[k], j);
                    if(nodeB.weight + nodeF.weight < nodeA.weight){
                        nodeA.weight = nodeB.weight + nodeF.weight;
                        nodeA.highestIndex = k;
                        System.out.println(k);
                    }
                }
            }
        }

//        System.out.println(node1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Path(i, j, nodeHeads);
            }
        }
//        System.out.println(node2);
//        System.out.println(getNode(nodeHeads[node1], node2).weight);

        System.out.println();
        long endTime=System.currentTimeMillis();
        System.out.println("Run time is ： " + (endTime - startTime)+" ms");
        System.out.println("Total memory is " + Runtime.getRuntime().totalMemory() + " bytes");
        long endMem = run.totalMemory()-run.freeMemory();
        System.out.println("memory > total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + endMem );
        System.out.println("memory difference:" + (endMem-startMem));

    }

    public static node getNode(node nodeHead, int val){
        node node = nodeHead;
        while(node.NodeID != val){
            node = node.next;
        }
        return node;
    }

    public static void changeNode(node nodeTail, int connectedNodeID, int distance) {
        node node = nodeTail;
        while(node.NodeID != connectedNodeID){
            node = node.next;
        }
        node.weight = distance;
    }

    public static void Path(int q, int r, node[] nodeHeads){
        int index = getNode(nodeHeads[q], r).highestIndex;
        if(index != 0){
            Path(q, index, nodeHeads);
            System.out.println(index);
            Path(index, r, nodeHeads);
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