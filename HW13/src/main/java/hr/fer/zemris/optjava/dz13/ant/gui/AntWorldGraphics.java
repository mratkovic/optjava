package hr.fer.zemris.optjava.dz13.ant.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntWorldGraphics extends JComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    AntMap map;
    AntWorldField[][] fields;
    Ant ant;

    public AntWorldGraphics(final AntMap map) {
        this.map = map;
        fields = new AntWorldField[map.width][map.height];
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(map.width, map.height));
        for (int x = 0; x < map.width; ++x) {
            for (int y = 0; y < map.height; ++y) {

                AntWorldField field = new AntWorldField();
                field.setFood(map.food[x][y]);
                field.setVisible(true);
                fields[x][y] = field;

                JPanel p = new JPanel();
                p.setLayout(new BorderLayout());
                p.add(field, BorderLayout.CENTER);
                p.setBorder(BorderFactory.createEmptyBorder(1, 1, 0, 0));
                add(p);
            }
        }
    }

    public void updateAnt(final Ant newAnt) {
        ant = newAnt.copy();
        resetFields();
        fields[ant.getX()][ant.getY()].setAnt(ant.copy());

    }

    public void resetFields() {
        for (int x = 0; x < map.width; ++x) {
            for (int y = 0; y < map.height; ++y) {
                AntWorldField field = fields[x][y];
                field.setFood(map.food[x][y]);
                field.setVisible(true);
                field.resetAnt();
            }
        }
        fields[ant.getX()][ant.getY()].setAnt(ant.copy());

    }
}
