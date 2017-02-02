package hr.fer.zemris.optjava.HW08;

import java.io.IOException;

import hr.fer.zemris.data.IDataset;
import hr.fer.zemris.data.TimeSequenceDataset;
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
    IDataset dataset;

    public ANNTest(final String testName) throws IOException {
        super(testName);
        String path = "08-Laser-generated-data.txt";
        dataset = TimeSequenceDataset.load(path, 5, -1);
    }

    public static Test suite() {
        return new TestSuite(ANNTest.class);
    }

    public void test1() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID, };
        FFANN ffann = new FFANN(new int[] { 8, 10, 1 }, activations, dataset);
        assertEquals(101, ffann.getWeightsCount());
    }

    public void test2() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID,
                FunctionFactory.SIGMOID, };
        FFANN ffann = new FFANN(new int[] { 8, 5, 4, 1 }, activations, dataset);
        assertEquals(74, ffann.getWeightsCount());
    }

    public void test3() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID, };
        FFANN ffann = new FFANN(new int[] { 4, 12, 1 }, activations, dataset);
        assertEquals(73, ffann.getWeightsCount());
    }

    public void test4() {
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID, FunctionFactory.SIGMOID,
                FunctionFactory.SIGMOID, };
        FFANN ffann = new FFANN(new int[] { 4, 5, 4, 1 }, activations, dataset);
        assertEquals(54, ffann.getWeightsCount());
    }

}
