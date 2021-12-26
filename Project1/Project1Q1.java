public class Project1Q1 {
    public static void main(String[] args){
        int[] array1 = {3, 7, 9};
        int[] array2 = {2, 7, 9, 1};
        int[] array3 = {1, 7, 10, 15, 3, 8, 12, 18};
        int[] array4 = {1, 3, 5, 5, 15, 18, 21, 5, 5, 6, 8, 10, 12, 16, 17, 17, 20, 25, 28};
        int m1 = 0, n1 = array1.length, m2 = 0, n2 = array2.length, m3 = 0, n3 = array3.length, m4 = 0, n4 = array4.length;
        for(int i = 1; i < array1.length; i++){
            if(array1[i] - array1[i - 1] < 0){
                m1 = i;
                n1 = array1.length - i;
            }
        }
        for(int i = 1; i < array2.length; i++){
            if(array2[i] - array2[i - 1] < 0){
                m2 = i;
                n2 = array2.length - i;
            }
        }
        for(int i = 1; i < array3.length; i++){
            if(array3[i] - array3[i - 1] < 0){
                m3 = i;
                n3 = array3.length - i;
            }
        }
        for(int i = 1; i < array4.length; i++){
            if(array4[i] - array4[i - 1] < 0){
                m4 = i;
                n4 = array4.length - i;
            }
        }
        for(int i = 0; i < array1.length; i++){
            System.out.print(mergeArray(array1, m1, n1)[i] + " ");
        }
        System.out.println();
        for(int i = 0; i < array2.length; i++){
            System.out.print(mergeArray(array2, m2, n2)[i] + " ");
        }
        System.out.println();
        for(int i = 0; i < array3.length; i++){
            System.out.print(mergeArray(array3, m3, n3)[i] + " ");
        }
        System.out.println();
        for(int i = 0; i < array4.length; i++){
            System.out.print(mergeArray(array4, m4, n4)[i] + " ");
        }
    }


    public static int[] mergeArray(int[] array, int m, int n){
        if(m == 0){
            return array;
        } else if(m >= n){
            int[] auxArray = new int[n];
            for(int i = 0; i < n; i++){
                auxArray[i] = array[i + m];
            }
            for(int i = m + n - 1; i >= n; i--){
                array[i] = array[i - n];
            }
            int i = 0, j = 0, k = 0;
            while(i < m && j < n){
                if(array[n + i] <= auxArray[j]){
                    array[k] = array[n + i];
                    i++;
                } else {
                    array[k] = auxArray[j];
                    j++;
                }
                k++;
            }
            if(i == m){
                for(int c = j; c < n; c++){
                    array[c + m] = auxArray[c];
                }
            }
        } else {
            int[] auxArray = new int[m];
            for(int i = 0; i < m; i++){
                auxArray[i] = array[i];
            }
            int i = 0, j = 0, k = 0;
            while(i < m && j < n){
                if(array[j + m] <= auxArray[i]){
                    array[k] = array[j + m];
                    j++;
                } else {
                    array[k] = auxArray[i];
                    i++;
                }
                k++;
            }
            if(j == n) {
                for(int c = i; c < m; c++){
                    array[c + n] = auxArray[c];
                }
            }
        }
        return array;
    }
}