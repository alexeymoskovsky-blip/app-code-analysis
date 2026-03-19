package com.shopify.sample.domain.repository;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import com.shopify.sample.RxUtil;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public final class CollectionRepository {
    public final GraphClient graphClient;

    public CollectionRepository(@NonNull GraphClient graphClient) {
        this.graphClient = (GraphClient) Util.checkNotNull(graphClient, "graphClient == null");
    }

    @NonNull
    public Single<List<Storefront.CollectionEdge>> nextPage(@Nullable final String str, final int i, @NonNull final Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition) {
        Util.checkNotNull(collectionConnectionQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.CollectionRepository$$ExternalSyntheticLambda2
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                CollectionRepository.lambda$nextPage$2(i, str, collectionConnectionQueryDefinition, queryRootQuery);
            }
        }))).map(new CollectionRepository$$ExternalSyntheticLambda3()).map(new Function() { // from class: com.shopify.sample.domain.repository.CollectionRepository$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Shop) obj).getCollections();
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CollectionRepository$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.CollectionConnection) obj).getEdges();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$nextPage$2(final int i, final String str, final Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.shop(new Storefront.ShopQueryDefinition() { // from class: com.shopify.sample.domain.repository.CollectionRepository$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.ShopQueryDefinition
            public final void define(Storefront.ShopQuery shopQuery) {
                CollectionRepository.lambda$nextPage$1(i, str, collectionConnectionQueryDefinition, shopQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$nextPage$1(final int i, final String str, Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition, Storefront.ShopQuery shopQuery) {
        shopQuery.collections(new Storefront.ShopQuery.CollectionsArgumentsDefinition() { // from class: com.shopify.sample.domain.repository.CollectionRepository$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.ShopQuery.CollectionsArgumentsDefinition
            public final void define(Storefront.ShopQuery.CollectionsArguments collectionsArguments) {
                CollectionRepository.lambda$nextPage$0(i, str, collectionsArguments);
            }
        }, collectionConnectionQueryDefinition);
    }

    public static /* synthetic */ void lambda$nextPage$0(int i, String str, Storefront.ShopQuery.CollectionsArguments collectionsArguments) {
        Storefront.ShopQuery.CollectionsArguments collectionsArgumentsFirst = collectionsArguments.first(Integer.valueOf(i));
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        collectionsArgumentsFirst.after(str).sortKey(Storefront.CollectionSortKeys.TITLE);
    }
}
