/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * Implementation of Red-Black Tree.
 * The method toPrettyString() will return a string with 
 * the values in the tree, in a pyramid fashion, each value 
 * appearing along with its color, so as to make it easy to 
 * visualize the structure of the tree.
 *
 * @author Edgar Ruiz 009634885
 *   
 */
package edu.csupomona.cs.cs241.prog_assgmnt_2;

import java.util.ArrayList;
/*
 * This class keeps the structure of a Red Black Tree and has methods to help add and remove nodes from the tree.
 */

public class RedBlackTree<K extends Comparable<K>, V> implements Tree <K,V>{

    /**
     * Length of key
     */
    private int longestString;

    /**
     * Boolean for color red
     */
    private final boolean RED = true;

    /**
     * Boolean for color black
     */
    private final boolean BLACK = false;

    /**
     * Node that represents nil leaves
     */
    private Node<K, V> nullLeaf;

    /**
     * Root node
     */
    public Node<K, V> root;

    /**
     * Constructs a Red Black Tree
     */
    public RedBlackTree() {
        root = null;
        nullLeaf = new Node<K, V>(BLACK, null, null, null, null, null);
        longestString = 0;
    }

    /**
     * Adds node to the tree and uses
     */
    public void add(K key, V value) {
        if (key == null || value == null) {     
        }
        if (key.toString().length() > longestString) {
            longestString = key.toString().length();
        }
        if (root == null) {
            root = new Node<K, V>(BLACK, nullLeaf, nullLeaf, null, value, key);
            root.getLeftChild().setParent(root);
            root.getRightChild().setParent(root);
        }
        Node<K, V> placeToAdd = addTraverse(key, root);
        if (placeToAdd.getKey().compareTo(key) < 0) {            
            placeToAdd.setRightChild(new Node<K, V>(RED, nullLeaf, nullLeaf, placeToAdd, value, key));          
            addCaseBalance(placeToAdd.getRightChild());
        } 
        else {
          
            placeToAdd.setLeftChild(new Node<K, V>(RED, nullLeaf, nullLeaf,
                    placeToAdd, value, key));
          
            addCaseBalance(placeToAdd.getLeftChild());
        }
    }
    
    /**
     * Checks the 5 cases when adding a node and fixes them 
     * if any of the 5 Red Black Tree invariants are broken
     */
    private void addCaseBalance(Node<K, V> currentNode) {

        // Case 1
        if (currentNode == root) {
            currentNode.setColor(BLACK);
            return;
        }
        Node<K, V> parent = currentNode.getParent();
        assert (parent != null);

        // Case 2
        if (!parent.isRed()) {
            return;
        }
        assert (parent.isRed());
        Node<K, V> grandparent = parent.getParent();
        Node<K, V> uncle = getUncle(currentNode);

        // Case 3
        if (uncle.isRed()) {
            uncle.setColor(BLACK);
            parent.setColor(BLACK);
            grandparent.setColor(RED);
            addCaseBalance(currentNode.getParent().getParent());
        } 
        // cases 4 & 5 
        else if (!uncle.isRed()) {
            if (parent.getRightChild() == currentNode && grandparent.getLeftChild() == parent) {
                rotateLeft(parent);
                currentNode = currentNode.getLeftChild();
                parent = currentNode.getParent();
                grandparent = parent.getParent();
            } 
            else if (parent.getLeftChild() == currentNode && grandparent.getRightChild() == parent) {
                rotateRight(parent);
                currentNode = currentNode.getRightChild();
                parent = currentNode.getParent();
                grandparent = parent.getParent();
            }
            if (parent.getLeftChild() == currentNode && grandparent.getLeftChild() == parent) {
                rotateRight(grandparent);
                grandparent.setColor(RED);
                parent.setColor(BLACK);

            } 
            else if (parent.getRightChild() == currentNode && grandparent.getRightChild() == parent) {
                rotateLeft(grandparent);
                grandparent.setColor(RED);
                parent.setColor(BLACK);
            }

        }

    }

