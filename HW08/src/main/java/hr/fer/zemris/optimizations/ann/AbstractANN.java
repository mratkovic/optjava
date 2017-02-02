package hr.fer.zemris.optimizations.ann;

import java.util.Arrays;
import java.util.LinkedList;

import hr.fer.zemris.data.IDataset;

public abstract class AbstractANN implements IANN {

    final int[] layers;
    ITransferFunction[] activations;
    final IDataset dataset;
    int numberOfWeights;
    double[] weights;

    public AbstractANN(final int[] layers, final ITransferFunction[] activations, final IDataset dataset) {
        this.dataset = dataset;
        this.layers = layers;
        this.activations = activations;
        numberOfWeights = 0;

        if (layers.length - 1 != activations.length) {
            this.activations = new ITransferFunction[layers.length - 1];
            Arrays.fill(this.activations, activations[0]);
        }
        for (int i = 1; i < layers.length; i++) {
            int perNeuron = layers[i - 1] + 1;
            numberOfWeights += perNeuron * layers[i];
        }
    }

    public AbstractANN(final String arh, final ITransferFunction[] activations, final IDataset dataset) {
        this(Arrays.stream(arh.split("[ ]*x[ ]*")).mapToInt(x -> Integer.parseInt(x)).toArray(), activations, dataset);
    }

    public AbstractANN(final int[] layers, final ITransferFunction activation, final IDataset dataset) {
        this(layers, new ITransferFunction[] { activation }, dataset);
    }

    public AbstractANN(final String arh, final ITransferFunction activation, final IDataset dataset) {
        this(arh, new ITransferFunction[] { activation }, dataset);
    }

    @Override
    public int getWeightsCount() {
        return numberOfWeights;
    }

    @Override
    public abstract double[] calcOutputs(double[] inputs);

    @Override
    public double getMSE() {
        double err = 0;

        for (int i = 0; i < dataset.numberOfSamples(); i++) {
            double[] predicted = calcOutputs(dataset.getInput(i));
            double[] correct = dataset.getOutput(i);

            for (int j = 0; j < predicted.length; j++) {
                err += Math.pow(correct[j] - predicted[j], 2);
            }
        }
        return err / dataset.numberOfSamples();
    }

    @Override
    public void setWeights(final double[] weights) {
        this.weights = weights;
    }

    public void stats(final double[] weights) {
        LinkedList<Integer> wrongIndices = new LinkedList<>();
        for (int i = 0; i < dataset.numberOfSamples(); i++) {

            double[] predicted = calcOutputs(dataset.getInput(i));
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
            double[] predicted = calcOutputs(dataset.getInput(i));
            double[] correct = dataset.getOutput(i);
            System.out.printf("Sample: %3d: predicted %s, correct %s\n", i, solArray2Str(predicted),
                    solArray2Str(correct));
        }
    }

    private static String solArray2Str(final double[] arr) {
        StringBuilder sb = new StringBuilder();
        for (double x : arr) {
            sb.append(" ");
        }
        return sb.toString();
    }

}
