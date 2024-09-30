package simpleflink.dto;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class InputMessage {
    public String sender;
    public String recipient;
    public LocalDateTime sentAt;
    public String message;


    public LocalDateTime getSentAt(){
        return this.sentAt;
    }
    public String getMessage(){
        return this.message;
    }    
    
    public String getRecipient(){
        return this.recipient;
    }    
    
    public String getSender(){
        return this.sender;
    }
}