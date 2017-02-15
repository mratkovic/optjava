package hr.fer.zemris.optjava.dz13.ant.ga.solution;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public interface AntAction {
    void execute(AntMap map, Ant ant);
}
