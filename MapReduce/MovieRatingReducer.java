import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class MovieRatingReducer extends Reducer<IntWritable, DoubleWritable, IntWritable, DoubleWritable> {
    @Override
    public void reduce(IntWritable key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException{

        double avgRating = 0;
        int numValues = 0;

        // Add up all the ratings
        for (DoubleWritable value: values) {
            avgRating += value.get();
            numValues++;
        }

        avgRating /= numValues;
        DoubleWritable average = new DoubleWritable(avgRating);

        // Output the average rating for this movie
        context.write(key, average);
    }
}
