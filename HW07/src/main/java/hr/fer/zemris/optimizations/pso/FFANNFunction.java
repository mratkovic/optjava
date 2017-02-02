package hr.fer.zemris.optimizations.pso;

import hr.fer.zemris.optimizations.IFunction;
import hr.fer.zemris.optimizations.ann.FFANN;

public class FFANNFunction implements IFunction {

    private final FFANN net;

    public FFANNFunction(final FFANN net) {
        this.net = net;
    }

    @Override
    public double valueAt(final double[] weights) {
        return net.getMSE(weights);
    }

    @Override
    public int nVariables() {
        return net.getWeightsCount();
    }

}
