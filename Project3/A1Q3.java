import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class A1Q3{
    public static class Data {
        public double X1;
        public double X2;
        public int Y;

        public Data(double X1, double X2, int Y) {
            this.X1 = X1;
            this.X2 = X2;
            this.Y = Y;
        }

        public Data(double X1, double X2){
            this.X1 = X1;
            this.X2 = X2;
        }
    }

    public static void main(String[] args) {
        ArrayList<Data> dataSet = readNodesFromCSV("/Users/linyang/IdeaProjects/project3/src/Project3_Test_Case.csv");
        int centroidInitializationTimes = 10;
        int movingCentroidTimes = 30;
        int centroidSum = 2;
        int flag1 = 0;
        int flag2 = 0;
        int length = dataSet.size();
        Data[] centroids = new Data[centroidSum];
        Data[] minCentroids = new Data[centroidSum];
        double minLoss = Double.MAX_VALUE;
        while(flag1 != centroidInitializationTimes){
            for(int i = 0; i < centroids.length; i++){
                int random = (int)(Math.random() * length);
                Data data = dataSet.get(random);
                centroids[i] = new Data(data.X1, data.X2);
            }                                             //new and assign centroids

            while(flag2 != movingCentroidTimes){
                for (Data data : dataSet) {
                    double minDistance = Double.MAX_VALUE;
                    for (int j = 0; j < centroids.length; j++) {
                        double Distance = (data.X1 - centroids[j].X1) * (data.X1 - centroids[j].X1) +
                                (data.X2 - centroids[j].X2) * (data.X2 - centroids[j].X2);
                        if (Distance < minDistance) {
                            data.Y = j;
                            minDistance = Distance;
                        }
                    }
                }

                double[] sumX1 = new double[centroidSum];
                double[] sumX2 = new double[centroidSum];
                int[] count = new int[centroidSum];

                for (Data data : dataSet) {
                    sumX1[data.Y] += data.X1;
                    sumX2[data.Y] += data.X2;
                    count[data.Y]++;
                }

                for(int i = 0; i < centroids.length; i++){
                    centroids[i].X1 = sumX1[i] / count[i];
                    centroids[i].X2 = sumX2[i] / count[i];
                }

                System.out.println("Iteration " + flag2 + ":");
                for(int i = 0; i < centroids.length; i++){
                    System.out.println("The coordinates of Centroid_" + i + " is (" + centroids[i].X1 +
                             "," + centroids[i].X2 + ")");
                    System.out.println("The number of points assigned to centroid_" + i + " is " + count[i]);
                }

                double lossSum = 0;
                for(Data data: dataSet){
                    lossSum += (data.X1 - centroids[data.Y].X1) * (data.X1 - centroids[data.Y].X1) +
                            (data.X2 - centroids[data.Y].X2) * (data.X2 - centroids[data.Y].X2);
                }

                double loss = lossSum/length;
                System.out.println("The loss for the iteration of training is " + loss);
                System.out.println();

                if(flag2 == movingCentroidTimes - 1){
                    if(loss < minLoss){
                        for(int i = 0; i < centroidSum; i++){
                            minCentroids[i] = centroids[i];
                        }
                        minLoss = loss;
                    }
                }
                flag2++;
            }
            flag2=0;
            flag1++;
        }
    }

    public static ArrayList<Data> readNodesFromCSV(String fileName) {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();
            line = br.readLine();

            // loop until all lines are read
            while (line != null) {
                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Data data = createData(attributes);

                // adding book into ArrayList
                dataArrayList.add(data);

                // read next line before looping
                //if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return dataArrayList;
    }

    public static Data createData(String[] metadata) {
        double X1 = Double.parseDouble(metadata[0]);
        double X2 = Double.parseDouble(metadata[1]);
        int Y = Integer.parseInt(metadata[2]);

        return new Data(X1, X2, Y);
    }
}