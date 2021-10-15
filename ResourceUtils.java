/*
 * Kal Young
 * 10/10/21
 * Object Oriented Software Development
 * Project 2: GUI Project w/ TweetCollection
 */

package project2;

// My system was having some errors with finding the files for some reason when going to select
// which one to choose, so I had a tutor help guide me on making a class to help find the files

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ResourceUtils {

	public static void copyToDisk(String path, boolean ifNotExist) throws IOException {
		// get URL to resource
		URL pathURL = ResourceUtils.class.getResource(path);
		if(pathURL == null) {
			throw new IOException("path does not exist in classpath / JAR");
		}
		
		// determine filepath on disk
		String filepath = "./" + path;
		if(path.startsWith("/")) {
			filepath = "." + path;
		}
		
		// create directory if necessary
		File file = new File(filepath);
		File parentDir = file.getParentFile();
		if(parentDir != null && !parentDir.exists()) {
			parentDir.mkdirs();
		}
		
		if(ifNotExist && file.exists()) {
			return; // file already exists
		}
		
		// copy file from classpath to destination
		OutputStream out = new FileOutputStream(file);
		InputStream in = pathURL.openStream();
		byte[] chunk = new byte[1024];
		int bytesRead = 0;
		while((bytesRead = in.read(chunk)) != -1) {
			out.write(chunk, 0, bytesRead);
		}
		out.close();
	}
	
}
