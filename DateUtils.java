package net.danlew.android.joda;

import android.content.Context;
import android.content.res.Resources;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.ReadablePeriod;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;

/* JADX INFO: loaded from: classes7.dex */
public class DateUtils {
    public static final DateTime EPOCH = new DateTime(0, DateTimeZone.UTC);
    public static final int FORMAT_ABBREV_ALL = 524288;
    public static final int FORMAT_ABBREV_MONTH = 65536;
    public static final int FORMAT_ABBREV_RELATIVE = 262144;
    public static final int FORMAT_ABBREV_TIME = 16384;
    public static final int FORMAT_ABBREV_WEEKDAY = 32768;
    public static final int FORMAT_NO_MIDNIGHT = 2048;
    public static final int FORMAT_NO_MONTH_DAY = 32;
    public static final int FORMAT_NO_NOON = 512;
    public static final int FORMAT_NO_YEAR = 8;
    public static final int FORMAT_NUMERIC_DATE = 131072;
    public static final int FORMAT_SHOW_DATE = 16;
    public static final int FORMAT_SHOW_TIME = 1;
    public static final int FORMAT_SHOW_WEEKDAY = 2;
    public static final int FORMAT_SHOW_YEAR = 4;
    public static final int FORMAT_UTC = 8192;

    public static String formatDateTime(Context context, ReadablePartial readablePartial, int i) {
        return android.text.format.DateUtils.formatDateTime(context, toMillis(readablePartial), i | 8192);
    }

    public static String formatDateTime(Context context, ReadableInstant readableInstant, int i) {
        return android.text.format.DateUtils.formatDateTime(context, toMillis(readableInstant), i | 8192);
    }

    public static String formatDateRange(Context context, ReadablePartial readablePartial, ReadablePartial readablePartial2, int i) {
        return formatDateRange(context, toMillis(readablePartial), toMillis(readablePartial2), i);
    }

    public static String formatDateRange(Context context, ReadableInstant readableInstant, ReadableInstant readableInstant2, int i) {
        return formatDateRange(context, toMillis(readableInstant), toMillis(readableInstant2), i);
    }

    public static String formatDateRange(Context context, long j, long j2, int i) {
        if (j != j2) {
            j2 += 1000;
        }
        return android.text.format.DateUtils.formatDateRange(context, j, j2, i | 8192);
    }

    public static long toMillis(ReadablePartial readablePartial) {
        return readablePartial.toDateTime(EPOCH).getMillis();
    }

    public static long toMillis(ReadableInstant readableInstant) {
        return (readableInstant instanceof DateTime ? (DateTime) readableInstant : new DateTime(readableInstant)).withZoneRetainFields(DateTimeZone.UTC).getMillis();
    }

    public static String formatElapsedTime(ReadableDuration readableDuration) {
        return formatElapsedTime(null, readableDuration);
    }

    public static String formatElapsedTime(StringBuilder sb, ReadableDuration readableDuration) {
        return android.text.format.DateUtils.formatElapsedTime(sb, readableDuration.toDuration().toStandardSeconds().getSeconds());
    }

    public static boolean isToday(ReadablePartial readablePartial) {
        if (readablePartial.isSupported(DateTimeFieldType.dayOfMonth()) && readablePartial.isSupported(DateTimeFieldType.monthOfYear()) && readablePartial.isSupported(DateTimeFieldType.year())) {
            return LocalDate.now().compareTo((ReadablePartial) (readablePartial instanceof LocalDate ? (LocalDate) readablePartial : new LocalDate(readablePartial))) == 0;
        }
        throw new IllegalArgumentException("isToday() must be passed a ReadablePartial that supports day of month, month of year and year.");
    }

    public static boolean isToday(ReadableInstant readableInstant) {
        return LocalDate.now().compareTo((ReadablePartial) new LocalDate(readableInstant)) == 0;
    }

    public static CharSequence getRelativeTimeSpanString(Context context, ReadablePartial readablePartial) {
        return getRelativeTimeSpanString(context, readablePartial.toDateTime(DateTime.now()));
    }

