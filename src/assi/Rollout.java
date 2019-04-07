package assi;

import java.util.Arrays;

public class Rollout extends Algorithm{

    //Get Distribution of new arriving Patients
    private static double[] incomerDistribution = Probability.getCumulatedBinomProbs();

    //Expected number of unexpected Incomers per Day
    private static int[] expectedUnplannedIncomers = new int[1];

    //Array with Strategy: Boolean if Operation will be executed
    private static boolean[] isOperationCanceled = new boolean[timeHorizon];

    //Generate Randomness
    private static double[][] randForTime = Randomness.getRandomnessForTime();
    private static double[][] randArray = Randomness.getRandomness();

    //Fields for Bumps and Cancelled Operations
    private static int bumps;
    private static int cancelled;

    //Set Bumps back to Zero
    public static void setBumpsBack(){
        bumps = 0;
    }

    //Set cancelled Patients Back to zero
    public static void setCancelledBack(){
        cancelled = 0;
    }

    // Execute one Rolllout for the Strategy "j"
    public static int[] execute(int[] state, int[] plannedOperationsPerDay, int j){

        //Loop over Timehorizon (Rollout)
        for (int t = 0; t < timeHorizon; t++){

            //Define Actions
            if(state[capacity-1] >= j){
                isOperationCanceled[j] = true;
            } else{
                isOperationCanceled[j] = false;
            }

            //Impact of Actions
            if(isOperationCanceled[j] == true){
                if(t < timeHorizon - 2){
                    plannedOperationsPerDay[t+2] = plannedOperationsPerDay[t+2] -1;
                    cancelled++;
                } else{}
            } else{}

            //Reduce LOSes of the Patience in the ICU
            state = State.reduceLos(state, capacity);

            //Length Arrival Vector (unexpected Patients)
            int lengthArrivalVector = State.getLengthArrivalVector (expectedUnplannedIncomers, maxLos, randForTime, incomerDistribution, t);

            //Length Total Arrival Vector (unexpected and unexpected Patients)
            int totalArrivals = lengthArrivalVector + plannedOperationsPerDay[t];

            //Get the Arrival Vector, with LOSes of new Patients
            int[] arrivalVector = State.getArrivalVector(totalArrivals, maxLos, randArray, Algorithm.losDistribution, t);

            //TODO Proof if Nessasary
            Arrays.sort(arrivalVector);
            State.reverseArray(arrivalVector, totalArrivals);

            //Merges State (old Patients) and new Arrival Vector (new Patients)
            int[] totalCalls = State.getTotalCalls(state, arrivalVector, capacity, totalArrivals);

            //Sort Total Calls in order to the LOSes
            Arrays.sort(totalCalls);
            State.reverseArray(totalCalls, (capacity + totalArrivals));

            //Reduce Calls to the Capacity (Bumping) and return new State for the next Day
            state = State.reduceTotalToCapacity(state, totalCalls, capacity, totalArrivals);

            //Bump Counter
            bumps = State.getBumpCount(totalCalls, capacity, totalArrivals, bumps);
        }
        return new int[]{bumps, cancelled};
    }
}



