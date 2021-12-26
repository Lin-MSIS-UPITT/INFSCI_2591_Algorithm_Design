import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class A1Q2{
    public static class Edge {
        public int NodeID;
        public int ConnectedNodeID;
        public int Distance;

        public Edge(int NodeID, int ConnectedNodeID, int Distance){
            this.NodeID = NodeID;
            this.ConnectedNodeID = ConnectedNodeID;
            this.Distance = Distance;
        }
    }

    public static class Vertex{
        public int VertexID;
        public Vertex Pre;
        public Vertex Next;
        public int Distance;

        public Vertex(int VertexID){
            this.VertexID = VertexID;
            this.Distance = Integer.MAX_VALUE;
        }
    }

    public static int[] U;

    public static void main(String[] args) {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        ArrayList<Node> nodes1 = Merge.nodes1;
        ArrayList<Node> nodes2 = Merge.nodes2;

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
        Vertex[] vertexHead = new Vertex[length];
        Vertex[] vertexTail = new Vertex[length];
        for(int i = 0; i < length; i++){
            vertexHead[i] = new Vertex(i);
            vertexTail[i] = new Vertex(i);
            vertexHead[i].Next = vertexTail[i];
            vertexTail[i].Pre = vertexHead[i];
        }

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                Vertex vertex = new Vertex(j);
                addVertex(vertex, vertexTail[i]);
            }
        }

        for(Node node : nodes1){
            int a = hashMap1.get(node.NodeID);
            int b = hashMap1.get(node.ConnectedNodeID);
            changeVertex(node.Distance, vertexHead[a], b);
        }

        for(Node node : nodes2){
            int a = hashMap1.get(node.NodeID);
            int b = hashMap1.get(node.ConnectedNodeID);
            changeVertex(node.Distance, vertexHead[a], b);
        }

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.Distance - edge2.Distance;
            }
        });

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(getVertex(vertexHead[i], j).Distance != Integer.MAX_VALUE){
                    Edge edge = new Edge(i, j, getVertex(vertexHead[i], j).Distance);
                    priorityQueue.offer(edge);
                }
            }
        }

        ArrayList<Edge> arrayList = new ArrayList<>();
        U = new int[length];
        for(int i = 0; i < U.length; i++){
            U[i] = i;
        }

        int i, j, p, q;
        int sum = 0;
        while(arrayList.size() != length - 1){
            Edge edge = priorityQueue.poll();
            i = edge.NodeID;
            j = edge.ConnectedNodeID;
            p = find(i);
            q = find(j);
            if(!equal(p, q)){
                merge(p, q);
                arrayList.add(edge);
                sum += edge.Distance;
            }
        }

        System.out.println("The result of the computed minimum-spanning tree is");
        System.out.println("Node\t\t\tNode\t\t\tDistance");

        for(Edge edge : arrayList){
            System.out.print(String.format("%-16d", edge.NodeID));
            System.out.print(String.format("%-16d", edge.ConnectedNodeID));
            System.out.println(String.format("%-16d", edge.Distance));
        }

        System.out.println("The total distance is " + sum);
        long afterUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        System.out.println("The memory usage for the program is " + (afterUsedMem - beforeUsedMem) + " bytes");

    }

    public static Vertex getVertex(Vertex vertexHead, int j) {
        Vertex vertex = vertexHead;
        while(vertex.VertexID != j){
            vertex = vertex.Next;
        }
        return vertex;
    }

    public static void changeVertex(int Distance, Vertex vertexHead, int vertexID) {
        Vertex vertex = vertexHead;
        while(vertex.VertexID != vertexID){
            vertex = vertex.Next;
        }
        vertex.Distance = Distance;
    }

    public static void addVertex(Vertex vertex, Vertex vertexTail) {
        Vertex pre = vertexTail.Pre;
        vertexTail.Pre = vertex;
        vertex.Next = vertexTail;
        pre.Next = vertex;
        vertex.Pre = pre;
    }

    public static void merge(int p, int q){
        if(p < q){
            U[q] = p;
        } else {
            U[p] = q;
        }
    }

    public static boolean equal(int p, int q) {
        if(p == q){
            return true;
        }
        return false;
    }

    public static int find(int i) {
        int j = i;
        while(U[j] != j){
            j = U[j];
        }
        return j;
    }
}