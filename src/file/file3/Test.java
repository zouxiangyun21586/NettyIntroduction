package file.file3;

import java.io.File;
import java.io.FileNotFoundException;

public class Test {
	private static String path = "F:\\√Ê ‘\\mvc";

	private static void isFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				isFile(file2);
			}

		} else {
			System.out.println(file.getPath());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(path);
		isFile(file);
	}
}