    public static CharSequence getRelativeTimeSpanString(Context context, ReadableInstant readableInstant) {
        return getRelativeTimeSpanString(context, readableInstant, 65556);
    }

    public static CharSequence getRelativeTimeSpanString(Context context, ReadablePartial readablePartial, int i) {
        return getRelativeTimeSpanString(context, readablePartial.toDateTime(DateTime.now()), i);
    }

    public static CharSequence getRelativeTimeSpanString(Context context, ReadableInstant readableInstant, int i) {
        long days;
        int i2;
        boolean z = (786432 & i) != 0;
        DateTime dateTimeWithMillisOfSecond = DateTime.now(readableInstant.getZone()).withMillisOfSecond(0);
        DateTime dateTimeWithMillisOfSecond2 = new DateTime(readableInstant).withMillisOfSecond(0);
        boolean zIsBefore = dateTimeWithMillisOfSecond.isBefore(dateTimeWithMillisOfSecond2);
        Interval interval = !zIsBefore ? new Interval(dateTimeWithMillisOfSecond2, dateTimeWithMillisOfSecond) : new Interval(dateTimeWithMillisOfSecond, dateTimeWithMillisOfSecond2);
        if (Minutes.minutesIn(interval).isLessThan(Minutes.ONE)) {
            days = Seconds.secondsIn(interval).getSeconds();
            if (zIsBefore) {
                if (z) {
                    i2 = R.plurals.joda_time_android_abbrev_in_num_seconds;
                } else {
                    i2 = R.plurals.joda_time_android_in_num_seconds;
                }
            } else if (z) {
                i2 = R.plurals.joda_time_android_abbrev_num_seconds_ago;
            } else {
                i2 = R.plurals.joda_time_android_num_seconds_ago;
            }
        } else if (Hours.hoursIn(interval).isLessThan(Hours.ONE)) {
            days = Minutes.minutesIn(interval).getMinutes();
            if (zIsBefore) {
                if (z) {
                    i2 = R.plurals.joda_time_android_abbrev_in_num_minutes;
                } else {
                    i2 = R.plurals.joda_time_android_in_num_minutes;
                }
            } else if (z) {
                i2 = R.plurals.joda_time_android_abbrev_num_minutes_ago;
            } else {
                i2 = R.plurals.joda_time_android_num_minutes_ago;
            }
        } else if (Days.daysIn(interval).isLessThan(Days.ONE)) {
            days = Hours.hoursIn(interval).getHours();
            if (zIsBefore) {
                if (z) {
                    i2 = R.plurals.joda_time_android_abbrev_in_num_hours;
                } else {
                    i2 = R.plurals.joda_time_android_in_num_hours;
                }
            } else if (z) {
                i2 = R.plurals.joda_time_android_abbrev_num_hours_ago;
            } else {
                i2 = R.plurals.joda_time_android_num_hours_ago;
            }
        } else if (Weeks.weeksIn(interval).isLessThan(Weeks.ONE)) {
            days = Days.daysIn(interval).getDays();
            if (zIsBefore) {
                if (z) {
                    i2 = R.plurals.joda_time_android_abbrev_in_num_days;
                } else {
                    i2 = R.plurals.joda_time_android_in_num_days;
                }
            } else if (z) {
                i2 = R.plurals.joda_time_android_abbrev_num_days_ago;
            } else {
                i2 = R.plurals.joda_time_android_num_days_ago;
            }
        } else {
            return formatDateRange(context, readableInstant, readableInstant, i);
        }
        return String.format(context.getResources().getQuantityString(i2, (int) days), Long.valueOf(days));
    }

    public static CharSequence getRelativeTimeSpanString(Context context, ReadablePartial readablePartial, boolean z) {
        return getRelativeTimeSpanString(context, readablePartial.toDateTime(DateTime.now()), z);
    }

