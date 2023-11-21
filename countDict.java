import java.io.*;
import java.util.*;

public class countDict {
	public static void main(String[] args) throws FileNotFoundException {
		int web2Count = 0;
		int web2aCount = 0;

		Scanner web2 = new Scanner(new File("dict/web2"));
		while (web2.hasNextLine()) {
			++web2Count;
			web2.nextLine();
		}
		Scanner web2a = new Scanner(new File("dict/web2a"));
		while (web2a.hasNextLine()) {
			++web2aCount;
			web2a.nextLine();
		}
		System.out.println("web2 has " + web2Count + " lines");
		System.out.println("web2a has " + web2aCount + " lines");
	}
}
