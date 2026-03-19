package com.shopify.sample.view.cart;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.shopify.sample.domain.interactor.CartWatchInteractor;
import com.shopify.sample.domain.interactor.CheckoutCreateInteractor;
import com.shopify.sample.domain.interactor.RealCartWatchInteractor;
import com.shopify.sample.domain.interactor.RealCheckoutCreateInteractor;
import com.shopify.sample.domain.model.Cart;
import com.shopify.sample.domain.model.CartItem;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.model.ShopSettings;
import com.shopify.sample.util.Function;
import com.shopify.sample.util.Util;
import com.shopify.sample.util.WeakObserver;
import com.shopify.sample.view.BaseViewModel;
import com.shopify.sample.view.LifeCycleBoundCallback;
import com.shopify.sample.view.cart.CartDetailsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.math.BigDecimal;
import kotlin.jvm.functions.Function1;
import timber.log.Timber;

/* JADX INFO: loaded from: classes6.dex */
public final class RealCartViewModel extends BaseViewModel implements CartDetailsViewModel, CartHeaderViewModel {
    public static final String STATE_KEY_CHECKOUT_ID = "checkout_id";
    public static final String STATE_KEY_PAY_CART = "pay_cart";
    public final LifeCycleBoundCallback<CartDetailsViewModel.AndroidPayCheckout> androidPayCheckoutCallback;
    public final LifeCycleBoundCallback<Cart> androidPayStartCheckoutCallback;
    public final MutableLiveData<Cart> cartLiveData;
    public final CartWatchInteractor cartWatchInteractor;
    public final CheckoutCreateInteractor checkoutCreateInteractor;
    public String checkoutId;
    public final MutableLiveData<Boolean> googleApiClientConnectionData;
    public final LifeCycleBoundCallback<Checkout> shopPayCheckoutCallback;
    public ShopSettings shopSettings;
    public final LifeCycleBoundCallback<Checkout> webCheckoutCallback;

