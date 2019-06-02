package com.geniusver.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by GeniusV on 2019-06-01.
 */
public class TreeNode implements Iterable {
    private String key;
    private Object value;
    private Map<String, TreeNode> children = new LinkedHashMap<>();

    public TreeNode() {

    }

    public TreeNode(String key) {
        this.key = key;
    }

    @Override
    public Iterator iterator() {
        return new TreeNodeIterator(children.entrySet().iterator());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Map<String, TreeNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, TreeNode> children) {
        this.children = children;
    }

    public TreeNode put(String key, Object object) {
        return putInternal(new String[]{key}, object, 0);
    }

    public TreeNode put(String[] keyPath, Object object) {
        return putInternal(keyPath, object, 0);
    }

    private TreeNode putInternal(String[] keyPath, Object value, int cur) {
        if (cur == keyPath.length) {
            this.value = value;
            return this;
        }
        TreeNode child = children.get(keyPath[cur]);
        if (child == null) {
            child = new TreeNode(keyPath[cur]);
            children.put(keyPath[cur], child);
        }
        return child.putInternal(keyPath, value, cur + 1);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String... keys) throws ClassCastException {
        return (T) getInternal(keys, 0);
    }

    public Object getInternal(String[] keyPath, int cur) {
        if (cur == keyPath.length) {
            return this.value;
        }
        TreeNode child = children.get(keyPath[cur]);
        if (child == null) {
            return null;
        }
        return child.getInternal(keyPath, cur + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return Objects.equals(key, treeNode.key) &&
                Objects.equals(value, treeNode.value) &&
                Objects.equals(children, treeNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, children);
    }

    @Override
    public String toString() {
        return "com.geniusver.util.TreeNode{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", children=" + children.keySet() +
                '}';
    }

    private class TreeNodeIterator implements Iterator {
        private Iterator<Map.Entry<String, TreeNode>> objectMapIterator;

        public TreeNodeIterator(Iterator<Map.Entry<String, TreeNode>> objectMapIterator) {
            this.objectMapIterator = objectMapIterator;
        }

        @Override
        public boolean hasNext() {
            return objectMapIterator.hasNext();
        }

        @Override
        public Object next() {
            return objectMapIterator.next().getValue();
        }
    }
}
