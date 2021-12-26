public class quiz13 {
    public static void main(String[] args) {
        int testCase1 = 1;
        int testCase2 = 2;
        int testCase3 = 3;
        int testCase4 = 4;
        int testCase5 = 5;
        int[] testArray1 = new int[testCase1];
        int[] testArray2 = new int[testCase2];
        int[] testArray3 = new int[testCase3];
        int[] testArray4 = new int[testCase4];
        int[] testArray5 = new int[testCase5];
        System.out.println("Different ways the robot can walk " + testCase1 + " meters are");
        queens(-1, testCase1, testArray1);
        System.out.println();
        System.out.println("Different ways the robot can walk " + testCase2 + " meters are");
        queens(-1, testCase2, testArray2);
        System.out.println();
        System.out.println("Different ways the robot can walk " + testCase3 + " meters are");
        queens(-1, testCase3, testArray3);
        System.out.println();
        System.out.println("Different ways the robot can walk " + testCase4 + " meters are");
        queens(-1, testCase4, testArray4);
        System.out.println();
        System.out.println("Different ways the robot can walk " + testCase5 + " meters are");
        queens(-1, testCase5, testArray5);
    }

    public static void queens(int i, int n, int[] testArray) {
        if(promising(i, testArray)){
            if(i == n - 1){
                int sum = 0;
                for(int j = 0; j < testArray.length; j++){
                    sum += testArray[j];
                }
                if(sum == n){
                    for(int j = 0; j < testArray.length; j++){
                        if(testArray[j] != 0){
                            System.out.print(testArray[j] + " ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for(int j = 0; j <= 3; j++){
                    testArray[i + 1] = j;
                    queens(i + 1, n, testArray);
                }
            }
        }
    }

    public static boolean promising(int i, int[] testArray) {
        int k = 0;
        boolean flag = true;
        while(k <= i && flag){
            if(k > 0 && testArray[k - 1] > testArray[k]){
                flag = false;
            }
            k++;
        }
        return flag;
    }
}