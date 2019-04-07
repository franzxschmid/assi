package assi;

public class Probability extends Algorithm {

    //Parameter Geometric Distribution
    private static double p = 0.360;

    //Parameter Binomial Distribution
    private static double r = 40;

    //Gets an Arrays with normalized Cumulated Geometric Distributed Numbers
    public static double[] getCumulatedGeomProbs() {

        //Unnormalized Numbers
        double[] nnGeomProbs = new double[maxLos + 1];
        double[] nnCumulatedGeomProbs = new double[maxLos + 1];

        for(int k = 1; k < (maxLos + 1); k++) {
            nnGeomProbs[k] = p * Math.pow((1 - p),(k - 1));
            nnCumulatedGeomProbs[k] = nnCumulatedGeomProbs[k-1] + nnGeomProbs[k];
        }

        //Normalisisation
        double[] geomProbs = new double[maxLos + 1];
        for(int k = 1; k < (maxLos + 1); k++) {
            geomProbs[k] = nnGeomProbs[k] / nnCumulatedGeomProbs[maxLos];
        }

        //Normalized Numbers
        double[] cumulatedGeomProbs = new double[maxLos + 1];
        for (int k = 0; k < (maxLos + 1); k++) {
            if(k == 0){
                cumulatedGeomProbs[k] = (geomProbs[k]);
            }
            else {
                cumulatedGeomProbs[k] = cumulatedGeomProbs[k - 1] + geomProbs[k];
            }
        }
        return cumulatedGeomProbs;
    }

    //Gets an Arrays with normalized Cumulated Binomial Distributed Numbers
    public static double[] getCumulatedBinomProbs(){

        //Unnormalized Numbers
        double[] nnBinomProbs = new double[maxIncomer + 1];
        double[] nnCumulatedBinomProbs = new double[maxIncomer + 1];
        for(int k = 0; k < maxIncomer + 1; k++) {
            nnBinomProbs[k]  = fakult( k + r - 1)/(fakult(k) * fakult(r - 1))*Math.pow((r /(r +4 )), r)*Math.pow((1 - (r / ( r + 4))), (k));
            if(k == 0){
                nnCumulatedBinomProbs[k] = (nnBinomProbs[k]);
            }
            else{
                nnCumulatedBinomProbs[k] = nnCumulatedBinomProbs[k - 1] + nnBinomProbs[k];
            }

        }
        //Normalization
        double[] binomProbs = new double[maxIncomer + 1];
        for(int k = 0; k < maxIncomer + 1; k++) {
            binomProbs[k] = nnBinomProbs[k] / nnCumulatedBinomProbs[maxIncomer];
        }

        //Normalized Numbers
        double[] cumulatedBinomProbs = new double[maxIncomer + 1];
        for (int k = 0; k < maxIncomer + 1; k++) {
            if(k == 0){
                cumulatedBinomProbs[k] = binomProbs[k];
            }
            else{
                cumulatedBinomProbs[k] = cumulatedBinomProbs[k - 1] + binomProbs[k];
            }
        }
        return cumulatedBinomProbs;
    }

    //Calculates Factorial
    private static double fakult(double n){
        if (n <= 1)
            return 1;
        else
            return n * fakult(n-1);
    }

}




