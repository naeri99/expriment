package com.fastcampus.streaming.flinkcourse.chapter4.valuestate;

import com.fastcampus.streaming.flinkcourse.chapter4.valuestate.function.CumulativeVolumeKeyedProcessFunction;
import com.fastcampus.streaming.flinkcourse.kafka.config.KafkaProperties;
import com.fastcampus.streaming.flinkcourse.kafka.config.KafkaSourceSink;
import com.fastcampus.streaming.flinkcourse.model.stock.Stock;
import com.fastcampus.streaming.flinkcourse.model.stock.StockTransaction;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;
import java.util.Properties;

public class ValueStateExercise {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new KafkaProperties()
                .setSourceTopic("stock-transactions")
                .setGroupId("value-state-exercise")
                .build();

        KafkaSource<StockTransaction> kafkaSource = KafkaSourceSink.createSource(properties, StockTransaction.class);

        DataStream<StockTransaction> stockTransactions = env.fromSource(
                kafkaSource,
                WatermarkStrategy.<StockTransaction>forBoundedOutOfOrderness(Duration.ofSeconds(1))
                        .withTimestampAssigner((event, timestamp) -> event.getTimestamp())
                        .withIdleness(Duration.ofMillis(500)),
                "stock-transaction-kafka-source");

        DataStream<Tuple2<String, Long>> cumulativeVolumes = stockTransactions
                .keyBy(StockTransaction::getSymbol)
                .process(new CumulativeVolumeKeyedProcessFunction());

        cumulativeVolumes.print();

        env.execute("Cumulative volume computation");
    }
}
