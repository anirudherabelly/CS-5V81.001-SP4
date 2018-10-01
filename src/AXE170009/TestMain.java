package AXE170009;
/**
 * This is a driver class that essentially runs tests (like a JUnit test class).
 * Motivation to write such a class is to eliminate adding JUnit or TestNG dependency.
 *
 * To add a new test case,
 * 1.Create a test function.
 * 2.Call the test function from main.
 * @author Kautil
 * @author Anirudh
 */
public class TestMain {
    private BinarySearchTree<Integer> leftOnlyTree;
    private BinarySearchTree<Integer> rightOnlyTree;
    private BinarySearchTree<Integer> imbalancedTree;
    private BinarySearchTree<Integer> singleElementTree;
    private BinarySearchTree<Integer> twoElementTree;

    public TestMain(){
        leftOnlyTree = new BinarySearchTree<>();
        rightOnlyTree = new BinarySearchTree<>();
        imbalancedTree = new BinarySearchTree<>();
        singleElementTree = new BinarySearchTree<>();
        twoElementTree = new BinarySearchTree<>();
    }

	/**
	 * the Main here runs the individual test functions.
	 * @param args this argument is added just to maintain consistency with java spec.
	 */
	public static void main(String[] args) {
		try {
			assert false;
			System.out.println("Please use the -ea jvm-option. Ex: java -ea AXE170009.TestMain");
			System.exit(0);
		}catch (AssertionError error){
			System.out.println("-ea option enabled good to go");
		}
		TestMain tester = new TestMain();
        tester.testAdd();
        tester.testToArray();
        tester.testMin();
        tester.testMax();
        tester.testContains();
        tester.testRemove();
        System.out.println("All Tests passed");
	}

    private void testAdd() {
        //insert into leftOnlyTree
        for(int i=1;i<=10;++i){
            assert rightOnlyTree.add(i);
        }
        assert !rightOnlyTree.add(1);
        rightOnlyTree.printTree();
        assert rightOnlyTree.size == 10;

        //insert into rightOnlyTree
        for(int i=10;i>=1;--i){
            assert leftOnlyTree.add(i);
        }
        assert !leftOnlyTree.add(1);
        leftOnlyTree.printTree();
        assert leftOnlyTree.size == 10;

        assert imbalancedTree.add(5);
        assert imbalancedTree.add(3);
        assert imbalancedTree.add(4);
        assert imbalancedTree.add(1);
        assert imbalancedTree.add(2);
        assert imbalancedTree.add(-4);
        assert imbalancedTree.add(7);
        assert imbalancedTree.add(6);
        assert imbalancedTree.add(8);
        assert !imbalancedTree.add(1);
        imbalancedTree.printTree();
        assert imbalancedTree.size == 9;

        assert singleElementTree.add(3);
        assert singleElementTree.size == 1;
        assert twoElementTree.add(3);
        assert twoElementTree.size == 1;
        assert twoElementTree.add(1);
        assert twoElementTree.size == 2;
    }

    private void testToArray() {
        Comparable arr[] = leftOnlyTree.toArray();
        int expectedI = 1;
        for (Comparable i:arr){
            assert expectedI == (Integer) i;
            expectedI++;
        }
        arr = rightOnlyTree.toArray();
        expectedI = 1;
        for (Comparable i:arr){
            assert expectedI == (Integer) i;
            expectedI++;
        }
        arr = imbalancedTree.toArray();
        expectedI = -4;
        assert expectedI == (Integer) arr[0];
        for(int i=1;i<=8;++i){
            assert i==(Integer) arr[i];
        }

        arr = singleElementTree.toArray();
        assert arr.length == singleElementTree.size;
        assert (Integer) arr[0] == 3;

        arr = twoElementTree.toArray();
        assert arr.length == twoElementTree.size;
        assert (Integer) arr[0] == 1;
        assert (Integer) arr[1] == 3;
	}

    private void testMax() {
        int max = leftOnlyTree.max();
        assert max == 10;
        max = rightOnlyTree.max();
        assert max == 10;
        max = imbalancedTree.max();
        assert max == 8;
        max = singleElementTree.max();
        assert max == 3;
        max = twoElementTree.max();
        assert max == 3;
    }

    private void testMin() {
        int min = leftOnlyTree.min();
        assert min == 1;
        min = rightOnlyTree.min();
        assert min == 1;
        min = imbalancedTree.min();
        assert min == -4;
        min = singleElementTree.min();
        assert min == 3;
        min = twoElementTree.min();
        assert min == 1;
    }

    private void testContains() {
        assert leftOnlyTree.contains(1);
        assert rightOnlyTree.contains(10);

        assert !imbalancedTree.contains(10);
        assert !imbalancedTree.contains(-5);
        assert !imbalancedTree.contains(-3);
        assert imbalancedTree.contains(-4);
        for(int i=1;i<=8;++i){
            assert imbalancedTree.contains(i);
        }

        assert !twoElementTree.contains(10);
        assert twoElementTree.contains(3);
        assert twoElementTree.contains(1);

        assert !singleElementTree.contains(1);
        assert singleElementTree.contains(3);
        assert !singleElementTree.contains(4);
    }

    private void testRemove() {
        assert singleElementTree.remove(3)==3;
        assert singleElementTree.root==null;
        assert singleElementTree.size == 0;
        assert singleElementTree.remove(2)==null;

        assert twoElementTree.remove(2)==null;
        assert twoElementTree.remove(3)==3;
        assert twoElementTree.root.element==1;
        assert twoElementTree.size == 1;
        assert twoElementTree.remove(1)==1;
        assert twoElementTree.root==null;
        assert twoElementTree.size==0;

        assert leftOnlyTree.remove(9)==9;
        assert leftOnlyTree.root.element==10;
        assert leftOnlyTree.root.left.element==8;

        assert rightOnlyTree.remove(2)==2;
        assert rightOnlyTree.root.element==1;
        assert rightOnlyTree.root.right.element==3;

        assert imbalancedTree.remove(-1) == null;	//entry not present
        assert imbalancedTree.remove(-4) == -4;		//entry with no children
        assert imbalancedTree.root.left.left.left == null;
        assert imbalancedTree.remove(7) == 7;		//entry with two children
        assert imbalancedTree.root.right.element == 6;
        assert imbalancedTree.remove(5)==5;
        assert imbalancedTree.root.element == 4;        //remove root
        assert imbalancedTree.remove(6) == 6;		//entry with one children
        assert imbalancedTree.root.right.element == 8;
        assert imbalancedTree.size == 5;
    }

    /**
	 * Runs the given function inside a try-catch block to capture and assert that given exception is raised
	 * @param function function that needs to be run inside the try block
	 * @param expectedEx the exception class type the function should throw
	 */
	private void checkException(Runnable function, Class<?> expectedEx){
		try {
			function.run();
			assert false;
		}catch (Exception e){
			assert e.getClass() == expectedEx;
		}
	}
}
