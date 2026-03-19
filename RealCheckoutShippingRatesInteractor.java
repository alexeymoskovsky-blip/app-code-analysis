package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.Storefront;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.repository.CheckoutRepository;
import com.shopify.sample.util.NotReadyException;
import com.shopify.sample.util.RxRetryHandler;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCheckoutShippingRatesInteractor implements CheckoutShippingRatesInteractor {
    public final CheckoutRepository repository = new CheckoutRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.CheckoutShippingRatesInteractor
    public Single<Checkout.ShippingRates> execute(@NonNull String str) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        return this.repository.shippingRates(str, new Storefront.CheckoutQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingRatesInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.CheckoutQueryDefinition
            public final void define(Storefront.CheckoutQuery checkoutQuery) {
                RealCheckoutShippingRatesInteractor.lambda$execute$0(checkoutQuery);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingRatesInteractor$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Converters.convertToShippingRates((Storefront.AvailableShippingRates) obj);
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingRatesInteractor$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return RealCheckoutShippingRatesInteractor.lambda$execute$1((Checkout.ShippingRates) obj);
            }
        }).retryWhen(RxRetryHandler.exponentialBackoff(500L, TimeUnit.MILLISECONDS, 1.2f).maxRetries(10).when(new Predicate() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingRatesInteractor$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return RealCheckoutShippingRatesInteractor.lambda$execute$2((Throwable) obj);
            }
        }).build());
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.CheckoutQuery checkoutQuery) {
        checkoutQuery.availableShippingRates(new CheckoutShippingRatesFragment());
    }

    public static /* synthetic */ SingleSource lambda$execute$1(Checkout.ShippingRates shippingRates) throws Exception {
        return shippingRates.ready ? Single.just(shippingRates) : Single.error(new NotReadyException("Shipping rates not available yet"));
    }

    public static /* synthetic */ boolean lambda$execute$2(Throwable th) throws Exception {
        return (th instanceof NotReadyException) || (th instanceof GraphError.HttpError) || (th instanceof GraphError.NetworkError);
    }
}
