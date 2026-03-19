package com.shopify.sample.domain.interactor;

import androidx.annotation.NonNull;
import com.petkit.android.activities.base.BaseApplication;
import com.shopify.buy3.Storefront;
import com.shopify.sample.domain.model.ShopSettings;
import com.shopify.sample.domain.repository.ShopRepository;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes6.dex */
public final class RealShopSettingInteractor implements ShopSettingInteractor {
    public final ShopRepository repository = new ShopRepository(BaseApplication.graphClient());

    @Override // com.shopify.sample.domain.interactor.ShopSettingInteractor
    @NonNull
    public Single<ShopSettings> execute() {
        return this.repository.shopSettings(new Storefront.ShopQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealShopSettingInteractor$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.ShopQueryDefinition
            public final void define(Storefront.ShopQuery shopQuery) {
                RealShopSettingInteractor.lambda$execute$1(shopQuery);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.interactor.RealShopSettingInteractor$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Converters.convertToShopSettings((Storefront.Shop) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$1(Storefront.ShopQuery shopQuery) {
        shopQuery.name().paymentSettings(new Storefront.PaymentSettingsQueryDefinition() { // from class: com.shopify.sample.domain.interactor.RealShopSettingInteractor$$ExternalSyntheticLambda2
            @Override // com.shopify.buy3.Storefront.PaymentSettingsQueryDefinition
            public final void define(Storefront.PaymentSettingsQuery paymentSettingsQuery) {
                RealShopSettingInteractor.lambda$execute$0(paymentSettingsQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Storefront.PaymentSettingsQuery paymentSettingsQuery) {
        paymentSettingsQuery.countryCode().acceptedCardBrands();
    }
}
