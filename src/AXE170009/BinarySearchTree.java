/** @author 
 *  Binary search tree (starter code)
 **/

package AXE170009;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

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


    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        return get(x)!=null;
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
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

    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
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
        Entry<T> newNode = new Entry<>(x,null,null);
        if(cmp<0){
            entryX.right = newNode;
        }else {
            entryX.left = newNode;
        }
        return true;
    }

    /**
     *  returns the entry with value.equals(x) if present or else the parent node under which x should have been present
     * @param x element whose location should be found
     * @return entry with x when found or the entry under which x should be present or null if no elements in the tree
     */
    private Entry<T> findEntry(T x){
        Entry<T> parentOfCursor = null;
        Entry<T> rootCursor = root;
        while(rootCursor!=null){
            int cmp = rootCursor.element.compareTo(x);
            if(cmp==0)
                break;
            else if(cmp>0){
                parentOfCursor = rootCursor;
                rootCursor=rootCursor.left;
            }else {
                parentOfCursor = rootCursor;
                rootCursor = rootCursor.right;
            }
        }
        return rootCursor==null?parentOfCursor:rootCursor;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        if(x==null){
            return null;
        }
        Entry<T> entryX = findEntry(x);
        if(entryX.element.compareTo(x)!=0){
            return null;
        }
        if(entryX.left==null||entryX.right==null){
            //bypass
            ;
        }else {
            //two children
        }

        size--;
        return null;
    }

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

    public T max() {
        return max(root);
    }

    private T max(Entry<T> root){
        if(root==null){
            return null;
        }
        Entry<T> rightMostEntry = root;
        while(rightMostEntry.right!=null){
            rightMostEntry=rightMostEntry.right;
        }
        return rightMostEntry.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        if(size==0){
            return null;
        }
        Comparable[] arr = new Comparable[size];
        fillArray(root,arr,0);
        return arr;
    }

    private int fillArray(Entry<T> root,Comparable arr[],int startIndex){
        if(root==null){
            return startIndex;
        }
        int insertIndex = startIndex;
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