    public RealCartViewModel(ShopSettings shopSettings) {
        RealCartWatchInteractor realCartWatchInteractor = new RealCartWatchInteractor();
        this.cartWatchInteractor = realCartWatchInteractor;
        this.checkoutCreateInteractor = new RealCheckoutCreateInteractor();
        this.webCheckoutCallback = new LifeCycleBoundCallback<>();
        this.shopPayCheckoutCallback = new LifeCycleBoundCallback<>();
        this.androidPayStartCheckoutCallback = new LifeCycleBoundCallback<>();
        this.androidPayCheckoutCallback = new LifeCycleBoundCallback<>();
        this.cartLiveData = new MutableLiveData<>();
        this.googleApiClientConnectionData = new MutableLiveData<>();
        this.shopSettings = shopSettings;
        registerRequest(CartDetailsViewModel.REQUEST_ID_UPDATE_CART, (Disposable) realCartWatchInteractor.execute().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(new WeakObserver.OnNextDelegate() { // from class: com.shopify.sample.view.cart.RealCartViewModel$$ExternalSyntheticLambda0
            @Override // com.shopify.sample.util.WeakObserver.OnNextDelegate
            public final void onNext(Object obj, Object obj2) {
                ((RealCartViewModel) obj).onCartUpdated((Cart) obj2);
            }
        }).create()));
    }

    @Override // com.shopify.sample.view.cart.CartHeaderViewModel
    public void webCheckout() {
        createCheckout(CartDetailsViewModel.REQUEST_ID_CREATE_WEB_CHECKOUT, this.cartLiveData.getValue());
    }

    @Override // com.shopify.sample.view.cart.CartHeaderViewModel
    public void androidPayCheckout() {
        createCheckout(CartDetailsViewModel.REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT, this.cartLiveData.getValue());
    }

    @Override // com.shopify.sample.view.cart.CartHeaderViewModel
    public void shopPayCheckout() {
        createCheckout(CartDetailsViewModel.REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT, this.cartLiveData.getValue());
    }

    @Override // com.shopify.sample.view.cart.CartHeaderViewModel
    public LiveData<Boolean> googleApiClientConnectionData() {
        return this.googleApiClientConnectionData;
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public void onGoogleApiClientConnectionChanged(boolean z) {
        this.googleApiClientConnectionData.setValue(Boolean.valueOf(z));
    }

    public static /* synthetic */ BigDecimal lambda$cartTotalLiveData$0(Cart cart) {
        return cart != null ? cart.totalPrice() : BigDecimal.ZERO;
    }

    @Override // com.shopify.sample.view.cart.CartHeaderViewModel
    public LiveData<BigDecimal> cartTotalLiveData() {
        return Transformations.map(this.cartLiveData, new Function1() { // from class: com.shopify.sample.view.cart.RealCartViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RealCartViewModel.lambda$cartTotalLiveData$0((Cart) obj);
            }
        });
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public LifeCycleBoundCallback<Checkout> webCheckoutCallback() {
        return this.webCheckoutCallback;
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public LifeCycleBoundCallback<Checkout> shopPayCheckoutCallback() {
        return this.shopPayCheckoutCallback;
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public LifeCycleBoundCallback<CartDetailsViewModel.AndroidPayCheckout> androidPayCheckoutCallback() {
        return this.androidPayCheckoutCallback;
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public LifeCycleBoundCallback<Cart> androidPayStartCheckoutCallback() {
        return this.androidPayStartCheckoutCallback;
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public Bundle saveState() {
        Bundle bundle = new Bundle();
        bundle.putString("checkout_id", this.checkoutId);
        return bundle;
    }

    @Override // com.shopify.sample.view.cart.CartDetailsViewModel
    public void restoreState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        this.checkoutId = bundle.getString("checkout_id");
    }

    public final void onCartUpdated(Cart cart) {
        this.cartLiveData.setValue(cart);
    }

    public final void createCheckout(final int i, Cart cart) {
        cancelRequest(CartDetailsViewModel.REQUEST_ID_CREATE_WEB_CHECKOUT);
        cancelRequest(CartDetailsViewModel.REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT);
        cancelRequest(CartDetailsViewModel.REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT);
        if (cart == null || cart.cartItems().size() == 0) {
            return;
        }
        showProgress(i);
        registerRequest(i, (Disposable) this.checkoutCreateInteractor.execute(Util.mapItems(cart.cartItems(), new Function() { // from class: com.shopify.sample.view.cart.RealCartViewModel$$ExternalSyntheticLambda2
            @Override // com.shopify.sample.util.Function
            public final Object apply(Object obj) {
                return RealCartViewModel.lambda$createCheckout$1((CartItem) obj);
            }
        })).toObservable().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(new WeakObserver.OnNextDelegate() { // from class: com.shopify.sample.view.cart.RealCartViewModel$$ExternalSyntheticLambda3
            @Override // com.shopify.sample.util.WeakObserver.OnNextDelegate
            public final void onNext(Object obj, Object obj2) {
                ((RealCartViewModel) obj).onCreateCheckout(i, (Checkout) obj2);
            }
        }).delegateOnError(new WeakObserver.OnErrorDelegate() { // from class: com.shopify.sample.view.cart.RealCartViewModel$$ExternalSyntheticLambda4
            @Override // com.shopify.sample.util.WeakObserver.OnErrorDelegate
            public final void onError(Object obj, Throwable th) {
                ((RealCartViewModel) obj).onCreateCheckoutError(i, th);
            }
        }).create()));
    }

    public static /* synthetic */ Checkout.LineItem lambda$createCheckout$1(CartItem cartItem) {
        return new Checkout.LineItem(cartItem.productVariantId, cartItem.variantTitle, cartItem.quantity, cartItem.price);
    }

    public final void onCreateCheckout(int i, @NonNull Checkout checkout) {
        hideProgress(i);
        this.checkoutId = checkout.id;
        if (i == CartDetailsViewModel.REQUEST_ID_CREATE_WEB_CHECKOUT) {
            this.webCheckoutCallback.notify(checkout);
        } else if (i != CartDetailsViewModel.REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT && i == CartDetailsViewModel.REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT) {
            this.shopPayCheckoutCallback.notify(checkout);
        }
    }

    public final void onCreateCheckoutError(int i, Throwable th) {
        Timber.e(th);
        hideProgress(i);
        notifyUserError(i, th, null);
    }
}
