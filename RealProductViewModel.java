package com.shopify.sample.view.product;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.shopify.sample.domain.interactor.CartAddItemInteractor;
import com.shopify.sample.domain.interactor.CheckoutCreateInteractor;
import com.shopify.sample.domain.interactor.ProductByIdInteractor;
import com.shopify.sample.domain.interactor.RealCartAddItemInteractor;
import com.shopify.sample.domain.interactor.RealCheckoutCreateInteractor;
import com.shopify.sample.domain.interactor.RealProductByIdInteractor;
import com.shopify.sample.domain.model.Cart;
import com.shopify.sample.domain.model.CartItem;
import com.shopify.sample.domain.model.Checkout;
import com.shopify.sample.domain.model.ProductDetails;
import com.shopify.sample.util.Function;
import com.shopify.sample.util.Util;
import com.shopify.sample.util.WeakObserver;
import com.shopify.sample.view.BaseViewModel;
import com.shopify.sample.view.LifeCycleBoundCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.UUID;
import timber.log.Timber;

/* JADX INFO: loaded from: classes6.dex */
public final class RealProductViewModel extends BaseViewModel implements ProductViewModel {
    public final String productId;
    public int REQUEST_ID_UPDATE_CART = UUID.randomUUID().hashCode();
    public int REQUEST_ID_CREATE_WEB_CHECKOUT = UUID.randomUUID().hashCode();
    public int REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT = UUID.randomUUID().hashCode();
    public int REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT = UUID.randomUUID().hashCode();
    public int REQUEST_ID_PREPARE_ANDROID_PAY = UUID.randomUUID().hashCode();
    public final ProductByIdInteractor productByIdInteractor = new RealProductByIdInteractor();
    public final CheckoutCreateInteractor checkoutCreateInteractor = new RealCheckoutCreateInteractor();
    public final CartAddItemInteractor cartAddItemInteractor = new RealCartAddItemInteractor();
    public final MutableLiveData<ProductDetails> productLiveData = new MutableLiveData<>();
    public final LifeCycleBoundCallback<Checkout> shopPayCheckoutCallback = new LifeCycleBoundCallback<>();

    public RealProductViewModel(@NonNull String str) {
        this.productId = (String) Util.checkNotNull(str, "productId == null");
        refetch();
    }

    @Override // com.shopify.sample.view.product.ProductViewModel
    public void refetch() {
        int i = ProductViewModel.REQUEST_ID_PRODUCT_DETAILS;
        showProgress(i);
        registerRequest(i, (Disposable) this.productByIdInteractor.execute(this.productId).toObservable().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(new WeakObserver.OnNextDelegate() { // from class: com.shopify.sample.view.product.RealProductViewModel$$ExternalSyntheticLambda0
            @Override // com.shopify.sample.util.WeakObserver.OnNextDelegate
            public final void onNext(Object obj, Object obj2) {
                ((RealProductViewModel) obj).onProductDetailsResponse((ProductDetails) obj2);
            }
        }).delegateOnError(new WeakObserver.OnErrorDelegate() { // from class: com.shopify.sample.view.product.RealProductViewModel$$ExternalSyntheticLambda1
            @Override // com.shopify.sample.util.WeakObserver.OnErrorDelegate
            public final void onError(Object obj, Throwable th) {
                ((RealProductViewModel) obj).onProductDetailsError(th);
            }
        }).create()));
    }

    @Override // com.shopify.sample.view.product.ProductViewModel
    public LiveData<ProductDetails> productLiveData() {
        return this.productLiveData;
    }

    @Override // com.shopify.sample.view.product.ProductViewModel
    public void addToCart(ProductDetails productDetails, int i) {
        if (productDetails != null) {
            ProductDetails.Variant variant = (ProductDetails.Variant) Util.checkNotNull(productDetails.variants.get(productDetails.getSelectedNum()), "can't find default variant");
            for (int i2 = 0; i2 < i; i2++) {
                this.cartAddItemInteractor.execute(new CartItem(productDetails.id, variant.id, productDetails.title, variant.title, variant.price, Util.mapItems(variant.selectedOptions, new Function() { // from class: com.shopify.sample.view.product.RealProductViewModel$$ExternalSyntheticLambda2
                    @Override // com.shopify.sample.util.Function
                    public final Object apply(Object obj) {
                        return RealProductViewModel.lambda$addToCart$0((ProductDetails.SelectedOption) obj);
                    }
                }), variant.image));
            }
        }
    }

    public static /* synthetic */ CartItem.Option lambda$addToCart$0(ProductDetails.SelectedOption selectedOption) {
        return new CartItem.Option(selectedOption.name, selectedOption.value);
    }

    @Override // com.shopify.sample.view.product.ProductViewModel
    public void shopPayCheckout(Cart cart) {
        createCheckout(this.REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT, cart);
    }

    @Override // com.shopify.sample.view.product.ProductViewModel
    public LifeCycleBoundCallback<Checkout> shopPayCheckoutCallback() {
        return this.shopPayCheckoutCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCreateCheckout(int i, @NonNull Checkout checkout) {
        hideProgress(i);
        if (i == this.REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT) {
            this.shopPayCheckoutCallback.notify(checkout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCreateCheckoutError(int i, Throwable th) {
        Timber.e(th);
        hideProgress(i);
        notifyUserError(i, th, null);
    }

    private void createCheckout(final int i, Cart cart) {
        cancelRequest(this.REQUEST_ID_CREATE_WEB_CHECKOUT);
        cancelRequest(this.REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT);
        cancelRequest(this.REQUEST_ID_CREATE_SHOP_PAY_CHECKOUT);
        if (cart == null) {
            return;
        }
        showProgress(i);
        registerRequest(i, (Disposable) this.checkoutCreateInteractor.execute(Util.mapItems(cart.cartItems(), new Function() { // from class: com.shopify.sample.view.product.RealProductViewModel$$ExternalSyntheticLambda3
            @Override // com.shopify.sample.util.Function
            public final Object apply(Object obj) {
                return RealProductViewModel.lambda$createCheckout$1((CartItem) obj);
            }
        })).toObservable().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(new WeakObserver.OnNextDelegate() { // from class: com.shopify.sample.view.product.RealProductViewModel$$ExternalSyntheticLambda4
            @Override // com.shopify.sample.util.WeakObserver.OnNextDelegate
            public final void onNext(Object obj, Object obj2) {
                ((RealProductViewModel) obj).onCreateCheckout(i, (Checkout) obj2);
            }
        }).delegateOnError(new WeakObserver.OnErrorDelegate() { // from class: com.shopify.sample.view.product.RealProductViewModel$$ExternalSyntheticLambda5
            @Override // com.shopify.sample.util.WeakObserver.OnErrorDelegate
            public final void onError(Object obj, Throwable th) {
                ((RealProductViewModel) obj).onCreateCheckoutError(i, th);
            }
        }).create()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Checkout.LineItem lambda$createCheckout$1(CartItem cartItem) {
        return new Checkout.LineItem(cartItem.productVariantId, cartItem.variantTitle, cartItem.quantity, cartItem.price);
    }

    public final void onProductDetailsResponse(ProductDetails productDetails) {
        hideProgress(ProductViewModel.REQUEST_ID_PRODUCT_DETAILS);
        this.productLiveData.setValue(productDetails);
    }

    public final void onProductDetailsError(Throwable th) {
        int i = ProductViewModel.REQUEST_ID_PRODUCT_DETAILS;
        hideProgress(i);
        notifyUserError(i, th);
    }
}
