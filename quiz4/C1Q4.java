import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

public class C1Q4 {
    public static void main(String args[]) {
        try {
            String pathname = "/Users/maomu/IdeaProjects/Upitt/src/input4.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            char[] arr = line.toCharArray();
            int n = line.length();
            int[][] array = new int[n][n];
            int flag = 0;
            while (line != null) {
                arr = line.toCharArray();
                for(int i = 0; i < array.length; i++){
                    array[flag][i] = arr[i] - 48;
                }
                flag++;
                line = br.readLine();
            }

            boolean connected = true;
            boolean complete = true;
            boolean directed = false;
            int[] totalDegree = new int[n];
            int[] inDegree = new int[n];
            int[] outDegree = new int[n];
            int[][] dist = new int[n][n];
            for(int i = 0; i < array.length; i++){
                for(int j = 0; j < array[0].length; j++){
                    dist[i][j] = (array[i][j] == 0 && i != j) ? 100000 : array[i][j];
                    if(i != j && array[i][j] == 0){
                        complete = false;
                    }
                }
            }

            for(int i = 0; i < n; i++){
                for(int j = 0; j <= i; j++){
                    if(array[i][j] != array[j][i]){
                        directed = true;
                        break;
                    }
                }
            }

            if(complete){
                System.out.println("The graph is complete");
            } else {
                System.out.println("The graph is not complete");
            }

            int[] count = new int[n];

            for(int k = 0; k < n; k++){
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }

            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(dist[i][j] >= 100000){
                        connected = false;
                    }
                }
            }

            if(connected){
                System.out.println("The graph is connected");
            } else {
                System.out.println("The graph is not connected");
            }

            if(!directed){
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        inDegree[i] += array[j][i];
                    }
                }

                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        outDegree[i] += array[i][j];
                    }
                }
            } else {
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        inDegree[i] += array[j][i];
                        if(array[j][i] == 1 && array[i][j] == 1){
                            count[i]++;
                        }
                    }
                }

                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        outDegree[i] += array[i][j];
                    }
                }
            }

            if(directed){
                for(int i = 0; i < n; i++){
                    totalDegree[i] = inDegree[i] + outDegree[i];
                }
            } else {
                for(int i = 0; i < n; i++){
                    totalDegree[i] = inDegree[i];
                }
            }

            for(int i = 0; i < n; i++){
                System.out.println("Vertex " + (i + 1) + "'s total degree is " + (totalDegree[i] - count[i]));
                System.out.println("Vertex " + (i + 1) + "'s in-degree is " + inDegree[i]);
                System.out.println("Vertex " + (i + 1) + "'s out-degree is " + outDegree[i]);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

