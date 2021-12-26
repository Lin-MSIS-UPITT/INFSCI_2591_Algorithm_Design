import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class quiz7 {
    public static class Edge {
        int Node;
        int connectNode;
        int weight;

        public Edge(int Node, int connectNode, int weight) {
            this.Node = Node;
            this.connectNode = connectNode;
            this.weight = weight;
        }
    }

    public static class node{
        int nodeID;
        node pre;
        node next;
        int weight = 0;
        public node(int nodeID, int weight){
           this.nodeID = nodeID;
           this.weight = weight;
        }
        public node(int nodeID){
            this.nodeID = nodeID;
        }
    }

        public static node getNode(node nodeHead, int val){
        node node = nodeHead;
        while(node.nodeID != val){
            node = node.next;
        }
        return node;
    }

    public static void addNode(node lastNode, int connectedNode, int weight){
        node node = new node(connectedNode, weight);
        node pre = lastNode.pre;
        lastNode.pre = node;
        node.next = lastNode;
        pre.next = node;
        node.pre = pre;
    }

    public static void main(String[] args) {
        Path pathToFile = Paths.get("/Users/codemaker/IdeaProjects/coder/src/Quiz6_Input_File.csv");
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();
            String[] attribute = line.split(",");
            int n = attribute.length;

            node[] nodeHeads = new node[n];
            node[] nodeTails = new node[n];
            for(int i = 0; i < n; i++){
                nodeHeads[i] = new node(i);
                nodeTails[i] = new node(-1);
                nodeHeads[i].next = nodeTails[i];
                nodeTails[i].pre =  nodeHeads[i];
            }

            int maxValue = Integer.MAX_VALUE;
            int vNear = 0;

            for(int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    if(i != j){
                        addNode(nodeTails[i], j, maxValue);
                    }
                }
            }

            for(int i = 0; i < n; i++){
                line = br.readLine();
                String[] attributes = line.split(",");
                for(int j = 0; j < n; j++){
                    node node = getNode(nodeHeads[i], j);
                    node.weight = Integer.parseInt(attributes[j]);
                    if(i != j && node.weight == 0){
                        node.weight = maxValue;
                    }
                }
            }

            ArrayList<Edge> arrayList = new ArrayList<>();
            int[] nearest = new int[n];
            int[] distance = new int[n];
            for(int i = 1; i < n; i++){
                nearest[i] = 0;
                node node = getNode(nodeHeads[0], i);
                distance[i] = node.weight;
            }

            for(int j = 0; j < n - 1; j++){
                maxValue = Integer.MAX_VALUE;
                for(int i = 1; i < n; i++){
                    if(0 <= distance[i] && distance[i] < maxValue){
                        maxValue = distance[i];
                        vNear = i;
                    }
                }
                arrayList.add(new Edge(vNear, nearest[vNear], distance[vNear]));
                distance[vNear] = -1;
                for(int i = 1; i < n; i++){
                    node node = getNode(nodeHeads[i], vNear);
                    if(node.weight < distance[i]){
                        distance[i] = node.weight;
                        nearest[i] = vNear;
                    }
                }
            }
            System.out.println("Node\tNode\tDistance");
            int sum = 0;
            for(Edge edge: arrayList){
                sum += edge.weight;
                System.out.println(edge.connectNode + "\t\t" + edge.Node + "\t\t" + edge.weight);
            }
            System.out.println("The correct total distance in the minimum-spanning tree is " + sum + " feet.");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}