package com.qiyukf.module.zip4j;

import com.qiyukf.module.zip4j.exception.ZipException;
import com.qiyukf.module.zip4j.headers.HeaderReader;
import com.qiyukf.module.zip4j.headers.HeaderUtil;
import com.qiyukf.module.zip4j.headers.HeaderWriter;
import com.qiyukf.module.zip4j.io.inputstream.NumberedSplitRandomAccessFile;
import com.qiyukf.module.zip4j.io.inputstream.ZipInputStream;
import com.qiyukf.module.zip4j.model.FileHeader;
import com.qiyukf.module.zip4j.model.ZipModel;
import com.qiyukf.module.zip4j.model.ZipParameters;
import com.qiyukf.module.zip4j.model.enums.RandomAccessFileMode;
import com.qiyukf.module.zip4j.progress.ProgressMonitor;
import com.qiyukf.module.zip4j.progress.enums.State;
import com.qiyukf.module.zip4j.tasks.AddFilesToZipTask;
import com.qiyukf.module.zip4j.tasks.AddFolderToZipTask;
import com.qiyukf.module.zip4j.tasks.AddStreamToZipTask;
import com.qiyukf.module.zip4j.tasks.AsyncZipTask;
import com.qiyukf.module.zip4j.tasks.ExtractAllFilesTask;
import com.qiyukf.module.zip4j.tasks.ExtractFileTask;
import com.qiyukf.module.zip4j.tasks.MergeSplitZipFileTask;
import com.qiyukf.module.zip4j.tasks.RemoveFilesFromZipTask;
import com.qiyukf.module.zip4j.tasks.RenameFilesTask;
import com.qiyukf.module.zip4j.tasks.SetCommentTask;
import com.qiyukf.module.zip4j.util.FileUtils;
import com.qiyukf.module.zip4j.util.InternalZipConstants;
import com.qiyukf.module.zip4j.util.RawIO;
import com.qiyukf.module.zip4j.util.UnzipUtil;
import com.qiyukf.module.zip4j.util.Zip4jUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes6.dex */
public class ZipFile {
    private Charset charset;
    private ExecutorService executorService;
    private HeaderWriter headerWriter;
    private boolean isEncrypted;
    private char[] password;
    private ProgressMonitor progressMonitor;
    private boolean runInThread;
    private ThreadFactory threadFactory;
    private File zipFile;
    private ZipModel zipModel;

    public ZipFile(String str) {
        this(new File(str), (char[]) null);
    }

    public ZipFile(String str, char[] cArr) {
        this(new File(str), cArr);
    }

    public ZipFile(File file) {
        this(file, (char[]) null);
    }

    public ZipFile(File file, char[] cArr) {
        this.headerWriter = new HeaderWriter();
        this.charset = InternalZipConstants.CHARSET_UTF_8;
        this.zipFile = file;
        this.password = cArr;
        this.runInThread = false;
        this.progressMonitor = new ProgressMonitor();
    }

    public void createSplitZipFile(List<File> list, ZipParameters zipParameters, boolean z, long j) throws ZipException {
        if (this.zipFile.exists()) {
            throw new ZipException("zip file: " + this.zipFile + " already exists. To add files to existing zip file use addFile method");
        }
        if (list == null || list.size() == 0) {
            throw new ZipException("input file List is null, cannot create zip file");
        }
        createNewZipModel();
        this.zipModel.setSplitArchive(z);
        this.zipModel.setSplitLength(j);
        new AddFilesToZipTask(this.zipModel, this.password, this.headerWriter, buildAsyncParameters()).execute(new AddFilesToZipTask.AddFilesToZipTaskParameters(list, zipParameters, this.charset));
    }

    public void createSplitZipFileFromFolder(File file, ZipParameters zipParameters, boolean z, long j) throws ZipException {
        if (file == null) {
            throw new ZipException("folderToAdd is null, cannot create zip file from folder");
        }
        if (zipParameters == null) {
            throw new ZipException("input parameters are null, cannot create zip file from folder");
        }
        if (this.zipFile.exists()) {
            throw new ZipException("zip file: " + this.zipFile + " already exists. To add files to existing zip file use addFolder method");
        }
        createNewZipModel();
        this.zipModel.setSplitArchive(z);
        if (z) {
            this.zipModel.setSplitLength(j);
        }
        addFolder(file, zipParameters, false);
    }

    public void addFile(String str) throws ZipException {
        addFile(str, new ZipParameters());
    }

