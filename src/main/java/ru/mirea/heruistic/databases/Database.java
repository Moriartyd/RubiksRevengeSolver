package ru.mirea.heruistic.databases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import ru.mirea.heruistic.cube.*;

public abstract class Database extends Generator {
	private String path;
	private byte[] database;
	
	public Database(String path, int size) {
		super();
		
		this.path = path;
		this.database = new byte[size];
	}
	
	public boolean load() {
		try {
			FileInputStream fis = new FileInputStream(path);
			fis.read(database);
			fis.close();
			count = database.length;
			return true;
		}
		catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	public void generate() {
		start();
	}
	
	protected void generate(Twist[] generators, LinkedList<Cube> cubes) {
		generate(database, generators, cubes);
		save();
		stats();
	}
	
	public int getLimit() {
		return database.length;
	}
	
	public float getDistance(Cube cube) {
		return database[index(cube)];
	}
	
	protected void save() {
		if (database == null) return;
		
		try {
			File file = new File(path);
			file.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(database);
			fos.close();
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	protected void stats() {
		if (database == null) return;
		
		int length = database.length;
		int max = 0;
		
		for (int i = 0; i < length; i++) {
			byte value = database[i];
			
			if (max < value) max = value;
		}
		
		int[] histogram = new int[max + 1];
		
		for (int i = 0; i < length; i++) {
			byte value = database[i];
			
			if (value < 0) {
				System.out.println("There is an invalid value in the database.");
				break;
			}
			
			histogram[value]++;
		}
		
		for (int i = 0; i <= max; i++) {
			System.out.println(i + "\t" + histogram[i]);
		}
	}
}