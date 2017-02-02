package hr.fer.zemris.optjava.HW08;

import java.io.IOException;

import hr.fer.zemris.data.IDataset;
import hr.fer.zemris.data.TimeSequenceDataset;
import hr.fer.zemris.optimizations.ann.ElmanNN;
import hr.fer.zemris.optimizations.ann.FunctionFactory;
import hr.fer.zemris.optimizations.ann.ITransferFunction;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ElmanNNTest extends TestCase {
    IDataset dataset;

    public ElmanNNTest(final String testName) throws IOException {
        super(testName);
        String path = "08-Laser-generated-data.txt";
        dataset = TimeSequenceDataset.load(path, 1, -1);
    }

    public static Test suite() {
        return new TestSuite(ElmanNNTest.class);
    }

    public void test1() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID, };
        ElmanNN ffann = new ElmanNN(new int[] { 1, 10, 1 }, activations, dataset);
        assertEquals(131, ffann.getOnlyWeights());
        assertEquals(141, ffann.getWeightsCount());
    }

    public void test2() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID,
                FunctionFactory.SIGMOID, };
        ElmanNN ffann = new ElmanNN(new int[] { 1, 5, 4, 1 }, activations, dataset);
        assertEquals(64, ffann.getOnlyWeights());
        assertEquals(69, ffann.getWeightsCount());
    }

    public void test3() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID, };
        ElmanNN ffann = new ElmanNN(new int[] { 1, 12, 1 }, activations, dataset);
        assertEquals(181, ffann.getOnlyWeights());
        assertEquals(193, ffann.getWeightsCount());
    }

    public void test4() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID,
                FunctionFactory.SIGMOID, FunctionFactory.SIGMOID };
        ElmanNN ffann = new ElmanNN(new int[] { 1, 4, 3, 5, 1 }, activations, dataset);
        assertEquals(65, ffann.getOnlyWeights());
        assertEquals(69, ffann.getWeightsCount());
    }

}
