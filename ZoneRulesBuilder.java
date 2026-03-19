package org.threeten.bp.zone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;
import org.threeten.bp.Year;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.chrono.ChronoLocalDate;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.jdk8.Jdk8Methods;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.TemporalAdjusters;
import org.threeten.bp.zone.ZoneOffsetTransitionRule;

/* JADX INFO: loaded from: classes5.dex */
public class ZoneRulesBuilder {
    public Map<Object, Object> deduplicateMap;
    public List<TZWindow> windowList = new ArrayList();

    public ZoneRulesBuilder addWindow(ZoneOffset zoneOffset, LocalDateTime localDateTime, ZoneOffsetTransitionRule.TimeDefinition timeDefinition) {
        Jdk8Methods.requireNonNull(zoneOffset, "standardOffset");
        Jdk8Methods.requireNonNull(localDateTime, "until");
        Jdk8Methods.requireNonNull(timeDefinition, "untilDefinition");
        TZWindow tZWindow = new TZWindow(zoneOffset, localDateTime, timeDefinition);
        if (this.windowList.size() > 0) {
            tZWindow.validateWindowOrder(this.windowList.get(r2.size() - 1));
        }
        this.windowList.add(tZWindow);
        return this;
    }

    public ZoneRulesBuilder addWindowForever(ZoneOffset zoneOffset) {
        return addWindow(zoneOffset, LocalDateTime.MAX, ZoneOffsetTransitionRule.TimeDefinition.WALL);
    }

    public ZoneRulesBuilder setFixedSavingsToWindow(int i) {
        if (this.windowList.isEmpty()) {
            throw new IllegalStateException("Must add a window before setting the fixed savings");
        }
        this.windowList.get(r0.size() - 1).setFixedSavings(i);
        return this;
    }

