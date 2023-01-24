package no.eg.nexstephub.client.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {
    protected static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
    public static final TimeZone timeZone_oslo = TimeZone.getTimeZone("Europe/Oslo");
    public static final ZoneId zoneId_oslo = ZoneId.of("Europe/Oslo"); // ECT
    public static final Locale norway = new Locale("nb_NO");

    public static final DateFormat dateFormat_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat dateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat dateTimeFormat_yyyy_MM_dd_HH_mm_ss_colon_separator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", norway);
    public static final DateFormat dateTimeFormat_yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss", norway);
    public static final DateFormat nexstepDateFormat = new SimpleDateFormat("yyyy-MM-dd", norway);
    public static final DateFormat nexstepTimeFormat = new SimpleDateFormat("HH:mm:ss", norway);
    public static final DateFormat nexstepDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", norway);
    public static final DateFormat dateFormat_dd_MM_yyyy = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
    public static final DateFormat timeFormat_HH_MM = new SimpleDateFormat("HH:mm", Locale.ROOT);
    public static final DateFormat dateFormat_yyyy = new SimpleDateFormat("yyyy");
    public static final DateFormat dateFormat_ddMMyy = new SimpleDateFormat("ddMMyy");
    public static final DateFormat dateFormat_dd_MM_yy = new SimpleDateFormat("dd.MM.yy");
    public static final DateFormat dateFormat_yyMM = new SimpleDateFormat("yyMM");

    public static final DateTimeFormatter dateformatter_ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter timeformatter_ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME;
    public static final DateTimeFormatter dateformatter_ISO_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", norway);
    public static final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_NO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss", norway);
    public static final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_with_period = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final DateTimeFormatter dateTimeFormatter_HH_mm_ss = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter dateTimeFormatter_dd_MM_yyyy_HH_mm = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", norway);
    public static final DateTimeFormatter hourTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", norway);
    public static final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_query = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    public static final DateTimeFormatter dateTimeFormatter_yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static ZonedDateTime get(Date inputDate, Time inputTime) {
        ZonedDateTime now = ZonedDateTime.of(LocalDateTime.now(), zoneId_oslo);

        try {
            if (inputDate != null && inputTime != null) {
                Date date = createDateWithTime(inputDate, inputTime);
                LocalDateTime localChangedDateTime = date.toInstant().atZone(zoneId_oslo).toLocalDateTime();
                ZonedDateTime zonedChangedDateTime = ZonedDateTime.of(localChangedDateTime, zoneId_oslo);
                return zonedChangedDateTime;
            } else {
                return now;
            }
        } catch (Exception e) {
            logger.error("Failed to parse date. Using current date and time.", e);
            return now;
        }
    }

    public static LocalDateTime fromInstant(Instant instant) {
        if (instant == null) return null;
        return instant.atZone(zoneId_oslo).toLocalDateTime();
    }

    public static String fromInstantToLocalDateTimeString(Instant instant) {
        if (instant == null) return null;
        LocalDateTime localDateTime = fromInstant(instant);
        return DateTimeUtil.dateTimeFormatter.format(localDateTime);
    }

    public static String fromInstantToNexstepDateFormat(Instant instant) {
        if (instant == null) return null;
        LocalDateTime localDateTime = fromInstant(instant);
        return dateTimeFormatter_yyyy_MM_dd.format(localDateTime);
    }

    public static ZonedDateTime get(Long timeInMillis) {
        ZonedDateTime now = ZonedDateTime.of(LocalDateTime.now(), zoneId_oslo);

        String dateTimeString = null;
        try {
            if (timeInMillis != null && timeInMillis > 0) {
                Timestamp timestamp = new Timestamp(timeInMillis);
                LocalDateTime localChangedDateTime = timestamp.toInstant().atZone(zoneId_oslo).toLocalDateTime();
                ZonedDateTime zonedChangedDateTime = ZonedDateTime.of(localChangedDateTime, zoneId_oslo);
                return zonedChangedDateTime;
            } else {
                return now;
            }
        } catch (Exception e) {
            logger.error("Failed to parse " + dateTimeString + " into date. Using current date and time.", e);
            return now;
        }
    }

    public static Date validateAndConvert(java.sql.Date inputDate) {
        return isNexstepDateNull(inputDate) ? null : inputDate;
    }

    public static boolean isNexstepDateNull(java.sql.Date date) {
        try {
            if (date == null) return true;
            if (DateTimeUtil.nexstepDateFormat.format(date).equals("0001-01-01")) return true;
            if (DateTimeUtil.nexstepDateFormat.format(date).equals("9999-12-31")) return true;
            String dateString = DateTimeUtil.nexstepDateFormat.format(date);
            if (StringUtils.startsWith(dateString,"0000")) return true;
            if (StringUtils.startsWith(dateString,"0001")) return true;
            return false;
        } catch (Exception e) {
            String s = "BaseDao.isNexstepDateNull: Invalid date: '" + date + "'. Date is ignored.";
            logger.error(s);
            //throw new IllegalArgumentException(s, e);
            return true;
        }
    }

    public static boolean isNexstepTimeNull(Time time) {
        try {
            if (time == null) return true;
            if (DateTimeUtil.nexstepTimeFormat.format(time).equals("00:00:00")) return true;
            return false;
        } catch (Exception e) {
            String s = "Invalid time: " + time;
            logger.error(s);
            throw new IllegalArgumentException(s, e);
        }
    }

    public static Calendar createCalendarFromDate(Date inputDate) {
        if (inputDate == null) return null;
        Calendar instance = Calendar.getInstance();
        instance.setTime(inputDate);
        return instance;
    }

    public static Instant createInstantFromDate(Date inputDate) {
        if (inputDate == null) return null;
        return createCalendarFromDate(inputDate).toInstant();
    }

    public static Time validate(Time inputTime) {
        return isNexstepTimeNull(inputTime) ? null : inputTime;
    }

    public static Date createDateWithTime(Date inputDate, Time inputTime) {
        dateTimeFormat.setTimeZone(timeZone_oslo);
        String dateTimeString = null;
        try {
            if (inputDate == null) {
                if (inputTime != null) {
                    dateTimeString = dateTimeFormat.format(inputTime);
                    return dateTimeFormat.parse(dateTimeString);
                }
                return null;
            }
            String dateString = dateFormat_yyyy_MM_dd.format(inputDate);
            String timeString = inputTime == null ? "00:00:00" : timeFormat.format((inputTime));
            dateTimeString = dateString + " " + timeString;
            return dateTimeFormat.parse(dateTimeString);
        } catch (Exception e) {
            logger.warn("Failed to parse " + dateTimeString + " into date. Using current date and time instead.");
            return Calendar.getInstance().getTime();
        }
    }

    public static Date merge(Date date, Time time) {
        // nexstepDateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (date != null) {
            String dateAsString = nexstepDateFormat.format(date);
            String timeAsString = "00:00:00";
            if (time != null) {
                timeAsString = nexstepTimeFormat.format(time);
            }

            try {
                Date merged = nexstepDateTimeFormat.parse(dateAsString + "T" + timeAsString);
                return merged;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static Date convertToDate(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(zoneId_oslo)
                        .toInstant());
    }

    public static Date convertToDateFromInstant(Instant instant) {
        return java.util.Date
                .from(instant);
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateToConvert.getTime()), DateTimeUtil.zoneId_oslo);
    }

    public static String createTimeStamp(Date changeDate, Time changeTime) {
        String dateTimeString = null;
        ZonedDateTime now = ZonedDateTime.of(LocalDateTime.now(), DateTimeUtil.zoneId_oslo);
        try {
            if (changeDate != null && changeTime != null) {
                String dateString = dateFormat_yyyy_MM_dd.format(changeDate);
                String timeString = timeFormat.format(changeTime);
                dateTimeString = dateString + " " + timeString;
                Date date = dateTimeFormat.parse(dateTimeString);
                LocalDateTime localChangedDateTime = date.toInstant().atZone(DateTimeUtil.zoneId_oslo).toLocalDateTime();
                //LocalDateTime localChangedDateTime = schema.getEventPayload().getChangeDate().toInstant().atZone(oslo).toLocalDateTime();
                ZonedDateTime zonedChangedDateTime = ZonedDateTime.of(localChangedDateTime, DateTimeUtil.zoneId_oslo);
                return zonedChangedDateTime.format(dateformatter_ISO_OFFSET_DATE_TIME);
            } else {
                return now.format(dateformatter_ISO_OFFSET_DATE_TIME);
            }
        } catch (Exception e) {
            logger.error("Failed to parse " + dateTimeString + " into date. Using current date and time.", e);
            return now.format(dateformatter_ISO_OFFSET_DATE_TIME);
        }
    }

    public static String createTimestampFromCurrentTime() {
        ZonedDateTime now = ZonedDateTime.of(LocalDateTime.now(), DateTimeUtil.zoneId_oslo);
        return now.format(dateformatter_ISO_OFFSET_DATE_TIME);
    }
}
