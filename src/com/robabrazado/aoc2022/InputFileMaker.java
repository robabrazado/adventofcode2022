package com.robabrazado.aoc2022;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class InputFileMaker {

	public static void main(String[] args) {
		InputFileMaker ifm = new InputFileMaker();
		
		try {
			ifm.initializeInputFiles();
		} catch (IOException ioe) {
			System.err.println("Unable to initialize new input files");
			System.err.println(ioe);
		}

	}
	
	public InputFileMaker() {
		return;
	}
	
	public void initializeInputFiles() throws IOException {
		// Find the latest day with input files
		String prefix = "aoc2022-day";
		File cacheDir = new File(InputGetter.CACHE_FOLDER_PATH);
		File[] cacheFiles = cacheDir.listFiles((File dir, String name) -> name.startsWith(prefix));
		Arrays.sort(cacheFiles);
		String lastFile = cacheFiles[cacheFiles.length - 1].getName();
		int today = Integer.parseInt(lastFile.substring(prefix.length(), prefix.length() + 2)) + 1;
		this.initializeInputFiles(today);
		return;
	}
	
	public void initializeInputFiles(int dayNum) throws IOException {
		initializeInputFile(InputGetter.getFilename(dayNum, 1));
		initializeInputFile(InputGetter.getFilename(dayNum));
		return;
	}
	
	private void initializeInputFile(String name) throws IOException {
		File fl = new File(InputGetter.CACHE_FOLDER_PATH, name);
		if (fl.createNewFile()) {
			System.out.println("Created " + fl.getName());
		} else {
			System.out.println(fl.getName() + " already exists");
		}
		return;
	}
	
}
