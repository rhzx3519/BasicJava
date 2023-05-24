package com.leetcode;

class TreeNode {
     int val;
      TreeNode left;
     TreeNode right;
      TreeNode() {}
    TreeNode(int val) { this.val = val; }
     TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
         this.right = right;
      }
 }

class Insufficient {
    private TreeNode dfs(TreeNode root, int s, int limit) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            if (root.val + s < limit) {
                return null;
            }
            return root;
        }

        root.left = dfs(root.left, root.val + s, limit);
        root.right = dfs(root.right, root.val + s, limit);
        if (root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    public TreeNode sufficientSubset(TreeNode root, int limit) {
        return dfs(root, 0, limit);
    }
}