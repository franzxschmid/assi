package assi;

public class Algorithm{

    //Maximal Length of Stay
    public static int maxLos = 20;

    //Maximal Numers of Incomers
    public static int maxIncomer = 12;

    //Capacity of the ICU
    public static int capacity = 19;

    //Timehorizon for the Length of the Rollout
    public static int timeHorizon = 365000;

    // Get Distributions for Incomer and Length of Stays
    public static double[] losDistribution = Probability.getCumulatedGeomProbs();

    // Initalize the Field for Total Costs
    private static int totalCosts;

    //Main Method
    public static void main(String[] args){

        //Expected new Patientce per Day
        int expectedPatiencePerDay = 2;

        //Planned Operations
        int[] plannedOperationsPerDay = new int[timeHorizon + 1];

        //Costs for Cancelling an Operation
        int costCancel = 50;

        //Costs for Bumping a Patient
        int costBump = 100;

        //Array to collect the Costs
        int [] totalCostsArray = new int[maxLos + 2];

        //Array to collect the Cancelled
        double[] cancelledPerYearArray = new double[maxLos + 2];

        //Generate Randomness
        double[] randInitial = Randomness.getRandomnessInitial();

        //Set initialState outside the Loop, so that every Strategie has the same Starting Conditions
        final int[] initialState = State.getInitialState(capacity,  maxLos, randInitial, losDistribution);

        //Loop over Strategies
        for	(int j = 0; j < maxLos + 2; j++){

            //Set Operations per day
            for (int t = 0; t < timeHorizon; t++){
                plannedOperationsPerDay[t] = expectedPatiencePerDay;
            }
            //Execute Rollout and Get Numer of Bumps and Cancelled
            int[] resultRollout = Rollout.execute(initialState, plannedOperationsPerDay, j);

            //Cost Function (Minimalization Problem) (Calculates per Year)
            int bumps = Math.round(resultRollout[0] / (timeHorizon / 365));
            int cancelled = Math.round(resultRollout[1] / (timeHorizon / 365));
            totalCosts = (costCancel * cancelled) + (costBump * bumps);

            //Saves Results in Array
            totalCostsArray[j] = totalCosts;
            cancelledPerYearArray[j] = cancelled;

            //Ausgabe: Anzahl gebumter Patienten und Verschobener Operationen
            System.out.println("Strategy " + j);
            System.out.println(" Number of Bumbs         " + bumps);
            System.out.println(" Number of Cancellations " + cancelled);
            System.out.println(" Costs per Year          " + totalCosts);
            System.out.println("***************************************");

            //Determination Criteria
            if((j > 0) && ((totalCostsArray[j] >= totalCostsArray[j-1]) || (cancelledPerYearArray[j] == cancelledPerYearArray[j-1]))){
                System.out.println("Strategy " + (j-1) + " is the best Strategy on Stage 1");
                break;
            }
            //Set Counters back to Zero
            Rollout.setBumpsBack();
            Rollout.setCancelledBack();
        }
    }
}
