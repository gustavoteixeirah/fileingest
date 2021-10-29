package dev.gustavoteixeira.filetransformer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Slf4j
public class ResourceUtils {

    public static Resource getResource(final ResourceLoader resourceLoader,
                                       final String bucketName,
                                       final String fileName) {
        final String resourceLocation = String.format("s3://%s/%s", bucketName, fileName);
        final Resource resource = resourceLoader.getResource(resourceLocation);
        log.info("RESOURCE -> {}.", resource);
        return resource;
    }

}
