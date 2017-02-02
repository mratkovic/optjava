package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.IEvaluatorProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class EVOThread extends Thread implements IRNGProvider, IEvaluatorProvider {
    public Evaluator evaluator;
    public final IRNG rng = new RNGRandomImpl();

    public EVOThread() {
    }

    public EVOThread(final Runnable target) {
        super(target);
    }

    public EVOThread(final Runnable target, final GrayScaleImage template) {
        super(target);
        evaluator = new Evaluator(template);
    }

    public EVOThread(final Runnable target, final String name) {
        super(target, name);
    }

    @Override
    public IRNG getRNG() {
        return rng;
    }

    @Override
    public Evaluator getEvaluator() {
        return this.evaluator;
    }
}