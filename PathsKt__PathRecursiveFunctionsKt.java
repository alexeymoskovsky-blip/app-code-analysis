package kotlin.io.path;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: PathRecursiveFunctions.kt */
/* JADX INFO: loaded from: classes7.dex */
public class PathsKt__PathRecursiveFunctionsKt extends PathsKt__PathReadWriteKt {

    /* JADX INFO: compiled from: PathRecursiveFunctions.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[CopyActionResult.values().length];
            try {
                iArr[CopyActionResult.CONTINUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CopyActionResult.TERMINATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CopyActionResult.SKIP_SUBTREE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[OnErrorResult.values().length];
            try {
                iArr2[OnErrorResult.TERMINATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[OnErrorResult.SKIP_SUBTREE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static /* synthetic */ Path copyToRecursively$default(Path path, Path path2, Function3 function3, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            function3 = new Function3() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively.1
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj2, Object obj3, Object obj4) {
                    return invoke(DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(obj2), DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(obj3), (Exception) obj4);
                }

                @NotNull
                public final Void invoke(@NotNull Path path3, @NotNull Path path4, @NotNull Exception exception) throws Exception {
                    Intrinsics.checkNotNullParameter(path3, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(path4, "<anonymous parameter 1>");
                    Intrinsics.checkNotNullParameter(exception, "exception");
                    throw exception;
                }
            };
        }
        return copyToRecursively(path, path2, (Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult>) function3, z, z2);
    }

    @SinceKotlin(version = "1.8")
    @ExperimentalPathApi
    @NotNull
    public static final Path copyToRecursively(@NotNull Path path, @NotNull Path target, @NotNull Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> onError, final boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(onError, "onError");
        if (z2) {
            return copyToRecursively(path, target, onError, z, new Function3<CopyActionContext, Path, Path, CopyActionResult>() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ CopyActionResult invoke(CopyActionContext copyActionContext, Path path2, Path path3) {
                    return invoke2(copyActionContext, DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path2), DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path3));
                }

                @NotNull
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CopyActionResult invoke2(@NotNull CopyActionContext copyToRecursively, @NotNull Path src, @NotNull Path dst) throws IllegalAccessException, FileSystemException, InvocationTargetException {
                    Intrinsics.checkNotNullParameter(copyToRecursively, "$this$copyToRecursively");
                    Intrinsics.checkNotNullParameter(src, "src");
                    Intrinsics.checkNotNullParameter(dst, "dst");
                    LinkOption[] linkOptions = LinkFollowing.INSTANCE.toLinkOptions(z);
                    boolean zIsDirectory = Files.isDirectory(dst, (LinkOption[]) Arrays.copyOf(new LinkOption[]{LinkOption.NOFOLLOW_LINKS}, 1));
                    LinkOption[] linkOptionArr = (LinkOption[]) Arrays.copyOf(linkOptions, linkOptions.length);
                    if (!Files.isDirectory(src, (LinkOption[]) Arrays.copyOf(linkOptionArr, linkOptionArr.length)) || !zIsDirectory) {
                        if (zIsDirectory) {
                            PathsKt__PathRecursiveFunctionsKt.deleteRecursively(dst);
                        }
                        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                        spreadBuilder.addSpread(linkOptions);
                        spreadBuilder.add(StandardCopyOption.REPLACE_EXISTING);
                        CopyOption[] copyOptionArr = (CopyOption[]) spreadBuilder.toArray(new CopyOption[spreadBuilder.size()]);
                        Intrinsics.checkNotNullExpressionValue(Files.copy(src, dst, (CopyOption[]) Arrays.copyOf(copyOptionArr, copyOptionArr.length)), "copy(this, target, *options)");
                    }
                    return CopyActionResult.CONTINUE;
                }
            });
        }
        return copyToRecursively$default(path, target, onError, z, (Function3) null, 8, (Object) null);
    }

    public static /* synthetic */ Path copyToRecursively$default(Path path, Path path2, Function3 function3, final boolean z, Function3 function32, int i, Object obj) {
        if ((i & 2) != 0) {
            function3 = new Function3() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively.3
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj2, Object obj3, Object obj4) {
                    return invoke(DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(obj2), DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(obj3), (Exception) obj4);
                }

                @NotNull
                public final Void invoke(@NotNull Path path3, @NotNull Path path4, @NotNull Exception exception) throws Exception {
                    Intrinsics.checkNotNullParameter(path3, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(path4, "<anonymous parameter 1>");
                    Intrinsics.checkNotNullParameter(exception, "exception");
                    throw exception;
                }
            };
        }
        if ((i & 8) != 0) {
            function32 = new Function3<CopyActionContext, Path, Path, CopyActionResult>() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ CopyActionResult invoke(CopyActionContext copyActionContext, Path path3, Path path4) {
                    return invoke2(copyActionContext, DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path3), DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path4));
                }

                @NotNull
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CopyActionResult invoke2(@NotNull CopyActionContext copyActionContext, @NotNull Path src, @NotNull Path dst) {
                    Intrinsics.checkNotNullParameter(copyActionContext, "$this$null");
                    Intrinsics.checkNotNullParameter(src, "src");
                    Intrinsics.checkNotNullParameter(dst, "dst");
                    return copyActionContext.copyToIgnoringExistingDirectory(src, dst, z);
                }
            };
        }
        return copyToRecursively(path, path2, (Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult>) function3, z, (Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult>) function32);
    }

    @SinceKotlin(version = "1.8")
    @ExperimentalPathApi
    @NotNull
    public static final Path copyToRecursively(@NotNull final Path path, @NotNull final Path target, @NotNull final Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> onError, boolean z, @NotNull final Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> copyAction) throws IOException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(onError, "onError");
        Intrinsics.checkNotNullParameter(copyAction, "copyAction");
        LinkOption[] linkOptions = LinkFollowing.INSTANCE.toLinkOptions(z);
        LinkOption[] linkOptionArr = (LinkOption[]) Arrays.copyOf(linkOptions, linkOptions.length);
        if (!Files.exists(path, (LinkOption[]) Arrays.copyOf(linkOptionArr, linkOptionArr.length))) {
            PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline20.m();
            throw PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline18.m(path.toString(), target.toString(), "The source file doesn't exist.");
        }
        boolean zStartsWith = false;
        if (Files.exists(path, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0)) && (z || !Files.isSymbolicLink(path))) {
            boolean z2 = Files.exists(target, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0)) && !Files.isSymbolicLink(target);
            if (!z2 || !Files.isSameFile(path, target)) {
                Path realPath = path.toRealPath(new LinkOption[0]);
                if (z2) {
                    zStartsWith = target.toRealPath(new LinkOption[0]).startsWith(realPath);
                } else {
                    Path parent = target.getParent();
                    if (parent != null && Files.exists(parent, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0)) && parent.toRealPath(new LinkOption[0]).startsWith(realPath)) {
                        zStartsWith = true;
                    }
                }
                if (zStartsWith) {
                    ExceptionsCollector$$ExternalSyntheticApiModelOutline5.m();
                    throw PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline19.m(path.toString(), target.toString(), "Recursively copying a directory into its subdirectory is prohibited.");
                }
            }
        }
        PathsKt__PathUtilsKt.visitFileTree$default(path, 0, z, new Function1<FileVisitorBuilder, Unit>() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            /* JADX INFO: renamed from: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5$1, reason: invalid class name */
            /* JADX INFO: compiled from: PathRecursiveFunctions.kt */
            public /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function2<Path, BasicFileAttributes, FileVisitResult> {
                public final /* synthetic */ Function3<CopyActionContext, Path, Path, CopyActionResult> $copyAction;
                public final /* synthetic */ Function3<Path, Path, Exception, OnErrorResult> $onError;
                public final /* synthetic */ Path $target;
                public final /* synthetic */ Path $this_copyToRecursively;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass1(Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> function3, Path path, Path path2, Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function32) {
                    super(2, Intrinsics.Kotlin.class, "copy", "copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/attribute/BasicFileAttributes;)Ljava/nio/file/FileVisitResult;", 0);
                    this.$copyAction = function3;
                    this.$this_copyToRecursively = path;
                    this.$target = path2;
                    this.$onError = function32;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ FileVisitResult invoke(Path path, BasicFileAttributes basicFileAttributes) {
                    return invoke2(DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path), PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5$1$$ExternalSyntheticApiModelOutline0.m(basicFileAttributes));
                }

                @NotNull
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final FileVisitResult invoke2(@NotNull Path p0, @NotNull BasicFileAttributes p1) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p1, "p1");
                    return PathsKt__PathRecursiveFunctionsKt.copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(this.$copyAction, this.$this_copyToRecursively, this.$target, this.$onError, p0, p1);
                }
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FileVisitorBuilder fileVisitorBuilder) {
                invoke2(fileVisitorBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5$2, reason: invalid class name */
            /* JADX INFO: compiled from: PathRecursiveFunctions.kt */
            public /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function2<Path, BasicFileAttributes, FileVisitResult> {
                public final /* synthetic */ Function3<CopyActionContext, Path, Path, CopyActionResult> $copyAction;
                public final /* synthetic */ Function3<Path, Path, Exception, OnErrorResult> $onError;
                public final /* synthetic */ Path $target;
                public final /* synthetic */ Path $this_copyToRecursively;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass2(Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> function3, Path path, Path path2, Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function32) {
                    super(2, Intrinsics.Kotlin.class, "copy", "copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/attribute/BasicFileAttributes;)Ljava/nio/file/FileVisitResult;", 0);
                    this.$copyAction = function3;
                    this.$this_copyToRecursively = path;
                    this.$target = path2;
                    this.$onError = function32;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ FileVisitResult invoke(Path path, BasicFileAttributes basicFileAttributes) {
                    return invoke2(DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path), PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5$1$$ExternalSyntheticApiModelOutline0.m(basicFileAttributes));
                }

                @NotNull
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final FileVisitResult invoke2(@NotNull Path p0, @NotNull BasicFileAttributes p1) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p1, "p1");
                    return PathsKt__PathRecursiveFunctionsKt.copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(this.$copyAction, this.$this_copyToRecursively, this.$target, this.$onError, p0, p1);
                }
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull FileVisitorBuilder visitFileTree) {
                Intrinsics.checkNotNullParameter(visitFileTree, "$this$visitFileTree");
                visitFileTree.onPreVisitDirectory(new AnonymousClass1(copyAction, path, target, onError));
                visitFileTree.onVisitFile(new AnonymousClass2(copyAction, path, target, onError));
                visitFileTree.onVisitFileFailed(new AnonymousClass3(onError, path, target));
                final Function3<Path, Path, Exception, OnErrorResult> function3 = onError;
                final Path path2 = path;
                final Path path3 = target;
                visitFileTree.onPostVisitDirectory(new Function2<Path, IOException, FileVisitResult>() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively.5.4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ FileVisitResult invoke(Path path4, IOException iOException) {
                        return invoke2(DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path4), iOException);
                    }

                    @NotNull
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final FileVisitResult invoke2(@NotNull Path directory, @Nullable IOException iOException) {
                        Intrinsics.checkNotNullParameter(directory, "directory");
                        if (iOException != null) {
                            return PathsKt__PathRecursiveFunctionsKt.copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(function3, path2, path3, directory, iOException);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

            /* JADX INFO: renamed from: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5$3, reason: invalid class name */
            /* JADX INFO: compiled from: PathRecursiveFunctions.kt */
            public /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function2<Path, Exception, FileVisitResult> {
                public final /* synthetic */ Function3<Path, Path, Exception, OnErrorResult> $onError;
                public final /* synthetic */ Path $target;
                public final /* synthetic */ Path $this_copyToRecursively;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass3(Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function3, Path path, Path path2) {
                    super(2, Intrinsics.Kotlin.class, "error", "copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(Lkotlin/jvm/functions/Function3;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/lang/Exception;)Ljava/nio/file/FileVisitResult;", 0);
                    this.$onError = function3;
                    this.$this_copyToRecursively = path;
                    this.$target = path2;
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ FileVisitResult invoke(Path path, Exception exc) {
                    return invoke2(DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(path), exc);
                }

                @NotNull
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final FileVisitResult invoke2(@NotNull Path p0, @NotNull Exception p1) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    Intrinsics.checkNotNullParameter(p1, "p1");
                    return PathsKt__PathRecursiveFunctionsKt.copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(this.$onError, this.$this_copyToRecursively, this.$target, p0, p1);
                }
            }
        }, 1, (Object) null);
        return target;
    }

    public static final Path copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(Path path, Path path2, Path path3) {
        Path pathResolve = path2.resolve(PathsKt__PathUtilsKt.relativeTo(path3, path));
        Intrinsics.checkNotNullExpressionValue(pathResolve, "target.resolve(relativePath)");
        return pathResolve;
    }

    public static final FileVisitResult copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function3, Path path, Path path2, Path path3, Exception exc) {
        return toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(function3.invoke(path3, copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(path, path2, path3), exc));
    }

    public static final FileVisitResult copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> function3, Path path, Path path2, Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function32, Path path3, BasicFileAttributes basicFileAttributes) {
        try {
            return toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(function3.invoke(DefaultCopyActionContext.INSTANCE, path3, copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(path, path2, path3)));
        } catch (Exception e) {
            return copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(function32, path, path2, path3, e);
        }
    }

    @ExperimentalPathApi
    public static final FileVisitResult toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(CopyActionResult copyActionResult) {
        int i = WhenMappings.$EnumSwitchMapping$0[copyActionResult.ordinal()];
        if (i == 1) {
            return FileVisitResult.CONTINUE;
        }
        if (i == 2) {
            return FileVisitResult.TERMINATE;
        }
        if (i == 3) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        throw new NoWhenBranchMatchedException();
    }

    @ExperimentalPathApi
    public static final FileVisitResult toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(OnErrorResult onErrorResult) {
        int i = WhenMappings.$EnumSwitchMapping$1[onErrorResult.ordinal()];
        if (i == 1) {
            return FileVisitResult.TERMINATE;
        }
        if (i == 2) {
            return FileVisitResult.SKIP_SUBTREE;
        }
        throw new NoWhenBranchMatchedException();
    }

    @SinceKotlin(version = "1.8")
    @ExperimentalPathApi
    public static final void deleteRecursively(@NotNull Path path) throws IllegalAccessException, FileSystemException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(path, "<this>");
        List<Exception> listDeleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt = deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(path);
        if (listDeleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt.isEmpty()) {
            return;
        }
        FileSystemException fileSystemExceptionM = ExceptionsCollector$$ExternalSyntheticApiModelOutline4.m("Failed to delete one or more files. See suppressed exceptions for details.");
        Iterator<T> it = listDeleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt.iterator();
        while (it.hasNext()) {
            ExceptionsKt__ExceptionsKt.addSuppressed(fileSystemExceptionM, (Exception) it.next());
        }
        throw fileSystemExceptionM;
    }

    public static final List<Exception> deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(Path path) {
        DirectoryStream directoryStreamM;
        boolean z = false;
        boolean z2 = true;
        ExceptionsCollector exceptionsCollector = new ExceptionsCollector(0, 1, null);
        Path parent = path.getParent();
        if (parent != null) {
            try {
                directoryStreamM = Files.newDirectoryStream(parent);
            } catch (Throwable unused) {
                directoryStreamM = PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline1.m(null);
            }
            if (directoryStreamM != null) {
                try {
                    DirectoryStream directoryStreamM2 = PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline1.m(directoryStreamM);
                    if (PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline2.m(directoryStreamM2)) {
                        exceptionsCollector.setPath(parent);
                        SecureDirectoryStream secureDirectoryStreamM = PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline3.m(directoryStreamM2);
                        Path fileName = path.getFileName();
                        Intrinsics.checkNotNullExpressionValue(fileName, "this.fileName");
                        handleEntry$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStreamM, fileName, exceptionsCollector);
                    } else {
                        z = true;
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(directoryStreamM, null);
                    z2 = z;
                } finally {
                }
            }
        }
        if (z2) {
            insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(path, exceptionsCollector);
        }
        return exceptionsCollector.getCollectedExceptions();
    }

    public static final void collectIfThrows$PathsKt__PathRecursiveFunctionsKt(ExceptionsCollector exceptionsCollector, Function0<Unit> function0) {
        try {
            function0.invoke();
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
    }

    public static final <R> R tryIgnoreNoSuchFileException$PathsKt__PathRecursiveFunctionsKt(Function0<? extends R> function0) {
        try {
            return function0.invoke();
        } catch (NoSuchFileException unused) {
            return null;
        }
    }

    public static final void handleEntry$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, ExceptionsCollector exceptionsCollector) {
        exceptionsCollector.enterEntry(path);
        try {
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
        if (isDirectory$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path, LinkOption.NOFOLLOW_LINKS)) {
            int totalExceptions = exceptionsCollector.getTotalExceptions();
            enterDirectory$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path, exceptionsCollector);
            if (totalExceptions == exceptionsCollector.getTotalExceptions()) {
                secureDirectoryStream.deleteDirectory(path);
                Unit unit = Unit.INSTANCE;
            }
            exceptionsCollector.exitEntry(path);
        }
        secureDirectoryStream.deleteFile(path);
        Unit unit2 = Unit.INSTANCE;
        exceptionsCollector.exitEntry(path);
    }

    public static final void enterDirectory$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, ExceptionsCollector exceptionsCollector) {
        SecureDirectoryStream secureDirectoryStreamNewDirectoryStream;
        try {
            try {
                secureDirectoryStreamNewDirectoryStream = secureDirectoryStream.newDirectoryStream(path, LinkOption.NOFOLLOW_LINKS);
            } catch (NoSuchFileException unused) {
                secureDirectoryStreamNewDirectoryStream = null;
            }
            if (secureDirectoryStreamNewDirectoryStream == null) {
                return;
            }
            try {
                SecureDirectoryStream secureDirectoryStreamM = PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline3.m(secureDirectoryStreamNewDirectoryStream);
                Iterator it = secureDirectoryStreamM.iterator();
                while (it.hasNext()) {
                    Path fileName = DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(it.next()).getFileName();
                    Intrinsics.checkNotNullExpressionValue(fileName, "entry.fileName");
                    handleEntry$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStreamM, fileName, exceptionsCollector);
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(secureDirectoryStreamNewDirectoryStream, null);
            } finally {
            }
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
    }

    public static final boolean isDirectory$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, LinkOption... linkOptionArr) {
        Boolean boolValueOf;
        try {
            boolValueOf = Boolean.valueOf(PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline12.m(secureDirectoryStream.getFileAttributeView(path, PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline10.m(), (LinkOption[]) Arrays.copyOf(linkOptionArr, linkOptionArr.length))).readAttributes().isDirectory());
        } catch (NoSuchFileException unused) {
            boolValueOf = null;
        }
        if (boolValueOf != null) {
            return boolValueOf.booleanValue();
        }
        return false;
    }

    public static final void insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(Path path, ExceptionsCollector exceptionsCollector) {
        try {
            if (Files.isDirectory(path, (LinkOption[]) Arrays.copyOf(new LinkOption[]{LinkOption.NOFOLLOW_LINKS}, 1))) {
                int totalExceptions = exceptionsCollector.getTotalExceptions();
                insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(path, exceptionsCollector);
                if (totalExceptions == exceptionsCollector.getTotalExceptions()) {
                    Files.deleteIfExists(path);
                }
            } else {
                Files.deleteIfExists(path);
            }
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
    }

    public static final void insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(Path path, ExceptionsCollector exceptionsCollector) {
        DirectoryStream directoryStreamNewDirectoryStream;
        try {
            try {
                directoryStreamNewDirectoryStream = Files.newDirectoryStream(path);
            } catch (NoSuchFileException unused) {
                directoryStreamNewDirectoryStream = null;
            }
            if (directoryStreamNewDirectoryStream == null) {
                return;
            }
            try {
                Iterator it = PathsKt__PathRecursiveFunctionsKt$$ExternalSyntheticApiModelOutline1.m(directoryStreamNewDirectoryStream).iterator();
                while (it.hasNext()) {
                    Path entry = DirectoryEntriesReader$$ExternalSyntheticApiModelOutline1.m(it.next());
                    Intrinsics.checkNotNullExpressionValue(entry, "entry");
                    insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(entry, exceptionsCollector);
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(directoryStreamNewDirectoryStream, null);
            } finally {
            }
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
    }
}