    /**
     * Deletes node with the exact key from the Red Black Tree
     */
    public V remove(K key) {
        V returnValue;
        if (root == null) {
            return null;
        }
        Node<K, V> toBeDeleted = searchHelp(key, root);
        if (toBeDeleted == null) {
            return null;
        }
        returnValue = toBeDeleted.getValue();
        if (toBeDeleted == root && root.getRightChild() == nullLeaf && root.getLeftChild() == nullLeaf) {
            root = null;
            return returnValue;
        }
        boolean successor = false;
        Node<K, V> replaceNode;
        if (toBeDeleted.getRightChild() == nullLeaf
                && toBeDeleted.getLeftChild() == nullLeaf) {
            replaceNode = toBeDeleted;
        } 
        else if (toBeDeleted.getRightChild() != nullLeaf) {
           
            successor = true;
            replaceNode = findInOrderSuccessor(toBeDeleted.getRightChild());

        } 
        else {
        
            replaceNode = findInOrderPredecessor(toBeDeleted.getLeftChild());
        }
        assert (replaceNode != null);
        toBeDeleted.setMapping(replaceNode.getKey(), replaceNode.getValue());
        Node<K, V> nodeNeedingBalance;
        if (replaceNode.isRed()) {
            assert (replaceNode.getRightChild() == nullLeaf && replaceNode.getLeftChild() == nullLeaf);
            if (replaceNode.getParent().getRightChild() == replaceNode) {
                replaceNode.getParent().setRightChild(nullLeaf);
            } 
            else {
                replaceNode.getParent().setLeftChild(nullLeaf);
            }
            return returnValue;
        } 
        else {
            if (successor) {
                assert (replaceNode.getLeftChild() == nullLeaf);
                if (replaceNode.getParent().getRightChild() == replaceNode) {
                    replaceNode.getParent().setRightChild(
                            replaceNode.getRightChild());
                } 
                else {
                    replaceNode.getParent().setLeftChild(
                            replaceNode.getRightChild());
                }
                replaceNode.getRightChild().setParent(replaceNode.getParent());
                nodeNeedingBalance = replaceNode.getRightChild();
                replaceNode.setRightChild(null);
                replaceNode.setLeftChild(null);
                replaceNode.setParent(null);
            } 
            else {
                if (replaceNode.getParent().getRightChild() == replaceNode) {
                    replaceNode.getParent().setRightChild(
                            replaceNode.getLeftChild());
                } 
                else {
                    replaceNode.getParent().setLeftChild(
                            replaceNode.getLeftChild());
                }
                replaceNode.getLeftChild().setParent(replaceNode.getParent());
                nodeNeedingBalance = replaceNode.getLeftChild();
                replaceNode.setRightChild(null);
                replaceNode.setLeftChild(null);
                replaceNode.setParent(null);
            }

            if (nodeNeedingBalance.isRed()) {
                nodeNeedingBalance.setColor(BLACK);
            } 
            else {
                deleteCaseBalance(nodeNeedingBalance);
            }
        }
        return returnValue;
    }

    /**
     * Looks up the value of a node with the given key
     */
    public V lookup(K key) {
        return searchHelp(key, root).getValue();
    }
    
    /**
     * Helps look up node by key using recursion
     * 
     */
    private Node<K, V> searchHelp(K key, Node<K, V> node) {

        if (node.getKey().compareTo(key) == 0) {
            return node;
        }
        if (node.getKey().compareTo(key) < 0 && node.getRightChild() != nullLeaf) {
            return searchHelp(key, node.getRightChild());
        }

        if (node.getKey().compareTo(key) > -1 && node.getLeftChild() != nullLeaf) {
            return searchHelp(key, node.getLeftChild());
        }
        return null;
    }

    /**
     *  Checks the 5 cases when deleting  a node and fixes them 
     * if any of the 5 Red Black Tree invariants are broken
     */
    private void deleteCaseBalance(Node<K, V> node) {
        if (node == root) {
            return;
        }
        Node<K, V> parent = node.getParent();
        Node<K, V> sibling = getSibling(node);
        if (sibling == nullLeaf) {
            return;
        }
        // Case 1 & 2
        if (sibling.isRed()) {
            sibling.setColor(BLACK);
            parent.setColor(BLACK);
            if (node == parent.getLeftChild()) {
                rotateLeft(parent);
            } 
            else {
                rotateRight(parent);
            }
            sibling = getSibling(node);
            parent = node.getParent();
        }
        if (!sibling.isRed()) {
            // Case 3
            if (!sibling.getRightChild().isRed()  && !sibling.getLeftChild().isRed()) {
                if (!parent.isRed()) {
                    sibling.setColor(RED);
                    deleteCaseBalance(parent);
                }
                // Case 3
                else {
                    sibling.setColor(RED);
                    parent.setColor(BLACK);
                    return;
                }
            }
            // Case 4
            if (node == parent.getLeftChild() && !sibling.getRightChild().isRed() && sibling.getLeftChild().isRed()) {
                sibling.setColor(RED);
                sibling.getLeftChild().setColor(BLACK);
                rotateRight(sibling);
            } 
            else if (node == parent.getRightChild() && !sibling.getLeftChild().isRed() && sibling.getRightChild().isRed()) {
                sibling.setColor(RED);
                sibling.getRightChild().setColor(BLACK);
                rotateLeft(sibling);
            }
            if (node == nullLeaf) {
                node.setParent(parent);
            }
            sibling = getSibling(node);
            if (sibling == nullLeaf) {
                return;
            }
            // Case 5
            if (node == parent.getLeftChild() && sibling.getRightChild().isRed()) {
                boolean temp = parent.isRed();
                parent.setColor(sibling.isRed());
                sibling.setColor(temp);
                sibling.getRightChild().setColor(BLACK);
                rotateLeft(parent);
            } 
            else if (node == parent.getRightChild() && sibling.getLeftChild().isRed()) {
                boolean temp = parent.isRed();
                parent.setColor(sibling.isRed());
                sibling.setColor(temp);
                sibling.getLeftChild().setColor(BLACK);
                rotateRight(parent);
            }
        }
    }

