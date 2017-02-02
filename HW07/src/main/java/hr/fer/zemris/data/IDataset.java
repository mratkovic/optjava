package hr.fer.zemris.data;

public interface IDataset {

    public int numberOfSamples();

    public double[] getInput(int index);

    public double[] getOutput(int index);

}
