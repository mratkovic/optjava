package hr.fer.zemris.optjava.dz13.ant.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import hr.fer.zemris.optjava.dz13.ant.Ant;

public class AntWorldField extends JComponent {
    private static final long serialVersionUID = 1L;

    private Ant ant;
    private boolean food;

    public AntWorldField() {
        super();
    }

    public void setAnt(final Ant ant) {
        this.ant = ant;
    }

    public void setFood(final boolean food) {
        this.food = food;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(Color.YELLOW);
        if (food) {
            g.setColor(Color.BLUE);
        }
        g.fillRect(0, 0, width, height);

        if (ant != null) {
            draw_ant(g, width, height);

        }

    }

    protected void draw_ant(final Graphics g, final int width, final int height) {
        g.setColor(Color.black);

        int[] x = null;
        int[] y = null;

        switch (ant.getDirection()) {
        case UP:
            x = new int[] { width / 2, width, 0 };
            y = new int[] { 0, height, height };
            break;

        case DOWN:
            x = new int[] { width / 2, width, 0 };
            y = new int[] { height, 0, 0 };
            break;

        case LEFT:
            x = new int[] { 0, width, width };
            y = new int[] { height / 2, 0, height };
            break;

        case RIGHT:
            x = new int[] { width, 0, 0 };
            y = new int[] { height / 2, 0, height };
            break;

        }
        g.fillPolygon(x, y, 3);
    }

    public void resetAnt() {
        ant = null;

    }

}
