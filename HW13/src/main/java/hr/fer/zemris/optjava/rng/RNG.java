package hr.fer.zemris.optjava.rng;

import java.io.InputStream;
import java.util.Properties;

import hr.fer.zemris.optjava.rng.provider.ThreadLocalRNGProvider;

public class RNG {
    private static IRNGProvider rngProvider;

    static {
        Properties properties = new Properties();
        InputStream is = RNG.class.getClassLoader().getResourceAsStream("rng-config.properties");
        try {
            properties.load(is);
            String className = properties.getProperty("rng-provider");
            rngProvider = (IRNGProvider) RNG.class.getClassLoader().loadClass(className).newInstance();

        } catch (Exception e) {
            rngProvider = new ThreadLocalRNGProvider();
        }
    }

    public static IRNG getRNG() {
        return rngProvider.getRNG();
    }
}