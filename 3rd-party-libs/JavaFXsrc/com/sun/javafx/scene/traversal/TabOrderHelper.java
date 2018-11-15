/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package com.sun.javafx.scene.traversal;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.List;

final class TabOrderHelper {
    private static Node findPreviousFocusableInList(List<Node> nodeList, int startIndex) {
        for (int i = startIndex ; i >= 0 ; i--) {
            Node prevNode = nodeList.get(i);
            // ParentTraverEngine can override traversability, so we need to check it first
            if (isDisabledOrInvisible(prevNode)) continue;
            final ParentTraversalEngine impl_traversalEngine = prevNode instanceof Parent ? ((Parent)prevNode).getImpl_traversalEngine() : null;
            if (prevNode instanceof Parent) {
                if (impl_traversalEngine != null && impl_traversalEngine.canTraverse()) {
                    Node selected = impl_traversalEngine.selectLast();
                    if (selected != null) {
                        return selected;
                    }
                } else {
                    List<Node> prevNodesList = ((Parent) prevNode).getChildrenUnmodifiable();
                    if (prevNodesList.size() > 0) {
                        Node newNode = findPreviousFocusableInList(prevNodesList, prevNodesList.size() - 1);
                        if (newNode != null) {
                            return newNode;
                        }
                    }
                }
            }
            if (impl_traversalEngine != null ? impl_traversalEngine.isParentTraversable() : prevNode.isFocusTraversable()) {
                return prevNode;
            }
        }
        return null;
    }

    private static boolean isDisabledOrInvisible(Node prevNode) {
        return prevNode.isDisabled() || !prevNode.impl_isTreeVisible();
    }

    public static Node findPreviousFocusablePeer(Node node, Parent root) {
        Node startNode = node;
        Node newNode = null;
        List<Node> parentNodes = findPeers(startNode);

        if (parentNodes == null) {
            // We are at top level, so select the last focusable node
            ObservableList<Node> rootChildren = ((Parent) node).getChildrenUnmodifiable();
            return findPreviousFocusableInList(rootChildren, rootChildren.size() - 1);
        }

        int ourIndex = parentNodes.indexOf(startNode);

        // Start with the siblings "to the left"
        newNode = findPreviousFocusableInList(parentNodes, ourIndex - 1);

        /*
        ** we've reached the end of the peer nodes, and none have been selected,
        ** time to look at our parents peers.....
        */
        while (newNode == null && startNode.getParent() != root) {
            List<Node> peerNodes;
            int parentIndex;

            Parent parent = startNode.getParent();
            if (parent != null) {
                // If the parent itself is traversable, select it
                final ParentTraversalEngine parentEngine = parent.getImpl_traversalEngine();
                if (parentEngine != null ? parentEngine.isParentTraversable() : parent.isFocusTraversable()) {
                    newNode = parent;
                } else {
                    peerNodes = findPeers(parent);
                    if (peerNodes != null) {
                        parentIndex = peerNodes.indexOf(parent);
                        newNode = findPreviousFocusableInList(peerNodes, parentIndex - 1);
                    }
                }
            }
            startNode = parent;
        }

        return newNode;
    }

    private static List<Node> findPeers(Node node) {
        List<Node> parentNodes = null;
        Parent parent = node.getParent();
        /*
        ** check that we haven't hit the top-level
        */
        if (parent != null) {
            parentNodes = parent.getChildrenUnmodifiable();
        }
        return parentNodes;
    }

