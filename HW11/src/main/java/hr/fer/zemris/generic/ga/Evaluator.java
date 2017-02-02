package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.optjava.ga.solution.GASolution;

public class Evaluator implements IGAEvaluator<int[]> {

    private final GrayScaleImage template;
    private GrayScaleImage im;

    public Evaluator(final GrayScaleImage template) {
        super();
        this.template = template;
    }

    public GrayScaleImage draw(final GASolution<int[]> p, GrayScaleImage im) {
        if (im == null) {
            im = new GrayScaleImage(template.width, template.height);
        }
        int[] pdata = p.data;
        byte bgcol = (byte) pdata[0];
        im.clear(bgcol);
        int n = (pdata.length - 1) / 5;
        int index = 1;
        for (int i = 0; i < n; i++) {
            im.rectangle(pdata[index], pdata[index + 1], pdata[index + 2], pdata[index + 3], (byte) pdata[index + 4]);
            index += 5;
        }
        return im;
    }

    public static GrayScaleImage draw(final GASolution<int[]> p, final int w, final int h) {
        GrayScaleImage im = new GrayScaleImage(w, h);
        int[] pdata = p.data;
        byte bgcol = (byte) pdata[0];
        im.clear(bgcol);
        int n = (pdata.length - 1) / 5;
        int index = 1;
        for (int i = 0; i < n; i++) {
            im.rectangle(pdata[index], pdata[index + 1], pdata[index + 2], pdata[index + 3], (byte) pdata[index + 4]);
            index += 5;
        }
        return im;
    }

    @Override
    public void evaluate(final GASolution<int[]> p) {
        if (im == null) {
            im = new GrayScaleImage(template.width, template.height);
        }
        draw(p, im);
        byte[] data = im.data;
        byte[] tdata = template.data;
        int w = im.width;
        int h = im.height;
        double error = 0;
        int index2 = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                error += Math.abs((data[index2] & 0xFF) - (tdata[index2] & 0xFF));
                index2++;
            }
        }
        p.fitness = -error;
    }
}
