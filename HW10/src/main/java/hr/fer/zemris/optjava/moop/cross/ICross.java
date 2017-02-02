package hr.fer.zemris.optjava.moop.cross;

import java.util.List;

import hr.fer.zemris.optjava.moop.solution.MultiObjectiveSolution;

public interface ICross<T extends MultiObjectiveSolution> {

    public List<T> crossParents(final T p1, T p2);

}
