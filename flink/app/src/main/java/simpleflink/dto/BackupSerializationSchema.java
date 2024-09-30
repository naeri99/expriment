package simpleflink.dto;
import simpleflink.dto.Backup;
import org.apache.flink.api.common.serialization.SerializationSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BackupSerializationSchema implements SerializationSchema<Backup> {

    ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(BackupSerializationSchema.class);

    @Override
    public byte[] serialize(Backup backupMessage) {
        if(objectMapper == null) {
            objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }
        try {
            return objectMapper.writeValueAsString(backupMessage).getBytes();
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Failed to parse JSON", e);
        }
        return new byte[0];
    }
}