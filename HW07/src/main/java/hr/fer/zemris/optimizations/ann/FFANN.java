package hr.fer.zemris.optimizations.ann;

import java.util.LinkedList;

import hr.fer.zemris.data.IDataset;

public class FFANN {

    private final int[] layers;
    private final ITransferFunction[] activations;
    private final IDataset dataset;
    private int numberOfWegihts;

    public FFANN(final int[] layers, final ITransferFunction[] activations, final IDataset dataset) {
        this.layers = layers;
        this.activations = activations;
        this.dataset = dataset;
        numberOfWegihts = 0;

        if (layers.length - 1 != activations.length) {
            throw new IllegalArgumentException(
                    "Invlaid number of activation functions, does not match number of hidden layers.");
        }
        for (int i = 1; i < layers.length; i++) {
            int perNeuron = layers[i - 1] + 1;
            numberOfWegihts += perNeuron * layers[i];
        }
    }

    public int getWeightsCount() {
        return numberOfWegihts;
    }

    public double[] calcOutputs(double[] inputs, final double[] weights) {
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

    public double getMSE(final double[] weights) {
        double err = 0;

        for (int i = 0; i < dataset.numberOfSamples(); i++) {
            double[] predicted = calcOutputs(dataset.getInput(i), weights);
            double[] correct = dataset.getOutput(i);

            for (int j = 0; j < predicted.length; j++) {
                err += Math.pow(correct[j] - predicted[j], 2);
            }
        }
        return err / dataset.numberOfSamples();
    }

    public void stats(final double[] weights) {
        LinkedList<Integer> wrongIndices = new LinkedList<>();
        for (int i = 0; i < dataset.numberOfSamples(); i++) {

            double[] predicted = calcOutputs(dataset.getInput(i), weights);
            double[] correct = dataset.getOutput(i);

            for (int j = 0; j < correct.length; j++) {
                int out = predicted[j] < 0.5 ? 0 : 1;
                if (out != correct[j]) {
                    wrongIndices.add(i);
                    break;
                }
            }
        }

        System.out.println("Incorrect classification for " + wrongIndices.size() + " samples");
        for (int i : wrongIndices) {
            double[] predicted = calcOutputs(dataset.getInput(i), weights);
            double[] correct = dataset.getOutput(i);
            System.out.printf("Sample: %3d: predicted %s, correct %s\n", i, solArray2Str(predicted),
                    solArray2Str(correct));
        }
    }

    private static String solArray2Str(final double[] arr) {
        StringBuilder sb = new StringBuilder();
        for (double x : arr) {
            sb.append(x < 0.5 ? 0 : 1);
            sb.append(" ");
        }
        return sb.toString();
    }
}