    public void addFile(String str, ZipParameters zipParameters) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("file to add is null or empty");
        }
        addFiles(Collections.singletonList(new File(str)), zipParameters);
    }

    public void addFile(File file) throws ZipException {
        addFiles(Collections.singletonList(file), new ZipParameters());
    }

    public void addFile(File file, ZipParameters zipParameters) throws ZipException {
        addFiles(Collections.singletonList(file), zipParameters);
    }

    public void addFiles(List<File> list) throws ZipException {
        addFiles(list, new ZipParameters());
    }

    public void addFiles(List<File> list, ZipParameters zipParameters) throws ZipException {
        if (list == null || list.size() == 0) {
            throw new ZipException("input file List is null or empty");
        }
        if (zipParameters == null) {
            throw new ZipException("input parameters are null");
        }
        if (this.progressMonitor.getState() == State.BUSY) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }
        readZipInfo();
        if (this.zipModel == null) {
            throw new ZipException("internal error: zip model is null");
        }
        if (this.zipFile.exists() && this.zipModel.isSplitArchive()) {
            throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files");
        }
        new AddFilesToZipTask(this.zipModel, this.password, this.headerWriter, buildAsyncParameters()).execute(new AddFilesToZipTask.AddFilesToZipTaskParameters(list, zipParameters, this.charset));
    }

    public void addFolder(File file) throws ZipException {
        addFolder(file, new ZipParameters());
    }

    public void addFolder(File file, ZipParameters zipParameters) throws ZipException {
        if (file == null) {
            throw new ZipException("input path is null, cannot add folder to zip file");
        }
        if (!file.exists()) {
            throw new ZipException("folder does not exist");
        }
        if (!file.isDirectory()) {
            throw new ZipException("input folder is not a directory");
        }
        if (!file.canRead()) {
            throw new ZipException("cannot read input folder");
        }
        if (zipParameters == null) {
            throw new ZipException("input parameters are null, cannot add folder to zip file");
        }
        addFolder(file, zipParameters, true);
    }

    private void addFolder(File file, ZipParameters zipParameters, boolean z) throws ZipException {
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null) {
            throw new ZipException("internal error: zip model is null");
        }
        if (z && zipModel.isSplitArchive()) {
            throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files");
        }
        new AddFolderToZipTask(this.zipModel, this.password, this.headerWriter, buildAsyncParameters()).execute(new AddFolderToZipTask.AddFolderToZipTaskParameters(file, zipParameters, this.charset));
    }

    public void addStream(InputStream inputStream, ZipParameters zipParameters) throws ZipException {
        if (inputStream == null) {
            throw new ZipException("inputstream is null, cannot add file to zip");
        }
        if (zipParameters == null) {
            throw new ZipException("zip parameters are null");
        }
        setRunInThread(false);
        readZipInfo();
        if (this.zipModel == null) {
            throw new ZipException("internal error: zip model is null");
        }
        if (this.zipFile.exists() && this.zipModel.isSplitArchive()) {
            throw new ZipException("Zip file already exists. Zip file format does not allow updating split/spanned files");
        }
        new AddStreamToZipTask(this.zipModel, this.password, this.headerWriter, buildAsyncParameters()).execute(new AddStreamToZipTask.AddStreamToZipTaskParameters(inputStream, zipParameters, this.charset));
    }

    public void extractAll(String str) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("output path is null or invalid");
        }
        if (!Zip4jUtil.createDirectoryIfNotExists(new File(str))) {
            throw new ZipException("invalid output path");
        }
        if (this.zipModel == null) {
            readZipInfo();
        }
        if (this.zipModel == null) {
            throw new ZipException("Internal error occurred when extracting zip file");
        }
        if (this.progressMonitor.getState() == State.BUSY) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }
        new ExtractAllFilesTask(this.zipModel, this.password, buildAsyncParameters()).execute(new ExtractAllFilesTask.ExtractAllFilesTaskParameters(str, this.charset));
    }

    public void extractFile(FileHeader fileHeader, String str) throws ZipException {
        extractFile(fileHeader, str, (String) null);
    }

    public void extractFile(FileHeader fileHeader, String str, String str2) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input file header is null, cannot extract file");
        }
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("destination path is empty or null, cannot extract file");
        }
        if (this.progressMonitor.getState() == State.BUSY) {
            throw new ZipException("invalid operation - Zip4j is in busy state");
        }
        readZipInfo();
        new ExtractFileTask(this.zipModel, this.password, buildAsyncParameters()).execute(new ExtractFileTask.ExtractFileTaskParameters(str, fileHeader, str2, this.charset));
    }

    public void extractFile(String str, String str2) throws ZipException {
        extractFile(str, str2, (String) null);
    }

    public void extractFile(String str, String str2, String str3) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("file to extract is null or empty, cannot extract file");
        }
        readZipInfo();
        FileHeader fileHeader = HeaderUtil.getFileHeader(this.zipModel, str);
        if (fileHeader == null) {
            throw new ZipException("No file found with name " + str + " in zip file", ZipException.Type.FILE_NOT_FOUND);
        }
        extractFile(fileHeader, str2, str3);
    }

    public List<FileHeader> getFileHeaders() throws ZipException {
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null || zipModel.getCentralDirectory() == null) {
            return Collections.emptyList();
        }
        return this.zipModel.getCentralDirectory().getFileHeaders();
    }

    public FileHeader getFileHeader(String str) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("input file name is emtpy or null, cannot get FileHeader");
        }
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null || zipModel.getCentralDirectory() == null) {
            return null;
        }
        return HeaderUtil.getFileHeader(this.zipModel, str);
    }

    public boolean isEncrypted() throws ZipException {
        if (this.zipModel == null) {
            readZipInfo();
            if (this.zipModel == null) {
                throw new ZipException("Zip Model is null");
            }
        }
        if (this.zipModel.getCentralDirectory() == null || this.zipModel.getCentralDirectory().getFileHeaders() == null) {
            throw new ZipException("invalid zip file");
        }
        Iterator<FileHeader> it = this.zipModel.getCentralDirectory().getFileHeaders().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FileHeader next = it.next();
            if (next != null && next.isEncrypted()) {
                this.isEncrypted = true;
                break;
            }
        }
        return this.isEncrypted;
    }

    public boolean isSplitArchive() throws ZipException {
        if (this.zipModel == null) {
            readZipInfo();
            if (this.zipModel == null) {
                throw new ZipException("Zip Model is null");
            }
        }
        return this.zipModel.isSplitArchive();
    }

    public void removeFile(FileHeader fileHeader) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input file header is null, cannot remove file");
        }
        removeFile(fileHeader.getFileName());
    }

    public void removeFile(String str) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("file name is empty or null, cannot remove file");
        }
        removeFiles(Collections.singletonList(str));
    }

    public void removeFiles(List<String> list) throws ZipException {
        if (list == null) {
            throw new ZipException("fileNames list is null");
        }
        if (list.isEmpty()) {
            return;
        }
        if (this.zipModel == null) {
            readZipInfo();
        }
        if (this.zipModel.isSplitArchive()) {
            throw new ZipException("Zip file format does not allow updating split/spanned files");
        }
        new RemoveFilesFromZipTask(this.zipModel, this.headerWriter, buildAsyncParameters()).execute(new RemoveFilesFromZipTask.RemoveFilesFromZipTaskParameters(list, this.charset));
    }

    public void renameFile(FileHeader fileHeader, String str) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("File header is null");
        }
        renameFile(fileHeader.getFileName(), str);
    }

    public void renameFile(String str, String str2) throws ZipException {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("file name to be changed is null or empty");
        }
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str2)) {
            throw new ZipException("newFileName is null or empty");
        }
        renameFiles(Collections.singletonMap(str, str2));
    }

    public void renameFiles(Map<String, String> map) throws ZipException {
        if (map == null) {
            throw new ZipException("fileNamesMap is null");
        }
        if (map.size() == 0) {
            return;
        }
        readZipInfo();
        if (this.zipModel.isSplitArchive()) {
            throw new ZipException("Zip file format does not allow updating split/spanned files");
        }
        new RenameFilesTask(this.zipModel, this.headerWriter, new RawIO(), this.charset, buildAsyncParameters()).execute(new RenameFilesTask.RenameFilesTaskParameters(map));
    }

    public void mergeSplitFiles(File file) throws ZipException {
        if (file == null) {
            throw new ZipException("outputZipFile is null, cannot merge split files");
        }
        if (file.exists()) {
            throw new ZipException("output Zip File already exists");
        }
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null) {
            throw new ZipException("zip model is null, corrupt zip file?");
        }
        new MergeSplitZipFileTask(zipModel, buildAsyncParameters()).execute(new MergeSplitZipFileTask.MergeSplitZipFileTaskParameters(file, this.charset));
    }

    public void setComment(String str) throws ZipException {
        if (str == null) {
            throw new ZipException("input comment is null, cannot update zip file");
        }
        if (!this.zipFile.exists()) {
            throw new ZipException("zip file does not exist, cannot set comment for zip file");
        }
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null) {
            throw new ZipException("zipModel is null, cannot update zip file");
        }
        if (zipModel.getEndOfCentralDirectoryRecord() == null) {
            throw new ZipException("end of central directory is null, cannot set comment");
        }
        new SetCommentTask(this.zipModel, buildAsyncParameters()).execute(new SetCommentTask.SetCommentTaskTaskParameters(str, this.charset));
    }

    public String getComment() throws ZipException {
        if (!this.zipFile.exists()) {
            throw new ZipException("zip file does not exist, cannot read comment");
        }
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot read comment");
        }
        if (zipModel.getEndOfCentralDirectoryRecord() == null) {
            throw new ZipException("end of central directory record is null, cannot read comment");
        }
        return this.zipModel.getEndOfCentralDirectoryRecord().getComment();
    }

    public ZipInputStream getInputStream(FileHeader fileHeader) throws IOException {
        if (fileHeader == null) {
            throw new ZipException("FileHeader is null, cannot get InputStream");
        }
        readZipInfo();
        ZipModel zipModel = this.zipModel;
        if (zipModel == null) {
            throw new ZipException("zip model is null, cannot get inputstream");
        }
        return UnzipUtil.createZipInputStream(zipModel, fileHeader, this.password);
    }

    public boolean isValidZipFile() {
        if (!this.zipFile.exists()) {
            return false;
        }
        try {
            readZipInfo();
            if (this.zipModel.isSplitArchive()) {
                return verifyAllSplitFilesOfZipExists(getSplitZipFiles());
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public List<File> getSplitZipFiles() throws ZipException {
        readZipInfo();
        return FileUtils.getSplitZipFiles(this.zipModel);
    }

    public void setPassword(char[] cArr) {
        this.password = cArr;
    }

    private void readZipInfo() throws ZipException {
        if (this.zipModel != null) {
            return;
        }
        if (!this.zipFile.exists()) {
            createNewZipModel();
            return;
        }
        if (!this.zipFile.canRead()) {
            throw new ZipException("no read access for the input zip file");
        }
        try {
            RandomAccessFile randomAccessFileInitializeRandomAccessFileForHeaderReading = initializeRandomAccessFileForHeaderReading();
            try {
                ZipModel allHeaders = new HeaderReader().readAllHeaders(randomAccessFileInitializeRandomAccessFileForHeaderReading, this.charset);
                this.zipModel = allHeaders;
                allHeaders.setZipFile(this.zipFile);
                if (randomAccessFileInitializeRandomAccessFileForHeaderReading != null) {
                    randomAccessFileInitializeRandomAccessFileForHeaderReading.close();
                }
            } catch (Throwable th) {
                if (randomAccessFileInitializeRandomAccessFileForHeaderReading != null) {
                    try {
                        randomAccessFileInitializeRandomAccessFileForHeaderReading.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (ZipException e) {
            throw e;
        } catch (IOException e2) {
            throw new ZipException(e2);
        }
    }

    private void createNewZipModel() {
        ZipModel zipModel = new ZipModel();
        this.zipModel = zipModel;
        zipModel.setZipFile(this.zipFile);
    }

    private RandomAccessFile initializeRandomAccessFileForHeaderReading() throws IOException {
        if (FileUtils.isNumberedSplitFile(this.zipFile)) {
            NumberedSplitRandomAccessFile numberedSplitRandomAccessFile = new NumberedSplitRandomAccessFile(this.zipFile, RandomAccessFileMode.READ.getValue(), FileUtils.getAllSortedNumberedSplitFiles(this.zipFile));
            numberedSplitRandomAccessFile.openLastSplitFileForReading();
            return numberedSplitRandomAccessFile;
        }
        return new RandomAccessFile(this.zipFile, RandomAccessFileMode.READ.getValue());
    }

    private AsyncZipTask.AsyncTaskParameters buildAsyncParameters() {
        if (this.runInThread) {
            if (this.threadFactory == null) {
                this.threadFactory = Executors.defaultThreadFactory();
            }
            this.executorService = Executors.newSingleThreadExecutor(this.threadFactory);
        }
        return new AsyncZipTask.AsyncTaskParameters(this.executorService, this.runInThread, this.progressMonitor);
    }

    private boolean verifyAllSplitFilesOfZipExists(List<File> list) {
        Iterator<File> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().exists()) {
                return false;
            }
        }
        return true;
    }

    public ProgressMonitor getProgressMonitor() {
        return this.progressMonitor;
    }

    public boolean isRunInThread() {
        return this.runInThread;
    }

    public void setRunInThread(boolean z) {
        this.runInThread = z;
    }

    public File getFile() {
        return this.zipFile;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) throws IllegalArgumentException {
        if (charset == null) {
            throw new IllegalArgumentException("charset cannot be null");
        }
        this.charset = charset;
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public String toString() {
        return this.zipFile.toString();
    }
}
