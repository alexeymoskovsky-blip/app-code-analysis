package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.model.UserMessageError;
import com.shopify.sample.domain.repository.CheckoutRepository;
import com.shopify.sample.domain.repository.UserError;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCheckoutShippingLineUpdateInteractor implements CheckoutShippingLineUpdateInteractor {
    public final CheckoutRepository repository = new CheckoutRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.CheckoutShippingLineUpdateInteractor
    public Single<Checkout> execute(@NonNull String str, @NonNull String str2) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotBlank(str2, "shippingRateHandle can't be empty");
        return this.repository.updateShippingLine(str, str2, new Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingLineUpdateInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition
            public final void define(Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
                RealCheckoutShippingLineUpdateInteractor.lambda$execute$0(checkoutShippingLineUpdatePayloadQuery);
            }
        }).map(new RealCheckoutCreateInteractor$$ExternalSyntheticLambda2()).onErrorResumeNext((Function<? super Throwable, ? extends SingleSource<? extends R>>) new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingLineUpdateInteractor$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return RealCheckoutShippingLineUpdateInteractor.lambda$execute$1((Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
        checkoutShippingLineUpdatePayloadQuery.checkout(new CheckoutFragment());
    }

    public static /* synthetic */ SingleSource lambda$execute$1(Throwable th) throws Exception {
        if (th instanceof UserError) {
            th = new UserMessageError(th.getMessage());
        }
        return Single.error(th);
    }
}
