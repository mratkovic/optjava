package hr.fer.zemris.optjava.rng.provider;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.impl.RNGRandomImpl;

public class ThreadLocalRNGProvider implements IRNGProvider {

    private final ThreadLocal<IRNG> thread = new ThreadLocal<>();

    public IRNG getRNG() {
        if (thread.get() == null) {
            thread.set(new RNGRandomImpl());
        }
        return thread.get();
    }

}
