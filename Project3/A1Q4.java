import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class A1Q4{
    public static class Data {
        public double Global_active_power;
        public double Global_reactive_power;
        public double Voltage;
        public double Global_intensity;
        public double Sub_metering_1;
        public double Sub_metering_2;
        public double Sub_metering_3;
        public int Y;

        public Data(double Global_active_power, double Global_reactive_power, double Voltage, double Global_intensity,
                    double Sub_metering_1, double Sub_metering_2, double Sub_metering_3) {
            this.Global_active_power = Global_active_power;
            this.Global_reactive_power = Global_reactive_power;
            this.Voltage = Voltage;
            this.Global_intensity = Global_intensity;
            this.Sub_metering_1 = Sub_metering_1;
            this.Sub_metering_2 = Sub_metering_2;
            this.Sub_metering_3 = Sub_metering_3;
        }
    }

    public static void main(String[] args) {
        ArrayList<Data> dataSet = readNodesFromCSV("/Users/linyang/IdeaProjects/project3/src/Project3_Power_Consumption.csv");
        int centroidInitializationTimes = 10;
        int movingCentroidTimes = 30;
        int centroidSum = 2;
        int flag1 = 0;
        int flag2 = 0;
        int length = dataSet.size();
        Data[] centroids = new Data[centroidSum];
        while(flag1 != centroidInitializationTimes){
            for(int i = 0; i < centroids.length; i++){
                int random = (int)(Math.random() * length);
                Data data = dataSet.get(random);
                centroids[i] = new Data(data.Global_active_power, data.Global_reactive_power, data.Voltage, data.Global_intensity
                , data.Sub_metering_1, data.Sub_metering_2, data.Sub_metering_3);
            }                                             //new and assign centroids

            while(flag2 != movingCentroidTimes){
                for (Data data : dataSet) {
                    double minDistance = Double.MAX_VALUE;
                    for (int j = 0; j < centroids.length; j++) {
                        double Distance = (data.Global_active_power - centroids[j].Global_active_power) * (data.Global_active_power - centroids[j].Global_active_power) +
                                (data.Global_reactive_power - centroids[j].Global_reactive_power) * (data.Global_reactive_power - centroids[j].Global_reactive_power)
                                + (data.Voltage - centroids[j].Voltage) * (data.Voltage - centroids[j].Voltage) +
                                (data.Global_intensity - centroids[j].Global_intensity) * (data.Global_intensity - centroids[j].Global_intensity)
                                + (data.Sub_metering_1 - centroids[j].Sub_metering_1) * (data.Sub_metering_1 - centroids[j].Sub_metering_1) +
                                (data.Sub_metering_2 - centroids[j].Sub_metering_2) * (data.Sub_metering_2 - centroids[j].Sub_metering_2)
                                + (data.Sub_metering_3 - centroids[j].Sub_metering_3) * (data.Sub_metering_3 - centroids[j].Sub_metering_3);
                        if (Distance < minDistance) {
                            data.Y = j;
                            minDistance = Distance;
                        }
                    }
                }

                double[] sumGlobalActivePower = new double[centroidSum];
                double[] sumGlobalReactivePower = new double[centroidSum];
                double[] sumVoltage = new double[centroidSum];
                double[] sumGlobalIntensity = new double[centroidSum];
                double[] sumSubMetering1 = new double[centroidSum];
                double[] sumSubMetering2 = new double[centroidSum];
                double[] sumSubMetering3 = new double[centroidSum];
                int[] count = new int[centroidSum];

                for (Data data : dataSet) {
                    sumGlobalActivePower[data.Y] += data.Global_active_power;
                    sumGlobalReactivePower[data.Y] += data.Global_reactive_power;
                    sumVoltage[data.Y] += data.Voltage;
                    sumGlobalIntensity[data.Y] += data.Global_intensity;
                    sumSubMetering1[data.Y] += data.Sub_metering_1;
                    sumSubMetering2[data.Y] += data.Sub_metering_2;
                    sumSubMetering3[data.Y] += data.Sub_metering_3;
                    count[data.Y]++;
                }

                for(int i = 0; i < centroids.length; i++){
                    centroids[i].Global_active_power = sumGlobalActivePower[i] / count[i];
                    centroids[i].Global_reactive_power = sumGlobalReactivePower[i] / count[i];
                    centroids[i].Voltage = sumVoltage[i] / count[i];
                    centroids[i].Global_intensity = sumGlobalIntensity[i] / count[i];
                    centroids[i].Sub_metering_1 = sumSubMetering1[i] / count[i];
                    centroids[i].Sub_metering_2 = sumSubMetering2[i] / count[i];
                    centroids[i].Sub_metering_3 = sumSubMetering3[i] / count[i];
                }

                System.out.println("Iteration " + flag1 + "_" + flag2 + ":");
                for(int i = 0; i < centroids.length; i++){
                    System.out.println("The coordinates of Centroid_" + i + " is (" + centroids[i].Global_active_power +
                             ", " + centroids[i].Global_reactive_power + "，" +  centroids[i].Voltage + ", " + centroids[i].Global_intensity
                    + ", " + centroids[i].Sub_metering_1 + ", " + centroids[i].Sub_metering_2 + ", " + centroids[i].Sub_metering_3 + ")");
                    System.out.println("The number of points assigned to centroid_" + i + " is " + count[i]);
                }

                double lossSum = 0;
                for(Data data: dataSet){
                    int j = data.Y;
                    lossSum += (data.Global_active_power - centroids[j].Global_active_power) * (data.Global_active_power - centroids[j].Global_active_power) +
                            (data.Global_reactive_power - centroids[j].Global_reactive_power) * (data.Global_reactive_power - centroids[j].Global_reactive_power)
                            + (data.Voltage - centroids[j].Voltage) * (data.Voltage - centroids[j].Voltage) +
                            (data.Global_intensity - centroids[j].Global_intensity) * (data.Global_intensity - centroids[j].Global_intensity)
                            + (data.Sub_metering_1 - centroids[j].Sub_metering_1) * (data.Sub_metering_1 - centroids[j].Sub_metering_1) +
                            (data.Sub_metering_2 - centroids[j].Sub_metering_2) * (data.Sub_metering_2 - centroids[j].Sub_metering_2)
                            + (data.Sub_metering_3 - centroids[j].Sub_metering_3) * (data.Sub_metering_3 - centroids[j].Sub_metering_3);
                }

                double loss = lossSum/length;
                System.out.println("The loss for the iteration of training is " + loss);
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
        double Global_active_power = Double.parseDouble(metadata[0]);
        double Global_reactive_power = Double.parseDouble(metadata[1]);
        double Voltage = Double.parseDouble(metadata[2]);
        double Global_intensity = Double.parseDouble(metadata[3]);
        double Sub_metering_1 = Double.parseDouble(metadata[4]);
        double Sub_metering_2 = Double.parseDouble(metadata[5]);
        double Sub_metering_3 = Double.parseDouble(metadata[6]);

        return new Data(Global_active_power, Global_reactive_power, Voltage, Global_intensity,
                Sub_metering_1, Sub_metering_2, Sub_metering_3);
    }
}