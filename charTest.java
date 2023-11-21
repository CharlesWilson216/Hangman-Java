public class charTest {
	public static void main(String[] args) {
		String test = "This-Is_A_Test_String";
		System.out.println(test);
		char[] charTest = test.toCharArray();
		for (int i = 0; i < charTest.length; ++i) {
			++charTest[i];
			System.out.print(charTest[i]);
		}
		System.out.println();
		test = String.valueOf(charTest);
		System.out.println(test);
	}
}
