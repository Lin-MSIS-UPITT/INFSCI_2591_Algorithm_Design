public class C2Q1 {
    public static void main(String[] args){
        int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};  //8
        int[] array2 = {2, 4, 9, 14, 14, 15, 18, 21, 27, 31,
                35, 42, 45, 50, 54, 59, 64, 69, 82, 84, 84};  //45
        int[] array3 = {3, 15, 21, 24, 83, 87, 88, 93, 178,
                319, 541, 669, 770, 793, 848, 970, 1439, 1546,
                1853, 2124, 2234, 2459, 2518, 2978, 3111, 3406, 3490,
                3669, 3796, 3936, 4112, 4776, 5277, 5667, 6067, 6555,
                7890, 8056, 8234, 9968};  //2356
        int result1 = divide_and_conquer(array1, 8,0,array1.length - 1);
        int result2 = divide_and_conquer(array2, 45, 0, array2.length - 1);
        int result3 = divide_and_conquer(array3, 2356, 0, array3.length - 1);
        if(result1 == -1){
            System.out.println("8 is not an item in Test Case 1");
        } else {
            System.out.println("8 is the " + result1 + "th item in Test Case 1");
        }
        if(result2 == -1){
            System.out.println("45 is not an item in Test Case 2");
        } else {
            System.out.println("45 is the " + result2 + "th item in Test Case 2");
        }
        if(result3 == -1){
            System.out.println("2356 is not an item in Test Case 3");
        } else {
            System.out.println("2356 is the " + result3 + "th item in Test Case 3");
        }
    }

    public static int divide_and_conquer(int[] array, int key, int left, int right){
        int mid;
        while(left <= right){
            mid = (left + right) / 2;
            if(array[mid] == key){
                return mid + 1;
            } else if(array[mid] < key){
                return divide_and_conquer(array, key, mid + 1, right);
            } else {
                return divide_and_conquer(array, key, left, mid - 1);
            }
        }
        return -1;
    }
}
