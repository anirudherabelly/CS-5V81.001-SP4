/** @author 
 *  Binary search tree (starter code)
 **/

package AXE170009;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Implementation of the Binary Search Tree data structure.
 * At every node in the tree all the elements to the left are smaller than root and all the elements
 * to the right are greater than the root.
 * This tree implementation doesn't allow storing of duplicates
 * @param <T> Type of the element to be stored.
 * @author Kautil
 * @author Anirudh
 */
public class BinarySearchTree < T extends Comparable < ? super T >> implements Iterable < T > {
    static class Entry < T > {
        T element;
        Entry < T > left,
        right;

        public Entry(T x, Entry < T > left, Entry < T > right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry < T > root;
    int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    /**
     * @param x element to search for
     * @return true if element is present in the tree else false
     */
    public boolean contains(T x) {
        return get(x)!=null;
    }

    /** Is there an element that is equal to x in the tree?
     *  @param x element to find
     *  @return Element in tree that is equal to x, null otherwise.
     */
    public T get(T x) {
        if(x==null){
            return null;
        }
        Entry<T> entryX = findEntry(x);
        if(entryX==null){
            return null;
        }
        return entryX.element.compareTo(x)==0?entryX.element:null;
    }

    /**
     *  Adds the element to the tree
     *  If tree contains a node with same key, replace element by x.
     *  @param x element to be added
     *  @return true if x is a new element added to tree. false otherwise
     *  @throws IllegalArgumentException if passed element is null
     */
    public boolean add(T x) {
        if(x==null){
            throw new IllegalArgumentException("Null values are not allowed in the BST");
        }
        Entry<T> entryX = findEntry(x);
        if(entryX==null){
            //no elements in the tree yet
            size++;
            root=new Entry<>(x,null,null);
            return true;
        }
        int cmp = entryX.element.compareTo(x);
        if(cmp==0){
            //replacing the element with current x
            entryX.element = x;
            return false;
        }
        size++;
        Entry<T> newNode = new Entry<>(x,null,null);
        if(cmp<0){
            entryX.right = newNode;
        }else {
            entryX.left = newNode;
        }
        return true;
    }

    /**
     * returns the entry with value.equals(x) if present or else the parent node under which x should have been present
     * @param x element whose location should be found
     * @return entry with x when found or the entry under which x should be present or null if no elements in the tree
     */
    private Entry<T> findEntry(T x){
        ParentChildPair<T> pair = getEntryWithParent(x);
        return pair.child==null?pair.parent:pair.child;
    }

    /**
     * returns the element along with its direct parent.
     * parent will be null if element is root.
     * child will be null if element is not found.
     * @param x element to be searched for
     * @return a pair with parent and element.
     */
    private ParentChildPair<T> getEntryWithParent(T x) {
        Entry<T> parentOfCursor = null;
        boolean isLeftChild = false;
        Entry<T> rootCursor = root;
        while(rootCursor!=null){
            int cmp = rootCursor.element.compareTo(x);
            if(cmp==0)
                break;
            else if(cmp>0){
                parentOfCursor = rootCursor;
                isLeftChild=true;
                rootCursor=rootCursor.left;
            }else {
                parentOfCursor = rootCursor;
                isLeftChild=false;
                rootCursor = rootCursor.right;
            }
        }
        return  new ParentChildPair<>(parentOfCursor,rootCursor,isLeftChild);
    }

    /**
     *  Removes and returns x if found, otherwise returns null
     */
    public T remove(T x) {
        if(x==null){
            return null;
        }
        ParentChildPair<T> pair = getEntryWithParent(x);
        if(pair.child==null){
            return null;
        }
        Entry<T> entryX = pair.child;
        if(entryX.left==null||entryX.right==null){
            //only one child bypass this element
            if(root.equals(entryX)){
                root = entryX.left==null?entryX.right:entryX.left;
            }else {
                bypass(pair);
            }
        }else {
            Entry<T> maxLeftChild = max(entryX.left);
            bypass(getEntryWithParent(maxLeftChild.element));
            maxLeftChild.right = pair.child.right;
            maxLeftChild.left = pair.child.left;
            if(pair.parent==null){
                //current is root element
                root = maxLeftChild;
            }else {
                if(pair.isLeftChild){
                    pair.parent.left=maxLeftChild;
                }else {
                    pair.parent.right=maxLeftChild;
                }
            }
        }
        size--;
        return entryX.element;
    }

    /**
     * Given a pair of parent and child this function establishes a link between parent and grandchild bypassing the current element.
     * The given child should have only one children of its own.
     * @param parentChildPair element to be bypassed and its parent as a pair.
     * @throws IllegalArgumentException when the element to be bypassed has two children
     */
    private void bypass(ParentChildPair<T> parentChildPair){
        if(parentChildPair.child.left!=null && parentChildPair.child.right!=null){
            throw new IllegalArgumentException("Wrong pair passed to the bypass method, Element doesn't have only one child");
        }
        Entry<T> child = parentChildPair.child;
        Entry<T> grandChild = child.left==null?child.right:child.left;
        if(parentChildPair.isLeftChild){
            parentChildPair.parent.left = grandChild;
        }else {
            parentChildPair.parent.right = grandChild;
        }
    }

    /**
     * @return the min element in the tree or null if tree is empty
     */
    public T min() {
        if(root==null){
            return null;
        }
        Entry<T> leftMostEntry = root;
        while(leftMostEntry.left!=null){
            leftMostEntry=leftMostEntry.left;
        }
        return leftMostEntry.element;
    }

    /**
     * @return the max element in the tree or null if tree is empty
     */
    public T max() {
        Entry<T> maxEntry = max(root);
        return maxEntry==null?null:maxEntry.element;
    }

    /**
     * returns the min element in the tree with given root or null if root is empty
     * @param root the root of the tree or subtree to be searched in
     */
    private Entry<T> max(Entry<T> root){
        if(root==null){
            return null;
        }
        Entry<T> rightMostEntry = root;
        while(rightMostEntry.right!=null){
            rightMostEntry=rightMostEntry.right;
        }
        return rightMostEntry;
    }

    /**
     * Creates an array with the elements using in-order traversal of tree.
     * the created array will have elements in the ascending order.
     * @return an array with elements in ascending order
     */
    public Comparable[] toArray() {
        if(size==0){
            return null;
        }
        Comparable[] arr = new Comparable[size];
        fillArray(root,arr,0);
        return arr;
    }

    /**
     * Util method for in-order traversing of the tree
     * @param root current root.
     * @param arr array to store the elements in.
     * @param index current insertion index of array
     * @return next location index for insertion
     */
    private int fillArray(Entry<T> root,Comparable arr[],int index){
        if(root==null){
            return index;
        }
        int insertIndex = index;
        //insert left subtree into arr first
        insertIndex = fillArray(root.left,arr,insertIndex);
        arr[insertIndex] = root.element;
        insertIndex = fillArray(root.right,arr,insertIndex+1);
        return insertIndex;
    }


    // Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator < T > iterator() {
        return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

    // End of Optional problem 2

    public static void main(String[] args) {
        BinarySearchTree < Integer > t = new BinarySearchTree < > ();
        Scanner in = new Scanner(System.in);
        while ( in .hasNext()) {
            int x = in .nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry < T > node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
    private class ParentChildPair<E>{
        Entry<E> parent;
        Entry<E> child;
        boolean isLeftChild;
        public ParentChildPair(Entry<E> parent, Entry<E> child, boolean isLeftChild) {
            this.parent = parent;
            this.child = child;
            this.isLeftChild = isLeftChild;
        }
    }
}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/