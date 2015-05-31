package com.telarix.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.telarix.CDRProcessingEngine.CDRProcessingEngineDriver;

public class DLLLoader {


	private	final static Logger logger = Logger.getLogger(DLLLoader.class);

	/**
	 * Gets a dll from the resources folder and copies it to an external temporary directory,
	 * so it can be loaded as a library into the classpath
	 * @param name is the name of the resource dll
	 * @return the file path of the newly copied dll
	 * @throws IOException
	 */
	public String dllToTemp(String name) throws IOException {


		InputStream in = CDRProcessingEngineDriver.class.getResourceAsStream(name);
		byte[] buffer = new byte[1024];
		int read = -1;
		File temp = new File(new File(System.getProperty("java.io.tmpdir")), name);
		FileOutputStream fos = new FileOutputStream(temp);

		while((read = in.read(buffer)) != -1) {
			fos.write(buffer, 0, read);
		}
		fos.close();
		in.close();

		return temp.getAbsolutePath();
	}

	/**
	 * Dynamically loads the sqljdbc_auth.dll from the resources directory in order to utilize the
	 * integrated security option for SQL Server jdbc
	 */
	public  void loadDlls() {


		logger.info("Loading the dll from resources....");
		try {
			File file = new File(dllToTemp("/sqljdbc_auth.dll"));
			String path = System.getProperty("java.library.path");
			System.setProperty("java.library.path", file.getParent() + ';' + path);
			Field sysPath = ClassLoader.class.getDeclaredField("sys_paths");
			sysPath.setAccessible( true );
			sysPath.set( null, null );
			System.loadLibrary("sqljdbc_auth");
		} catch (UnsatisfiedLinkError e) {

			logger.error(e.toString(),(Throwable)e);
			logger.error("Native code library failed to load.\n" + e.toString(),(Throwable)e);
			logger.error("Exiting System");
			System.exit(2);
			//System.err.println(System.getProperty("java.library.path"));
			//System.err.println("Native code library failed to load.\n" + e);
			
		} catch (IllegalAccessException e) {
			
			logger.error(e.toString(),(Throwable)e);
			logger.error("Exiting System");
			
		} catch (NoSuchFieldException e) {
			
			logger.error(e.toString(),(Throwable)e);
			logger.error("Exiting System");
			
		} catch (IOException e) {
			
			logger.error(e.toString(),(Throwable)e);
			logger.error("Exiting System");
		}

		logger.info("Dll Loaded !!");
	}

}
