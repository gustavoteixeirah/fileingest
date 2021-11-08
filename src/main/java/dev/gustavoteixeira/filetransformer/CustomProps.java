package dev.gustavoteixeira.filetransformer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mycustomconfig")
public class CustomProps {
    /**
     * The Spring Cloud Data Flow platform to use for launching tasks.
     */
    private String whoami = "default";

    public String getWhoami() {
        return whoami;
    }

    public void setWhoami(String whoami) {
        this.whoami = whoami;
    }
}
