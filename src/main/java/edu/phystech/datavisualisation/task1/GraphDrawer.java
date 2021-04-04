package edu.phystech.datavisualisation.task1;

import edu.phystech.datavisualisation.dto.Graph;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.BLACK;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GraphDrawer extends JPanel {
    private final JFrame frame = new JFrame("PLot");
    private Graph graph;
    private final int RADIUS = 3;
    private final int SIZE_X = 400;
    private final int SIZE_Y = 400;

    public GraphDrawer() {
        Canvas canvas = new Canvas();
        canvas.setSize(SIZE_X, SIZE_Y);
        this.setSize(SIZE_X, SIZE_Y);
        frame.add(this);
        frame.add(canvas);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame draw(Graph graph) {
        this.graph = graph;
        return frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BLACK);

        graph.nodesMap.forEach((s, node) -> g.fillRoundRect(
                (int) ((node.x) * 10 ) + 10,
                (int) ((node.y) * 10 ) + 10,
                 RADIUS,
                 RADIUS,
                RADIUS,
                RADIUS
        ));
        graph.edges.forEach(edge -> g.drawLine(
                (int) ((graph.nodesMap.get(edge.source).x) * 10) + 10,
                (int) ((graph.nodesMap.get(edge.source).y) * 10) + 10,
                (int) ((graph.nodesMap.get(edge.target).x) * 10) + 10,
                (int) ((graph.nodesMap.get(edge.target).y ) * 10 + 10)
        ));
    }
}