package com.example.maven.basic.referenceFieldUpdater;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReferenceFieldUpdater {
    private static class TreeNode {
        volatile Integer value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }
    }

    private static final AtomicReferenceFieldUpdater<TreeNode, Integer> TREE_NODE_LEFT_UPDATER =
            AtomicReferenceFieldUpdater.newUpdater(TreeNode.class, Integer.class, "value");

    @Test
    public void test1() {
        TreeNode root = new TreeNode(0);
        TREE_NODE_LEFT_UPDATER.compareAndSet(root, 0, 1);
        assertThat(root.value).isEqualTo(1);
    }
}
