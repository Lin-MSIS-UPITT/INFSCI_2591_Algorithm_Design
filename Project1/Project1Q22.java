public class Project1Q22 {
    public static void main(String[] args){
        String a1 = "7000";
        String a2 = "7294";
        String b1 = "25";
        String b2 = "5038385";
        String c1 = "-59724";
        String c2 = "783";
        String d1 = "8516";
        String d2 = "-82147953548159344";
        String e1 = "45952456856498465985";
        String e2 = "98654651986546519856";
        String f1 = "-45952456856498465985";
        String f2 = "-98654651986546519856";
        System.out.print(a1 + " * " + a2 + " = ");
        Rectangle_Multiplication(a1, a2);
        System.out.print(b1 + " * " + b2 + " = ");
        Rectangle_Multiplication(b1, b2);
        System.out.print(c1 + " * " + c2 + " = ");
        Rectangle_Multiplication(c1, c2);
        System.out.print(d1 + " * " + d2 + " = ");
        Rectangle_Multiplication(d1, d2);
        System.out.print(e1 + " * " + e2 + " = ");
        Rectangle_Multiplication(e1, e2);
        System.out.print(f1 + " * " + f2 + " = ");
        Rectangle_Multiplication(f1, f2);
    }

    public static void Rectangle_Multiplication(String a, String b) {
        char[] array1 = a.toCharArray();
        char[] array2 = b.toCharArray();
        boolean flag1 = array1[0] == '-';
        boolean flag2 = array2[0] == '-';
        int[] arr1 = new int[array1.length];
        int[] arr2 = new int[array2.length];
        int[] arr3 = new int[array1.length + array2.length - 1];
        if (flag1) {
            for (int i = 1; i < arr1.length; i++) {
                arr1[i] = array1[i] - 48;
            }
        } else {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = array1[i] - 48;
            }
        }
        if (flag2) {
            for (int i = 1; i < arr2.length; i++) {
                arr2[i] = array2[i] - 48;
            }
        } else {
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = array2[i] - 48;
            }
        }

        for (int i = arr1.length - 1; i >= 0; i--) {
            for (int j = arr2.length - 1; j >= 0; j--) {
                arr3[i + j] += arr1[i] * arr2[j];
            }
        }

        for (int i = arr3.length - 1; i >= 1; i--) {
            arr3[i - 1] += arr3[i] / 10;
            arr3[i] = arr3[i] % 10;
        }

        int index = 0;
        while (arr3[index] == 0) {
            index++;
        }

        if ((!flag1 && flag2) || (flag1 && !flag2)) {
            System.out.print("-");
        }
        for(int i = index; i < arr3.length; i++){
            System.out.print(arr3[i]);
        }
        System.out.println();
    }
}