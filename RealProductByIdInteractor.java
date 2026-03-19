package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.sample.data.graphql.Query$$ExternalSyntheticLambda7;
import com.shopify.sample.domain.model.ProductDetails;
import com.shopify.sample.domain.repository.ProductRepository;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes6.dex */
public final class RealProductByIdInteractor implements ProductByIdInteractor {
    public final ProductRepository repository = new ProductRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.ProductByIdInteractor
    @NonNull
    public Single<ProductDetails> execute(@NonNull String str) {
        Util.checkNotNull(str, "productId == null");
        return this.repository.product(str, new Storefront.ProductQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda9
            @Override // com.shopify.buy3.Storefront.ProductQueryDefinition
            public final void define(Storefront.ProductQuery productQuery) {
                RealProductByIdInteractor.lambda$execute$10(productQuery);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Converters.convertToProductDetails((Storefront.Product) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$10(Storefront.ProductQuery productQuery) {
        productQuery.title().descriptionHtml().tags().images(new Storefront.ProductQuery.ImagesArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
            public final void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                RealProductByIdInteractor.lambda$execute$0(imagesArguments);
            }
        }, new Storefront.ImageConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
            public final void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                RealProductByIdInteractor.lambda$execute$2(imageConnectionQuery);
            }
        }).options(new Storefront.ProductOptionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda2
            @Override // com.shopify.buy3.Storefront.ProductOptionQueryDefinition
            public final void define(Storefront.ProductOptionQuery productOptionQuery) {
                RealProductByIdInteractor.lambda$execute$3(productOptionQuery);
            }
        }).variants(new Storefront.ProductQuery.VariantsArgumentsDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda3
            @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
            public final void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                RealProductByIdInteractor.lambda$execute$4(variantsArguments);
            }
        }, new Storefront.ProductVariantConnectionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda4
            @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
            public final void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                RealProductByIdInteractor.lambda$execute$9(productVariantConnectionQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.ProductQuery.ImagesArguments imagesArguments) {
        imagesArguments.first(250);
    }

    public static /* synthetic */ void lambda$execute$2(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda11
            @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
            public final void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                RealProductByIdInteractor.lambda$execute$1(imageEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$1(Storefront.ImageEdgeQuery imageEdgeQuery) {
        imageEdgeQuery.node(new Query$$ExternalSyntheticLambda7());
    }

    public static /* synthetic */ void lambda$execute$3(Storefront.ProductOptionQuery productOptionQuery) {
        productOptionQuery.name().values();
    }

    public static /* synthetic */ void lambda$execute$4(Storefront.ProductQuery.VariantsArguments variantsArguments) {
        variantsArguments.first(250);
    }

    public static /* synthetic */ void lambda$execute$9(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda8
            @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
            public final void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                RealProductByIdInteractor.lambda$execute$8(productVariantEdgeQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$8(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda5
            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
            public final void define(Storefront.ProductVariantQuery productVariantQuery) {
                RealProductByIdInteractor.lambda$execute$7(productVariantQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$7(Storefront.ProductVariantQuery productVariantQuery) {
        productVariantQuery.title().availableForSale().selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda6
            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
            public final void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                RealProductByIdInteractor.lambda$execute$5(selectedOptionQuery);
            }
        }).price().image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealProductByIdInteractor$$ExternalSyntheticLambda7
            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
            public final void define(Storefront.ImageQuery imageQuery) {
                imageQuery.src();
            }
        });
    }

    public static /* synthetic */ void lambda$execute$5(Storefront.SelectedOptionQuery selectedOptionQuery) {
        selectedOptionQuery.name().value();
    }
}
