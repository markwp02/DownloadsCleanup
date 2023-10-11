package com.markp.downloadscleanup.utilities;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Utility class for low level code used by FileRouter
 */
public class FileUtility {

    /**
     * Generates the directory for archiving based on the current year and month
     * @return archive location
     */
    public static String getArchiveFolder() {
        LocalDate today = LocalDate.now();
        String month = today.getMonthValue() + " " + today.getMonth().toString();
        String year = String.valueOf(today.getYear());
        String archiveFolder = "/" + year + "/" + month;
        return archiveFolder;
    }

    /**
     * Combines the supplied base folder, sub and archive folders. Sub and archive folders optionally
     * can have '/' chars, as it will work either way.
     * @param baseFolder
     * @parm subFolderFolder
     * @param archiveFolder
     * @return
     */
    public static String getDestinationFolder(String baseFolder, String subFolder, String archiveFolder) {
        return baseFolder + "/" + subFolder + "/" + archiveFolder;
    }

    /**
     * Method that will concat all regexes in the supplied list separated with the '|' char
     *
     * @param regexList ArrayList of valid regex in the format (.*.xxx$) where "xxx" can be
     *                  replaced with the file suffix
     * @return String of combined regexes
     */
    public static String combineRegex(ArrayList<String> regexList) {
        String combinedRegex = "";

        for (String regex: regexList) {
            combinedRegex += regex + "|";
        }
        return combinedRegex;
    }
}
