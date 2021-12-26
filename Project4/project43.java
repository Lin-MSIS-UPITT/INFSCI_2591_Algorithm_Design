import java.util.ArrayList;

public class project43 {

    public static class Node{
        int x;
        int y;
        int z;
        public Node(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static void main(String[] args) {
        ArrayList<Node> arrayList0 = new ArrayList<>();
        System.out.println("Legal queen configurations for n = 2 is " + subsets(arrayList0, 2, 0, 0));
        ArrayList<Node> arrayList1 = new ArrayList<>();
        System.out.println("Legal queen configurations for n = 3 is " + subsets(arrayList1, 3, 0, 0));
        ArrayList<Node> arrayList2 = new ArrayList<>();
        System.out.println("Legal queen configurations for n = 4 is " + subsets(arrayList2, 4, 0, 0));
        ArrayList<Node> arrayList3 = new ArrayList<>();
        System.out.println("Legal queen configurations for n = 5 is " + subsets(arrayList3, 5, 0, 0));
    }

    public static int subsets(ArrayList<Node> arrayList, int n, int flag, int sum){
        if(flag == n){
            sum++;
            return sum;
        }
        for(int z = 0; z < n; z++){
            for(int y = 0; y < n; y++){
                for(int x = 0; x < n; x++){
                    if(checkBigger(arrayList, x, y, z)){
                        arrayList.add(new Node(x, y, z));
                        if(checkPromising(arrayList, x, y, z)){
                            sum = subsets(arrayList, n, flag + 1, sum);
                        }
                        remove(arrayList);
                    }
                }
            }
        }
        return sum;
    }

    public static void remove(ArrayList<Node> arrayList){
        int n = arrayList.size();
        if(n != 0){
            arrayList.remove(arrayList.get(n - 1));
        }
    }

    public static boolean checkPromising(ArrayList<Node> arrayList, int x, int y, int z){
        int length = arrayList.size();
        for(int i = 0; i < length - 1; i++){
            Node node = arrayList.get(i);
//            if (node.equals(arrayList.get(length - 1))) continue;
            int xx = node.x;
            int yy = node.y;
            int zz = node.z;
            if(x == xx && y == yy) return false;
            if(z != zz){
                boolean b1 = Math.abs(y - yy) == Math.abs(z - zz);
                if(x == xx){
                    if(b1) return false;
                } else {
                    boolean b = Math.abs(x - xx) == Math.abs(z - zz);
                    if(y == yy){
                        if(b) return false;
                    } else if(b && b1){
                        return false;
                    }
                }
            }
            if(z == zz && (x == xx || y == yy || Math.abs(x - xx) == Math.abs(y - yy))) return false;
        }
        return true;
    }

    public static boolean checkBigger(ArrayList<Node> arrayList, int x, int y, int z){
        if(!arrayList.isEmpty()){
            int n = arrayList.size() - 1;
            Node node = arrayList.get(n);
            int xx = node.x;
            int yy = node.y;
            int zz = node.z;
            if(z < zz) return false;
            if(z == zz) {
                if (y < yy) return false;
                if (y == yy) {
                    if (x <= xx) return false;
                }
            }
        }
        return true;
    }
}