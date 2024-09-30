package simpleflink.second;
import simpleflink.dto.InputMessage;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import java.time.ZoneId;
import javax.annotation.Nullable;


public class InputMessageTimestampAssigner implements AssignerWithPunctuatedWatermarks<InputMessage> {
 
    @Override
    public long extractTimestamp(InputMessage element, long previousElementTimestamp) {
        ZoneId zoneId = ZoneId.systemDefault();
        return element.getSentAt().atZone(zoneId).toEpochSecond() * 1000;
    }

    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(InputMessage lastElement, long extractedTimestamp) {
        return new Watermark(extractedTimestamp - 1500);
    }
}