package edu.phystech.datavisualisation;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.pgm.util.io.graphml.GraphMLReader;
import edu.phystech.datavisualisation.dto.Node;
import edu.phystech.datavisualisation.exceptions.NonBinaryTreeException;
import edu.phystech.datavisualisation.task1.GraphDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Application {

    public static edu.phystech.datavisualisation.dto.Graph graphNew = new edu.phystech.datavisualisation.dto.Graph();

    private final JFrame frame = new JFrame("Plotting result");
    private Graph graph;
    private final int PADDING = 10;
    private final int RADIUS = 3;
    private final int SIZE_X = 500;
    private final int SIZE_Y = 500;

    public void drawGraph() {
        Canvas canvas = new Canvas();
        canvas.setSize(SIZE_X, SIZE_Y);
        frame.add(canvas);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame draw(Graph graph) {
        this.graph = graph;
        return frame;
    }

    private static void dfsRecursive1(String current, boolean[] isVisited) {
        Integer cur = Integer.parseInt(current.substring(1));
        isVisited[cur] = true;
        visit1(current);
        ArrayList<String> destination = new ArrayList<>();
        if (graphNew.nodesMap.get(current).getLeftChildId() != null){
            destination.add(graphNew.nodesMap.get(current).getLeftChildId());
        }
        if (graphNew.nodesMap.get(current).getRightChildId() != null){
            destination.add(graphNew.nodesMap.get(current).getRightChildId());
        }
        for (String dest : destination) {
            if (!isVisited[Integer.parseInt(dest.substring(1))])
                dfsRecursive1(dest, isVisited);
        }
    }

    private static void dfsRecursive2(String current, boolean[] isVisited) {
        Integer cur = Integer.parseInt(current.substring(1));
        isVisited[cur] = true;
        visit2(current);
        ArrayList<String> destination = new ArrayList<>();
        if (graphNew.nodesMap.get(current).getLeftChildId() != null){
            destination.add(graphNew.nodesMap.get(current).getLeftChildId());
        }
        if (graphNew.nodesMap.get(current).getRightChildId() != null){
            destination.add(graphNew.nodesMap.get(current).getRightChildId());
        }
        for (String dest : destination) {
            if (!isVisited[Integer.parseInt(dest.substring(1))])
                dfsRecursive2(dest, isVisited);
        }
    }

    private static void visit1(String current){
        if (graphNew.nodesMap.get(current).getParentId()==null){
            graphNew.nodesMap.get(current).setLeftDepth(1);
            graphNew.nodesMap.get(current).setRightDepth(1);
        } else if (graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getLeftChildId()
                == graphNew.nodesMap.get(current).getId()){
            while (graphNew.nodesMap.get(current).getParentId()!=null){
                String parent = graphNew.nodesMap.get(current).getParentId();
                graphNew.nodesMap.get(parent).setLeftDepth(graphNew.nodesMap.get(parent).getLeftDepth() + 1);
                current = parent;
            }
        } else {
            while (graphNew.nodesMap.get(current).getParentId()!=null){
                String parent = graphNew.nodesMap.get(current).getParentId();
                graphNew.nodesMap.get(parent).setRightDepth(graphNew.nodesMap.get(parent).getRightDepth() + 1);
                current = parent;
            }
        }
    }


    private static void visit2(String current){
        if (graphNew.nodesMap.get(current).getParentId()==null){
            graphNew.nodesMap.get(current).setX(1);
            graphNew.nodesMap.get(current).setY(1);
        } else if (graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getLeftChildId()
                == graphNew.nodesMap.get(current).getId()){
            String rightId = graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getRightChildId();
            Integer depth = 1;
            if (rightId != null){
                depth = graphNew.nodesMap.get(rightId).getLeftDepth() + 1;
            }
            graphNew.nodesMap.get(current).setX((graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getX()));
            graphNew.nodesMap.get(current).setY((graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getY() + depth));
        } else {
            String leftId = graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getLeftChildId();
            Integer depth = 1;
            if (leftId != null){
                depth = graphNew.nodesMap.get(leftId).getRightDepth() + 1;
            }
            graphNew.nodesMap.get(current).setX((graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getX() + depth));
            graphNew.nodesMap.get(current).setY((graphNew.nodesMap.get(graphNew.nodesMap.get(current).getParentId()).getY()));
        }
    }

    public static void main(String[] args) throws IOException {
       // edu.phystech.datavisualisation.dto.Graph graphNew = new edu.phystech.datavisualisation.dto.Graph();
        Graph graph = new TinkerGraph();
        GraphMLReader reader = new GraphMLReader(graph);

        InputStream is = new BufferedInputStream(new FileInputStream("src/main/resources/testGraph2.xml"));
        reader.inputGraph(is);

        Iterable<Vertex> vertices = graph.getVertices();
        Iterator<Vertex> verticesIterator = vertices.iterator();
        while (verticesIterator.hasNext()) {
            Vertex vertex = verticesIterator.next();
            graphNew.nodes.add(new Node(vertex.getId().toString()));
            graphNew.nodesMap.put(vertex.getId().toString(), new Node(vertex.getId().toString()));
        }

        Iterable<Edge> edges = graph.getEdges();
        Iterator<Edge> edgeIterator = edges.iterator();
        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            graphNew.edges.add(new edu.phystech.datavisualisation.dto.Edge(edge.getOutVertex().getId().toString(), edge.getInVertex().getId().toString()));
        }
        graphNew.edges.forEach(edge -> {
            try {
                graphNew.nodesMap.get(edge.source).setLeftChildId(edge.target);
                graphNew.nodesMap.get(edge.target).setParentId(edge.source);
            } catch (NonBinaryTreeException e) {
                e.printStackTrace();
            }
        });
        String root = graphNew.getRoot();
        boolean[] isVisited = new boolean[graphNew.nodes.size()];
        dfsRecursive1(root,isVisited);
        boolean[] isVisited2 = new boolean[graphNew.nodes.size()];
        dfsRecursive2(root, isVisited2);

        new GraphDrawer().draw(graphNew);
    }
}
