package dev.gustavoteixeira.filetransformer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

/**
 * @author Glenn Renfro
 */
@ConfigurationProperties("timestamp")
public class TimestampTaskProperties {

    /**
     * The timestamp format, "yyyy-MM-dd HH:mm:ss.SSS" by default.
     */
    private String format = "yyyy-MM-dd HH:mm:ss.SSS";

    public String getFormat() {
        Assert.hasText(format, "format must not be empty nor null");
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}