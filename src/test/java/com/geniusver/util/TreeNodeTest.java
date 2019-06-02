package com.geniusver.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by GeniusV on 2019-06-02.
 */
class TreeNodeTest {
    @Nested
    class GetTest {
        @Test
        void level1() {
            TreeNode tree = new TreeNode();
            tree.put(new String[]{"k"}, "v");

            String value = tree.get("k");
            assertEquals("v", value);
        }


        @Test
        void level3() {
            TreeNode tree = new TreeNode();
            tree.put(new String[]{"k1", "k2", "k3"}, "v");

            String res = tree.get("k1", "k2", "k3");
            assertEquals("v", res);
        }


        @Test
        void multiput() {
            TreeNode tree = new TreeNode();
            TreeNode f = tree.put(new String[]{"k1", "k2", "k3"}, "v");
            f.put("k4", "v2");
            tree.put(new String[]{"k1", "k2", "k5"}, "v3");

            System.out.println(tree);
            assertEquals("v", tree.get("k1", "k2", "k3"));
            assertEquals("v", f.getValue());
            assertEquals("v2", f.get("k4"));
            assertEquals("v3", tree.get("k1", "k2", "k5"));

        }

    }
}