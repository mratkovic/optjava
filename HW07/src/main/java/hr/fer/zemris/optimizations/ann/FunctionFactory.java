package hr.fer.zemris.optimizations.ann;

public class FunctionFactory {

    public static final ITransferFunction IDENTITY = x -> {
        return x;
    };

    public static final ITransferFunction NEGATION = x -> {
        return -x;
    };

    public static final ITransferFunction SIGMOID = x -> {
        return 1.0 / (1.0 + Math.exp(-x));
    };

    public static final ITransferFunction SIGMOID2 = x -> {
        return 1.0 / (1.0 + exp(-x));
    };

    /**
     * Aproksimacija e^val
     * http://martin.ankerl.com/2007/02/11/optimized-exponential-functions-for-java/
     */
    private static double exp(final double val) {
        long tmp = (long) (1512775 * val + (1072693248 - 60801));
        return Double.longBitsToDouble(tmp << 32);
    }
}
