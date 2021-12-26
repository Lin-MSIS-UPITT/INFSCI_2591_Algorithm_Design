public class quiz12 {
    public static void main(String[] args) {
        int[] array = {3,4,324,34,24,234,5,42,553,74,463,6,8,357,37,64,753,6};
        for(int i = 0; i < array.length; i++){
            for(int j = 1; j < array.length; j++){
                if(array[j - 1] > array[j]){
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        System.out.println("The sorted array is");
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
    }
}
