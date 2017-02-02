package hr.fer.zemris.optimizations.ann;

import hr.fer.zemris.data.IDataset;

public class FFANN extends AbstractANN {

    public FFANN(final int[] layers, final ITransferFunction[] activations, final IDataset dataset) {
        super(layers, activations, dataset);
    }

    public FFANN(final String arh, final ITransferFunction[] activations, final IDataset dataset) {
        super(arh, activations, dataset);
    }

    public FFANN(final int[] layers, final ITransferFunction activation, final IDataset dataset) {
        super(layers, activation, dataset);
    }

    public FFANN(final String arh, final ITransferFunction activation, final IDataset dataset) {
        super(arh, activation, dataset);
    }

    @Override
    public double[] calcOutputs(double[] inputs) {
        double[] outputs = null;

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
        }
        return outputs;
    }
}
