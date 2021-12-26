import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Quiz11 {
    public static int W = 13;
    public static int n = 5;
    public static int sum = 0;
    public static int maxProfit = 0;

    public static class Item{
        public int profit;
        public int weight;
        public int value;

        public Item(int profit, int weight){
            this.profit = profit;
            this.weight = weight;
            this.value = profit / weight;
        }
    }

    public static class Node{
        public int level;
        public int profit;
        public int weight;
        public double bound;
        public ArrayList<Integer> path;

        public Node(int level, int profit, int weight){
            this.level = level;
            this.profit = profit;
            this.weight = weight;
            path = new ArrayList<>();
        }
        public Node(){
            path = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        Item[] items = new Item[5];
        items[0] = new Item(20, 2);
        items[1] = new Item(30, 5);
        items[2] = new Item(35, 7);
        items[3] = new Item(12, 3);
        items[4] = new Item(3, 1);
        Node node = knapsack3(items);
        System.out.println("The number of nodes it visits is " + sum);
        System.out.println("The maximum profit is " + maxProfit);
        System.out.print("The ith item is ");
        for (Integer integer: node.path) {
            System.out.print(integer + " ");
        }
    }

    public static Node knapsack3(Item[] items){
        PriorityQueue<Node> PQ = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return (int)(node2.bound - node1.bound);
            }
        });
        Node node = new Node();
        Node v = new Node(-1, 0,0);
        v.bound = bound(v, items);
        PQ.offer(v);
        sum += 1;
        while(!PQ.isEmpty()){
            v = PQ.poll();
            if(v.bound > maxProfit) {
                Node u = new Node();
                sum += 2;
                u.level = v.level + 1;
                u.weight = v.weight + items[u.level].weight;
                u.profit = v.profit + items[u.level].profit;
                u.path.addAll(v.path);
                u.path.add(u.level + 1);

                if (u.weight <= W && u.profit > maxProfit) {
                    maxProfit = u.profit;
                    node = u;
                }
                u.bound = bound(u, items);
                if (u.bound > maxProfit) {
                    PQ.offer(u);
                }
                Node w = new Node();
                w.level = v.level + 1;
                w.weight = v.weight;
                w.profit = v.profit;
                w.bound = bound(w, items);
                w.path.addAll(v.path);
                if (w.bound > maxProfit) {
                    PQ.offer(w);
                }
            }
        }
        return node;
    }

    public static double bound(Node u, Item[] items) {
        if(u.weight >= W){
            return 0;
        } else {
            double result = u.profit;
            int j = u.level + 1;
            int totalWeight = u.weight;
            while(j < n && totalWeight + items[j].weight <= W){
                totalWeight += items[j].weight;
                result += items[j].profit;
                j++;
            }

            int k = j;
            if(k < n){
                result += (W - totalWeight) * items[k].value;
            }
            return result;
        }
    }
}