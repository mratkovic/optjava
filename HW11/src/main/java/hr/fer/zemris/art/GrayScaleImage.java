package hr.fer.zemris.art;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrayScaleImage {
    public int width;
    public int height;
    public byte[] data;

    public GrayScaleImage(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.data = new byte[height * width];
    }

    public void clear(final byte color) {
        int index = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                data[index] = color;
                index++;
            }
        }
    }

    public void rectangle(final int x, final int y, final int w, final int h, final byte color) {
        int xs = x;
        int xe = x + w - 1;
        int ys = y;
        int ye = y + h - 1;

        // Ako sigurno ne crtam:
        if (width <= xs || height <= ys || xe < 0 || ye < 0) {
            return;
        }
        xs = Math.max(xs, 0);
        ys = Math.max(ys, 0);

        xe = Math.min(xe, width - 1);
        ye = Math.min(ye, height - 1);

        for (int yl = ys; yl <= ye; yl++) {
            int index = yl * width + xs;
            for (int xl = xs; xl <= xe; xl++) {
                data[index] = color;
                index++;
            }
        }
    }

    public void save(final File file) throws IOException {
        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] buf = new int[1];
        WritableRaster r = bim.getRaster();
        int index = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                buf[0] = data[index] & 0xFF;
                r.setPixel(w, h, buf);
                index++;
            }
        }
        try {
            ImageIO.write(bim, "png", file);
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static GrayScaleImage load(final File file) throws IOException {
        BufferedImage bim = ImageIO.read(file);
        if (bim.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IOException("Image not grayscale.");
        }
        GrayScaleImage im = new GrayScaleImage(bim.getWidth(), bim.getHeight());
        try {
            int[] buf = new int[1];
            WritableRaster r = bim.getRaster();
            int index = 0;
            for (int h = 0; h < im.height; h++) {
                for (int w = 0; w < im.width; w++) {
                    r.getPixel(w, h, buf);
                    im.data[index] = (byte) buf[0];
                    index++;
                }
            }
        } catch (Exception ex) {
            throw new IOException("Image not grayscale.");
        }
        return im;
    }
}