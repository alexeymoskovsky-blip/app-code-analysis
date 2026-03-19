package com.shopify.sample.domain.repository;

import androidx.annotation.NonNull;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public final class ShopRepository {
    public final GraphClient graphClient;

    public ShopRepository(@NonNull GraphClient graphClient) {
        this.graphClient = (GraphClient) Util.checkNotNull(graphClient, "graphClient == null");
    }

    @NonNull
    public Single<Storefront.Shop> shopSettings(@NonNull final Storefront.ShopQueryDefinition shopQueryDefinition) {
        Util.checkNotNull(shopQueryDefinition, "query == null");
        QueryGraphCall queryGraphCallQueryGraph = this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.ShopRepository$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                queryRootQuery.shop(shopQueryDefinition);
            }
        }));
        Objects.requireNonNull(queryGraphCallQueryGraph);
        return Single.fromCallable(new CheckoutRepository$$ExternalSyntheticLambda27(queryGraphCallQueryGraph)).flatMap(new CheckoutRepository$$ExternalSyntheticLambda28()).map(new CollectionRepository$$ExternalSyntheticLambda3()).subscribeOn(Schedulers.io());
    }
}
