package org.commoncrawl.tutorial;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.commoncrawl.protocol.shared.ArcFileItem;
import org.jsoup.Jsoup;

public class WordCountMapper extends MapReduceBase 
  implements Mapper<Text, ArcFileItem, Text, IntWritable> {
  
  public void map(Text key, ArcFileItem value,
	      OutputCollector<Text, IntWritable> output, Reporter reporter)
	      throws IOException {
	  // Extract text from attached HTML
	  String page_content = value.getContent().toString();
	  //String page_content = Jsoup.parse(value.getContent().toString()).text();
	  // Remove all punctuation
	  page_content.replaceAll("\\p{Punct}", "");
	  // Normalize whitespace to single spaces
	  page_content.replaceAll("\\t|\\n", " ");
	  page_content.replaceAll("\\s+", " ");
	  // Split by space and output to OutputCollector
	  for (String word: page_content.split(" ")) {
		  output.collect(new Text(word), new IntWritable(1));
	  }
  }
}
