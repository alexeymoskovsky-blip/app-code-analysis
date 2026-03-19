package org.threeten.bp.chrono;

import com.github.sunnysuperman.commons.config.Config;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import org.threeten.bp.Clock;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Period;
import org.threeten.bp.ZoneId;
import org.threeten.bp.jdk8.Jdk8Methods;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.Temporal;
import org.threeten.bp.temporal.TemporalAccessor;
import org.threeten.bp.temporal.TemporalAdjuster;
import org.threeten.bp.temporal.TemporalAmount;
import org.threeten.bp.temporal.TemporalField;
import org.threeten.bp.temporal.TemporalUnit;
import org.threeten.bp.temporal.UnsupportedTemporalTypeException;
import org.threeten.bp.temporal.ValueRange;

/* JADX INFO: loaded from: classes5.dex */
public final class ThaiBuddhistDate extends ChronoDateImpl<ThaiBuddhistDate> implements Serializable {
    private static final long serialVersionUID = -8722293800195731463L;
    public final LocalDate isoDate;

    @Override // org.threeten.bp.chrono.ChronoDateImpl, org.threeten.bp.temporal.Temporal
    public /* bridge */ /* synthetic */ long until(Temporal temporal, TemporalUnit temporalUnit) {
        return super.until(temporal, temporalUnit);
    }

    public static ThaiBuddhistDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static ThaiBuddhistDate now(ZoneId zoneId) {
        return now(Clock.system(zoneId));
    }

    public static ThaiBuddhistDate now(Clock clock) {
        return new ThaiBuddhistDate(LocalDate.now(clock));
    }

    public static ThaiBuddhistDate of(int i, int i2, int i3) {
        return ThaiBuddhistChronology.INSTANCE.date(i, i2, i3);
    }

    public static ThaiBuddhistDate from(TemporalAccessor temporalAccessor) {
        return ThaiBuddhistChronology.INSTANCE.date(temporalAccessor);
    }

