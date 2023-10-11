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

    @Value("${base.destination.folder}")
    private String baseDestinationFolder;

    @Value("${code.sub.folder}")
    private String codeSubFolder;

    @Value("${code.suffix}")
    private String codeSuffix;

    @Value("${pdf.sub.folder}")
    private String pdfSubFolder;

    @Value("${pdf.suffix}")
    private String pdfSuffix;

    @Value("${text.sub.folder}")
    private String textSubFolder;

    @Value("${text.suffix}")
    private String textSuffix;

    @Value("${image.sub.folder}")
    private String imageSubFolder;

    @Value("${image.suffix}")
    private String imageSuffix;

    @Value("${compressed.sub.folder}")
    private String compressedSubFolder;

    @Value("${compressed.suffix}")
    private String compressedSuffix;

    @Value("${app.sub.folder}")
    private String appSubFolder;

    @Value("${app.suffix}")
    private String appSuffix;

    @Override
    public void configure() throws Exception {
        String archiveFolder = FileUtility.getArchiveFolder();
        String codeDestinationFolder = FileUtility.getDestinationFolder(baseDestinationFolder,codeSubFolder ,archiveFolder);
        String pdfDestinationFolder = FileUtility.getDestinationFolder(baseDestinationFolder,pdfSubFolder ,archiveFolder);
        String textDestinationFolder = FileUtility.getDestinationFolder(baseDestinationFolder,textSubFolder ,archiveFolder);
        String imageDestinationFolder = FileUtility.getDestinationFolder(baseDestinationFolder,imageSubFolder ,archiveFolder);
        String compressedDestinationFolder = FileUtility.getDestinationFolder(baseDestinationFolder,compressedSubFolder ,archiveFolder);
        String appDestinationFolder = FileUtility.getDestinationFolder(baseDestinationFolder,appSubFolder ,archiveFolder);

        String sourceSuffix = FileUtility.combineRegex(new ArrayList<>(Arrays.asList(codeSuffix, pdfSuffix, textSuffix, imageSuffix, compressedSuffix, appSuffix)));

        from("file://" + sourceFolder + "?include=" + sourceSuffix)
                .id("FILE_ROUTE")
                .log("Received file: ${header.CamelFileName}")
                .choice()
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(codeSuffix))
                    .setHeader("status", simple("CODE"))
                    .log("Route to code: " + codeDestinationFolder)
                    .to("file://" + codeDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(pdfSuffix))
                    .setHeader("status", simple("PDF"))
                    .log("Route to pdf: " + pdfDestinationFolder)
                    .to("file://" + pdfDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(textSuffix))
                    .setHeader("status", simple("TEXT"))
                    .log("Route to text: " + textDestinationFolder)
                    .to("file://" + textDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(imageSuffix))
                    .setHeader("status", simple("IMAGE"))
                    .log("Route to image: " + imageDestinationFolder)
                    .to("file://" + imageDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(compressedSuffix))
                    .setHeader("status", simple("COMPRESSED"))
                    .log("Route to compressed: " + compressedDestinationFolder)
                    .to("file://" + compressedDestinationFolder)
                .when((exchange) -> exchange.getIn().getHeader("CamelFileName").toString().matches(appSuffix))
                    .setHeader("status", simple("APP"))
                    .log("Route to app: " + appDestinationFolder)
                    .to("file://" + appDestinationFolder)
                .otherwise()
                    .setHeader("status", simple("FAILURE"))
                    .log("Invalid file type for file ${header.CamelFileName}")
                .end();
    }
}
