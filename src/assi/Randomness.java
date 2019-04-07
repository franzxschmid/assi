package assi;

    public class Randomness extends Algorithm {

        public static double[] getRandomnessInitial(){
            //Zufallszahlen für Initialzustand festlegen
            double[] randomnessInitial = new double[capacity];
            for(int i = 0; i < capacity; i++){
                randomnessInitial[i] = Math.random();
            }
            return randomnessInitial;
        }

        public static double[][] getRandomnessForTime(){
            double[][] randomnessForTime = new double[capacity][timeHorizon];
            for (int t = 0; t < timeHorizon; t++){
                for(int i = 0; i < 1; i++){
                    randomnessForTime[i][t] = Math.random();
                }
            }
            return randomnessForTime;
        }

        public static double[][] getRandomness(){
            double[][] randomness = new double[capacity][timeHorizon];
            for (int t = 0; t < timeHorizon; t++){
                for(int i = 0; i < maxIncomer + 2; i++){
                    randomness[i][t] = Math.random();
                }
            }
            return randomness;
        }



}
