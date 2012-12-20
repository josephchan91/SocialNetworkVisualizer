package edu.upenn.mkse212;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.upenn.mkse212.IKeyValueStorage;
import edu.upenn.mkse212.KeyValueStoreFactory;

public class ParseInputGraph {
	
  public static void main(String[] args) throws IOException {
    IKeyValueStorage storageSystem = KeyValueStoreFactory.getKeyValueStore(KeyValueStoreFactory.STORETYPE.BERKELEY, 
		    "socialGraph", "/home/mkse212/bdb/", "user", "authKey", false);

    File here = new File(".");

    int count = 0;

    for ( String fname : here.list()) {
      if (fname.startsWith("livejournal-links")) {
    	  System.out.println("Parsing " + fname + "...");
    	  BufferedReader br = new BufferedReader(new FileReader(fname));
    	  // Store each edge as a kv pair in the kvs
    	  String line;
    	  while ((line = br.readLine()) != null) {
    		  String[] vertices = line.split("\t");
    		  storageSystem.put(vertices[0], vertices[1]);
    		  count++;
    	  }    		
      }
    }

    System.out.println("\nLoaded " + count + " edges");

    storageSystem.close();
  }
}
