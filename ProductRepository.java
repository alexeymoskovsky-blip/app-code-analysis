package com.shopify.sample.domain.repository;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import com.shopify.sample.RxUtil;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public final class ProductRepository {
    public final GraphClient graphClient;

    public ProductRepository(@NonNull GraphClient graphClient) {
        this.graphClient = (GraphClient) Util.checkNotNull(graphClient, "graphClient == null");
    }

    @NonNull
    public Single<List<Storefront.ProductEdge>> nextPage(@NonNull final String str, @Nullable final String str2, final int i, @NonNull final Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition) {
        Util.checkNotNull(str, "collectionId == null");
        Util.checkNotNull(productConnectionQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                ProductRepository.lambda$nextPage$3(str, i, str2, productConnectionQueryDefinition, queryRootQuery);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ProductRepository.lambda$nextPage$4((Storefront.QueryRoot) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Collection) obj).getProducts();
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.ProductConnection) obj).getEdges();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$nextPage$3(String str, final int i, final String str2, final Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(str), new Storefront.NodeQueryDefinition() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda5
            @Override // com.shopify.buy3.Storefront.NodeQueryDefinition
            public final void define(Storefront.NodeQuery nodeQuery) {
                ProductRepository.lambda$nextPage$2(i, str2, productConnectionQueryDefinition, nodeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$nextPage$1(final int i, final String str, Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition, Storefront.CollectionQuery collectionQuery) {
        collectionQuery.products(new Storefront.CollectionQuery.ProductsArgumentsDefinition() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda9
            @Override // com.shopify.buy3.Storefront.CollectionQuery.ProductsArgumentsDefinition
            public final void define(Storefront.CollectionQuery.ProductsArguments productsArguments) {
                ProductRepository.lambda$nextPage$0(i, str, productsArguments);
            }
        }, productConnectionQueryDefinition);
    }

    public static /* synthetic */ void lambda$nextPage$2(final int i, final String str, final Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition, Storefront.NodeQuery nodeQuery) {
        nodeQuery.onCollection(new Storefront.CollectionQueryDefinition() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda8
            @Override // com.shopify.buy3.Storefront.CollectionQueryDefinition
            public final void define(Storefront.CollectionQuery collectionQuery) {
                ProductRepository.lambda$nextPage$1(i, str, productConnectionQueryDefinition, collectionQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$nextPage$0(int i, String str, Storefront.CollectionQuery.ProductsArguments productsArguments) {
        Storefront.CollectionQuery.ProductsArguments productsArgumentsFirst = productsArguments.first(Integer.valueOf(i));
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        productsArgumentsFirst.after(str);
    }

    public static /* synthetic */ Storefront.Collection lambda$nextPage$4(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Collection) queryRoot.getNode();
    }

    @NonNull
    public Single<Storefront.Product> product(@NonNull final String str, @NonNull final Storefront.ProductQueryDefinition productQueryDefinition) {
        Util.checkNotNull(str, "productId == null");
        Util.checkNotNull(productQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda6
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                ProductRepository.lambda$product$6(str, productQueryDefinition, queryRootQuery);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ProductRepository.lambda$product$7((Storefront.QueryRoot) obj);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$product$6(String str, final Storefront.ProductQueryDefinition productQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(str), new Storefront.NodeQueryDefinition() { // from class: com.shopify.sample.domain.repository.ProductRepository$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.NodeQueryDefinition
            public final void define(Storefront.NodeQuery nodeQuery) {
                nodeQuery.onProduct(productQueryDefinition);
            }
        });
    }

    public static /* synthetic */ Storefront.Product lambda$product$7(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Product) queryRoot.getNode();
    }
}
