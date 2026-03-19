package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.instrument.crashshield.CrashShieldHandler;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ShareApi {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_GRAPH_NODE = "me";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String PHOTOS_EDGE = "photos";
    private static final String TAG = "ShareApi";
    private String graphNode = "me";
    private String message;
    private final ShareContent shareContent;

    public static /* synthetic */ void access$000(ShareApi shareApi, ArrayList arrayList, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        if (CrashShieldHandler.isObjectCrashing(ShareApi.class)) {
            return;
        }
        try {
            shareApi.stageArrayList(arrayList, onMapValueCompleteListener);
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, ShareApi.class);
        }
    }

    public static /* synthetic */ void access$100(ShareApi shareApi, SharePhoto sharePhoto, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        if (CrashShieldHandler.isObjectCrashing(ShareApi.class)) {
            return;
        }
        try {
            shareApi.stagePhoto(sharePhoto, onMapValueCompleteListener);
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, ShareApi.class);
        }
    }

    public static void share(ShareContent shareContent, FacebookCallback<Sharer.Result> facebookCallback) {
        if (CrashShieldHandler.isObjectCrashing(ShareApi.class)) {
            return;
        }
        try {
            new ShareApi(shareContent).share(facebookCallback);
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, ShareApi.class);
        }
    }

    public ShareApi(ShareContent shareContent) {
        this.shareContent = shareContent;
    }

    public String getMessage() {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return null;
        }
        try {
            return this.message;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
            return null;
        }
    }

    public void setMessage(String str) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            this.message = str;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    public String getGraphNode() {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return null;
        }
        try {
            return this.graphNode;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
            return null;
        }
    }

    public void setGraphNode(String str) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            this.graphNode = str;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    public ShareContent getShareContent() {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return null;
        }
        try {
            return this.shareContent;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
            return null;
        }
    }

    public boolean canShare() {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return false;
        }
        try {
            if (getShareContent() == null) {
                return false;
            }
            AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
            if (!AccessToken.isCurrentAccessTokenActive()) {
                return false;
            }
            Set<String> permissions2 = currentAccessToken.getPermissions();
            if (permissions2 != null && permissions2.contains("publish_actions")) {
                return true;
            }
            Log.w(TAG, "The publish_actions permissions are missing, the share will fail unless this app was authorized to publish in another installation.");
            return true;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
            return false;
        }
    }

    public void share(FacebookCallback<Sharer.Result> facebookCallback) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            if (!canShare()) {
                ShareInternalUtility.invokeCallbackWithError(facebookCallback, "Insufficient permissions for sharing content via Api.");
                return;
            }
            ShareContent shareContent = getShareContent();
            try {
                ShareContentValidation.validateForApiShare(shareContent);
                if (shareContent instanceof ShareLinkContent) {
                    shareLinkContent((ShareLinkContent) shareContent, facebookCallback);
                } else if (shareContent instanceof SharePhotoContent) {
                    sharePhotoContent((SharePhotoContent) shareContent, facebookCallback);
                } else if (shareContent instanceof ShareVideoContent) {
                    shareVideoContent((ShareVideoContent) shareContent, facebookCallback);
                }
            } catch (FacebookException e) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, e);
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private String getGraphPath(String str) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return null;
        }
        try {
            return String.format(Locale.ROOT, GRAPH_PATH_FORMAT, URLEncoder.encode(getGraphNode(), "UTF-8"), str);
        } catch (UnsupportedEncodingException unused) {
            return null;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
            return null;
        }
    }

    private void addCommonParameters(Bundle bundle, ShareContent shareContent) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            List<String> peopleIds = shareContent.getPeopleIds();
            if (!Utility.isNullOrEmpty(peopleIds)) {
                bundle.putString("tags", TextUtils.join(", ", peopleIds));
            }
            if (!Utility.isNullOrEmpty(shareContent.getPlaceId())) {
                bundle.putString("place", shareContent.getPlaceId());
            }
            if (!Utility.isNullOrEmpty(shareContent.getPageId())) {
                bundle.putString("page", shareContent.getPageId());
            }
            if (Utility.isNullOrEmpty(shareContent.getRef())) {
                return;
            }
            bundle.putString("ref", shareContent.getRef());
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private static void handleImagesOnAction(Bundle bundle) {
        if (CrashShieldHandler.isObjectCrashing(ShareApi.class)) {
            return;
        }
        try {
            String string = bundle.getString("image");
            if (string != null) {
                try {
                    try {
                        JSONArray jSONArray = new JSONArray(string);
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                            if (jSONObjectOptJSONObject != null) {
                                putImageInBundleWithArrayFormat(bundle, i, jSONObjectOptJSONObject);
                            } else {
                                bundle.putString(String.format(Locale.ROOT, "image[%d][url]", Integer.valueOf(i)), jSONArray.getString(i));
                            }
                        }
                        bundle.remove("image");
                    } catch (JSONException unused) {
                        putImageInBundleWithArrayFormat(bundle, 0, new JSONObject(string));
                        bundle.remove("image");
                    }
                } catch (JSONException unused2) {
                }
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, ShareApi.class);
        }
    }

    private static void putImageInBundleWithArrayFormat(Bundle bundle, int i, JSONObject jSONObject) throws JSONException {
        if (CrashShieldHandler.isObjectCrashing(ShareApi.class)) {
            return;
        }
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                bundle.putString(String.format(Locale.ROOT, "image[%d][%s]", Integer.valueOf(i), next), jSONObject.get(next).toString());
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, ShareApi.class);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v7, types: [T, java.lang.Integer] */
    private void sharePhotoContent(SharePhotoContent sharePhotoContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            final Mutable mutable = new Mutable(0);
            AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
            ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            final ArrayList arrayList3 = new ArrayList();
            GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Integer] */
                @Override // com.facebook.GraphRequest.Callback
                public void onCompleted(GraphResponse graphResponse) {
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    if (jSONObject != null) {
                        arrayList2.add(jSONObject);
                    }
                    if (graphResponse.getError() != null) {
                        arrayList3.add(graphResponse);
                    }
                    mutable.value = Integer.valueOf(((Integer) r0.value).intValue() - 1);
                    if (((Integer) mutable.value).intValue() == 0) {
                        if (!arrayList3.isEmpty()) {
                            ShareInternalUtility.invokeCallbackWithResults(facebookCallback, null, (GraphResponse) arrayList3.get(0));
                        } else {
                            if (arrayList2.isEmpty()) {
                                return;
                            }
                            ShareInternalUtility.invokeCallbackWithResults(facebookCallback, ((JSONObject) arrayList2.get(0)).optString("id"), graphResponse);
                        }
                    }
                }
            };
            try {
                for (SharePhoto sharePhoto : sharePhotoContent.getPhotos()) {
                    try {
                        Bundle sharePhotoCommonParameters = getSharePhotoCommonParameters(sharePhoto, sharePhotoContent);
                        Bitmap bitmap = sharePhoto.getBitmap();
                        Uri imageUrl = sharePhoto.getImageUrl();
                        String caption = sharePhoto.getCaption();
                        if (caption == null) {
                            caption = getMessage();
                        }
                        String str = caption;
                        if (bitmap != null) {
                            arrayList.add(GraphRequest.newUploadPhotoRequest(currentAccessToken, getGraphPath(PHOTOS_EDGE), bitmap, str, sharePhotoCommonParameters, callback));
                        } else if (imageUrl != null) {
                            arrayList.add(GraphRequest.newUploadPhotoRequest(currentAccessToken, getGraphPath(PHOTOS_EDGE), imageUrl, str, sharePhotoCommonParameters, callback));
                        }
                    } catch (JSONException e) {
                        ShareInternalUtility.invokeCallbackWithException(facebookCallback, e);
                        return;
                    }
                }
                mutable.value = Integer.valueOf(((Integer) mutable.value).intValue() + arrayList.size());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((GraphRequest) it.next()).executeAsync();
                }
            } catch (FileNotFoundException e2) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, e2);
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private void shareLinkContent(ShareLinkContent shareLinkContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.2
                @Override // com.facebook.GraphRequest.Callback
                public void onCompleted(GraphResponse graphResponse) {
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    ShareInternalUtility.invokeCallbackWithResults(facebookCallback, jSONObject == null ? null : jSONObject.optString("id"), graphResponse);
                }
            };
            Bundle bundle = new Bundle();
            addCommonParameters(bundle, shareLinkContent);
            bundle.putString("message", getMessage());
            bundle.putString("link", Utility.getUriString(shareLinkContent.getContentUrl()));
            bundle.putString("ref", shareLinkContent.getRef());
            new GraphRequest(AccessToken.getCurrentAccessToken(), getGraphPath("feed"), bundle, HttpMethod.POST, callback).executeAsync();
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private void shareVideoContent(ShareVideoContent shareVideoContent, FacebookCallback<Sharer.Result> facebookCallback) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            try {
                VideoUploader.uploadAsync(shareVideoContent, getGraphNode(), facebookCallback);
            } catch (FileNotFoundException e) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, e);
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private Bundle getSharePhotoCommonParameters(SharePhoto sharePhoto, SharePhotoContent sharePhotoContent) throws JSONException {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return null;
        }
        try {
            Bundle parameters = sharePhoto.getParameters();
            if (!parameters.containsKey("place") && !Utility.isNullOrEmpty(sharePhotoContent.getPlaceId())) {
                parameters.putString("place", sharePhotoContent.getPlaceId());
            }
            if (!parameters.containsKey("tags") && !Utility.isNullOrEmpty(sharePhotoContent.getPeopleIds())) {
                List<String> peopleIds = sharePhotoContent.getPeopleIds();
                if (!Utility.isNullOrEmpty(peopleIds)) {
                    JSONArray jSONArray = new JSONArray();
                    for (String str : peopleIds) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("tag_uid", str);
                        jSONArray.put(jSONObject);
                    }
                    parameters.putString("tags", jSONArray.toString());
                }
            }
            if (!parameters.containsKey("ref") && !Utility.isNullOrEmpty(sharePhotoContent.getRef())) {
                parameters.putString("ref", sharePhotoContent.getRef());
            }
            return parameters;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
            return null;
        }
    }

    private void stageArrayList(final ArrayList arrayList, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            final JSONArray jSONArray = new JSONArray();
            stageCollectionValues(new CollectionMapper.Collection<Integer>() { // from class: com.facebook.share.ShareApi.3
                @Override // com.facebook.internal.CollectionMapper.Collection
                public Iterator<Integer> keyIterator() {
                    final int size = arrayList.size();
                    final Mutable mutable = new Mutable(0);
                    return new Iterator<Integer>() { // from class: com.facebook.share.ShareApi.3.1
                        @Override // java.util.Iterator
                        public void remove() {
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.util.Iterator
                        public boolean hasNext() {
                            return ((Integer) mutable.value).intValue() < size;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Integer] */
                        @Override // java.util.Iterator
                        public Integer next() {
                            Mutable mutable2 = mutable;
                            T t = mutable2.value;
                            Integer num = (Integer) t;
                            mutable2.value = Integer.valueOf(((Integer) t).intValue() + 1);
                            return num;
                        }
                    };
                }

                @Override // com.facebook.internal.CollectionMapper.Collection
                public Object get(Integer num) {
                    return arrayList.get(num.intValue());
                }

                @Override // com.facebook.internal.CollectionMapper.Collection
                public void set(Integer num, Object obj, CollectionMapper.OnErrorListener onErrorListener) {
                    try {
                        jSONArray.put(num.intValue(), obj);
                    } catch (JSONException e) {
                        String localizedMessage = e.getLocalizedMessage();
                        if (localizedMessage == null) {
                            localizedMessage = "Error staging object.";
                        }
                        onErrorListener.onError(new FacebookException(localizedMessage));
                    }
                }
            }, new CollectionMapper.OnMapperCompleteListener() { // from class: com.facebook.share.ShareApi.4
                @Override // com.facebook.internal.CollectionMapper.OnMapperCompleteListener
                public void onComplete() {
                    onMapValueCompleteListener.onComplete(jSONArray);
                }

                @Override // com.facebook.internal.CollectionMapper.OnErrorListener
                public void onError(FacebookException facebookException) {
                    onMapValueCompleteListener.onError(facebookException);
                }
            });
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private <T> void stageCollectionValues(CollectionMapper.Collection<T> collection, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            CollectionMapper.iterate(collection, new CollectionMapper.ValueMapper() { // from class: com.facebook.share.ShareApi.5
                @Override // com.facebook.internal.CollectionMapper.ValueMapper
                public void mapValue(Object obj, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
                    if (obj instanceof ArrayList) {
                        ShareApi.access$000(ShareApi.this, (ArrayList) obj, onMapValueCompleteListener);
                    } else if (obj instanceof SharePhoto) {
                        ShareApi.access$100(ShareApi.this, (SharePhoto) obj, onMapValueCompleteListener);
                    } else {
                        onMapValueCompleteListener.onComplete(obj);
                    }
                }
            }, onMapperCompleteListener);
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    private void stagePhoto(final SharePhoto sharePhoto, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            Bitmap bitmap = sharePhoto.getBitmap();
            Uri imageUrl = sharePhoto.getImageUrl();
            if (bitmap == null && imageUrl == null) {
                onMapValueCompleteListener.onError(new FacebookException("Photos must have an imageURL or bitmap."));
                return;
            }
            GraphRequest.Callback callback = new GraphRequest.Callback() { // from class: com.facebook.share.ShareApi.6
                @Override // com.facebook.GraphRequest.Callback
                public void onCompleted(GraphResponse graphResponse) {
                    FacebookRequestError error = graphResponse.getError();
                    if (error != null) {
                        String errorMessage = error.getErrorMessage();
                        onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, errorMessage != null ? errorMessage : "Error staging photo."));
                        return;
                    }
                    JSONObject jSONObject = graphResponse.getJSONObject();
                    if (jSONObject == null) {
                        onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                        return;
                    }
                    String strOptString = jSONObject.optString("uri");
                    if (strOptString == null) {
                        onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                        return;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("url", strOptString);
                        jSONObject2.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, sharePhoto.getUserGenerated());
                        onMapValueCompleteListener.onComplete(jSONObject2);
                    } catch (JSONException e) {
                        String localizedMessage = e.getLocalizedMessage();
                        onMapValueCompleteListener.onError(new FacebookException(localizedMessage != null ? localizedMessage : "Error staging photo."));
                    }
                }
            };
            if (bitmap != null) {
                ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), bitmap, callback).executeAsync();
                return;
            }
            try {
                ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), imageUrl, callback).executeAsync();
            } catch (FileNotFoundException e) {
                String localizedMessage = e.getLocalizedMessage();
                if (localizedMessage == null) {
                    localizedMessage = "Error staging photo.";
                }
                onMapValueCompleteListener.onError(new FacebookException(localizedMessage));
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }
}
