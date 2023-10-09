package com.markp.downloadscleanup.routes;

import com.markp.downloadscleanup.utilities.DateUtility;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PdfFileRouter extends RouteBuilder {

    @Value("${source.folder}")
    private String sourceFolder;

    @Value("${pdf.destination.folder}")
    private String pdfDestinationBaseFolder;

    @Value("${pdf.suffix}")
    private String pdfSuffix;

    @Override
    public void configure() throws Exception {
        String archiveFolder = DateUtility.getArchiveFolder();
        String pdfDestinationFolder = pdfDestinationBaseFolder + archiveFolder;

        from("file://" + sourceFolder + "?include=" + pdfSuffix)
                .log("Picked up pdf $simple{header.CamelFileName} from " + sourceFolder + " will route to " + pdfDestinationFolder)
                .to("file://" + pdfDestinationFolder);
    }
}
