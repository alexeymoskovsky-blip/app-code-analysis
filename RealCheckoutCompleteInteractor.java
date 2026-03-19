package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.Storefront;
import com.shopify.sample.domain.model.Address;
import com.shopify.sample.domain.model.Cart;
import com.shopify.sample.domain.model.Payment;
import com.shopify.sample.domain.model.UserMessageError;
import com.shopify.sample.domain.repository.CheckoutRepository;
import com.shopify.sample.domain.repository.UserError;
import com.shopify.sample.util.NotReadyException;
import com.shopify.sample.util.RxRetryHandler;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCheckoutCompleteInteractor implements CheckoutCompleteInteractor {
    public final CheckoutRepository repository = new CheckoutRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.CheckoutCompleteInteractor
    public Single<Payment> execute(@NonNull final String str, @NonNull Cart cart, @NonNull String str2, @NonNull String str3, @NonNull Address address) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotNull(cart, "payCart == null");
        Util.checkNotNull(str2, "paymentToken == null");
        Util.checkNotBlank(str3, "email can't be empty");
        Util.checkNotNull(address, "billingAddress == null");
        final Storefront.TokenizedPaymentInput identifier = new Storefront.TokenizedPaymentInput(cart.totalPrice(), str2, new Storefront.MailingAddressInput().setAddress1(address.address1).setAddress2(address.address2).setCity(address.city).setCountry(address.country).setFirstName(address.firstName).setLastName(address.lastName).setPhone(address.phone).setProvince(address.province).setZip(address.zip), "android_pay", str2).setIdentifier(str2);
        return this.repository.updateEmail(str, str3, new Storefront.CheckoutEmailUpdatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda1
            @Override // com.shopify.buy3.Storefront.CheckoutEmailUpdatePayloadQueryDefinition
            public final void define(Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
                RealCheckoutCompleteInteractor.lambda$execute$0(checkoutEmailUpdatePayloadQuery);
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$execute$2(str, identifier, (Storefront.Checkout) obj);
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$execute$6((Storefront.Payment) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Converters.convertToPayment((Storefront.Payment) obj);
            }
        }).onErrorResumeNext(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return RealCheckoutCompleteInteractor.lambda$execute$7((Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
        checkoutEmailUpdatePayloadQuery.checkout(new CheckoutFragment());
    }

    public final /* synthetic */ SingleSource lambda$execute$2(String str, Storefront.TokenizedPaymentInput tokenizedPaymentInput, Storefront.Checkout checkout) throws Exception {
        return this.repository.complete(str, tokenizedPaymentInput, new Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition
            public final void define(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
                RealCheckoutCompleteInteractor.lambda$execute$1(checkoutCompleteWithTokenizedPaymentPayloadQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$1(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
        checkoutCompleteWithTokenizedPaymentPayloadQuery.payment(new PaymentFragment());
    }

    public final /* synthetic */ SingleSource lambda$execute$6(Storefront.Payment payment) throws Exception {
        if (payment.getReady().booleanValue()) {
            return Single.just(payment);
        }
        return this.repository.paymentById(payment.getId().toString(), new Storefront.NodeQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda6
            @Override // com.shopify.buy3.Storefront.NodeQueryDefinition
            public final void define(Storefront.NodeQuery nodeQuery) {
                RealCheckoutCompleteInteractor.lambda$execute$3(nodeQuery);
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return RealCheckoutCompleteInteractor.lambda$execute$4((Storefront.Payment) obj);
            }
        }).retryWhen(RxRetryHandler.exponentialBackoff(500L, TimeUnit.MILLISECONDS, 1.2f).maxRetries(10).when(new Predicate() { // from class: com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return RealCheckoutCompleteInteractor.lambda$execute$5((Throwable) obj);
            }
        }).build());
    }

    public static /* synthetic */ void lambda$execute$3(Storefront.NodeQuery nodeQuery) {
        nodeQuery.onPayment(new PaymentFragment());
    }

    public static /* synthetic */ SingleSource lambda$execute$4(Storefront.Payment payment) throws Exception {
        return payment.getReady().booleanValue() ? Single.just(payment) : Single.error(new NotReadyException("Payment transaction is not finished"));
    }

    public static /* synthetic */ boolean lambda$execute$5(Throwable th) throws Exception {
        return (th instanceof NotReadyException) || (th instanceof GraphError.HttpError) || (th instanceof GraphError.NetworkError);
    }

    public static /* synthetic */ SingleSource lambda$execute$7(Throwable th) throws Exception {
        if (th instanceof UserError) {
            th = new UserMessageError(th.getMessage());
        }
        return Single.error(th);
    }
}
