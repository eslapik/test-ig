import java.util.logging.LogManager;

public abstract class Example {
    static {
        try {
            LogManager.getLogManager().readConfiguration(Example.class.getResourceAsStream("/logging.properties"));
        } catch (Exception e) {
            throw new IllegalStateException("Could not read in logging configuration", e);
        }
    }
}