package com.shopify.sample.view.checkout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.shopify.sample.domain.interactor.CartClearInteractor;
import com.shopify.sample.domain.interactor.CheckoutCompleteInteractor;
import com.shopify.sample.domain.interactor.CheckoutShippingAddressUpdateInteractor;
import com.shopify.sample.domain.interactor.CheckoutShippingLineUpdateInteractor;
import com.shopify.sample.domain.interactor.CheckoutShippingRatesInteractor;
import com.shopify.sample.domain.interactor.RealCartClearInteractor;
import com.shopify.sample.domain.interactor.RealCheckoutCompleteInteractor;
import com.shopify.sample.domain.interactor.RealCheckoutShippingAddressUpdateInteractor;
import com.shopify.sample.domain.interactor.RealCheckoutShippingLineUpdateInteractor;
import com.shopify.sample.domain.interactor.RealCheckoutShippingRatesInteractor;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.model.Payment;
import com.shopify.sample.util.Util;
import com.shopify.sample.util.WeakSingleObserver;
import com.shopify.sample.view.BaseViewModel;
import com.shopify.sample.view.LifeCycleBoundCallback;
import com.shopify.sample.view.checkout.CheckoutViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.Collections;
import timber.log.Timber;

/* JADX INFO: loaded from: classes6.dex */
public class RealCheckoutViewModel extends BaseViewModel implements CheckoutViewModel, CheckoutShippingRatesViewModel {
    public final String checkoutId;
    public final MutableLiveData<Checkout.ShippingRate> pendingSelectShippingRateLiveData;
    public final MutableLiveData<Checkout.ShippingRate> selectedShippingRateLiveData;
    public final MutableLiveData<Checkout.ShippingRates> shippingRatesLiveData;
    public final LifeCycleBoundCallback<Payment> successPaymentLiveData;
    public final CheckoutShippingRatesInteractor checkoutShippingRatesInteractor = new RealCheckoutShippingRatesInteractor();
    public final CheckoutShippingAddressUpdateInteractor checkoutShippingAddressUpdateInteractor = new RealCheckoutShippingAddressUpdateInteractor();
    public final CheckoutShippingLineUpdateInteractor checkoutShippingLineUpdateInteractor = new RealCheckoutShippingLineUpdateInteractor();
    public final CheckoutCompleteInteractor checkoutCompleteInteractor = new RealCheckoutCompleteInteractor();
    public final CartClearInteractor cartClearInteractor = new RealCartClearInteractor();

    public final void completeCheckout() {
    }

    @Override // com.shopify.sample.view.checkout.CheckoutShippingRatesViewModel
    public void fetchShippingRates() {
    }