    public ThaiBuddhistDate(LocalDate localDate) {
        Jdk8Methods.requireNonNull(localDate, Config.TYPE_DATE);
        this.isoDate = localDate;
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate
    public ThaiBuddhistChronology getChronology() {
        return ThaiBuddhistChronology.INSTANCE;
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate
    public ThaiBuddhistEra getEra() {
        return (ThaiBuddhistEra) super.getEra();
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate
    public int lengthOfMonth() {
        return this.isoDate.lengthOfMonth();
    }

    @Override // org.threeten.bp.jdk8.DefaultInterfaceTemporalAccessor, org.threeten.bp.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (isSupported(temporalField)) {
                ChronoField chronoField = (ChronoField) temporalField;
                int i = AnonymousClass1.$SwitchMap$org$threeten$bp$temporal$ChronoField[chronoField.ordinal()];
                if (i == 1 || i == 2 || i == 3) {
                    return this.isoDate.range(temporalField);
                }
                if (i == 4) {
                    ValueRange valueRangeRange = ChronoField.YEAR.range();
                    return ValueRange.of(1L, getProlepticYear() <= 0 ? (-(valueRangeRange.getMinimum() + 543)) + 1 : 543 + valueRangeRange.getMaximum());
                }
                return getChronology().range(chronoField);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    /* JADX INFO: renamed from: org.threeten.bp.chrono.ThaiBuddhistDate$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$threeten$bp$temporal$ChronoField;

        static {
            int[] iArr = new int[ChronoField.values().length];
            $SwitchMap$org$threeten$bp$temporal$ChronoField = iArr;
            try {
                iArr[ChronoField.DAY_OF_MONTH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$threeten$bp$temporal$ChronoField[ChronoField.DAY_OF_YEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$threeten$bp$temporal$ChronoField[ChronoField.ALIGNED_WEEK_OF_MONTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$threeten$bp$temporal$ChronoField[ChronoField.YEAR_OF_ERA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$threeten$bp$temporal$ChronoField[ChronoField.PROLEPTIC_MONTH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$threeten$bp$temporal$ChronoField[ChronoField.YEAR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$threeten$bp$temporal$ChronoField[ChronoField.ERA.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // org.threeten.bp.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int i = AnonymousClass1.$SwitchMap$org$threeten$bp$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
            if (i == 4) {
                int prolepticYear = getProlepticYear();
                if (prolepticYear < 1) {
                    prolepticYear = 1 - prolepticYear;
                }
                return prolepticYear;
            }
            if (i == 5) {
                return getProlepticMonth();
            }
            if (i == 6) {
                return getProlepticYear();
            }
            if (i != 7) {
                return this.isoDate.getLong(temporalField);
            }
            return getProlepticYear() < 1 ? 0 : 1;
        }
        return temporalField.getFrom(this);
    }

    private long getProlepticMonth() {
        return ((((long) getProlepticYear()) * 12) + ((long) this.isoDate.getMonthValue())) - 1;
    }

    private int getProlepticYear() {
        return this.isoDate.getYear() + 543;
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate, org.threeten.bp.jdk8.DefaultInterfaceTemporal, org.threeten.bp.temporal.Temporal
    public ThaiBuddhistDate with(TemporalAdjuster temporalAdjuster) {
        return (ThaiBuddhistDate) super.with(temporalAdjuster);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    @Override // org.threeten.bp.chrono.ChronoLocalDate, org.threeten.bp.temporal.Temporal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.threeten.bp.chrono.ThaiBuddhistDate with(org.threeten.bp.temporal.TemporalField r8, long r9) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof org.threeten.bp.temporal.ChronoField
            if (r0 == 0) goto L92
            r0 = r8
            org.threeten.bp.temporal.ChronoField r0 = (org.threeten.bp.temporal.ChronoField) r0
            long r1 = r7.getLong(r0)
            int r3 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r3 != 0) goto L10
            return r7
        L10:
            int[] r1 = org.threeten.bp.chrono.ThaiBuddhistDate.AnonymousClass1.$SwitchMap$org$threeten$bp$temporal$ChronoField
            int r2 = r0.ordinal()
            r2 = r1[r2]
            r3 = 7
            r4 = 6
            r5 = 4
            if (r2 == r5) goto L3a
            r6 = 5
            if (r2 == r6) goto L25
            if (r2 == r4) goto L3a
            if (r2 == r3) goto L3a
            goto L52
        L25:
            org.threeten.bp.chrono.ThaiBuddhistChronology r8 = r7.getChronology()
            org.threeten.bp.temporal.ValueRange r8 = r8.range(r0)
            r8.checkValidValue(r9, r0)
            long r0 = r7.getProlepticMonth()
            long r9 = r9 - r0
            org.threeten.bp.chrono.ThaiBuddhistDate r8 = r7.plusMonths(r9)
            return r8
        L3a:
            org.threeten.bp.chrono.ThaiBuddhistChronology r2 = r7.getChronology()
            org.threeten.bp.temporal.ValueRange r2 = r2.range(r0)
            int r2 = r2.checkValidIntValue(r9, r0)
            int r0 = r0.ordinal()
            r0 = r1[r0]
            if (r0 == r5) goto L7b
            if (r0 == r4) goto L6e
            if (r0 == r3) goto L5d
        L52:
            org.threeten.bp.LocalDate r0 = r7.isoDate
            org.threeten.bp.LocalDate r8 = r0.with(r8, r9)
            org.threeten.bp.chrono.ThaiBuddhistDate r8 = r7.with(r8)
            return r8
        L5d:
            org.threeten.bp.LocalDate r8 = r7.isoDate
            int r9 = r7.getProlepticYear()
            int r9 = (-542) - r9
            org.threeten.bp.LocalDate r8 = r8.withYear(r9)
            org.threeten.bp.chrono.ThaiBuddhistDate r8 = r7.with(r8)
            return r8
        L6e:
            org.threeten.bp.LocalDate r8 = r7.isoDate
            int r2 = r2 + (-543)
            org.threeten.bp.LocalDate r8 = r8.withYear(r2)
            org.threeten.bp.chrono.ThaiBuddhistDate r8 = r7.with(r8)
            return r8
        L7b:
            org.threeten.bp.LocalDate r8 = r7.isoDate
            int r9 = r7.getProlepticYear()
            r10 = 1
            if (r9 < r10) goto L85
            goto L87
        L85:
            int r2 = 1 - r2
        L87:
            int r2 = r2 + (-543)
            org.threeten.bp.LocalDate r8 = r8.withYear(r2)
            org.threeten.bp.chrono.ThaiBuddhistDate r8 = r7.with(r8)
            return r8
        L92:
            org.threeten.bp.temporal.Temporal r8 = r8.adjustInto(r7, r9)
            org.threeten.bp.chrono.ThaiBuddhistDate r8 = (org.threeten.bp.chrono.ThaiBuddhistDate) r8
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.threeten.bp.chrono.ThaiBuddhistDate.with(org.threeten.bp.temporal.TemporalField, long):org.threeten.bp.chrono.ThaiBuddhistDate");
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate, org.threeten.bp.jdk8.DefaultInterfaceTemporal, org.threeten.bp.temporal.Temporal
    public ThaiBuddhistDate plus(TemporalAmount temporalAmount) {
        return (ThaiBuddhistDate) super.plus(temporalAmount);
    }

    @Override // org.threeten.bp.chrono.ChronoDateImpl, org.threeten.bp.chrono.ChronoLocalDate, org.threeten.bp.temporal.Temporal
    public ThaiBuddhistDate plus(long j, TemporalUnit temporalUnit) {
        return (ThaiBuddhistDate) super.plus(j, temporalUnit);
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate, org.threeten.bp.jdk8.DefaultInterfaceTemporal, org.threeten.bp.temporal.Temporal
    public ThaiBuddhistDate minus(TemporalAmount temporalAmount) {
        return (ThaiBuddhistDate) super.minus(temporalAmount);
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate, org.threeten.bp.jdk8.DefaultInterfaceTemporal, org.threeten.bp.temporal.Temporal
    public ThaiBuddhistDate minus(long j, TemporalUnit temporalUnit) {
        return (ThaiBuddhistDate) super.minus(j, temporalUnit);
    }

    @Override // org.threeten.bp.chrono.ChronoDateImpl
    public ThaiBuddhistDate plusYears(long j) {
        return with(this.isoDate.plusYears(j));
    }

    @Override // org.threeten.bp.chrono.ChronoDateImpl
    public ThaiBuddhistDate plusMonths(long j) {
        return with(this.isoDate.plusMonths(j));
    }

    @Override // org.threeten.bp.chrono.ChronoDateImpl
    public ThaiBuddhistDate plusDays(long j) {
        return with(this.isoDate.plusDays(j));
    }

    public final ThaiBuddhistDate with(LocalDate localDate) {
        return localDate.equals(this.isoDate) ? this : new ThaiBuddhistDate(localDate);
    }

    @Override // org.threeten.bp.chrono.ChronoDateImpl, org.threeten.bp.chrono.ChronoLocalDate
    public final ChronoLocalDateTime<ThaiBuddhistDate> atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    @Override // org.threeten.bp.chrono.ChronoDateImpl, org.threeten.bp.chrono.ChronoLocalDate
    public ChronoPeriod until(ChronoLocalDate chronoLocalDate) {
        Period periodUntil = this.isoDate.until(chronoLocalDate);
        return getChronology().period(periodUntil.getYears(), periodUntil.getMonths(), periodUntil.getDays());
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate
    public long toEpochDay() {
        return this.isoDate.toEpochDay();
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ThaiBuddhistDate) {
            return this.isoDate.equals(((ThaiBuddhistDate) obj).isoDate);
        }
        return false;
    }

    @Override // org.threeten.bp.chrono.ChronoLocalDate
    public int hashCode() {
        return getChronology().getId().hashCode() ^ this.isoDate.hashCode();
    }

    private Object writeReplace() {
        return new Ser((byte) 7, this);
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(get(ChronoField.YEAR));
        dataOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
        dataOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
    }

    public static ChronoLocalDate readExternal(DataInput dataInput) throws IOException {
        return ThaiBuddhistChronology.INSTANCE.date(dataInput.readInt(), (int) dataInput.readByte(), (int) dataInput.readByte());
    }
}