    public static CharSequence getRelativeTimeSpanString(Context context, ReadableInstant readableInstant, boolean z) {
        String dateRange;
        int i;
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDate = new LocalDate(readableInstant);
        if (Days.daysBetween(localDateNow, localDate).getDays() == 0) {
            dateRange = formatDateRange(context, readableInstant, readableInstant, 1);
            i = R.string.joda_time_android_preposition_for_time;
        } else if (Years.yearsBetween(localDateNow, localDate).getYears() != 0) {
            dateRange = formatDateRange(context, readableInstant, readableInstant, 131092);
            i = R.string.joda_time_android_preposition_for_date;
        } else {
            dateRange = formatDateRange(context, readableInstant, readableInstant, 65552);
            i = R.string.joda_time_android_preposition_for_date;
        }
        return z ? context.getString(i, dateRange) : dateRange;
    }

    public static CharSequence getRelativeDateTimeString(Context context, ReadablePartial readablePartial, ReadablePeriod readablePeriod, int i) {
        if (!readablePartial.isSupported(DateTimeFieldType.hourOfDay()) || !readablePartial.isSupported(DateTimeFieldType.minuteOfHour())) {
            throw new IllegalArgumentException("getRelativeDateTimeString() must be passed a ReadablePartial that supports time, otherwise it makes no sense");
        }
        return getRelativeDateTimeString(context, readablePartial.toDateTime(DateTime.now()), readablePeriod, i);
    }

    public static CharSequence getRelativeDateTimeString(Context context, ReadableInstant readableInstant, ReadablePeriod readablePeriod, int i) {
        Duration durationFrom;
        Resources resources = context.getResources();
        DateTime dateTimeWithMillisOfSecond = DateTime.now(readableInstant.getZone()).withMillisOfSecond(0);
        DateTime dateTimeWithMillisOfSecond2 = new DateTime(readableInstant).withMillisOfSecond(0);
        boolean zIsBefore = dateTimeWithMillisOfSecond.isBefore(dateTimeWithMillisOfSecond2);
        Duration duration = !zIsBefore ? new Duration(dateTimeWithMillisOfSecond2, dateTimeWithMillisOfSecond) : new Duration(dateTimeWithMillisOfSecond, dateTimeWithMillisOfSecond2);
        Duration durationTo = Days.ONE.toPeriod().toDurationTo(dateTimeWithMillisOfSecond2);
        if (readablePeriod != null) {
            if (!zIsBefore) {
                durationFrom = readablePeriod.toPeriod().toDurationTo(dateTimeWithMillisOfSecond);
            } else {
                durationFrom = readablePeriod.toPeriod().toDurationFrom(dateTimeWithMillisOfSecond);
            }
            Duration durationTo2 = Weeks.ONE.toPeriod().toDurationTo(dateTimeWithMillisOfSecond2);
            if (durationFrom.isLongerThan(durationTo2)) {
                durationTo = durationTo2;
            } else if (!durationFrom.isShorterThan(durationTo)) {
                durationTo = durationFrom;
            }
        }
        String dateRange = formatDateRange(context, readableInstant, readableInstant, 1);
        if (duration.isLongerThan(durationTo)) {
            return resources.getString(R.string.joda_time_android_date_time, getRelativeTimeSpanString(context, readableInstant, false), dateRange);
        }
        return resources.getString(R.string.joda_time_android_relative_time, getRelativeTimeSpanString(context, readableInstant, i), dateRange);
    }

    public static CharSequence formatDuration(Context context, ReadableDuration readableDuration) {
        Resources resources = context.getResources();
        Duration duration = readableDuration.toDuration();
        int standardHours = (int) duration.getStandardHours();
        if (standardHours != 0) {
            return resources.getQuantityString(R.plurals.joda_time_android_duration_hours, standardHours, Integer.valueOf(standardHours));
        }
        int standardMinutes = (int) duration.getStandardMinutes();
        if (standardMinutes != 0) {
            return resources.getQuantityString(R.plurals.joda_time_android_duration_minutes, standardMinutes, Integer.valueOf(standardMinutes));
        }
        int standardSeconds = (int) duration.getStandardSeconds();
        return resources.getQuantityString(R.plurals.joda_time_android_duration_seconds, standardSeconds, Integer.valueOf(standardSeconds));
    }
}
