package hr.fer.zemris.optimizations;

import hr.fer.zemris.optimizations.ann.IANN;

public class ANNFunction implements IFunction {

    private final IANN net;

    public ANNFunction(final IANN net) {
        this.net = net;
    }

    @Override
    public double valueAt(final double[] weights) {
        net.setWeights(weights);
        return net.getMSE();
    }

    @Override
    public int nVariables() {
        return net.getWeightsCount();
    }

}