    public RealCheckoutViewModel(@NonNull String str) {
        MutableLiveData<Checkout.ShippingRate> mutableLiveData = new MutableLiveData<>();
        this.pendingSelectShippingRateLiveData = mutableLiveData;
        this.selectedShippingRateLiveData = new MutableLiveData<>();
        this.shippingRatesLiveData = new MutableLiveData<>();
        LifeCycleBoundCallback<Payment> lifeCycleBoundCallback = new LifeCycleBoundCallback<>();
        this.successPaymentLiveData = lifeCycleBoundCallback;
        this.checkoutId = Util.checkNotBlank(str, "checkoutId can't be empty");
        mutableLiveData.observeForever(new Observer() { // from class: com.shopify.sample.view.checkout.RealCheckoutViewModel$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$new$0((Checkout.ShippingRate) obj);
            }
        });
        lifeCycleBoundCallback.observeForever(new Observer() { // from class: com.shopify.sample.view.checkout.RealCheckoutViewModel$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                this.f$0.lambda$new$1((Payment) obj);
            }
        });
    }

    public final /* synthetic */ void lambda$new$0(Checkout.ShippingRate shippingRate) {
        cancelAllRequests();
        this.selectedShippingRateLiveData.setValue(null);
        if (shippingRate != null) {
            applyShippingRate(shippingRate);
        }
    }

    public final /* synthetic */ void lambda$new$1(Payment payment) {
        if (payment != null) {
            this.cartClearInteractor.execute();
        }
    }

    @Override // com.shopify.sample.view.checkout.CheckoutShippingRatesViewModel
    public void selectShippingRate(Checkout.ShippingRate shippingRate) {
        this.pendingSelectShippingRateLiveData.setValue(shippingRate);
    }

    @Override // com.shopify.sample.view.checkout.CheckoutShippingRatesViewModel
    public LiveData<Checkout.ShippingRate> selectedShippingRateLiveData() {
        return this.selectedShippingRateLiveData;
    }

    @Override // com.shopify.sample.view.checkout.CheckoutShippingRatesViewModel
    public LiveData<Checkout.ShippingRates> shippingRatesLiveData() {
        return this.shippingRatesLiveData;
    }

    @Override // com.shopify.sample.view.checkout.CheckoutViewModel
    public LifeCycleBoundCallback<Payment> successPaymentLiveData() {
        return this.successPaymentLiveData;
    }

    @Override // com.shopify.sample.view.checkout.CheckoutViewModel
    public void confirmCheckout() {
        if (selectedShippingRateLiveData().getValue() == null) {
            notifyUserError(CheckoutViewModel.REQUEST_ID_CONFIRM_CHECKOUT, new CheckoutViewModel.ShippingRateMissingException());
        }
    }

    public final void applyShippingRate(Checkout.ShippingRate shippingRate) {
        int i = CheckoutViewModel.REQUEST_ID_APPLY_SHIPPING_RATE;
        showProgress(i);
        registerRequest(i, (Disposable) this.checkoutShippingLineUpdateInteractor.execute(this.checkoutId, shippingRate.handle).observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakSingleObserver.forTarget(this).delegateOnSuccess(new WeakSingleObserver.OnSuccessDelegate() { // from class: com.shopify.sample.view.checkout.RealCheckoutViewModel$$ExternalSyntheticLambda2
            @Override // com.shopify.sample.util.WeakSingleObserver.OnSuccessDelegate
            public final void onSuccess(Object obj, Object obj2) {
                RealCheckoutViewModel.lambda$applyShippingRate$2((RealCheckoutViewModel) obj, (Checkout) obj2);
            }
        }).delegateOnError(new WeakSingleObserver.OnErrorDelegate() { // from class: com.shopify.sample.view.checkout.RealCheckoutViewModel$$ExternalSyntheticLambda3
            @Override // com.shopify.sample.util.WeakSingleObserver.OnErrorDelegate
            public final void onError(Object obj, Throwable th) {
                RealCheckoutViewModel.lambda$applyShippingRate$3((RealCheckoutViewModel) obj, th);
            }
        }).create()));
    }

    public static /* synthetic */ void lambda$applyShippingRate$2(RealCheckoutViewModel realCheckoutViewModel, Checkout checkout) {
        realCheckoutViewModel.onApplyShippingRate(checkout, CheckoutViewModel.REQUEST_ID_APPLY_SHIPPING_RATE);
    }

    public static /* synthetic */ void lambda$applyShippingRate$3(RealCheckoutViewModel realCheckoutViewModel, Throwable th) {
        realCheckoutViewModel.onRequestError(CheckoutViewModel.REQUEST_ID_APPLY_SHIPPING_RATE, th);
    }

    public final void onApplyShippingRate(Checkout checkout, int i) {
        hideProgress(i);
        this.selectedShippingRateLiveData.setValue(checkout.shippingLine);
    }

    public final void onShippingRates(Checkout.ShippingRates shippingRates) {
        hideProgress(CheckoutShippingRatesViewModel.REQUEST_ID_FETCH_SHIPPING_RATES);
        this.shippingRatesLiveData.setValue(shippingRates != null ? shippingRates : new Checkout.ShippingRates(false, Collections.emptyList()));
        this.pendingSelectShippingRateLiveData.setValue(shippingRates != null ? (Checkout.ShippingRate) Util.firstItem(shippingRates.shippingRates) : null);
    }

    public final void onRequestError(int i, Throwable th) {
        Timber.e(th);
        hideProgress(i);
        notifyUserError(i, th);
    }

    public final void updateShippingAddress() {
        this.pendingSelectShippingRateLiveData.setValue(null);
        this.selectedShippingRateLiveData.setValue(null);
        this.shippingRatesLiveData.setValue(new Checkout.ShippingRates(false, Collections.emptyList()));
    }

    public final void onUpdateCheckoutShippingAddress(Checkout checkout) {
        invalidateShippingRates();
    }

    public final void invalidateShippingRates() {
        Util.checkNotBlank(this.checkoutId, "checkoutId can't be empty");
        int i = CheckoutShippingRatesViewModel.REQUEST_ID_FETCH_SHIPPING_RATES;
        showProgress(i);
        registerRequest(i, (Disposable) this.checkoutShippingRatesInteractor.execute(this.checkoutId).observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakSingleObserver.forTarget(this).delegateOnSuccess(new WeakSingleObserver.OnSuccessDelegate() { // from class: com.shopify.sample.view.checkout.RealCheckoutViewModel$$ExternalSyntheticLambda0
            @Override // com.shopify.sample.util.WeakSingleObserver.OnSuccessDelegate
            public final void onSuccess(Object obj, Object obj2) {
                ((RealCheckoutViewModel) obj).onShippingRates((Checkout.ShippingRates) obj2);
            }
        }).delegateOnError(new WeakSingleObserver.OnErrorDelegate() { // from class: com.shopify.sample.view.checkout.RealCheckoutViewModel$$ExternalSyntheticLambda1
            @Override // com.shopify.sample.util.WeakSingleObserver.OnErrorDelegate
            public final void onError(Object obj, Throwable th) {
                RealCheckoutViewModel.lambda$invalidateShippingRates$4((RealCheckoutViewModel) obj, th);
            }
        }).create()));
    }

    public static /* synthetic */ void lambda$invalidateShippingRates$4(RealCheckoutViewModel realCheckoutViewModel, Throwable th) {
        realCheckoutViewModel.onRequestError(CheckoutShippingRatesViewModel.REQUEST_ID_FETCH_SHIPPING_RATES, th);
    }

    public final void onCompleteCheckout(Payment payment) {
        int i = CheckoutViewModel.REQUEST_ID_COMPLETE_CHECKOUT;
        hideProgress(i);
        if (!payment.ready) {
            Timber.e("Payment transaction has not been finished yet", new Object[0]);
            notifyUserError(i, new RuntimeException("Payment transaction has not been finished yet"));
        } else if (payment.errorMessage != null) {
            Timber.e("Payment transaction failed", new Object[0]);
            notifyUserError(i, new RuntimeException("Payment transaction failed"), payment.errorMessage);
        } else {
            this.successPaymentLiveData.notify(payment);
        }
    }
}