    public ZoneRulesBuilder addRuleToWindow(LocalDateTime localDateTime, ZoneOffsetTransitionRule.TimeDefinition timeDefinition, int i) {
        Jdk8Methods.requireNonNull(localDateTime, "transitionDateTime");
        return addRuleToWindow(localDateTime.getYear(), localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), (DayOfWeek) null, localDateTime.toLocalTime(), false, timeDefinition, i);
    }

    public ZoneRulesBuilder addRuleToWindow(int i, Month month, int i2, LocalTime localTime, boolean z, ZoneOffsetTransitionRule.TimeDefinition timeDefinition, int i3) {
        return addRuleToWindow(i, i, month, i2, (DayOfWeek) null, localTime, z, timeDefinition, i3);
    }

    public ZoneRulesBuilder addRuleToWindow(int i, int i2, Month month, int i3, DayOfWeek dayOfWeek, LocalTime localTime, boolean z, ZoneOffsetTransitionRule.TimeDefinition timeDefinition, int i4) {
        Jdk8Methods.requireNonNull(month, "month");
        Jdk8Methods.requireNonNull(localTime, "time");
        Jdk8Methods.requireNonNull(timeDefinition, "timeDefinition");
        ChronoField chronoField = ChronoField.YEAR;
        chronoField.checkValidValue(i);
        chronoField.checkValidValue(i2);
        if (i3 < -28 || i3 > 31 || i3 == 0) {
            throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
        }
        if (z && !localTime.equals(LocalTime.MIDNIGHT)) {
            throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
        }
        if (this.windowList.isEmpty()) {
            throw new IllegalStateException("Must add a window before adding a rule");
        }
        this.windowList.get(r1.size() - 1).addRule(i, i2, month, i3, dayOfWeek, localTime, z ? 1 : 0, timeDefinition, i4);
        return this;
    }

    public ZoneRulesBuilder addRuleToWindow(int i, int i2, Month month, int i3, DayOfWeek dayOfWeek, LocalTime localTime, int i4, ZoneOffsetTransitionRule.TimeDefinition timeDefinition, int i5) {
        Jdk8Methods.requireNonNull(month, "month");
        Jdk8Methods.requireNonNull(timeDefinition, "timeDefinition");
        ChronoField chronoField = ChronoField.YEAR;
        chronoField.checkValidValue(i);
        chronoField.checkValidValue(i2);
        if (i3 < -28 || i3 > 31 || i3 == 0) {
            throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
        }
        if (this.windowList.isEmpty()) {
            throw new IllegalStateException("Must add a window before adding a rule");
        }
        this.windowList.get(r1.size() - 1).addRule(i, i2, month, i3, dayOfWeek, localTime, i4, timeDefinition, i5);
        return this;
    }

    public ZoneRules toRules(String str) {
        return toRules(str, new HashMap());
    }

    public ZoneRules toRules(String str, Map<Object, Object> map) {
        int i;
        Jdk8Methods.requireNonNull(str, "zoneId");
        this.deduplicateMap = map;
        if (this.windowList.isEmpty()) {
            throw new IllegalStateException("No windows have been added to the builder");
        }
        ArrayList arrayList = new ArrayList(4);
        ArrayList arrayList2 = new ArrayList(256);
        ArrayList arrayList3 = new ArrayList(2);
        int i2 = 0;
        TZWindow tZWindow = this.windowList.get(0);
        ZoneOffset zoneOffset = tZWindow.standardOffset;
        int iIntValue = tZWindow.fixedSavingAmountSecs != null ? tZWindow.fixedSavingAmountSecs.intValue() : 0;
        ZoneOffset zoneOffset2 = (ZoneOffset) deduplicate(ZoneOffset.ofTotalSeconds(zoneOffset.getTotalSeconds() + iIntValue));
        LocalDateTime localDateTime = (LocalDateTime) deduplicate(LocalDateTime.of(Year.MIN_VALUE, 1, 1, 0, 0));
        ZoneOffset zoneOffset3 = zoneOffset2;
        for (TZWindow tZWindow2 : this.windowList) {
            tZWindow2.tidy(localDateTime.getYear());
            Integer numValueOf = tZWindow2.fixedSavingAmountSecs;
            if (numValueOf == null) {
                numValueOf = Integer.valueOf(i2);
                for (TZRule tZRule : tZWindow2.ruleList) {
                    if (tZRule.toTransition(zoneOffset, iIntValue).toEpochSecond() > localDateTime.toEpochSecond(zoneOffset3)) {
                        break;
                    }
                    numValueOf = Integer.valueOf(tZRule.savingAmountSecs);
                }
            }
            if (!zoneOffset.equals(tZWindow2.standardOffset)) {
                arrayList.add(deduplicate(new ZoneOffsetTransition(LocalDateTime.ofEpochSecond(localDateTime.toEpochSecond(zoneOffset3), i2, zoneOffset), zoneOffset, tZWindow2.standardOffset)));
                zoneOffset = (ZoneOffset) deduplicate(tZWindow2.standardOffset);
            }
            ZoneOffset zoneOffset4 = (ZoneOffset) deduplicate(ZoneOffset.ofTotalSeconds(zoneOffset.getTotalSeconds() + numValueOf.intValue()));
            if (!zoneOffset3.equals(zoneOffset4)) {
                arrayList2.add((ZoneOffsetTransition) deduplicate(new ZoneOffsetTransition(localDateTime, zoneOffset3, zoneOffset4)));
            }
            iIntValue = numValueOf.intValue();
            for (TZRule tZRule2 : tZWindow2.ruleList) {
                ZoneOffsetTransition zoneOffsetTransition = (ZoneOffsetTransition) deduplicate(tZRule2.toTransition(zoneOffset, iIntValue));
                if (zoneOffsetTransition.toEpochSecond() >= localDateTime.toEpochSecond(zoneOffset3) && zoneOffsetTransition.toEpochSecond() < tZWindow2.createDateTimeEpochSecond(iIntValue)) {
                    i = iIntValue;
                    if (!zoneOffsetTransition.getOffsetBefore().equals(zoneOffsetTransition.getOffsetAfter())) {
                        arrayList2.add(zoneOffsetTransition);
                        iIntValue = tZRule2.savingAmountSecs;
                    }
                } else {
                    i = iIntValue;
                }
                iIntValue = i;
            }
            for (TZRule tZRule3 : tZWindow2.lastRuleList) {
                arrayList3.add((ZoneOffsetTransitionRule) deduplicate(tZRule3.toTransitionRule(zoneOffset, iIntValue)));
                iIntValue = tZRule3.savingAmountSecs;
            }
            zoneOffset3 = (ZoneOffset) deduplicate(tZWindow2.createWallOffset(iIntValue));
            i2 = 0;
            localDateTime = (LocalDateTime) deduplicate(LocalDateTime.ofEpochSecond(tZWindow2.createDateTimeEpochSecond(iIntValue), 0, zoneOffset3));
        }
        return new StandardZoneRules(tZWindow.standardOffset, zoneOffset2, arrayList, arrayList2, arrayList3);
    }

    public <T> T deduplicate(T t) {
        if (!this.deduplicateMap.containsKey(t)) {
            this.deduplicateMap.put(t, t);
        }
        return (T) this.deduplicateMap.get(t);
    }

    public class TZWindow {
        public Integer fixedSavingAmountSecs;
        public final ZoneOffset standardOffset;
        public final ZoneOffsetTransitionRule.TimeDefinition timeDefinition;
        public final LocalDateTime windowEnd;
        public List<TZRule> ruleList = new ArrayList();
        public int maxLastRuleStartYear = Year.MIN_VALUE;
        public List<TZRule> lastRuleList = new ArrayList();

        public TZWindow(ZoneOffset zoneOffset, LocalDateTime localDateTime, ZoneOffsetTransitionRule.TimeDefinition timeDefinition) {
            this.windowEnd = localDateTime;
            this.timeDefinition = timeDefinition;
            this.standardOffset = zoneOffset;
        }

        public void setFixedSavings(int i) {
            if (this.ruleList.size() > 0 || this.lastRuleList.size() > 0) {
                throw new IllegalStateException("Window has DST rules, so cannot have fixed savings");
            }
            this.fixedSavingAmountSecs = Integer.valueOf(i);
        }

        public void addRule(int i, int i2, Month month, int i3, DayOfWeek dayOfWeek, LocalTime localTime, int i4, ZoneOffsetTransitionRule.TimeDefinition timeDefinition, int i5) {
            boolean z;
            if (this.fixedSavingAmountSecs != null) {
                throw new IllegalStateException("Window has a fixed DST saving, so cannot have DST rules");
            }
            if (this.ruleList.size() >= 2000) {
                throw new IllegalStateException("Window has reached the maximum number of allowed rules");
            }
            int i6 = i2;
            if (i6 == 999999999) {
                z = true;
                i6 = i;
            } else {
                z = false;
            }
            for (int i7 = i; i7 <= i6; i7++) {
                TZRule tZRule = ZoneRulesBuilder.this.new TZRule(i7, month, i3, dayOfWeek, localTime, i4, timeDefinition, i5);
                if (z) {
                    this.lastRuleList.add(tZRule);
                    this.maxLastRuleStartYear = Math.max(i, this.maxLastRuleStartYear);
                } else {
                    this.ruleList.add(tZRule);
                }
            }
        }

        public void validateWindowOrder(TZWindow tZWindow) {
            if (this.windowEnd.isBefore(tZWindow.windowEnd)) {
                throw new IllegalStateException("Windows must be added in date-time order: " + this.windowEnd + " < " + tZWindow.windowEnd);
            }
        }

        public void tidy(int i) {
            if (this.lastRuleList.size() == 1) {
                throw new IllegalStateException("Cannot have only one rule defined as being forever");
            }
            if (this.windowEnd.equals(LocalDateTime.MAX)) {
                this.maxLastRuleStartYear = Math.max(this.maxLastRuleStartYear, i) + 1;
                for (TZRule tZRule : this.lastRuleList) {
                    addRule(tZRule.year, this.maxLastRuleStartYear, tZRule.month, tZRule.dayOfMonthIndicator, tZRule.dayOfWeek, tZRule.time, tZRule.adjustDays, tZRule.timeDefinition, tZRule.savingAmountSecs);
                    tZRule.year = this.maxLastRuleStartYear + 1;
                }
                int i2 = this.maxLastRuleStartYear;
                if (i2 == 999999999) {
                    this.lastRuleList.clear();
                } else {
                    this.maxLastRuleStartYear = i2 + 1;
                }
            } else {
                int year = this.windowEnd.getYear();
                for (TZRule tZRule2 : this.lastRuleList) {
                    addRule(tZRule2.year, year + 1, tZRule2.month, tZRule2.dayOfMonthIndicator, tZRule2.dayOfWeek, tZRule2.time, tZRule2.adjustDays, tZRule2.timeDefinition, tZRule2.savingAmountSecs);
                }
                this.lastRuleList.clear();
                this.maxLastRuleStartYear = Year.MAX_VALUE;
            }
            Collections.sort(this.ruleList);
            Collections.sort(this.lastRuleList);
            if (this.ruleList.size() == 0 && this.fixedSavingAmountSecs == null) {
                this.fixedSavingAmountSecs = 0;
            }
        }

        public boolean isSingleWindowStandardOffset() {
            return this.windowEnd.equals(LocalDateTime.MAX) && this.timeDefinition == ZoneOffsetTransitionRule.TimeDefinition.WALL && this.fixedSavingAmountSecs == null && this.lastRuleList.isEmpty() && this.ruleList.isEmpty();
        }

        public ZoneOffset createWallOffset(int i) {
            return ZoneOffset.ofTotalSeconds(this.standardOffset.getTotalSeconds() + i);
        }

        public long createDateTimeEpochSecond(int i) {
            ZoneOffset zoneOffsetCreateWallOffset = createWallOffset(i);
            return this.timeDefinition.createDateTime(this.windowEnd, this.standardOffset, zoneOffsetCreateWallOffset).toEpochSecond(zoneOffsetCreateWallOffset);
        }
    }

    public class TZRule implements Comparable<TZRule> {
        public int adjustDays;
        public int dayOfMonthIndicator;
        public DayOfWeek dayOfWeek;
        public Month month;
        public int savingAmountSecs;
        public LocalTime time;
        public ZoneOffsetTransitionRule.TimeDefinition timeDefinition;
        public int year;

        public TZRule(int i, Month month, int i2, DayOfWeek dayOfWeek, LocalTime localTime, int i3, ZoneOffsetTransitionRule.TimeDefinition timeDefinition, int i4) {
            this.year = i;
            this.month = month;
            this.dayOfMonthIndicator = i2;
            this.dayOfWeek = dayOfWeek;
            this.time = localTime;
            this.adjustDays = i3;
            this.timeDefinition = timeDefinition;
            this.savingAmountSecs = i4;
        }

        public ZoneOffsetTransition toTransition(ZoneOffset zoneOffset, int i) {
            LocalDateTime localDateTime = (LocalDateTime) ZoneRulesBuilder.this.deduplicate(LocalDateTime.of(((LocalDate) ZoneRulesBuilder.this.deduplicate(toLocalDate())).plusDays(this.adjustDays), this.time));
            ZoneOffset zoneOffset2 = (ZoneOffset) ZoneRulesBuilder.this.deduplicate(ZoneOffset.ofTotalSeconds(zoneOffset.getTotalSeconds() + i));
            return new ZoneOffsetTransition((LocalDateTime) ZoneRulesBuilder.this.deduplicate(this.timeDefinition.createDateTime(localDateTime, zoneOffset, zoneOffset2)), zoneOffset2, (ZoneOffset) ZoneRulesBuilder.this.deduplicate(ZoneOffset.ofTotalSeconds(zoneOffset.getTotalSeconds() + this.savingAmountSecs)));
        }

        public ZoneOffsetTransitionRule toTransitionRule(ZoneOffset zoneOffset, int i) {
            Month month;
            if (this.dayOfMonthIndicator < 0 && (month = this.month) != Month.FEBRUARY) {
                this.dayOfMonthIndicator = month.maxLength() - 6;
            }
            ZoneOffsetTransition transition = toTransition(zoneOffset, i);
            return new ZoneOffsetTransitionRule(this.month, this.dayOfMonthIndicator, this.dayOfWeek, this.time, this.adjustDays, this.timeDefinition, zoneOffset, transition.getOffsetBefore(), transition.getOffsetAfter());
        }

        @Override // java.lang.Comparable
        public int compareTo(TZRule tZRule) {
            int iCompareTo = this.year - tZRule.year;
            if (iCompareTo == 0) {
                iCompareTo = this.month.compareTo(tZRule.month);
            }
            if (iCompareTo == 0) {
                iCompareTo = toLocalDate().compareTo((ChronoLocalDate) tZRule.toLocalDate());
            }
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            long secondOfDay = this.time.toSecondOfDay() + (this.adjustDays * 86400);
            long secondOfDay2 = tZRule.time.toSecondOfDay() + (tZRule.adjustDays * 86400);
            if (secondOfDay < secondOfDay2) {
                return -1;
            }
            return secondOfDay > secondOfDay2 ? 1 : 0;
        }

        public final LocalDate toLocalDate() {
            int i = this.dayOfMonthIndicator;
            if (i < 0) {
                LocalDate localDateOf = LocalDate.of(this.year, this.month, this.month.length(IsoChronology.INSTANCE.isLeapYear(this.year)) + 1 + this.dayOfMonthIndicator);
                DayOfWeek dayOfWeek = this.dayOfWeek;
                return dayOfWeek != null ? localDateOf.with(TemporalAdjusters.previousOrSame(dayOfWeek)) : localDateOf;
            }
            LocalDate localDateOf2 = LocalDate.of(this.year, this.month, i);
            DayOfWeek dayOfWeek2 = this.dayOfWeek;
            return dayOfWeek2 != null ? localDateOf2.with(TemporalAdjusters.nextOrSame(dayOfWeek2)) : localDateOf2;
        }
    }
}
