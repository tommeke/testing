package com.bigstick.nlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class HMMGene {
	public static void main( String[] args) throws Exception {
		HMMGene gene = new HMMGene();
	}
	
	private Hashtable<String,Hashtable<String,Integer>> emissionCountTable = new Hashtable<String,Hashtable<String,Integer>>();
	private Hashtable<String,Integer> yTable = new Hashtable<String,Integer>();
	private Hashtable<String,Integer> xTable = new Hashtable<String,Integer>();
	
	public HMMGene() throws Exception {
		parseFrequencies( "/home/tom/coursera/nlp/week1/gene.counts");
		//generateRareTraining( "/home/tom/coursera/nlp/week1/gene.train", "/home/tom/coursera/nlp/week1/gene.train.rare");
		simpleTagger( "/home/tom/coursera/nlp/week1/gene.dev", "/home/tom/coursera/nlp/week1/gene_dev.p1.out");
	}
	
	private void simpleTagger( String input, String output) throws Exception {
		BufferedWriter writer = new BufferedWriter( new FileWriter( output));
		BufferedReader reader = new BufferedReader(new FileReader( input));
		String line = null;
		while ( (line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				writer.newLine();
				continue;
			}
			String x = "_RARE_";
			if (xTable.contains(line.trim()))
				x = line.trim();
			double max = 0;
			String tag = null;
			for ( String y : yTable.keySet()) {
				double p = ((double) emissionCountTable.get(x).get(y))/((double) yTable.get(y));
				if (p>max) {
					max = p;
					tag = y;
				}
			}
			writer.write( line + " " + tag);
			writer.newLine();
		}
		reader.close();
		writer.flush();
		writer.close();
	}
	
	private void generateRareTraining( String input, String output) throws Exception {
		BufferedWriter writer = new BufferedWriter( new FileWriter( output));
		BufferedReader reader = new BufferedReader(new FileReader( input));
		String line = null;
		StringTokenizer tokenizer = null;
		while ( (line = reader.readLine()) != null) {
			if (line.isEmpty()) {
				writer.newLine();
				continue;
			}
			tokenizer = new StringTokenizer(line);
			String word = tokenizer.nextToken();
			String tag = tokenizer.nextToken();
			if (xTable.get(word) < 5)
				word = "_RARE_";
			writer.write( word + " " + tag);
			writer.newLine();
		}
		reader.close();
		writer.flush();
		writer.close();
	}
	
	private void parseFrequencies( String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
		StringTokenizer tokenizer = null;
		while ( (line = reader.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			String token = tokenizer.nextToken();
			int count = Integer.parseInt(token);
			token = tokenizer.nextToken();
			if (token.equalsIgnoreCase("WORDTAG")) {
				String y = tokenizer.nextToken();
				String x = tokenizer.nextToken();
				Hashtable<String,Integer> table = emissionCountTable.get( x);
				if (table == null) {
					table = new Hashtable<String,Integer>();
					emissionCountTable.put(x, table);
				}
				table.put( y, count);
				//Y
				Integer yCount = yTable.get(y);
				if (yCount == null)
					yTable.put( y, count);
				else
					yTable.put(y, yCount+count);
				//X
				Integer xCount = xTable.get(x);
				if (xCount == null)
					xTable.put( x, count);
				else
					xTable.put(x, xCount+count);
			}
		}					
		
		reader.close();
	}
}
