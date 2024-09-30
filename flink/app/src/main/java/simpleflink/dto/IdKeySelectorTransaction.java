package simpleflink.dto;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.functions.KeySelector;


public class IdKeySelectorTransaction implements KeySelector<Tuple2<Integer, String>, Integer> {
    @Override
    public Integer getKey(Tuple2<Integer, String> value) {
        return value.f0;
    }
}