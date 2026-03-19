package vn.tungdx.mediapicker.utils;

import android.os.FileObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* JADX INFO: loaded from: classes6.dex */
public class RecursiveFileObserver extends FileObserver {
    public OnFileCreatedListener mFileCreatedListener;
    public int mMask;
    public List<SingleFileObserver> mObservers;
    public String mPath;

    public interface OnFileCreatedListener {
        void onFileCreate(File file);
    }

    public RecursiveFileObserver(String str) {
        this(str, 4095);
    }

    public RecursiveFileObserver(String str, int i) {
        super(str, i);
        this.mPath = str;
        this.mMask = i;
    }

    public void setFileCreatedListener(OnFileCreatedListener onFileCreatedListener) {
        this.mFileCreatedListener = onFileCreatedListener;
    }

    @Override // android.os.FileObserver
    public void startWatching() {
        int i;
        if (this.mObservers != null) {
            return;
        }
        this.mObservers = new ArrayList();
        Stack stack = new Stack();
        stack.push(this.mPath);
        while (true) {
            i = 0;
            if (stack.empty()) {
                break;
            }
            String str = (String) stack.pop();
            this.mObservers.add(new SingleFileObserver(str, this.mMask));
            File[] fileArrListFiles = new File(str).listFiles();
            if (fileArrListFiles != null) {
                while (i < fileArrListFiles.length) {
                    if (fileArrListFiles[i].isDirectory() && !fileArrListFiles[i].getName().equals(".") && !fileArrListFiles[i].getName().equals("..")) {
                        stack.push(fileArrListFiles[i].getPath());
                    }
                    i++;
                }
            }
        }
        while (i < this.mObservers.size()) {
            this.mObservers.get(i).startWatching();
            i++;
        }
    }

    @Override // android.os.FileObserver
    public void stopWatching() {
        if (this.mObservers == null) {
            return;
        }
        for (int i = 0; i < this.mObservers.size(); i++) {
            this.mObservers.get(i).stopWatching();
        }
        this.mObservers.clear();
        this.mObservers = null;
    }

    @Override // android.os.FileObserver
    public void onEvent(int i, String str) {
        OnFileCreatedListener onFileCreatedListener;
        if (i != 256 || (onFileCreatedListener = this.mFileCreatedListener) == null) {
            return;
        }
        onFileCreatedListener.onFileCreate(new File(str));
    }

    public class SingleFileObserver extends FileObserver {
        public String mPath;

        public SingleFileObserver(String str, int i) {
            super(str, i);
            this.mPath = str;
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, String str) {
            RecursiveFileObserver.this.onEvent(i, this.mPath + "/" + str);
        }
    }
}
