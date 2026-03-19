package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.sample.data.graphql.Query$$ExternalSyntheticLambda5;
import com.shopify.sample.data.graphql.Query$$ExternalSyntheticLambda7;
import com.shopify.sample.domain.model.Product;
import com.shopify.sample.domain.repository.ProductRepository;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCollectionProductNextPageInteractor implements CollectionProductNextPageInteractor {
    public final ProductRepository repository = new ProductRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.CollectionProductNextPageInteractor
    @NonNull
    public Single<List<Product>> execute(@NonNull String str, @Nullable String str2, int i) {
        Util.checkNotNull(str, "collectionId == null");
        return this.repository.nextPage(str, str2, i, new Storefront.ProductConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda8
            @Override // com.shopify.buy3.Storefront.ProductConnectionQueryDefinition
            public final void define(Storefront.ProductConnectionQuery productConnectionQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$8(productConnectionQuery);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Converters.convertToProducts((List) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$8(Storefront.ProductConnectionQuery productConnectionQuery) {
        productConnectionQuery.edges(new Storefront.ProductEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.ProductEdgeQueryDefinition
            public final void define(Storefront.ProductEdgeQuery productEdgeQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$7(productEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$7(Storefront.ProductEdgeQuery productEdgeQuery) {
        productEdgeQuery.cursor().node(new Storefront.ProductQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda2
            @Override // com.shopify.buy3.Storefront.ProductQueryDefinition
            public final void define(Storefront.ProductQuery productQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$6(productQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$6(Storefront.ProductQuery productQuery) {
        productQuery.title().images(new Storefront.ProductQuery.ImagesArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda3
            @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
            public final void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                RealCollectionProductNextPageInteractor.lambda$execute$0(imagesArguments);
            }
        }, new Storefront.ImageConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda4
            @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
            public final void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$2(imageConnectionQuery);
            }
        }).variants(new Storefront.ProductQuery.VariantsArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda5
            @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
            public final void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                RealCollectionProductNextPageInteractor.lambda$execute$3(variantsArguments);
            }
        }, new Storefront.ProductVariantConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda6
            @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
            public final void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$5(productVariantConnectionQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.ProductQuery.ImagesArguments imagesArguments) {
        imagesArguments.first(1);
    }

    public static /* synthetic */ void lambda$execute$2(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
            public final void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$1(imageEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$1(Storefront.ImageEdgeQuery imageEdgeQuery) {
        imageEdgeQuery.node(new Query$$ExternalSyntheticLambda7());
    }

    public static /* synthetic */ void lambda$execute$3(Storefront.ProductQuery.VariantsArguments variantsArguments) {
        variantsArguments.first(250);
    }

    public static /* synthetic */ void lambda$execute$5(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCollectionProductNextPageInteractor$$ExternalSyntheticLambda7
            @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
            public final void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                RealCollectionProductNextPageInteractor.lambda$execute$4(productVariantEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$4(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        productVariantEdgeQuery.node(new Query$$ExternalSyntheticLambda5());
    }
}
