package simpleflink.dto;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.functions.KeySelector;

public class IdKeySelectorAddress implements KeySelector<Tuple3<Integer, String, String>, Integer> {
    @Override
    public Integer getKey(Tuple3<Integer, String, String> value) {
        return value.f0;
    }
  }