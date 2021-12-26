import java.util.Arrays;
import java.util.Comparator;

public class quiz10 {
    public static int W = 9;
    public static int maxProfit = 0;
    public static int n = 5;
    public static boolean[] include = new boolean[5];
    public static boolean[] bestSet = new boolean[5];


    public static class Item{
        int price;
        int weight;
        int val;
        public Item(int price, int weight){
            this.price = price;
            this.weight = weight;
            this.val = price / weight;
        }
    }

    public static void main(String[] args) {
        Item[] items = new Item[5];
        items[0] = new Item(20, 2);
        items[1] = new Item(30, 5);
        items[2] = new Item(35, 7);
        items[3] = new Item(12, 3);
        items[4] = new Item(3, 1);

        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o2.val - o1.val;
            }
        });
        knapsack(-1, 0, 0, items);

        System.out.print("ith items: ");
        for(int i = 0; i < bestSet.length; i++){
            if(bestSet[i]){
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
        System.out.println("The maximum profit is " + maxProfit);
    }

    public static void knapsack(int i, int profit, int weight, Item[] items){
        if(weight <= W && profit > maxProfit){
            maxProfit = profit;
            for(int j = 0; j < include.length; j++){
                bestSet[j] = include[j];
            }
        }

        if(promising(i, profit, weight, items)){
            include[i + 1] = true;
            knapsack(i + 1, profit + items[i + 1].price, weight + items[i + 1].weight, items);
            include[i + 1] = false;
            knapsack(i + 1, profit, weight, items);
        }
    }

    public static boolean promising(int i, int profit, int weight, Item[] items) {
        if(weight >= W){
            return false;
        } else {
            int j = i + 1;
            int bound = profit;
            int totalWeight = weight;
            while(j < n && totalWeight +  items[j].weight <= W){
                totalWeight += items[j].weight;
                bound += items[j].price;
                j++;
            }
            int k = j;
            if(k < n){
                bound += (W - totalWeight) * items[k].val;
            }
            return bound > maxProfit;
        }
    }
}
