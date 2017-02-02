package hr.fer.zemris.optjava.HW07;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.data.IDataset;
import hr.fer.zemris.data.IrisDataset;
import hr.fer.zemris.optimizations.ann.FFANN;
import hr.fer.zemris.optimizations.ann.FunctionFactory;
import hr.fer.zemris.optimizations.ann.ITransferFunction;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ANNTest extends TestCase {
    public ANNTest(final String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ANNTest.class);
    }

    public void test1() throws IOException {
        String path = "07-iris-formatirano.data";
        IDataset dataset = IrisDataset.load(path);
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID,
                FunctionFactory.SIGMOID };
        FFANN ffann = new FFANN(new int[] { 4, 5, 3, 3 }, activations, dataset);

        double[] weights = new double[ffann.getWeightsCount()];
        Arrays.fill(weights, 0.1);

        double mse = ffann.getMSE(weights);
        assertEquals(0.8365366587431725, mse);
    }

    public void test2() throws IOException {
        String path = "07-iris-formatirano.data";
        IDataset dataset = IrisDataset.load(path);
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID, };
        FFANN ffann = new FFANN(new int[] { 4, 3, 3 }, activations, dataset);

        double[] weights = new double[ffann.getWeightsCount()];
        Arrays.fill(weights, 0.1);

        double mse = ffann.getMSE(weights);
        assertEquals(0.8566740399081082, mse);
    }

    public void test3() throws IOException {
        String path = "07-iris-formatirano.data";
        IDataset dataset = IrisDataset.load(path);
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID, };
        FFANN ffann = new FFANN(new int[] { 4, 3, 3 }, activations, dataset);

        double[] weights = new double[ffann.getWeightsCount()];
        Arrays.fill(weights, -0.2);

        double mse = ffann.getMSE(weights);
        assertEquals(0.7019685477806382, mse);
    }
}
