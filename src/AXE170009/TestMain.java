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
		tester.testToArray();
		System.out.println("All Tests passed");
	}

    private void testToArray() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(5);
        bst.add(3);
        bst.add(4);
        bst.add(1);
        bst.add(2);
        bst.add(7);
        bst.add(6);
        bst.add(8);
        Comparable arr[] = bst.toArray();
        for (Comparable i:arr){
            System.out.println(i);
        }
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
