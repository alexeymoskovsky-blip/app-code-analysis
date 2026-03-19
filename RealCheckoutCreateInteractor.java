package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.model.UserMessageError;
import com.shopify.sample.domain.repository.CheckoutRepository;
import com.shopify.sample.domain.repository.UserError;
import com.shopify.sample.util.Function;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCheckoutCreateInteractor implements CheckoutCreateInteractor {
    public final CheckoutRepository repository = new CheckoutRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.CheckoutCreateInteractor
    public Single<Checkout> execute(@NonNull List<Checkout.LineItem> list) {
        Util.checkNotEmpty(list, "lineItems can't be empty");
        return this.repository.create(new Storefront.CheckoutCreateInput().setLineItems(Util.mapItems(list, new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCreateInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.sample.util.Function
            public final Object apply(Object obj) {
                return RealCheckoutCreateInteractor.lambda$execute$0((Checkout.LineItem) obj);
            }
        })), new Storefront.CheckoutCreatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCreateInteractor$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.CheckoutCreatePayloadQueryDefinition
            public final void define(Storefront.CheckoutCreatePayloadQuery checkoutCreatePayloadQuery) {
                RealCheckoutCreateInteractor.lambda$execute$1(checkoutCreatePayloadQuery);
            }
        }).map(new RealCheckoutCreateInteractor$$ExternalSyntheticLambda2()).onErrorResumeNext((io.reactivex.functions.Function<? super Throwable, ? extends SingleSource<? extends R>>) new io.reactivex.functions.Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCreateInteractor$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return RealCheckoutCreateInteractor.lambda$execute$2((Throwable) obj);
            }
        });
    }

    public static /* synthetic */ Storefront.CheckoutLineItemInput lambda$execute$0(Checkout.LineItem lineItem) {
        return new Storefront.CheckoutLineItemInput(lineItem.quantity, new ID(lineItem.variantId));
    }

    public static /* synthetic */ void lambda$execute$1(Storefront.CheckoutCreatePayloadQuery checkoutCreatePayloadQuery) {
        checkoutCreatePayloadQuery.checkout(new CheckoutCreateFragment());
    }

    public static /* synthetic */ SingleSource lambda$execute$2(Throwable th) throws Exception {
        if (th instanceof UserError) {
            th = new UserMessageError(th.getMessage());
        }
        return Single.error(th);
    }
}
