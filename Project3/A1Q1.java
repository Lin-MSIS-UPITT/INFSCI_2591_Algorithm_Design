import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class A1Q1{
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

    public static int[] U;

    public static void main(String[] args) {
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        int[][] matrix = Merge.adjMatrix();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.Distance - edge2.Distance;
            }
        });

        int length = matrix.length;
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(matrix[i][j] != 0){
                    Edge edge = new Edge(i, j, matrix[i][j]);
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