package net.pancham138.horseinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModLog {

    private static final ModMessageFactory messageFactory = new ModMessageFactory(HorseInfo.MOD_NAME);

    public static Logger getLogger(final String name) {
        return LogManager.getLogger(name, messageFactory);
    }

    public static Logger getLogger(final Class<?> clazz) {
        return LogManager.getLogger(clazz, messageFactory);
    }

    public static Logger getLogger() {
        return LogManager.getLogger(getClassName(2), messageFactory);
    }

    public static Logger getLogger(final Object value) {
        return LogManager.getLogger(value != null ? value.getClass() : getClassName(2),
                messageFactory);
    }

    private static String getClassName(final int depth) {
        return new Throwable().getStackTrace()[depth].getClassName();
    }

}