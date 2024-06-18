package com.HarshTyagi.PurchiApp.Helper;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

public final class DateHelper {

    private static final DateTimeFormatter ISO_FORMATTER = ISODateTimeFormat.dateTimeParser().withOffsetParsed();

    private DateHelper() {

    }

    public static Interval defineInterval(final String startDateString, final String endDateString){
        if(StringUtils.isEmpty(startDateString) || StringUtils.isEmpty(endDateString)){
            throw new IllegalArgumentException("startDate and endDate are mandatory.");
        }
        return new Interval(parseJodaDateTimeOptionalMillis(startDateString),parseJodaDateTimeOptionalMillis(endDateString));
    }

    public static DateTime parseJodaDateTimeOptionalMillis(String text){
        if(text == null){
            return  null;
        }
        try {
            return ISO_FORMATTER.parseDateTime(text);
        }
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Invalid date/time string ( '" + text + "') cannot be parsed");
        }
    }
}
