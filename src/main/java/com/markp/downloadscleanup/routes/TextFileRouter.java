package com.markp.downloadscleanup.routes;

import com.markp.downloadscleanup.utilities.DateUtility;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TextFileRouter extends RouteBuilder {

    @Value("${source.folder}")
    private String sourceFolder;

    @Value("${text.destination.folder}")
    private String textDestinationBaseFolder;

    @Value("${text.suffix}")
    private String textSuffix;

    @Override
    public void configure() throws Exception {
        String archiveFolder = DateUtility.getArchiveFolder();
        String textDestinationFolder = textDestinationBaseFolder + archiveFolder;

        from("file://" + sourceFolder + "?include=" + textSuffix)
                .log("Picked up text $simple{header.CamelFileName} from " + sourceFolder + " will route to " + textDestinationFolder)
                .to("file://" + textDestinationFolder);
    }
}
