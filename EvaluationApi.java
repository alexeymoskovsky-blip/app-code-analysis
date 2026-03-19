package com.qiyukf.unicorn.api.evaluation;

import android.content.Context;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.unicorn.api.evaluation.entry.EvaluationOpenEntry;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.h;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class EvaluationApi {
    private OnEvaluationEventListener onEvaluationEventListener;

    public static abstract class OnEvaluationEventListener {
        public void onEvaluationMessageClick(EvaluationOpenEntry evaluationOpenEntry, Context context) {
        }

        public void onEvaluationStateChange(int i) {
        }
    }

    public static class SingletonHolder {
        private static final EvaluationApi sInstance = new EvaluationApi();

        private SingletonHolder() {
        }
    }

    public static EvaluationApi getInstance() {
        return SingletonHolder.sInstance;
    }

    public OnEvaluationEventListener getOnEvaluationEventListener() {
        return this.onEvaluationEventListener;
    }

    public void setOnEvaluationEventListener(OnEvaluationEventListener onEvaluationEventListener) {
        this.onEvaluationEventListener = onEvaluationEventListener;
    }

    public void evaluate(String str, long j, int i, String str2, List<String> list, String str3, int i2, RequestCallbackWrapper<String> requestCallbackWrapper) {
        h hVar = new h();
        hVar.a = str;
        hVar.g = j;
        hVar.b = i;
        hVar.c = str2;
        hVar.d = list;
        hVar.e = str3;
        hVar.f = i2;
        c.h().d().a(hVar, requestCallbackWrapper);
    }
}
