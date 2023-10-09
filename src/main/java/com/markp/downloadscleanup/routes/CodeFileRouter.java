package com.markp.downloadscleanup.routes;

import com.markp.downloadscleanup.utilities.DateUtility;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CodeFileRouter extends RouteBuilder {

    @Value("${source.folder}")
    private String sourceFolder;

    @Value("${code.destination.folder}")
    private String codeDestinationBaseFolder;

    @Value("${code.suffix}")
    private String codeSuffix;

    @Override
    public void configure() throws Exception {
        String archiveFolder = DateUtility.getArchiveFolder();
        String codeDestinationFolder = codeDestinationBaseFolder + archiveFolder;

        from("file://" + sourceFolder + "?include=" + codeSuffix)
                .log("Picked up text $simple{header.CamelFileName} from " + sourceFolder + " will route to " + codeDestinationFolder)
                .to("file://" + codeDestinationFolder);
    }
}
