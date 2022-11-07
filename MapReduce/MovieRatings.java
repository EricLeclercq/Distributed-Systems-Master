import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MovieRatings {

    public static void main(String[] args) throws Exception {
        // on verifie que les chemins des données et resultats sont fournis
        if (args.length != 2) {
            System.err.println("Syntaxe : MovieRatings <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "MovieLens MR");

        job.setJarByClass(MovieRatings.class);
        job.setJobName("moyenne des notes");

        // on connecte les entrée et sorties sur les répertoires passés en paramètre
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // définir la classe qui réalise le map
        job.setMapperClass(MovieRatingMapper.class);
        // definir la classe qui réalise le reduce
        job.setReducerClass(MovieRatingReducer.class);

        // definir le format de la sortie
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(DoubleWritable.class);

        // lancer l'exec
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
