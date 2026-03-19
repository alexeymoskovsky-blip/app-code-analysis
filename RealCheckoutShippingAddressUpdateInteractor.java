package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.sample.domain.model.Address;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.model.UserMessageError;
import com.shopify.sample.domain.repository.CheckoutRepository;
import com.shopify.sample.domain.repository.UserError;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCheckoutShippingAddressUpdateInteractor implements CheckoutShippingAddressUpdateInteractor {
    public final CheckoutRepository repository = new CheckoutRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.CheckoutShippingAddressUpdateInteractor
    public Single<Checkout> execute(@NonNull String str, @NonNull Address address) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotNull(address, "address == null");
        return this.repository.updateShippingAddress(str, new Storefront.MailingAddressInput().setAddress1(address.address1).setAddress2(address.address2).setCity(address.city).setCountry(address.country).setFirstName(address.firstName).setLastName(address.lastName).setPhone(address.phone).setProvince(address.province).setZip(address.zip), new Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingAddressUpdateInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition
            public final void define(Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
                RealCheckoutShippingAddressUpdateInteractor.lambda$execute$0(checkoutShippingAddressUpdatePayloadQuery);
            }
        }).map(new RealCheckoutCreateInteractor$$ExternalSyntheticLambda2()).onErrorResumeNext((Function<? super Throwable, ? extends SingleSource<? extends R>>) new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutShippingAddressUpdateInteractor$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return RealCheckoutShippingAddressUpdateInteractor.lambda$execute$1((Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
        checkoutShippingAddressUpdatePayloadQuery.checkout(new CheckoutFragment());
    }

    public static /* synthetic */ SingleSource lambda$execute$1(Throwable th) throws Exception {
        if (th instanceof UserError) {
            th = new UserMessageError(th.getMessage());
        }
        return Single.error(th);
    }
}
