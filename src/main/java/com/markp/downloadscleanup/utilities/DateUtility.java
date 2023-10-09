package com.markp.downloadscleanup.utilities;

import java.time.LocalDate;

public class DateUtility {

    public static String getArchiveFolder() {
        LocalDate today = LocalDate.now();
        String month = today.getMonthValue() + " " + today.getMonth().toString();
        String year = String.valueOf(today.getYear());
        String archiveFolder = "/" + year + "/" + month;
        return archiveFolder;
    }
}
