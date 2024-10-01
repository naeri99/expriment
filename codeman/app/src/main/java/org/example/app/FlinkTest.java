/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example.app;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


class FlinkTest {
    public void testFlink()  throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> text = env.fromElements(
            "Apache Flink is a stream processing framework",
            "Flink can also perform batch processing",
            "Flink supports stateful computations over data streams"
        );

        DataStream<Tuple2<String, Integer>> wordCounts = text
            .flatMap(new Tokenizer())              // Split input text into words
            .keyBy(value -> value.f0)              // Group by the word (Tuple2.f0)
            .sum(1);   


         wordCounts.print();

        // Execute the Flink job
        env.execute("Word Count Example");
    }



    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // Split the sentence into words
            String[] tokens = value.toLowerCase().split("\\W+");

            // Emit each word with a count of 1
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
