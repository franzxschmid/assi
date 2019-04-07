package assi;

import java.util.Arrays;

public class State{

    //Reverse Sorting
    public static int[] reverseArray(int[] oldArray, int length) {
        int[] newArray = new int[length];
        int j = length;
        for (int i = 0; i < length; i++) {
            newArray[j - 1] = oldArray[i];
            j = j - 1;
        }
        return newArray;
    }

    //Get initial State of the ICU (all LOSes in ICU)
    public static int[] getInitialState(int capacity, int maxLos, double[] randInitial, double[] losDistribution){

        int[] initialState = new int[capacity];

        //Inversionmethod with LOS Distribution
        for(int i = 0; i < capacity; i++){
            for (int k = 0; k < maxLos; k++){
                if(randInitial[i] < losDistribution[k]){
                    initialState[i] = k;
                    break;
                }
                else{
                }
            }
        }
        //Sort Values Initialstate and invert Order (from Highest to Lowest)
        Arrays.sort(initialState);
        initialState = State.reverseArray(initialState, capacity);

        return initialState;
    }

    //Reduces all LOSes in a State by one, if LOS bigger than zero
    public static int[] reduceLos(int[] state, int capacity){
        for (int i = 0; i < capacity; i++) {
             if(state[i] > 0){
                 state[i] = state[i] - 1;
             }
        }
        return state;
    }
    //Get length of the Arrival Vectors (How many new Patient arrive)
    public static int getLengthArrivalVector (int[] expectedUnplannedIncomers, int maxLos, double[][] randForTime, double[] incomerDistribution, int time){
        for(int i = 0; i < 1; i++){
            for (int k = 0; k < maxLos; k++){
                if(randForTime[i][time] < incomerDistribution[k]){
                    expectedUnplannedIncomers[i] = k;
                    break;
                }
            }
        }
        return expectedUnplannedIncomers[0];
    }

    //Get Arrival Vector (How long are the LOSes of the new Patients)
    public static int[] getArrivalVector(int totalArrivals, int maxLos, double[][] randArray, double[] losDistribution, int time){
        //Initaliez Arrival Vector with the right Length
        int[] arrivalVector = new int[totalArrivals];

        //Inversionsmethod with the Distribution of LOSes
        for(int i = 0; i < totalArrivals; i++){
            for (int k = 0; k < maxLos; k++){
                if(randArray[i][time] < losDistribution[k]){
                    arrivalVector[i] = k;
                    break;
                }
            }
        }
        return arrivalVector;
    }

    //Merges State with Arrival Vector (get all Patients taht should stay in the ICU)
    public static int[] getTotalCalls(int[] state, int[] arrivalVector, int capacity, int totalArrivals){
        int[] totalCalls = new int[capacity+totalArrivals];
        for (int i = 0; i < (capacity + totalArrivals); i++){
            if(i < capacity){
                totalCalls[i] = state[i];
            } else{
                totalCalls[i] = arrivalVector[i - capacity];
            }
        }
        return totalCalls;
    }

    //Recuce the Number of total Calls to the Capayity
    public static int[] reduceTotalToCapacity(int[] state, int[] totalCalls, int capacity, int totalArrivals){
        for (int i = 0; i < (capacity + totalArrivals); i++) {
            if (i < capacity) {
                state[i] = totalCalls[i];
            }
        }
        return state;
    }

    //Count bumped Patients
    public static int getBumpCount(int[] totalCalls, int capacity, int totalArrivals, int bumps){

        for (int i = 0; i < (capacity + totalArrivals); i++) {
            if((i >= capacity) && (totalCalls[i] != 0)){
                bumps++;
            }
        }
        return bumps;
    }
}
