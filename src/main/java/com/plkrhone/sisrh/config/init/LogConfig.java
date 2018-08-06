package com.plkrhone.sisrh.config.init;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LogConfig {
    long time = -30;

    private static LogConfig instance;

    public static LogConfig getInstance() {
        if (instance == null) instance = new LogConfig();
        return instance;
    }

    private LogConfig() {
        LocalDate dateTime = LocalDate.now();
        LocalDate dateIn = dateTime.plusDays(time);

        File log = new File(System.getProperty("user.dir") + "/log");
        if (log.exists()) {
            File[] files = log.listFiles();
            for (File f : files) {
                LocalDate date = Instant.ofEpochMilli(f.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate();
                if (dateIn.isAfter(date))
                    f.delete();
            }
        } else
            log.mkdir();
   }
}
