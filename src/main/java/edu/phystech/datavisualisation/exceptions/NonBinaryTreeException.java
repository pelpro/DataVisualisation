package edu.phystech.datavisualisation.exceptions;

public class NonBinaryTreeException extends Exception {

    final String nodeId;

    public NonBinaryTreeException(String nodeId) {
        super(String.format("Provided tree isn't correct, node %s has more than 2 childrens", nodeId));
        this.nodeId = nodeId;
    }
}
