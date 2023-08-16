import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MovieRatingMapper extends Mapper<LongWritable, Text, IntWritable, DoubleWritable> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // on saute la première ligne
        if (key.get() == 0 && value.toString().contains("userId")) {
            return;
        }

        String line = value.toString();
        String[] attributes = line.split(",");

        if (attributes.length >= 2) {
            // ligne du fichier rating : [userId, movieId, rating, timestamp]
            int movieId = Integer.parseInt(attributes[1]);
            double rating = Double.parseDouble(attributes[2]);

            // Convert to Hadoop types
            IntWritable mapKey = new IntWritable(movieId);
            DoubleWritable mapValue = new DoubleWritable(rating);

            // sortie de couples k,v
            context.write(mapKey, mapValue);
        }
    }
}
