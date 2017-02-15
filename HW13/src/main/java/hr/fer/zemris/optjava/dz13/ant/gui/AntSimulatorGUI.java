package hr.fer.zemris.optjava.dz13.ant.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;
import hr.fer.zemris.optjava.dz13.ant.Direction;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.AntGPSolution;

public class AntSimulatorGUI extends JFrame implements IAntListener {

    private static final long serialVersionUID = 1L;

    private final JButton nextButton = new JButton("Next");
    private final JButton runAllButton = new JButton("Run all");
    private final JButton resetButton = new JButton("Reset");
    private final JLabel foodLabel = new JLabel("Food: 0");
    private final JLabel stepsLabel = new JLabel("Steps: 0");

    private final BlockingAnt ant;
    private final AntWorldGraphics map;

    public AntSimulatorGUI(final AntWorldGraphics map, final BlockingAnt ant) {
        this.ant = ant;
        this.map = map;

        initGui();
    }

    protected void initGui() {
        setTitle("AntGUI");
        setSize(500, 500);
        setLayout(new BorderLayout());
        add(map);
        JPanel p = new JPanel();
        p.add(nextButton);
        nextButton.addActionListener(e -> {
            ant.resume();
        });

        p.add(resetButton);
        resetButton.addActionListener(e -> {
            resetData(map, ant);
            SwingUtilities.invokeLater(() -> {
                this.repaint();
            });

        });

        p.add(runAllButton);
        runAllButton.addActionListener(e -> {
            ant.runAll();

        });
        p.add(foodLabel);
        p.add(stepsLabel);

        add(p, BorderLayout.SOUTH);
        ant.addListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        map.updateAnt(ant);
    }

    @Override
    public void update(final Ant source) {
        map.updateAnt(source);
        foodLabel.setText("Food: " + ant.getFoodEaten());
        stepsLabel.setText("Steps: " + ant.getStepsTaken());
        SwingUtilities.invokeLater(() -> {
            this.repaint();
        });

    }

    protected static void resetData(final AntWorldGraphics map, final BlockingAnt ant) {
        map.map.restoreMap();
        ant.setReset();
        map.updateAnt(ant);
        map.resetFields();
    }

    public static void showSolution(final AntMap map, final AntGPSolution solution, final int minFood,
            final int maxSteps) {
        AntWorldGraphics world = new AntWorldGraphics(map);
        BlockingAnt ant = new BlockingAnt(0, 0, Direction.RIGHT);

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new AntSimulatorGUI(world, ant).setVisible(true);
            }
        });
        while (true) {
            try {
                ant.reset();
                runAnt(map, solution, maxSteps, minFood, world, ant);
            } catch (AntRestartException e) {
                continue;
            }
        }

    }

    protected static void runAnt(final AntMap map, final AntGPSolution solution, final int maxSteps, final int minFood,
            final AntWorldGraphics world, final BlockingAnt ant) {
        while (ant.getStepsTaken() <= maxSteps && ant.getFoodEaten() < minFood) {
            solution.execute(map, ant);
        }
        resetData(world, ant);
    }

}
