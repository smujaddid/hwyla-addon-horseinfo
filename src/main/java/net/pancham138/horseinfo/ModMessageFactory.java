package net.pancham138.horseinfo;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

public class ModMessageFactory implements MessageFactory {

    private String prefix;

    public ModMessageFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Message newMessage(String message, Object... params) {
        return ParameterizedMessageFactory.INSTANCE.newMessage(
                "[" + this.prefix + "] " + message,
            params
        );
    }
    
    @Override
    public Message newMessage(String message) {
        return this.newMessage((Object) message);
    }
    
    @Override
    public Message newMessage(Object message) {
        return ParameterizedMessageFactory.INSTANCE.newMessage("[" + this.prefix + "] {}", message);
    }
    
}