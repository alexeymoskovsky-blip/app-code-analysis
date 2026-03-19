package vn.tungdx.mediapicker.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.danikula.videocache.sourcestorage.DatabaseSourceInfoStorage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes6.dex */
public class MediaUtils {
    public static final String[] PROJECT_PHOTO = {DatabaseSourceInfoStorage.COLUMN_ID};
    public static final String[] PROJECT_VIDEO = {DatabaseSourceInfoStorage.COLUMN_ID};

    public static Uri getPhotoUri(Cursor cursor) {
        return getMediaUri(cursor, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public static Uri getVideoUri(Cursor cursor) {
        return getMediaUri(cursor, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }

    public static Uri getMediaUri(Cursor cursor, Uri uri) {
        return Uri.withAppendedPath(uri, cursor.getString(cursor.getColumnIndex(DatabaseSourceInfoStorage.COLUMN_ID)));
    }

    public static File createDefaultImageFile() throws IOException {
        String str = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()) + ".jpg";
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!externalStoragePublicDirectory.exists()) {
            externalStoragePublicDirectory.mkdirs();
        }
        return new File(externalStoragePublicDirectory, str);
    }

    public static int getLastImageId(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{DatabaseSourceInfoStorage.COLUMN_ID}, null, null, "_id DESC");
        if (cursorQuery == null) {
            return 0;
        }
        int i = cursorQuery.moveToFirst() ? cursorQuery.getInt(cursorQuery.getColumnIndex(DatabaseSourceInfoStorage.COLUMN_ID)) : 0;
        cursorQuery.close();
        return i;
    }

    public static String checkNull(Context context, int i, File file) {
        String[] strArr = {DatabaseSourceInfoStorage.COLUMN_ID, "_data"};
        String[] strArr2 = {Integer.toString(i)};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursorQuery = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, strArr, "_id>?", strArr2, "_id DESC");
        String path = null;
        if (cursorQuery == null) {
            return null;
        }
        if (cursorQuery.getCount() >= 2) {
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex(DatabaseSourceInfoStorage.COLUMN_ID));
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("_data"));
                if (string.equals(file.getPath())) {
                    contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{Long.toString(i2)});
                    file.delete();
                } else {
                    path = string;
                }
                cursorQuery.moveToNext();
            }
        } else {
            path = file.getPath();
            Log.e("MediaUtils", "Not found duplicate.");
        }
        cursorQuery.close();
        return path;
    }

    public static String getFileExtension(File file) {
        String name = file.getName();
        int iLastIndexOf = name.lastIndexOf(".");
        if (iLastIndexOf == -1) {
            return "";
        }
        return name.substring(iLastIndexOf);
    }

    public static boolean isImageExtension(String str) {
        String[] strArr = {".jpg", ".jpeg"};
        for (int i = 0; i < 2; i++) {
            if (str.equalsIgnoreCase(strArr[i])) {
                return true;
            }
        }
        return false;
    }

    public static String getRealImagePathFromURI(ContentResolver contentResolver, Uri uri) {
        Cursor cursorQuery = contentResolver.query(uri, null, null, null, null);
        if (cursorQuery == null) {
            return uri.getPath();
        }
        cursorQuery.moveToFirst();
        try {
            return cursorQuery.getString(cursorQuery.getColumnIndex("_data"));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getRealVideoPathFromURI(ContentResolver contentResolver, Uri uri) {
        Cursor cursorQuery = contentResolver.query(uri, null, null, null, null);
        if (cursorQuery == null) {
            return uri.getPath();
        }
        cursorQuery.moveToFirst();
        try {
            return cursorQuery.getString(cursorQuery.getColumnIndex("_data"));
        } catch (Exception unused) {
            return null;
        }
    }

    public static void galleryAddPic(Context context, File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }

    public static long getDuration(Context context, String str) throws Throwable {
        long duration;
        MediaPlayer mediaPlayer = null;
        try {
            try {
                MediaPlayer mediaPlayer2 = new MediaPlayer();
                try {
                    mediaPlayer2.setDataSource(context, Uri.parse(str));
                    mediaPlayer2.prepare();
                    duration = mediaPlayer2.getDuration();
                    mediaPlayer2.reset();
                    mediaPlayer2.release();
                } catch (Exception e) {
                    e = e;
                    mediaPlayer = mediaPlayer2;
                    e.printStackTrace();
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }
                    duration = 0;
                } catch (Throwable th) {
                    th = th;
                    mediaPlayer = mediaPlayer2;
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
            return duration;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static long getDuration(Context context, Uri uri) {
        Cursor cursorQuery = MediaStore.Video.query(context.getContentResolver(), uri, new String[]{"duration"});
        if (cursorQuery == null) {
            return 0L;
        }
        cursorQuery.moveToFirst();
        long j = cursorQuery.getLong(cursorQuery.getColumnIndex("duration"));
        cursorQuery.close();
        return j;
    }

    public static Bitmap decodeSampledBitmapFromFile(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }
}
