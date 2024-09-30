package simpleflink.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import simpleflink.dto.InputMessage;
import java.util.UUID;
import java.time.LocalDateTime;


public class Backup {
    @JsonProperty("inputMessages")
    List<InputMessage> inputMessages;
    @JsonProperty("backupTimestamp")
    LocalDateTime backupTimestamp;
    @JsonProperty("uuid")
    UUID uuid;

    public Backup(List<InputMessage> inputMessages, LocalDateTime backupTimestamp) {
        this.inputMessages = inputMessages;
        this.backupTimestamp = backupTimestamp;
        this.uuid = UUID.randomUUID();
    }
}