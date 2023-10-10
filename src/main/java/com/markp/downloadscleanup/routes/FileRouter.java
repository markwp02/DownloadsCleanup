package com.markp.downloadscleanup.routes;

import com.markp.downloadscleanup.utilities.FileUtility;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class FileRouter extends RouteBuilder {

    @Value("${source.folder}")
    private String sourceFolder;

    @Value("${code.destination.folder}")
    private String codeDestinationBaseFolder;

    @Value("${code.suffix}")
    private String codeSuffix;

    @Value("${pdf.destination.folder}")
    private String pdfDestinationBaseFolder;

    @Value("${pdf.suffix}")
    private String pdfSuffix;

    @Value("${text.destination.folder}")
    private String textDestinationBaseFolder;

    @Value("${text.suffix}")
    private String textSuffix;

    @Override
    public void configure() throws Exception {
        String archiveFolder = FileUtility.getArchiveFolder();
        String codeDestinationFolder = FileUtility.getDestinationFolder(codeDestinationBaseFolder, archiveFolder);
        String pdfDestinationFolder = FileUtility.getDestinationFolder(pdfDestinationBaseFolder, archiveFolder);
        String textDestinationFolder = FileUtility.getDestinationFolder(textDestinationBaseFolder, archiveFolder);

        String sourceSuffix = FileUtility.combineRegex(new ArrayList<>(Arrays.asList(codeSuffix, pdfSuffix, textSuffix)));

        from("file://" + sourceFolder + "?include=" + sourceSuffix)
                .id("FILE_ROUTE")
                .log("Received file: ${header.CamelFileName}")
                .choice()
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(codeSuffix))
                    .setHeader("status", simple("CODE"))
                    .log("Route to code: " + codeDestinationFolder)
                    .to("file://" + codeDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().endsWith("pdf"))
                    .setHeader("status", simple("PDF"))
                    .log("Route to pdf: " + pdfDestinationFolder)
                    .to("file://" + pdfDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().endsWith("txt"))
                    .setHeader("status", simple("TEXT"))
                    .log("Route to text: " + textDestinationFolder)
                    .to("file://" + textDestinationFolder)
                .otherwise()
                    .setHeader("status", simple("FAILURE"))
                    .log("Invalid file type for file ${header.CamelFileName}")
                .end();
    }
}