    /**
     * Returns sibling of node
     * 
     */
    private Node<K, V> getSibling(Node<K, V> node) {
        if (node.getParent().getRightChild() == node) {
            return node.getParent().getLeftChild();
        } 
        else {
            return node.getParent().getRightChild();
        }
    }

    /**
     * Makes right rotation on node
     * 
     */
    private void rotateRight(Node<K, V> node) {
        if (node != root) {
            if (node.getParent().getLeftChild() == node) {
                node.getLeftChild().setParent(node.getParent());
                node.getParent().setLeftChild(node.getLeftChild());
                Node<K, V> nodesOldLeftChild = node.getLeftChild();
                node.setLeftChild(node.getLeftChild().getRightChild());
                node.getLeftChild().setParent(node);
                nodesOldLeftChild.setRightChild(node);
                node.setParent(nodesOldLeftChild);
            } 
            else if (node.getParent().getRightChild() == node) {
                node.getLeftChild().setParent(node.getParent());
                node.getParent().setRightChild(node.getLeftChild());
                Node<K, V> nodesOldLeftChild = node.getLeftChild();
                node.setLeftChild(node.getLeftChild().getRightChild());
                node.getLeftChild().setParent(node);
                nodesOldLeftChild.setRightChild(node);
                node.setParent(nodesOldLeftChild);
            }
        } 
        else {
            node.getLeftChild().setParent(null);
            root = node.getLeftChild();
            node.setLeftChild(node.getLeftChild().getRightChild());
            node.getLeftChild().setParent(node);
            root.setRightChild(node);
            node.setParent(root);
        }
    }

    /**
     * Makes left rotation on node
     * 
     */
    private void rotateLeft(Node<K, V> node) {
        if (node != root) {
            if (node.getParent().getLeftChild() == node) {
                node.getParent().setLeftChild(node.getRightChild());
                node.getRightChild().setParent(node.getParent());
                Node<K, V> nodesOldRightChild = node.getRightChild();
                node.setRightChild(node.getRightChild().getLeftChild());
                node.getRightChild().setParent(node);
                nodesOldRightChild.setLeftChild(node);
                node.setParent(nodesOldRightChild);
            } 
            else if (node.getParent().getRightChild() == node) {
                node.getParent().setRightChild(node.getRightChild());
                node.getRightChild().setParent(node.getParent());
                Node<K, V> nodesOldRightChild = node.getRightChild();
                node.setRightChild(node.getRightChild().getLeftChild());
                node.getRightChild().setParent(node);
                nodesOldRightChild.setLeftChild(node);
                node.setParent(nodesOldRightChild);
            }
        } 
        else {
            node.getRightChild().setParent(null);
            root = node.getRightChild();
            node.setRightChild(node.getRightChild().getLeftChild());
            node.getRightChild().setParent(node);
            root.setLeftChild(node);
            node.setParent(root);
        }

    }

    /**
     * Finds uncle of given node
     * 
     */
    private Node<K, V> getUncle(Node<K, V> currentNode) {
        Node<K, V> parent = currentNode.getParent();
        if (parent.getParent() == null) {
        }
        if (parent.getParent().getLeftChild() == parent) {
            return parent.getParent().getRightChild();
        } 
        else if (parent.getParent().getRightChild() == parent) {
            return parent.getParent().getLeftChild();
        }
        return null;
    }

    /**
     * Finds node which new node will be added to
     * 
     */
    private Node<K, V> addTraverse(K key, Node<K, V> startNode) {
        Node<K, V> left = startNode.getLeftChild();
        Node<K, V> right = startNode.getRightChild();
        if (left == nullLeaf && right == nullLeaf) {
            return startNode;
        }
        if (left == nullLeaf && right != nullLeaf) {
            if (startNode.getKey().compareTo(key) > -1) {
                return startNode;
            } 
            else {
                return addTraverse(key, right);
            }
        }
        if (right == nullLeaf && left != nullLeaf) {
            if (startNode.getKey().compareTo(key) < 0) {
                return startNode;
            } 
            else {
                return addTraverse(key, left);
            }
        }
        if (left != nullLeaf && right != nullLeaf) {
            if (startNode.getKey().compareTo(key) < 0) {
                return addTraverse(key, right);
            } 
            else {
                return addTraverse(key, left);
            }

        }
        return null;
    }

