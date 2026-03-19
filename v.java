package com.qiyukf.unicorn.n;

import android.text.TextUtils;
import com.facebook.gamingservices.cloudgaming.internal.SDKConstants;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.unicorn.h.a.d.av;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: UnReadMessageListOperator.java */
/* JADX INFO: loaded from: classes6.dex */
public final class v {
    public static synchronized void a() {
        com.qiyukf.unicorn.d.c.a(Boolean.FALSE);
        com.qiyukf.unicorn.k.c.a(new com.qiyukf.unicorn.h.a.f.t(), com.qiyukf.unicorn.k.c.a());
    }

    public static void a(final av avVar, final com.qiyukf.unicorn.f.x xVar) {
        new Thread(new Runnable() { // from class: com.qiyukf.unicorn.n.v.1
            @Override // java.lang.Runnable
            public final void run() {
                RequestCallback<JSONArray> requestCallback = new RequestCallback<JSONArray>() { // from class: com.qiyukf.unicorn.n.v.1.1
                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final /* synthetic */ void onSuccess(JSONArray jSONArray) {
                        JSONArray jSONArray2 = jSONArray;
                        com.qiyukf.unicorn.d.c.a(Boolean.TRUE);
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < jSONArray2.length(); i++) {
                            arrayList.add(v.a(com.qiyukf.nimlib.n.j.d(jSONArray2, i), xVar));
                        }
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) it.next();
                            if (dVar == null) {
                                it.remove();
                            } else if (MsgDBHelper.queryMessageIdByUuid(dVar.getUuid()) != 0) {
                                it.remove();
                            } else if (dVar.getUuid().contains(MqttTopic.MULTI_LEVEL_WILDCARD) && MsgDBHelper.queryMessageIdByUuid(dVar.getUuid().substring(dVar.getUuid().indexOf(MqttTopic.MULTI_LEVEL_WILDCARD) + 1)) != 0) {
                                it.remove();
                            } else if (dVar.getUuid().contains(MqttTopic.MULTI_LEVEL_WILDCARD) && MsgDBHelper.queryMessageIdByUuid(dVar.getUuid().substring(dVar.getUuid().lastIndexOf(MqttTopic.MULTI_LEVEL_WILDCARD) + 1)) != 0) {
                                it.remove();
                            } else if (dVar.getAttachStr() != null && dVar.getAttachStr().contains("\"cmd\":121") && !dVar.getAttachStr().contains("\"show\"")) {
                                it.remove();
                            }
                        }
                        if (arrayList.size() != 0) {
                            MsgDBHelper.saveMessages(arrayList);
                            Collections.sort(arrayList, new Comparator<com.qiyukf.nimlib.session.d>() { // from class: com.qiyukf.unicorn.n.v.2
                                @Override // java.util.Comparator
                                public final /* synthetic */ int compare(com.qiyukf.nimlib.session.d dVar2, com.qiyukf.nimlib.session.d dVar3) {
                                    return (int) (dVar2.getTime() - dVar3.getTime());
                                }
                            });
                            com.qiyukf.nimlib.i.b.b(arrayList);
                        }
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onFailed(int i) {
                        AbsUnicornLog.i("UnReadMessageListOperat", "request unreadList error code code= ".concat(String.valueOf(i)));
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onException(Throwable th) {
                        AbsUnicornLog.e("UnReadMessageListOperat", "request unreadList error", th);
                    }
                };
                av avVar2 = avVar;
                HashMap map = new HashMap();
                map.put("appKey", com.qiyukf.unicorn.c.e());
                map.put("fromAccount", com.qiyukf.uikit.b.b());
                map.put(SDKConstants.PARAM_ACCESS_TOKEN, avVar2.a());
                map.put("beginTime", String.valueOf(System.currentTimeMillis() - 604800000));
                map.put(SDKConstants.PARAM_END_TIME, String.valueOf(System.currentTimeMillis()));
                int i = com.qiyukf.unicorn.c.f().pullMessageCount;
                if (i == 0) {
                    i = 20;
                } else if (i > 100) {
                    i = 100;
                }
                map.put("limit", String.valueOf(i));
                map.put("fromType", "Android");
                map.put("version", "2500");
                com.qiyukf.unicorn.i.a.a(map, requestCallback);
            }
        }).start();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.qiyukf.nimlib.session.d a(org.json.JSONObject r11, com.qiyukf.unicorn.f.x r12) {
        /*
            Method dump skipped, instruction units count: 622
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.n.v.a(org.json.JSONObject, com.qiyukf.unicorn.f.x):com.qiyukf.nimlib.session.d");
    }

    private static String a(String str, String str2) {
        JSONArray jSONArrayG;
        if (!com.qiyukf.unicorn.m.a.a().b().z() && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObjectA = com.qiyukf.nimlib.n.j.a(str);
                if (jSONObjectA != null && jSONObjectA.has("trashWords") && jSONObjectA.has("auditResult") && jSONObjectA.getInt("auditResult") == 2 && (jSONArrayG = com.qiyukf.nimlib.n.j.g(jSONObjectA, "trashWords")) != null && jSONArrayG.length() != 0) {
                    return u.a(str2, jSONArrayG);
                }
                return str2;
            } catch (Exception e) {
                AbsUnicornLog.e("UnReadMessageListOperat", "replaceTrash failed: " + e.getMessage(), e);
            }
        }
        return str2;
    }
}
