public class C1Q31 {
    public static void main(String[] args){
        int n = 17;
        int k = 10;
        long startTime = System.nanoTime();
        System.out.println(bin(n , k));
        long endTime = System.nanoTime();
        System.out.println("Algorithm 3.1 runs " + (endTime - startTime)
                + " nanoseconds when n = " + n + " and k = " + k);
    }

    public static int bin(int n, int k){
        if(k == 0 || n == k)
            return 1;
        else
            return bin(n - 1, k - 1) + bin(n - 1, k);
    }
}