    /**
     * Finds in order successor starting at root
     */
    private Node<K, V> findInOrderSuccessor(Node<K, V> startNode) {
        if (startNode.getLeftChild() == nullLeaf) {
            return startNode;
        }
        else if (startNode.getLeftChild() != nullLeaf) {
            return findInOrderSuccessor(startNode.getLeftChild());
        }
        else if (startNode.getRightChild() != nullLeaf) {
            return findInOrderSuccessor(startNode.getRightChild());
        }
        return null;
    }

    /**
     * Finds in order predecessor starting at root
     */

    private Node<K, V> findInOrderPredecessor(Node<K, V> startNode) {

        if (startNode.getRightChild() == nullLeaf) {
            return startNode;
        }
        else if (startNode.getRightChild() != nullLeaf) {
            return findInOrderPredecessor(startNode.getRightChild());
        }
        else if (startNode.getLeftChild() != nullLeaf) {
            return findInOrderPredecessor(startNode.getLeftChild());
        }
        return null;
    }

    /**
     * Finds depth of tree
     */
    private int getTreeDepth(Node<K, V> start) {
        if (start == null) {
            return 0;
        }
        return Math.max(getTreeDepth(start.getRightChild()) + 1,
                getTreeDepth(start.getLeftChild()) + 1);

    }

    /**
     * Used to verify the number of red and black nodes in each path
     * Keeps number of black nodes in each path equal
     */
    public int verify() {
        return verifyHelp(root, 1, false, -1);
    }

    /**
     * Used by verify method
     */
    private int verifyHelp(Node<K, V> node, int numofBlack, boolean wasRed, int firstPathBlack) {
        if (node == null) {
            if (firstPathBlack == -1) {
                firstPathBlack = numofBlack;
            } 
            else {
                assert (numofBlack == firstPathBlack);
            }
            return firstPathBlack;
        }
        if (!node.isRed()) {
            numofBlack++;
        }
        if (node.isRed()) {
        }
        verifyHelp(node.getRightChild(), numofBlack, node.isRed(), firstPathBlack);
        verifyHelp(node.getLeftChild(), numofBlack, node.isRed(), firstPathBlack);
        return numofBlack;
    }
    
    /**
     * Prints Red Black Tree to visualize the structure of the tree
     */
    public String toPrettyString() {
        ArrayList<NodeDepth<K, V>> queue = new ArrayList<NodeDepth<K, V>>();
        int depth = 0;
        int treeDepth = getTreeDepth(root);
        int beginningSpaces = longestString + 2;
        for (int i = 0; i < treeDepth - 2; i++) {
            beginningSpaces = (beginningSpaces * 2) + longestString + 2;
        }
        int inBetweenSpaces = beginningSpaces;
        queue.add(0, new NodeDepth<K, V>(depth, root));
        NodeDepth<K, V> m = null;
        for (int i = 0; i < beginningSpaces; i++) {
            System.out.print(" ");
        }
        while (depth < treeDepth) {
            m = queue.remove(0);
            if (m.getDepth() > depth) {
                depth = m.getDepth();
                if (!(depth < treeDepth)) {
                    break;
                }
                System.out.println();
                inBetweenSpaces = beginningSpaces;
                beginningSpaces = ((beginningSpaces - (longestString + 2)) / 2);
                for (int i = 0; i < beginningSpaces; i++) {
                    System.out.print(" ");
                }

            }
            Node<K, V> right;
            Node<K, V> left;
            if (m.getNode() == null) {
                right = null;
                left = null;
            } 
            else {
                right = m.getNode().getRightChild();
                left = m.getNode().getLeftChild();
            }
            queue.add(queue.size(), new NodeDepth<K, V>(depth + 1, left));
            queue.add(queue.size(), new NodeDepth<K, V>(depth + 1, right));
            if (m.getNode() == null) {
                for (int j = 0; j < longestString + 2; j++) {
                    System.out.print(" ");
                }
            } 
            else if (m.getNode() == nullLeaf) {
                for (int j = 0; j < longestString; j++) {
                    System.out.print("N");
                }
                System.out.print(":B");
            } 
            else {
                System.out.print(m.getNode().getKey());

                for (int j = m.getNode().getKey().toString().length(); j < longestString; j++) {
                    System.out.print("E");
                }

                if (m.getNode().isRed()) {
                    System.out.print(":R");
                } 
                else {
                    System.out.print(":B");
                }
            }

            for (int k = 0; k < inBetweenSpaces; k++) {
                System.out.print(" ");
            }

        }
        System.out.println();
        return " ";
    }
}