    private static Node findNextFocusableInList(List<Node> nodeList, int startIndex) {
        for (int i = startIndex ; i < nodeList.size() ; i++) {
            Node nextNode = nodeList.get(i);
            if (isDisabledOrInvisible(nextNode)) continue;
            final ParentTraversalEngine impl_traversalEngine = nextNode instanceof Parent ? ((Parent)nextNode).getImpl_traversalEngine() : null;
            // ParentTraverEngine can override traversability, so we need to check it first
            if (impl_traversalEngine != null ? impl_traversalEngine.isParentTraversable() : nextNode.isFocusTraversable()) {
                return nextNode;
            }
            else if (nextNode instanceof Parent) {
                if (impl_traversalEngine!= null && impl_traversalEngine.canTraverse()) {
                    Node selected = impl_traversalEngine.selectFirst();
                    if (selected != null) {
                        return selected;
                    } else {
                        // If the Parent has it's own engine, but no selection can be done, skip it
                        continue;
                    }
                }
                List<Node> nextNodesList = ((Parent)nextNode).getChildrenUnmodifiable();
                if (nextNodesList.size() > 0) {
                    Node newNode = findNextFocusableInList(nextNodesList, 0);
                    if (newNode != null) {
                        return newNode;
                    }
                }
            }
        }
        return null;
    }

    public static Node findNextFocusablePeer(Node node, Parent root, boolean traverseIntoCurrent) {
        Node startNode = node;
        Node newNode = null;

        // First, try to find next peer among the node children
        if (traverseIntoCurrent && node instanceof Parent) {
            newNode = findNextFocusableInList(((Parent)node).getChildrenUnmodifiable(), 0);
        }

        // Next step is to select the siblings "to the right"
        if (newNode == null) {
            List<Node> parentNodes = findPeers(startNode);
            if (parentNodes == null) {
                // We got a top level Node that has no focusable children (we know that from the first step above), so
                // there's nothing to do.
                return null;
            }
            int ourIndex = parentNodes.indexOf(startNode);
            newNode = findNextFocusableInList(parentNodes, ourIndex + 1);
        }

        /*
        ** we've reached the end of the peer nodes, and none have been selected,
        ** time to look at our parents peers.....
        */
        while (newNode == null && startNode.getParent() != root) {
            List<Node> peerNodes;
            int parentIndex;

            Parent parent = startNode.getParent();
            if (parent != null) {
                peerNodes = findPeers(parent);
                if (peerNodes != null) {
                    parentIndex = peerNodes.indexOf(parent);
                    newNode = findNextFocusableInList(peerNodes, parentIndex + 1);
                }
            }
            startNode = parent;
        }

        return newNode;
    }

    public static Node getFirstTargetNode(Parent p) {
        if (p == null || isDisabledOrInvisible(p)) return null;
        final ParentTraversalEngine impl_traversalEngine = p.getImpl_traversalEngine();
        if (impl_traversalEngine!= null && impl_traversalEngine.canTraverse()) {
            Node selected = impl_traversalEngine.selectFirst();
            if (selected != null) {
                return selected;
            }
        }
        List<Node> parentsNodes = p.getChildrenUnmodifiable();
        for (Node n : parentsNodes) {
            if (isDisabledOrInvisible(n)) continue;
            final ParentTraversalEngine parentEngine = n instanceof Parent ? ((Parent)n).getImpl_traversalEngine() : null;
            if (parentEngine != null ? parentEngine.isParentTraversable() : n.isFocusTraversable()) {
                return n;
            }
            if (n instanceof Parent) {
                Node result = getFirstTargetNode((Parent)n);
                if (result != null) return result;
            }
        }
        return null;
    }

    public static Node getLastTargetNode(Parent p) {
        if (p == null || isDisabledOrInvisible(p)) return null;
        final ParentTraversalEngine impl_traversalEngine = p.getImpl_traversalEngine();
        if (impl_traversalEngine!= null && impl_traversalEngine.canTraverse()) {
            Node selected = impl_traversalEngine.selectLast();
            if (selected != null) {
                return selected;
            }
        }
        List<Node> parentsNodes = p.getChildrenUnmodifiable();
        for (int i = parentsNodes.size() - 1; i >= 0; --i) {
            Node n = parentsNodes.get(i);
            if (isDisabledOrInvisible(n)) continue;

            if (n instanceof Parent) {
                Node result = getLastTargetNode((Parent) n);
                if (result != null) return result;
            }
            final ParentTraversalEngine parentEngine = n instanceof Parent ? ((Parent)n).getImpl_traversalEngine() : null;
            if (parentEngine != null ? parentEngine.isParentTraversable() : n.isFocusTraversable()) {
                return n;
            }
        }
        return null;
    }
}
