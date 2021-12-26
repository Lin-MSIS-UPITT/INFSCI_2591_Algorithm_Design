public class C1Q32 {
    public static void main(String[] args){
        int n = 25;
        int k = 10;
        long startTime = System.nanoTime();
        System.out.println(bin(n , k));
        long endTime = System.nanoTime();
        System.out.println("Algorithm 3.2 runs " + (endTime - startTime)
                + " nanoseconds when n = " + n + " and k = " + k);
    }

    public static int bin(int n, int k){
        int[][] B = new int[n+1][k+1];
        for(int i = 0; i <= n; i++){
            for(int j = 0; j <= Math.min(i, k); j++){
                if(j == 0 || j == i){
                    B[i][j] = 1;
                } else {
                    B[i][j] = B[i-1][j-1] + B[i-1][j];
                }
            }
        }
        return B[n][k];
    }
}
