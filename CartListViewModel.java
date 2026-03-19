package com.shopify.sample.view.cart;

import androidx.annotation.NonNull;
import com.shopify.sample.domain.interactor.CartAddItemInteractor;
import com.shopify.sample.domain.interactor.CartFetchInteractor;
import com.shopify.sample.domain.interactor.CartRemoveItemInteractor;
import com.shopify.sample.domain.interactor.CartWatchInteractor;
import com.shopify.sample.domain.interactor.RealCartAddItemInteractor;
import com.shopify.sample.domain.interactor.RealCartFetchInteractor;
import com.shopify.sample.domain.interactor.RealCartRemoveItemInteractor;
import com.shopify.sample.domain.interactor.RealCartWatchInteractor;
import com.shopify.sample.domain.model.Cart;
import com.shopify.sample.domain.model.CartItem;
import com.shopify.sample.view.BasePaginatedListViewModel;
import com.shopify.sample.view.base.ListItemViewModel;
import com.shopify.sample.view.cart.CartListItemViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class CartListViewModel extends BasePaginatedListViewModel<CartItem> implements CartListItemViewModel.OnChangeQuantityClickListener {
    public final CartWatchInteractor cartWatchInteractor = new RealCartWatchInteractor();
    public final CartAddItemInteractor cartAddItemInteractor = new RealCartAddItemInteractor();
    public final CartRemoveItemInteractor cartRemoveItemInteractor = new RealCartRemoveItemInteractor();
    public final CartFetchInteractor cartFetchInteractor = new RealCartFetchInteractor();

    public final /* synthetic */ ObservableSource lambda$nextPageRequestComposer$0(String str) throws Exception {
        return this.cartWatchInteractor.execute().map(new Function() { // from class: com.shopify.sample.view.cart.CartListViewModel$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Cart) obj).cartItems();
            }
        });
    }

    public final /* synthetic */ ObservableSource lambda$nextPageRequestComposer$1(Observable observable) {
        return observable.flatMap(new Function() { // from class: com.shopify.sample.view.cart.CartListViewModel$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$nextPageRequestComposer$0((String) obj);
            }
        });
    }

    @Override // com.shopify.sample.view.BasePaginatedListViewModel
    public ObservableTransformer<String, List<CartItem>> nextPageRequestComposer() {
        return new ObservableTransformer() { // from class: com.shopify.sample.view.cart.CartListViewModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableTransformer
            public final ObservableSource apply(Observable observable) {
                return this.f$0.lambda$nextPageRequestComposer$1(observable);
            }
        };
    }

    @Override // com.shopify.sample.view.BasePaginatedListViewModel
    @NonNull
    public List<ListItemViewModel> convertAndMerge(@NonNull List<CartItem> list, @NonNull List<ListItemViewModel> list2) {
        ArrayList arrayList = new ArrayList();
        Iterator<CartItem> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new CartListItemViewModel(it.next(), this));
        }
        if (!list.isEmpty()) {
            arrayList.add(new CartSubtotalListItemViewModel(this.cartFetchInteractor.execute()));
        }
        return arrayList;
    }

    @Override // com.shopify.sample.view.cart.CartListItemViewModel.OnChangeQuantityClickListener
    public void onAddCartItemClick(CartItem cartItem) {
        this.cartAddItemInteractor.execute(cartItem);
    }

    @Override // com.shopify.sample.view.cart.CartListItemViewModel.OnChangeQuantityClickListener
    public void onRemoveCartItemClick(CartItem cartItem) {
        this.cartRemoveItemInteractor.execute(cartItem);
    }
}
