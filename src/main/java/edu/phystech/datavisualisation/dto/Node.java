package edu.phystech.datavisualisation.dto;

import edu.phystech.datavisualisation.exceptions.NonBinaryTreeException;

public class Node {

    public Node(String id){
        this.id = id;
    }

    public String id;

    public String leftChildId;
    public String rightChildId;
    public String parentId;

    public int leftDepth;

    public int getLeftDepth() {
        return leftDepth;
    }

    public void setLeftDepth(int leftDepth) {
        this.leftDepth = leftDepth;
    }

    public int getRightDepth() {
        return rightDepth;
    }

    public void setRightDepth(int rightDepth) {
        this.rightDepth = rightDepth;
    }

    public int rightDepth;

    public int x;
    public int y;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeftChildId() {
        return leftChildId;
    }

    public void setLeftChildId(String leftChildId) throws NonBinaryTreeException {
        if (this.leftChildId == null) {
            this.leftChildId = leftChildId;
        } else {
            setRightChildId(leftChildId);
        }
    }

    public String getRightChildId() {
        return rightChildId;
    }

    public void setRightChildId(String rightChildId) throws NonBinaryTreeException {
        if (this.rightChildId == null) {
            this.rightChildId = rightChildId;
        } else {
            throw new NonBinaryTreeException(id);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
