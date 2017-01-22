package traffic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class calculation2 {
	public static long exectime;
	static long startTime = System.currentTimeMillis();
	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			System.out.println("Line == : "+line);
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				word.set(tokenizer.nextToken());
				output.collect(word, one);
			}
		}
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text key, Iterator<IntWritable> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			output.collect(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(calculation2.class);
		conf.setJobName("wordcount");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);

		conf.setMapperClass(Map.class);
		/*conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);*/

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path("/hadoop/mapred/system/JOB2.txt"));		
		
		FileOutputFormat.setOutputPath(conf, new Path("/hadoop/mapred/system/outjob2.txt"));
		FileSystem fs=FileSystem.get(conf);
		if(fs.exists(new Path("/hadoop/mapred/system/JOB2.txt")))
			fs.delete(new Path("/hadoop/mapred/system/outjob2.txt"));
		
		
		JobClient.runJob(conf);
		 exectime=System.currentTimeMillis() - startTime;
		System.out.println("Completed in "+(exectime));
		/*BufferedWriter bw=new BufferedWriter(new FileWriter("./DTG.txt",true));
		bw.write(String.valueOf(exectime));
		bw.newLine();
		bw.close();*/
		/*System.out.println("==============Cluster Statuc =================");
		System.out.println(new JobClient().getClusterStatus());*/
		
	}
}
