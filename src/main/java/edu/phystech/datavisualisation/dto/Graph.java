package edu.phystech.datavisualisation.dto;

import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.nodesMap = new HashMap<>();
    }

    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;

    public String root;

    public HashMap<String, Node> nodesMap;

    public String getRoot(){
        nodesMap.forEach((s, node) -> {
            if (node.getParentId() == null) {
                System.out.println(node.getId());
                this.root = node.getId();
            }
        });
        return root;
    }
}
