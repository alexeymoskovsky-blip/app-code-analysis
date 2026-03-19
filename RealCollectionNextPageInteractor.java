package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.sample.data.graphql.Query$$ExternalSyntheticLambda5;
import com.shopify.sample.data.graphql.Query$$ExternalSyntheticLambda7;
import com.shopify.sample.domain.model.Collection;
import com.shopify.sample.domain.repository.CollectionRepository;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCollectionNextPageInteractor implements CollectionNextPageInteractor {
    public final CollectionRepository repository = new CollectionRepository(BaseApplication.graphClient());

    public static /* synthetic */ void lambda$execute$12(final int i, Storefront.CollectionConnectionQuery collectionConnectionQuery) {
        collectionConnectionQuery.edges(new Storefront.CollectionEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.CollectionEdgeQueryDefinition
            public final void define(Storefront.CollectionEdgeQuery collectionEdgeQuery) {
                RealCollectionNextPageInteractor.lambda$execute$11(i, collectionEdgeQuery);
            }
        });
    }

    @Override // com.shopify.sample.domain.interactor.CollectionNextPageInteractor
    @NonNull
    public Single<List<Collection>> execute(@Nullable String str, final int i) {
        return this.repository.nextPage(str, i, new Storefront.CollectionConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda10
            @Override // com.shopify.buy3.Storefront.CollectionConnectionQueryDefinition
            public final void define(Storefront.CollectionConnectionQuery collectionConnectionQuery) {
                RealCollectionNextPageInteractor.lambda$execute$12(i, collectionConnectionQuery);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Converters.convertToCollections((List) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$11(final int i, Storefront.CollectionEdgeQuery collectionEdgeQuery) {
        collectionEdgeQuery.cursor().node(new Storefront.CollectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda12
            @Override // com.shopify.buy3.Storefront.CollectionQueryDefinition
            public final void define(Storefront.CollectionQuery collectionQuery) {
                RealCollectionNextPageInteractor.lambda$execute$10(i, collectionQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$10(final int i, Storefront.CollectionQuery collectionQuery) {
        collectionQuery.title().description().image(new Query$$ExternalSyntheticLambda7()).products(new Storefront.CollectionQuery.ProductsArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda8
            @Override // com.shopify.buy3.Storefront.CollectionQuery.ProductsArgumentsDefinition
            public final void define(Storefront.CollectionQuery.ProductsArguments productsArguments) {
                RealCollectionNextPageInteractor.lambda$execute$0(i, productsArguments);
            }
        }, new Storefront.ProductConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda9
            @Override // com.shopify.buy3.Storefront.ProductConnectionQueryDefinition
            public final void define(Storefront.ProductConnectionQuery productConnectionQuery) {
                RealCollectionNextPageInteractor.lambda$execute$9(productConnectionQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(int i, Storefront.CollectionQuery.ProductsArguments productsArguments) {
        productsArguments.first(Integer.valueOf(i));
    }

    public static /* synthetic */ void lambda$execute$9(Storefront.ProductConnectionQuery productConnectionQuery) {
        productConnectionQuery.edges(new Storefront.ProductEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda7
            @Override // com.shopify.buy3.Storefront.ProductEdgeQueryDefinition
            public final void define(Storefront.ProductEdgeQuery productEdgeQuery) {
                RealCollectionNextPageInteractor.lambda$execute$8(productEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$8(Storefront.ProductEdgeQuery productEdgeQuery) {
        productEdgeQuery.cursor().node(new Storefront.ProductQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda13
            @Override // com.shopify.buy3.Storefront.ProductQueryDefinition
            public final void define(Storefront.ProductQuery productQuery) {
                RealCollectionNextPageInteractor.lambda$execute$7(productQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$7(Storefront.ProductQuery productQuery) {
        productQuery.title().images(new Storefront.ProductQuery.ImagesArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda3
            @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
            public final void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                RealCollectionNextPageInteractor.lambda$execute$1(imagesArguments);
            }
        }, new Storefront.ImageConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda4
            @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
            public final void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                RealCollectionNextPageInteractor.lambda$execute$3(imageConnectionQuery);
            }
        }).variants(new Storefront.ProductQuery.VariantsArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda5
            @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
            public final void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                RealCollectionNextPageInteractor.lambda$execute$4(variantsArguments);
            }
        }, new Storefront.ProductVariantConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda6
            @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
            public final void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                RealCollectionNextPageInteractor.lambda$execute$6(productVariantConnectionQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$1(Storefront.ProductQuery.ImagesArguments imagesArguments) {
        imagesArguments.first(1);
    }

    public static /* synthetic */ void lambda$execute$3(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda2
            @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
            public final void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                RealCollectionNextPageInteractor.lambda$execute$2(imageEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$2(Storefront.ImageEdgeQuery imageEdgeQuery) {
        imageEdgeQuery.node(new Query$$ExternalSyntheticLambda7());
    }

    public static /* synthetic */ void lambda$execute$4(Storefront.ProductQuery.VariantsArguments variantsArguments) {
        variantsArguments.first(250);
    }

    public static /* synthetic */ void lambda$execute$6(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionNextPageInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
            public final void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                RealCollectionNextPageInteractor.lambda$execute$5(productVariantEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$5(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        productVariantEdgeQuery.node(new Query$$ExternalSyntheticLambda5());
    }
}
