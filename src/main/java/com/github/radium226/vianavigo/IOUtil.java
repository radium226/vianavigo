package com.github.radium226.vianavigo;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

	final public static void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				
			}
		}
	}
	
}
