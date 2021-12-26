import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
class edge {
    int Node;
    int connectNode;
    int weight;

    public edge(int Node, int connectNode, int weight) {
        this.Node = Node;
        this.connectNode = connectNode;
        this.weight = weight;
    }
}

class NODES {
    public int NodeID;
    public int ConnectedNodeID;
    public int Distance;
    public String Coordinates;
    public String Intersection_Name;

    public NODES(int NodeID, int ConnectedNodeID, int Distance, String Coordinates, String Intersection_Name) {
        this.NodeID = NodeID;
        this.ConnectedNodeID = ConnectedNodeID;
        this.Distance = Distance;
        this.Coordinates = Coordinates;
        this.Intersection_Name = Intersection_Name;
    }
}

public class A1Q4 {
    public static class Node{
        public int NodeID;
        public int weight = 0;
        public int touch = 0;
        public int length = 0;
        public Node next;
        public Node pre;
        public edge edge;

        public Node(int NodeID, int weight){
            this.NodeID = NodeID;
            this.weight = weight;
        }

        public Node(int NodeID){
            this.NodeID = NodeID;
        }
    }

    public static Node getNode(Node nodeHead, int val){
        Node node = nodeHead;
        while(node.NodeID != val){
            node = node.next;
        }
        return node;
    }

    public static void addNode(Node lastNode, int connectedNode, int weight){
        Node node = new Node(connectedNode, weight);
        Node pre = lastNode.pre;
        lastNode.pre = node;
        node.next = lastNode;
        pre.next = node;
        node.pre = pre;
    }

    public static void changeNode(Node nodeTail, int connectedNodeID, int distance) {
        Node node = nodeTail;
        while(node.NodeID != connectedNodeID){
            node = node.next;
        }
        node.weight = distance;
    }

    public static void main(String... args) {
        Runtime run = Runtime.getRuntime();

// System.out.println("memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + (run.totalMemory()-run.freeMemory()) );
        run.gc();
        long startMem = run.totalMemory()-run.freeMemory();
        System.out.println("memory> total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + startMem );

        long startTime=System.currentTimeMillis();
        ArrayList<NODES> nodes = readNodesFromCSV("/Users/codemaker/IdeaProjects/coder/src/Project2_Input_File6.csv");
        int maxValue = Integer.MAX_VALUE / 10;
        int m = nodes.size();
        int n = nodes.get(m - 1).NodeID + 1;
//        int node1 = 187;
//        int node2 = 68;

        Node[] nodeHeads = new Node[n];
        Node[] nodeTails = new Node[n];
        for(int i = 0; i < n; i++){
            nodeHeads[i] = new Node(i);
            nodeTails[i] = new Node(-1);
            nodeHeads[i].next = nodeTails[i];
            nodeTails[i].pre =  nodeHeads[i];
        }

        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if(i != j){
                    addNode(nodeTails[i], j, maxValue);
                }
            }
        }

        for(NODES Node : nodes){
            changeNode(nodeHeads[Node.NodeID], Node.ConnectedNodeID, Node.Distance);
        }

        int vNear = 0;
        for(int k = 0; k < n; k++){
            for(int i = 0; i < n; i++){
                Node flag = nodeHeads[k];
                while(flag.NodeID != -1){
                    flag.touch = k;
                    flag.length = flag.weight;
                    flag = flag.next;
                }
            }

            for(int j = 0; j < n; j++){
                maxValue = Integer.MAX_VALUE / 10;
                for(int i = 0; i < n; i++){
                    if(0 <= getNode(nodeHeads[k], i).length && getNode(nodeHeads[k], i).length < maxValue){
                        maxValue = getNode(nodeHeads[k], i).length;
                        vNear = i;
                    }
                }

                getNode(nodeHeads[k], j).edge = new edge(vNear, getNode(nodeHeads[k], vNear).touch, getNode(nodeHeads[k], vNear).length);
                for(int i = 0; i < n; i++){
                    Node Node1 = getNode(nodeHeads[vNear], i);
                    Node Node2 = getNode(nodeHeads[k], vNear);
                    Node Node3 = getNode(nodeHeads[k], i);
                    if(Node1.weight + Node2.length < Node3.length){
                        Node3.length = Node1.weight + Node2.length;
                        Node3.touch = vNear;
                    }
                }
                getNode(nodeHeads[k], vNear).length = -1;
            }
            System.out.println(k);
        }

        System.out.println("Node\tNode\tDistance");
//        for(int i = 0; i < n; i++){
//            System.out.println(getNode(nodeHeads[node1], i).edge.connectNode + "\t\t" + getNode(nodeHeads[node1], i).edge.Node + "\t\t" + getNode(nodeHeads[node1], i).edge.weight);
//        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Path(i, j, nodeHeads);
            }
        }
//        System.out.println(node2);
        System.out.println("The correct total distance in the minimum-spanning tree is feet.");

        System.out.println();
        long endTime=System.currentTimeMillis();
        System.out.println("Run time is ： " + (endTime - startTime)+" ms");
        System.out.println("Total memory is " + Runtime.getRuntime().totalMemory() + " bytes");
        long endMem = run.totalMemory()-run.freeMemory();
        System.out.println("memory > total:" + run.totalMemory() + " free:" + run.freeMemory() + " used:" + endMem );
        System.out.println("memory difference:" + (endMem-startMem));
    }

    public static void Path(int q, int r, Node[] nodeHeads){
        if(q == r){
            return;
        }
        Path(q, getNode(nodeHeads[q], r).touch, nodeHeads);
        System.out.println(getNode(nodeHeads[q], r).touch);
    }

    public static ArrayList<NODES> readNodesFromCSV(String fileName) {
        ArrayList<NODES> nodes = new ArrayList<>();
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

                NODES node = createNode(attributes);

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

    public static NODES createNode(String[] metadata) {
        int NodeID = Integer.parseInt(metadata[0]);
        int ConnectedNodeID = Integer.parseInt(metadata[1]);
        int Distance = Integer.parseInt(metadata[2]);
        String Coordinates = metadata[3];
        String Intersection_Name = metadata[4];

        return new NODES(NodeID, ConnectedNodeID, Distance, Coordinates, Intersection_Name);
    }
}