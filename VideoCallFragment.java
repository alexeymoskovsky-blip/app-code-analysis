package com.petkit.android.activities.mate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.petkit.android.activities.mate.compatibility.Compatibility;
import com.petkit.android.activities.mate.compatibility.CompatibilityScaleGestureDetector;
import com.petkit.android.activities.mate.compatibility.CompatibilityScaleGestureListener;
import com.petkit.android.activities.mate.utils.LinphoneManager;
import com.petkit.android.activities.mate.utils.LinphoneUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import org.linphone.core.LinphoneCall;
import org.linphone.mediastream.video.AndroidVideoWindowImpl;

/* JADX INFO: loaded from: classes4.dex */
@SuppressLint({"InlinedApi"})
public class VideoCallFragment extends Fragment implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, CompatibilityScaleGestureListener {
    private AndroidVideoWindowImpl androidVideoWindowImpl;
    private InCallActivity inCallActivity;
    private SurfaceView mCaptureView;
    private GestureDetector mGestureDetector;
    private CompatibilityScaleGestureDetector mScaleDetector;
    private SurfaceView mVideoView;
    private float mZoomCenterX;
    private float mZoomCenterY;
    private float mZoomFactor = 1.0f;

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate;
        if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null && LinphoneManager.getLc().hasCrappyOpenGL()) {
            viewInflate = layoutInflater.inflate(R.layout.video_no_opengl, viewGroup, false);
        } else {
            viewInflate = layoutInflater.inflate(R.layout.video, viewGroup, false);
        }
        PetkitLog.d("VideoCallFragment onCreateView");
        this.mVideoView = (SurfaceView) viewInflate.findViewById(R.id.videoSurface);
        SurfaceView surfaceView = (SurfaceView) viewInflate.findViewById(R.id.videoCaptureSurface);
        this.mCaptureView = surfaceView;
        surfaceView.getHolder().setType(3);
        fixZOrder(this.mVideoView, this.mCaptureView);
        this.androidVideoWindowImpl = new AndroidVideoWindowImpl(this.mVideoView, this.mCaptureView, new AndroidVideoWindowImpl.VideoWindowListener() { // from class: com.petkit.android.activities.mate.VideoCallFragment.1
            @Override // org.linphone.mediastream.video.AndroidVideoWindowImpl.VideoWindowListener
            public void onVideoRenderingSurfaceReady(AndroidVideoWindowImpl androidVideoWindowImpl, SurfaceView surfaceView2) {
                if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
                    LinphoneManager.getLc().setVideoWindow(androidVideoWindowImpl);
                }
                VideoCallFragment.this.mVideoView = surfaceView2;
            }

            @Override // org.linphone.mediastream.video.AndroidVideoWindowImpl.VideoWindowListener
            public void onVideoRenderingSurfaceDestroyed(AndroidVideoWindowImpl androidVideoWindowImpl) {
                if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
                    LinphoneManager.getLc().setVideoWindow(null);
                }
            }

            @Override // org.linphone.mediastream.video.AndroidVideoWindowImpl.VideoWindowListener
            public void onVideoPreviewSurfaceReady(AndroidVideoWindowImpl androidVideoWindowImpl, SurfaceView surfaceView2) {
                VideoCallFragment.this.mCaptureView = surfaceView2;
                if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
                    LinphoneManager.getLc().setPreviewWindow(null);
                }
            }

            @Override // org.linphone.mediastream.video.AndroidVideoWindowImpl.VideoWindowListener
            public void onVideoPreviewSurfaceDestroyed(AndroidVideoWindowImpl androidVideoWindowImpl) {
                if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
                    LinphoneManager.getLc().setPreviewWindow(null);
                }
            }
        });
        this.mVideoView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.mate.VideoCallFragment.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (VideoCallFragment.this.mScaleDetector != null) {
                    VideoCallFragment.this.mScaleDetector.onTouchEvent(motionEvent);
                }
                VideoCallFragment.this.mGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        return viewInflate;
    }

    private void fixZOrder(SurfaceView surfaceView, SurfaceView surfaceView2) {
        surfaceView.setZOrderOnTop(false);
        surfaceView2.setZOrderOnTop(true);
        surfaceView2.setZOrderMediaOverlay(true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        AndroidVideoWindowImpl androidVideoWindowImpl = this.androidVideoWindowImpl;
        if (androidVideoWindowImpl != null) {
            synchronized (androidVideoWindowImpl) {
                try {
                    if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
                        LinphoneManager.getLc().setVideoWindow(this.androidVideoWindowImpl);
                    }
                } finally {
                }
            }
        }
        this.mGestureDetector = new GestureDetector(this.inCallActivity, this);
        this.mScaleDetector = Compatibility.getScaleGestureDetector(this.inCallActivity, this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        PetkitLog.d("VideoCallFragment onPause");
        AndroidVideoWindowImpl androidVideoWindowImpl = this.androidVideoWindowImpl;
        if (androidVideoWindowImpl != null) {
            synchronized (androidVideoWindowImpl) {
                try {
                    if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
                        LinphoneManager.getLc().setVideoWindow(null);
                    }
                } finally {
                }
            }
        }
        super.onPause();
    }

    @Override // com.petkit.android.activities.mate.compatibility.CompatibilityScaleGestureListener
    public boolean onScale(CompatibilityScaleGestureDetector compatibilityScaleGestureDetector) {
        LinphoneCall currentCall;
        this.mZoomFactor *= compatibilityScaleGestureDetector.getScaleFactor();
        this.mZoomFactor = Math.max(0.1f, Math.min(this.mZoomFactor, Math.max(this.mVideoView.getHeight() / ((this.mVideoView.getWidth() * 3) / 4), this.mVideoView.getWidth() / ((this.mVideoView.getHeight() * 3) / 4))));
        if (!LinphoneManager.isInstanciated() || LinphoneManager.getLcIfManagerNotDestroyedOrNull() == null || (currentCall = LinphoneManager.getLc().getCurrentCall()) == null) {
            return false;
        }
        currentCall.zoomVideo(this.mZoomFactor, this.mZoomCenterX, this.mZoomCenterY);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0051  */
    @Override // android.view.GestureDetector.OnGestureListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onScroll(android.view.MotionEvent r5, android.view.MotionEvent r6, float r7, float r8) {
        /*
            r4 = this;
            org.linphone.core.LinphoneCore r5 = com.petkit.android.activities.mate.utils.LinphoneManager.getLcIfManagerNotDestroyedOrNull()
            if (r5 == 0) goto L93
            org.linphone.core.LinphoneCore r5 = com.petkit.android.activities.mate.utils.LinphoneManager.getLc()
            org.linphone.core.LinphoneCall r5 = r5.getCurrentCall()
            boolean r5 = com.petkit.android.activities.mate.utils.LinphoneUtils.isCallEstablished(r5)
            if (r5 == 0) goto L93
            float r5 = r4.mZoomFactor
            r6 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L93
            r0 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            r5 = 0
            int r2 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r2 <= 0) goto L32
            float r2 = r4.mZoomCenterX
            int r3 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r3 >= 0) goto L32
            double r2 = (double) r2
            double r2 = r2 + r0
            float r7 = (float) r2
            r4.mZoomCenterX = r7
            goto L41
        L32:
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 >= 0) goto L41
            float r7 = r4.mZoomCenterX
            int r2 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r2 <= 0) goto L41
            double r2 = (double) r7
            double r2 = r2 - r0
            float r7 = (float) r2
            r4.mZoomCenterX = r7
        L41:
            int r7 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r7 >= 0) goto L51
            float r7 = r4.mZoomCenterY
            int r2 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r2 >= 0) goto L51
            double r7 = (double) r7
            double r7 = r7 + r0
            float r7 = (float) r7
            r4.mZoomCenterY = r7
            goto L60
        L51:
            int r7 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r7 <= 0) goto L60
            float r7 = r4.mZoomCenterY
            int r8 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r8 <= 0) goto L60
            double r7 = (double) r7
            double r7 = r7 - r0
            float r7 = (float) r7
            r4.mZoomCenterY = r7
        L60:
            float r7 = r4.mZoomCenterX
            int r7 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r7 <= 0) goto L68
            r4.mZoomCenterX = r6
        L68:
            float r7 = r4.mZoomCenterX
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 >= 0) goto L70
            r4.mZoomCenterX = r5
        L70:
            float r7 = r4.mZoomCenterY
            int r7 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r7 <= 0) goto L78
            r4.mZoomCenterY = r6
        L78:
            float r6 = r4.mZoomCenterY
            int r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r6 >= 0) goto L80
            r4.mZoomCenterY = r5
        L80:
            org.linphone.core.LinphoneCore r5 = com.petkit.android.activities.mate.utils.LinphoneManager.getLc()
            org.linphone.core.LinphoneCall r5 = r5.getCurrentCall()
            float r6 = r4.mZoomFactor
            float r7 = r4.mZoomCenterX
            float r8 = r4.mZoomCenterY
            r5.zoomVideo(r6, r7, r8)
            r5 = 1
            return r5
        L93:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.mate.VideoCallFragment.onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float):boolean");
    }

    @Override // android.view.GestureDetector.OnDoubleTapListener
    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() == null || !LinphoneUtils.isCallEstablished(LinphoneManager.getLc().getCurrentCall())) {
            return false;
        }
        if (this.mZoomFactor == 1.0f) {
            this.mZoomFactor = Math.max(this.mVideoView.getHeight() / ((this.mVideoView.getWidth() * 3) / 4), this.mVideoView.getWidth() / ((this.mVideoView.getHeight() * 3) / 4));
        } else {
            resetZoom();
        }
        LinphoneManager.getLc().getCurrentCall().zoomVideo(this.mZoomFactor, this.mZoomCenterX, this.mZoomCenterY);
        return true;
    }

    private void resetZoom() {
        this.mZoomFactor = 1.0f;
        this.mZoomCenterY = 0.5f;
        this.mZoomCenterX = 0.5f;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this.inCallActivity = null;
        PetkitLog.d("VideoCallFragment onDestroy");
        this.mCaptureView = null;
        SurfaceView surfaceView = this.mVideoView;
        if (surfaceView != null) {
            surfaceView.setOnTouchListener(null);
            this.mVideoView = null;
        }
        AndroidVideoWindowImpl androidVideoWindowImpl = this.androidVideoWindowImpl;
        if (androidVideoWindowImpl != null) {
            androidVideoWindowImpl.release();
            this.androidVideoWindowImpl = null;
        }
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            gestureDetector.setOnDoubleTapListener(null);
            this.mGestureDetector = null;
        }
        CompatibilityScaleGestureDetector compatibilityScaleGestureDetector = this.mScaleDetector;
        if (compatibilityScaleGestureDetector != null) {
            compatibilityScaleGestureDetector.destroy();
            this.mScaleDetector = null;
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        InCallActivity inCallActivity = (InCallActivity) getActivity();
        this.inCallActivity = inCallActivity;
        if (inCallActivity != null) {
            inCallActivity.bindVideoFragment(this);
        }
    }
}
