//Implement the Backtracking algorithm for the n-Queens problem (Algorithm 5.1)
//        on your system, and run it on problem instances in which n = 4, 8, 10, and 12.
public class A8Q1 {
        public static int sum;
        public static void main(String[] args) {
                int[] col1 = new int[4];
                queens(-1, 4, col1);
                System.out.println("The number of solutions for n = 4 is " + sum);
                sum = 0;
                int[] col2 = new int[8];
                queens(-1, 8, col2);
                System.out.println("The number of solutions for n = 8 is " + sum);
                sum = 0;
                int[] col3 = new int[10];
                queens(-1, 10, col3);
                System.out.println("The number of solutions for n = 10 is " + sum);
                sum = 0;
                int[] col4 = new int[12];
                queens(-1, 12, col4);
                System.out.println("The number of solutions for n = 12 is " + sum);
        }

        public static void queens(int i, int n, int[] col) {
                if(promising(i, col)){
                        if(i == n - 1){
                                sum++;
                        } else {
                                for(int j = 0; j < n; j++){
                                        col[i + 1] = j;
                                        queens(i + 1, n, col);
                                }
                        }
                }
        }

        public static boolean promising(int i, int[] col) {
                int k = 0;
                boolean flag = true;

                while(k < i && flag){
                        if(col[i] == col[k] || Math.abs(col[i] - col[k]) == i - k){
                                flag = false;
                        }
                        k++;
                }
                return flag;
        }
}