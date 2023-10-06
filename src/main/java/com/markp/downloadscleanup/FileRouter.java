package com.markp.downloadscleanup;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouter extends RouteBuilder {

    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER = "src/test/destination-folder";

    @Override
    public void configure() throws Exception {
        from("file://" + SOURCE_FOLDER).to("file://" + DESTINATION_FOLDER);
    }
}
