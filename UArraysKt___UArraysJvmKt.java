package kotlin.collections.unsigned;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.OverloadResolutionByLambdaReturnType;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _UArraysJvm.kt */
/* JADX INFO: loaded from: classes7.dex */
public class UArraysKt___UArraysJvmKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: elementAt-qFRl0hI, reason: not valid java name */
    public static final int m2177elementAtqFRl0hI(int[] elementAt, int i) {
        Intrinsics.checkNotNullParameter(elementAt, "$this$elementAt");
        return UIntArray.m1851getpVg5ArA(elementAt, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: elementAt-r7IrZao, reason: not valid java name */
    public static final long m2178elementAtr7IrZao(long[] elementAt, int i) {
        Intrinsics.checkNotNullParameter(elementAt, "$this$elementAt");
        return ULongArray.m1930getsVKNKU(elementAt, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: elementAt-PpDY95g, reason: not valid java name */
    public static final byte m2175elementAtPpDY95g(byte[] elementAt, int i) {
        Intrinsics.checkNotNullParameter(elementAt, "$this$elementAt");
        return UByteArray.m1772getw2LRezQ(elementAt, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: elementAt-nggk6HY, reason: not valid java name */
    public static final short m2176elementAtnggk6HY(short[] elementAt, int i) {
        Intrinsics.checkNotNullParameter(elementAt, "$this$elementAt");
        return UShortArray.m2035getMh2AYeg(elementAt, i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: asList--ajY-9A, reason: not valid java name */
    public static final List<UInt> m2163asListajY9A(@NotNull int[] asList) {
        Intrinsics.checkNotNullParameter(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$1(asList);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: asList-QwZRm1k, reason: not valid java name */
    public static final List<ULong> m2165asListQwZRm1k(@NotNull long[] asList) {
        Intrinsics.checkNotNullParameter(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$2(asList);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: asList-GBYM_sE, reason: not valid java name */
    public static final List<UByte> m2164asListGBYM_sE(@NotNull byte[] asList) {
        Intrinsics.checkNotNullParameter(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$3(asList);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: asList-rL5Bavg, reason: not valid java name */
    public static final List<UShort> m2166asListrL5Bavg(@NotNull short[] asList) {
        Intrinsics.checkNotNullParameter(asList, "$this$asList");
        return new UArraysKt___UArraysJvmKt$asList$4(asList);
    }

    /* JADX INFO: renamed from: binarySearch-2fe2U9s$default, reason: not valid java name */
    public static /* synthetic */ int m2168binarySearch2fe2U9s$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = UIntArray.m1852getSizeimpl(iArr);
        }
        return m2167binarySearch2fe2U9s(iArr, i, i2, i3);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: binarySearch-2fe2U9s, reason: not valid java name */
    public static final int m2167binarySearch2fe2U9s(@NotNull int[] binarySearch, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i2, i3, UIntArray.m1852getSizeimpl(binarySearch));
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int iUintCompare = UnsignedKt.uintCompare(binarySearch[i5], i);
            if (iUintCompare < 0) {
                i2 = i5 + 1;
            } else {
                if (iUintCompare <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return -(i2 + 1);
    }

    /* JADX INFO: renamed from: binarySearch-K6DWlUc$default, reason: not valid java name */
    public static /* synthetic */ int m2172binarySearchK6DWlUc$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = ULongArray.m1931getSizeimpl(jArr);
        }
        return m2171binarySearchK6DWlUc(jArr, j, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: binarySearch-K6DWlUc, reason: not valid java name */
    public static final int m2171binarySearchK6DWlUc(@NotNull long[] binarySearch, long j, int i, int i2) {
        Intrinsics.checkNotNullParameter(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, ULongArray.m1931getSizeimpl(binarySearch));
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >>> 1;
            int iUlongCompare = UnsignedKt.ulongCompare(binarySearch[i4], j);
            if (iUlongCompare < 0) {
                i = i4 + 1;
            } else {
                if (iUlongCompare <= 0) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    /* JADX INFO: renamed from: binarySearch-WpHrYlw$default, reason: not valid java name */
    public static /* synthetic */ int m2174binarySearchWpHrYlw$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = UByteArray.m1773getSizeimpl(bArr);
        }
        return m2173binarySearchWpHrYlw(bArr, b, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: binarySearch-WpHrYlw, reason: not valid java name */
    public static final int m2173binarySearchWpHrYlw(@NotNull byte[] binarySearch, byte b, int i, int i2) {
        Intrinsics.checkNotNullParameter(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, UByteArray.m1773getSizeimpl(binarySearch));
        int i3 = b & 255;
        int i4 = i2 - 1;
        while (i <= i4) {
            int i5 = (i + i4) >>> 1;
            int iUintCompare = UnsignedKt.uintCompare(binarySearch[i5], i3);
            if (iUintCompare < 0) {
                i = i5 + 1;
            } else {
                if (iUintCompare <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return -(i + 1);
    }

    /* JADX INFO: renamed from: binarySearch-EtDCXyQ$default, reason: not valid java name */
    public static /* synthetic */ int m2170binarySearchEtDCXyQ$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = UShortArray.m2036getSizeimpl(sArr);
        }
        return m2169binarySearchEtDCXyQ(sArr, s, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: binarySearch-EtDCXyQ, reason: not valid java name */
    public static final int m2169binarySearchEtDCXyQ(@NotNull short[] binarySearch, short s, int i, int i2) {
        Intrinsics.checkNotNullParameter(binarySearch, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, UShortArray.m2036getSizeimpl(binarySearch));
        int i3 = s & 65535;
        int i4 = i2 - 1;
        while (i <= i4) {
            int i5 = (i + i4) >>> 1;
            int iUintCompare = UnsignedKt.uintCompare(binarySearch[i5], i3);
            if (iUintCompare < 0) {
                i = i5 + 1;
            } else {
                if (iUintCompare <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return -(i + 1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: max--ajY-9A, reason: not valid java name */
    public static final /* synthetic */ UInt m2179maxajY9A(int[] max) {
        Intrinsics.checkNotNullParameter(max, "$this$max");
        return UArraysKt___UArraysKt.m2559maxOrNullajY9A(max);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: max-QwZRm1k, reason: not valid java name */
    public static final /* synthetic */ ULong m2181maxQwZRm1k(long[] max) {
        Intrinsics.checkNotNullParameter(max, "$this$max");
        return UArraysKt___UArraysKt.m2561maxOrNullQwZRm1k(max);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: max-GBYM_sE, reason: not valid java name */
    public static final /* synthetic */ UByte m2180maxGBYM_sE(byte[] max) {
        Intrinsics.checkNotNullParameter(max, "$this$max");
        return UArraysKt___UArraysKt.m2560maxOrNullGBYM_sE(max);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: max-rL5Bavg, reason: not valid java name */
    public static final /* synthetic */ UShort m2182maxrL5Bavg(short[] max) {
        Intrinsics.checkNotNullParameter(max, "$this$max");
        return UArraysKt___UArraysKt.m2562maxOrNullrL5Bavg(max);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use maxByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxBy-jgv0xPQ, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> UInt m2185maxByjgv0xPQ(int[] maxBy, Function1<? super UInt, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(maxBy, "$this$maxBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (UIntArray.m1854isEmptyimpl(maxBy)) {
            return null;
        }
        int iM1851getpVg5ArA = UIntArray.m1851getpVg5ArA(maxBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(maxBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(UInt.m1785boximpl(iM1851getpVg5ArA));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                int iM1851getpVg5ArA2 = UIntArray.m1851getpVg5ArA(maxBy, it.nextInt());
                R rInvoke2 = selector.invoke(UInt.m1785boximpl(iM1851getpVg5ArA2));
                if (rInvoke.compareTo(rInvoke2) < 0) {
                    iM1851getpVg5ArA = iM1851getpVg5ArA2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return UInt.m1785boximpl(iM1851getpVg5ArA);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use maxByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxBy-MShoTSo, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> ULong m2184maxByMShoTSo(long[] maxBy, Function1<? super ULong, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(maxBy, "$this$maxBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (ULongArray.m1933isEmptyimpl(maxBy)) {
            return null;
        }
        long jM1930getsVKNKU = ULongArray.m1930getsVKNKU(maxBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(maxBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(ULong.m1864boximpl(jM1930getsVKNKU));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                long jM1930getsVKNKU2 = ULongArray.m1930getsVKNKU(maxBy, it.nextInt());
                R rInvoke2 = selector.invoke(ULong.m1864boximpl(jM1930getsVKNKU2));
                if (rInvoke.compareTo(rInvoke2) < 0) {
                    jM1930getsVKNKU = jM1930getsVKNKU2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return ULong.m1864boximpl(jM1930getsVKNKU);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use maxByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxBy-JOV_ifY, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> UByte m2183maxByJOV_ifY(byte[] maxBy, Function1<? super UByte, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(maxBy, "$this$maxBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (UByteArray.m1775isEmptyimpl(maxBy)) {
            return null;
        }
        byte bM1772getw2LRezQ = UByteArray.m1772getw2LRezQ(maxBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(maxBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(UByte.m1708boximpl(bM1772getw2LRezQ));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                byte bM1772getw2LRezQ2 = UByteArray.m1772getw2LRezQ(maxBy, it.nextInt());
                R rInvoke2 = selector.invoke(UByte.m1708boximpl(bM1772getw2LRezQ2));
                if (rInvoke.compareTo(rInvoke2) < 0) {
                    bM1772getw2LRezQ = bM1772getw2LRezQ2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return UByte.m1708boximpl(bM1772getw2LRezQ);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use maxByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxBy-xTcfx_M, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> UShort m2186maxByxTcfx_M(short[] maxBy, Function1<? super UShort, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(maxBy, "$this$maxBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (UShortArray.m2038isEmptyimpl(maxBy)) {
            return null;
        }
        short sM2035getMh2AYeg = UShortArray.m2035getMh2AYeg(maxBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(maxBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(UShort.m1971boximpl(sM2035getMh2AYeg));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                short sM2035getMh2AYeg2 = UShortArray.m2035getMh2AYeg(maxBy, it.nextInt());
                R rInvoke2 = selector.invoke(UShort.m1971boximpl(sM2035getMh2AYeg2));
                if (rInvoke.compareTo(rInvoke2) < 0) {
                    sM2035getMh2AYeg = sM2035getMh2AYeg2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return UShort.m1971boximpl(sM2035getMh2AYeg);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxWith-YmdZ_VM, reason: not valid java name */
    public static final /* synthetic */ UInt m2188maxWithYmdZ_VM(int[] maxWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(maxWith, "$this$maxWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2568maxWithOrNullYmdZ_VM(maxWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxWith-zrEWJaI, reason: not valid java name */
    public static final /* synthetic */ ULong m2190maxWithzrEWJaI(long[] maxWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(maxWith, "$this$maxWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2570maxWithOrNullzrEWJaI(maxWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxWith-XMRcp5o, reason: not valid java name */
    public static final /* synthetic */ UByte m2187maxWithXMRcp5o(byte[] maxWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(maxWith, "$this$maxWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2567maxWithOrNullXMRcp5o(maxWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use maxWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.maxWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: maxWith-eOHTfZs, reason: not valid java name */
    public static final /* synthetic */ UShort m2189maxWitheOHTfZs(short[] maxWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(maxWith, "$this$maxWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2569maxWithOrNulleOHTfZs(maxWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: min--ajY-9A, reason: not valid java name */
    public static final /* synthetic */ UInt m2191minajY9A(int[] min) {
        Intrinsics.checkNotNullParameter(min, "$this$min");
        return UArraysKt___UArraysKt.m2615minOrNullajY9A(min);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: min-QwZRm1k, reason: not valid java name */
    public static final /* synthetic */ ULong m2193minQwZRm1k(long[] min) {
        Intrinsics.checkNotNullParameter(min, "$this$min");
        return UArraysKt___UArraysKt.m2617minOrNullQwZRm1k(min);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: min-GBYM_sE, reason: not valid java name */
    public static final /* synthetic */ UByte m2192minGBYM_sE(byte[] min) {
        Intrinsics.checkNotNullParameter(min, "$this$min");
        return UArraysKt___UArraysKt.m2616minOrNullGBYM_sE(min);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minOrNull()", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: min-rL5Bavg, reason: not valid java name */
    public static final /* synthetic */ UShort m2194minrL5Bavg(short[] min) {
        Intrinsics.checkNotNullParameter(min, "$this$min");
        return UArraysKt___UArraysKt.m2618minOrNullrL5Bavg(min);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use minByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minBy-jgv0xPQ, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> UInt m2197minByjgv0xPQ(int[] minBy, Function1<? super UInt, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(minBy, "$this$minBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (UIntArray.m1854isEmptyimpl(minBy)) {
            return null;
        }
        int iM1851getpVg5ArA = UIntArray.m1851getpVg5ArA(minBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(minBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(UInt.m1785boximpl(iM1851getpVg5ArA));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                int iM1851getpVg5ArA2 = UIntArray.m1851getpVg5ArA(minBy, it.nextInt());
                R rInvoke2 = selector.invoke(UInt.m1785boximpl(iM1851getpVg5ArA2));
                if (rInvoke.compareTo(rInvoke2) > 0) {
                    iM1851getpVg5ArA = iM1851getpVg5ArA2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return UInt.m1785boximpl(iM1851getpVg5ArA);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use minByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minBy-MShoTSo, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> ULong m2196minByMShoTSo(long[] minBy, Function1<? super ULong, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(minBy, "$this$minBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (ULongArray.m1933isEmptyimpl(minBy)) {
            return null;
        }
        long jM1930getsVKNKU = ULongArray.m1930getsVKNKU(minBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(minBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(ULong.m1864boximpl(jM1930getsVKNKU));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                long jM1930getsVKNKU2 = ULongArray.m1930getsVKNKU(minBy, it.nextInt());
                R rInvoke2 = selector.invoke(ULong.m1864boximpl(jM1930getsVKNKU2));
                if (rInvoke.compareTo(rInvoke2) > 0) {
                    jM1930getsVKNKU = jM1930getsVKNKU2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return ULong.m1864boximpl(jM1930getsVKNKU);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use minByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minBy-JOV_ifY, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> UByte m2195minByJOV_ifY(byte[] minBy, Function1<? super UByte, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(minBy, "$this$minBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (UByteArray.m1775isEmptyimpl(minBy)) {
            return null;
        }
        byte bM1772getw2LRezQ = UByteArray.m1772getw2LRezQ(minBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(minBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(UByte.m1708boximpl(bM1772getw2LRezQ));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                byte bM1772getw2LRezQ2 = UByteArray.m1772getw2LRezQ(minBy, it.nextInt());
                R rInvoke2 = selector.invoke(UByte.m1708boximpl(bM1772getw2LRezQ2));
                if (rInvoke.compareTo(rInvoke2) > 0) {
                    bM1772getw2LRezQ = bM1772getw2LRezQ2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return UByte.m1708boximpl(bM1772getw2LRezQ);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.util.Iterator, kotlin.collections.IntIterator] */
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @Deprecated(message = "Use minByOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minByOrNull(selector)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minBy-xTcfx_M, reason: not valid java name */
    public static final /* synthetic */ <R extends Comparable<? super R>> UShort m2198minByxTcfx_M(short[] minBy, Function1<? super UShort, ? extends R> selector) {
        Intrinsics.checkNotNullParameter(minBy, "$this$minBy");
        Intrinsics.checkNotNullParameter(selector, "selector");
        if (UShortArray.m2038isEmptyimpl(minBy)) {
            return null;
        }
        short sM2035getMh2AYeg = UShortArray.m2035getMh2AYeg(minBy, 0);
        int lastIndex = ArraysKt___ArraysKt.getLastIndex(minBy);
        if (lastIndex != 0) {
            R rInvoke = selector.invoke(UShort.m1971boximpl(sM2035getMh2AYeg));
            ?? it = new IntRange(1, lastIndex).iterator();
            while (it.hasNext()) {
                short sM2035getMh2AYeg2 = UShortArray.m2035getMh2AYeg(minBy, it.nextInt());
                R rInvoke2 = selector.invoke(UShort.m1971boximpl(sM2035getMh2AYeg2));
                if (rInvoke.compareTo(rInvoke2) > 0) {
                    sM2035getMh2AYeg = sM2035getMh2AYeg2;
                    rInvoke = rInvoke2;
                }
            }
        }
        return UShort.m1971boximpl(sM2035getMh2AYeg);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minWith-YmdZ_VM, reason: not valid java name */
    public static final /* synthetic */ UInt m2200minWithYmdZ_VM(int[] minWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(minWith, "$this$minWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2624minWithOrNullYmdZ_VM(minWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minWith-zrEWJaI, reason: not valid java name */
    public static final /* synthetic */ ULong m2202minWithzrEWJaI(long[] minWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(minWith, "$this$minWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2626minWithOrNullzrEWJaI(minWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minWith-XMRcp5o, reason: not valid java name */
    public static final /* synthetic */ UByte m2199minWithXMRcp5o(byte[] minWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(minWith, "$this$minWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2623minWithOrNullXMRcp5o(minWith, comparator);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @Deprecated(message = "Use minWithOrNull instead.", replaceWith = @ReplaceWith(expression = "this.minWithOrNull(comparator)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.5", hiddenSince = "1.6", warningSince = "1.4")
    /* JADX INFO: renamed from: minWith-eOHTfZs, reason: not valid java name */
    public static final /* synthetic */ UShort m2201minWitheOHTfZs(short[] minWith, Comparator comparator) {
        Intrinsics.checkNotNullParameter(minWith, "$this$minWith");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        return UArraysKt___UArraysKt.m2625minWithOrNulleOHTfZs(minWith, comparator);
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigDecimal")
    @OverloadResolutionByLambdaReturnType
    public static final BigDecimal sumOfBigDecimal(int[] sumOf, Function1<? super UInt, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "valueOf(this.toLong())");
        int iM1852getSizeimpl = UIntArray.m1852getSizeimpl(sumOf);
        for (int i = 0; i < iM1852getSizeimpl; i++) {
            bigDecimalValueOf = bigDecimalValueOf.add(selector.invoke(UInt.m1785boximpl(UIntArray.m1851getpVg5ArA(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "this.add(other)");
        }
        return bigDecimalValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigDecimal")
    @OverloadResolutionByLambdaReturnType
    public static final BigDecimal sumOfBigDecimal(long[] sumOf, Function1<? super ULong, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "valueOf(this.toLong())");
        int iM1931getSizeimpl = ULongArray.m1931getSizeimpl(sumOf);
        for (int i = 0; i < iM1931getSizeimpl; i++) {
            bigDecimalValueOf = bigDecimalValueOf.add(selector.invoke(ULong.m1864boximpl(ULongArray.m1930getsVKNKU(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "this.add(other)");
        }
        return bigDecimalValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigDecimal")
    @OverloadResolutionByLambdaReturnType
    public static final BigDecimal sumOfBigDecimal(byte[] sumOf, Function1<? super UByte, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "valueOf(this.toLong())");
        int iM1773getSizeimpl = UByteArray.m1773getSizeimpl(sumOf);
        for (int i = 0; i < iM1773getSizeimpl; i++) {
            bigDecimalValueOf = bigDecimalValueOf.add(selector.invoke(UByte.m1708boximpl(UByteArray.m1772getw2LRezQ(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "this.add(other)");
        }
        return bigDecimalValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigDecimal")
    @OverloadResolutionByLambdaReturnType
    public static final BigDecimal sumOfBigDecimal(short[] sumOf, Function1<? super UShort, ? extends BigDecimal> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "valueOf(this.toLong())");
        int iM2036getSizeimpl = UShortArray.m2036getSizeimpl(sumOf);
        for (int i = 0; i < iM2036getSizeimpl; i++) {
            bigDecimalValueOf = bigDecimalValueOf.add(selector.invoke(UShort.m1971boximpl(UShortArray.m2035getMh2AYeg(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigDecimalValueOf, "this.add(other)");
        }
        return bigDecimalValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigInteger")
    @OverloadResolutionByLambdaReturnType
    public static final BigInteger sumOfBigInteger(int[] sumOf, Function1<? super UInt, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "valueOf(this.toLong())");
        int iM1852getSizeimpl = UIntArray.m1852getSizeimpl(sumOf);
        for (int i = 0; i < iM1852getSizeimpl; i++) {
            bigIntegerValueOf = bigIntegerValueOf.add(selector.invoke(UInt.m1785boximpl(UIntArray.m1851getpVg5ArA(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "this.add(other)");
        }
        return bigIntegerValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigInteger")
    @OverloadResolutionByLambdaReturnType
    public static final BigInteger sumOfBigInteger(long[] sumOf, Function1<? super ULong, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "valueOf(this.toLong())");
        int iM1931getSizeimpl = ULongArray.m1931getSizeimpl(sumOf);
        for (int i = 0; i < iM1931getSizeimpl; i++) {
            bigIntegerValueOf = bigIntegerValueOf.add(selector.invoke(ULong.m1864boximpl(ULongArray.m1930getsVKNKU(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "this.add(other)");
        }
        return bigIntegerValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigInteger")
    @OverloadResolutionByLambdaReturnType
    public static final BigInteger sumOfBigInteger(byte[] sumOf, Function1<? super UByte, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "valueOf(this.toLong())");
        int iM1773getSizeimpl = UByteArray.m1773getSizeimpl(sumOf);
        for (int i = 0; i < iM1773getSizeimpl; i++) {
            bigIntegerValueOf = bigIntegerValueOf.add(selector.invoke(UByte.m1708boximpl(UByteArray.m1772getw2LRezQ(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "this.add(other)");
        }
        return bigIntegerValueOf;
    }

    @SinceKotlin(version = "1.4")
    @ExperimentalUnsignedTypes
    @InlineOnly
    @JvmName(name = "sumOfBigInteger")
    @OverloadResolutionByLambdaReturnType
    public static final BigInteger sumOfBigInteger(short[] sumOf, Function1<? super UShort, ? extends BigInteger> selector) {
        Intrinsics.checkNotNullParameter(sumOf, "$this$sumOf");
        Intrinsics.checkNotNullParameter(selector, "selector");
        BigInteger bigIntegerValueOf = BigInteger.valueOf(0L);
        Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "valueOf(this.toLong())");
        int iM2036getSizeimpl = UShortArray.m2036getSizeimpl(sumOf);
        for (int i = 0; i < iM2036getSizeimpl; i++) {
            bigIntegerValueOf = bigIntegerValueOf.add(selector.invoke(UShort.m1971boximpl(UShortArray.m2035getMh2AYeg(sumOf, i))));
            Intrinsics.checkNotNullExpressionValue(bigIntegerValueOf, "this.add(other)");
        }
        return bigIntegerValueOf;
    }
}
