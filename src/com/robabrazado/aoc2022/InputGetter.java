package com.robabrazado.aoc2022;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class InputGetter {
	public static final String CACHE_FOLDER_PATH = "C:\\Users\\rob\\Documents\\Eclipse Projects\\Advent of Code 2022\\inputs_cache";
	
	public static String[] getInputLines(int dayNum) {
		return getInputLines(dayNum, 0);
	}
	
	public static String[] getInputLines(int dayNum, int testNum) {
		if (dayNum < 1 || dayNum > 25) {
			throw new IllegalArgumentException("Day number must be between 1 and 25");
		}
		
		if (testNum < 0) {
			throw new IllegalArgumentException("Test number must be 0 or greater");
		}
		
		File cacheDir = new File(CACHE_FOLDER_PATH);
		String cacheFilename = getFilename(dayNum, testNum);
		File cacheFile = new File(cacheDir, cacheFilename);
		
		if (!cacheFile.exists()) {
			throw new RuntimeException("No such file: " + cacheFilename);
		}
		
		List<String> retVal = new ArrayList<String>();
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(cacheFile));
			String line = null;
			while ((line = inputReader.readLine()) != null) {
				retVal.add(line);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (inputReader != null) {
				try {
					inputReader.close();
				} catch (IOException ignore) {}
			}
		}
		
		return retVal.toArray(new String[retVal.size()]);
	}
	
	public static String getFilename(int dayNum) {
		return getFilename(dayNum, 0);
	}
	
	public static String getFilename(int dayNum, int testNum) {
		return String.format("aoc2022-day%02d", dayNum) +
			((testNum == 0) ? "-input" : "-test" + String.valueOf(testNum)) +
			".txt";
	}
	
}
