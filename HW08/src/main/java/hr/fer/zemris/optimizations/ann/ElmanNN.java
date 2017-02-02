package hr.fer.zemris.optimizations.ann;

import java.util.Arrays;

import hr.fer.zemris.data.IDataset;

public class ElmanNN extends AbstractANN {
    double[] context;
    int onlyWeights;

    public ElmanNN(final int[] layers, final ITransferFunction[] activations, final IDataset dataset) {
        super(layers, activations, dataset);
        initParams();
    }

    private void initParams() {
        layers[0] += layers[1];
        // adjust param count
        numberOfWeights += layers[1] * layers[1];
        this.onlyWeights = numberOfWeights;
        numberOfWeights += layers[1];

    }

    public ElmanNN(final String arh, final ITransferFunction[] activations, final IDataset dataset) {
        super(arh, activations, dataset);
        initParams();
    }

    public ElmanNN(final int[] layers, final ITransferFunction activation, final IDataset dataset) {
        super(layers, activation, dataset);
        initParams();
    }

    public ElmanNN(final String arh, final ITransferFunction activation, final IDataset dataset) {
        super(arh, activation, dataset);
        initParams();
    }

    public int getOnlyWeights() {
        return onlyWeights;
    }

    @Override
    public void setWeights(final double[] weights) {
        this.weights = weights;
        int n = weights.length;
        int size = layers[1];
        this.context = Arrays.copyOfRange(weights, n - size, n);
    }

    private double[] inputVector(final double[] netIn) {
        double[] inputs = new double[netIn.length + layers[1]];

        for (int i = 0; i < netIn.length; ++i) {
            inputs[i] = netIn[i];
        }
        for (int i = 0; i < context.length; ++i) {
            inputs[i + netIn.length] = context[i];
        }
        return inputs;
    }

    @Override
    public double[] calcOutputs(final double[] ins) {
        double[] outputs = null;
        double[] inputs = inputVector(ins);

        int index = 0;
        for (int i = 1; i < layers.length; i++) {
            outputs = new double[layers[i]];

            for (int k = 0; k < layers[i]; k++) {
                double net = 0;

                net += weights[index++]; // w0
                for (int j = 0; j < layers[i - 1]; j++) {
                    net += weights[index++] * inputs[j];
                }
                outputs[k] = activations[i - 1].valueAt(net);
            }
            inputs = outputs;

            if (i == 1) { // store context
                context = Arrays.copyOf(outputs, outputs.length);
            }
        }
        return outputs;
    }
}