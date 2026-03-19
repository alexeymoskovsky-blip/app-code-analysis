package com.shopify.sample.util;

import android.content.Context;
import android.content.Intent;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.mall.MallWebActivity;
import com.petkit.android.activities.mall.utils.ShopifyUtils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import com.shopify.buy3.GraphCallResult;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import com.shopify.sample.domain.model.Product;
import com.shopify.sample.util.MallUtils;
import com.shopify.sample.util.Util;
import com.shopify.sample.view.product.ProductDetailsActivity;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes6.dex */
public class MallUtils {
    public static void goToWebOrProductDetail(Context context, String str, int i) {
        if (context == null || str == null) {
            return;
        }
        if (i == 1) {
            goToProductDetail(context, str);
            return;
        }
        if (i == 2) {
            ShopifyUtils.getShareUrl(context, str, new PetkitCallback<String>() { // from class: com.shopify.sample.util.MallUtils.1
                public final /* synthetic */ Context val$context;

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                }

                public AnonymousClass1(Context context2) {
                    context = context2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str2) {
                    Context context2 = context;
                    context2.startActivity(MallWebActivity.newIntent(context2, "", str2));
                }
            });
        } else if (str.contains("http")) {
            new HashMap().put("id", str);
            ShopifyUtils.getShareUrl(context2, str, new PetkitCallback<String>() { // from class: com.shopify.sample.util.MallUtils.2
                public final /* synthetic */ Context val$context;

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                }

                public AnonymousClass2(Context context2) {
                    context = context2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str2) {
                    Context context2 = context;
                    context2.startActivity(MallWebActivity.newIntent(context2, "", str2));
                }
            });
        } else {
            goToProductDetail(context2, str);
        }
    }

    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$1 */
    public class AnonymousClass1 implements PetkitCallback<String> {
        public final /* synthetic */ Context val$context;

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
        }

        public AnonymousClass1(Context context2) {
            context = context2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str2) {
            Context context2 = context;
            context2.startActivity(MallWebActivity.newIntent(context2, "", str2));
        }
    }

    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public final /* synthetic */ Context val$context;

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
        }

        public AnonymousClass2(Context context2) {
            context = context2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str2) {
            Context context2 = context;
            context2.startActivity(MallWebActivity.newIntent(context2, "", str2));
        }
    }

    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$3 */
    public class AnonymousClass3 implements Storefront.QueryRootQuery.ProductArgumentsDefinition {
        public final /* synthetic */ String val$productId;

        public AnonymousClass3(String str) {
            str = str;
        }

        @Override // com.shopify.buy3.Storefront.QueryRootQuery.ProductArgumentsDefinition
        public void define(Storefront.QueryRootQuery.ProductArguments productArguments) {
            productArguments.id(new ID(str));
        }
    }

    public static void goToProductDetail(Context context, final String str) {
        LoadDialog.show(context);
        BaseApplication.graphClient().queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.util.MallUtils$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                MallUtils.lambda$goToProductDetail$0(str, queryRootQuery);
            }
        })).enqueue(CallbackExecutors.createDefault().handler(), (Function1<? super GraphCallResult<? extends Storefront.QueryRoot>, Unit>) new AnonymousClass5(context, str));
    }

    public static /* synthetic */ void lambda$goToProductDetail$0(String str, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.product(new Storefront.QueryRootQuery.ProductArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.3
            public final /* synthetic */ String val$productId;

            public AnonymousClass3(String str2) {
                str = str2;
            }

            @Override // com.shopify.buy3.Storefront.QueryRootQuery.ProductArgumentsDefinition
            public void define(Storefront.QueryRootQuery.ProductArguments productArguments) {
                productArguments.id(new ID(str));
            }
        }, new Storefront.ProductQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4
            @Override // com.shopify.buy3.Storefront.ProductQueryDefinition
            public void define(Storefront.ProductQuery productQuery) {
                productQuery.title();
                productQuery.descriptionHtml();
                productQuery.options(new Storefront.ProductQuery.OptionsArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.4.1
                    public AnonymousClass1() {
                    }

                    @Override // com.shopify.buy3.Storefront.ProductQuery.OptionsArgumentsDefinition
                    public void define(Storefront.ProductQuery.OptionsArguments optionsArguments) {
                        optionsArguments.first(3);
                    }
                }, new Storefront.ProductOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.2
                    public AnonymousClass2() {
                    }

                    @Override // com.shopify.buy3.Storefront.ProductOptionQueryDefinition
                    public void define(Storefront.ProductOptionQuery productOptionQuery) {
                        productOptionQuery.name();
                        productOptionQuery.values();
                    }
                });
                productQuery.images(new Storefront.ProductQuery.ImagesArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.4.3
                    public AnonymousClass3() {
                    }

                    @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
                    public void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                        imagesArguments.first(3);
                    }
                }, new Storefront.ImageConnectionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4
                    public C02104() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1 */
                    public class AnonymousClass1 implements Storefront.ImageEdgeQueryDefinition {
                        public AnonymousClass1() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                        public class C02111 implements Storefront.ImageQueryDefinition {
                            public C02111() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                        public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                            imageEdgeQuery.cursor();
                            imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                                public C02111() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
                    public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                        imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1
                            public AnonymousClass1() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                            public class C02111 implements Storefront.ImageQueryDefinition {
                                public C02111() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                            public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                                imageEdgeQuery.cursor();
                                imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                                    public C02111() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                            }
                        });
                    }
                });
                productQuery.variants(new Storefront.ProductQuery.VariantsArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.4.5
                    public AnonymousClass5() {
                    }

                    @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
                    public void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                        variantsArguments.first(3);
                    }
                }, new Storefront.ProductVariantConnectionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6
                    public AnonymousClass6() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1 */
                    public class AnonymousClass1 implements Storefront.ProductVariantEdgeQueryDefinition {
                        public AnonymousClass1() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                        public class C02121 implements Storefront.ProductVariantQueryDefinition {
                            public C02121() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                            public class C02131 implements Storefront.ImageQueryDefinition {
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                            public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                productVariantQuery.price();
                                productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                                productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                });
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                            public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                        public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                            productVariantEdgeQuery.cursor();
                            productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                                public C02121() {
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                                public class C02131 implements Storefront.ImageQueryDefinition {
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                }

                                @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                                public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                    productVariantQuery.price();
                                    productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                        public C02131() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                        public void define(Storefront.ImageQuery imageQuery) {
                                            imageQuery.src();
                                        }
                                    });
                                    productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                        public AnonymousClass2() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                        public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                            selectedOptionQuery.value();
                                            selectedOptionQuery.name();
                                        }
                                    });
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                                public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                }
                            });
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
                    public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                        productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1
                            public AnonymousClass1() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                            public class C02121 implements Storefront.ProductVariantQueryDefinition {
                                public C02121() {
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                                public class C02131 implements Storefront.ImageQueryDefinition {
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                }

                                @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                                public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                    productVariantQuery.price();
                                    productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                        public C02131() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                        public void define(Storefront.ImageQuery imageQuery) {
                                            imageQuery.src();
                                        }
                                    });
                                    productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                        public AnonymousClass2() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                        public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                            selectedOptionQuery.value();
                                            selectedOptionQuery.name();
                                        }
                                    });
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                                public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                            public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                                productVariantEdgeQuery.cursor();
                                productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                                    public C02121() {
                                    }

                                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                                    public class C02131 implements Storefront.ImageQueryDefinition {
                                        public C02131() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                        public void define(Storefront.ImageQuery imageQuery) {
                                            imageQuery.src();
                                        }
                                    }

                                    @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                                    public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                        productVariantQuery.price();
                                        productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                            public C02131() {
                                            }

                                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                            public void define(Storefront.ImageQuery imageQuery) {
                                                imageQuery.src();
                                            }
                                        });
                                        productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                            public AnonymousClass2() {
                                            }

                                            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                            public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                                selectedOptionQuery.value();
                                                selectedOptionQuery.name();
                                            }
                                        });
                                    }

                                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                                    public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                        public AnonymousClass2() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                        public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                            selectedOptionQuery.value();
                                            selectedOptionQuery.name();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$1 */
            public class AnonymousClass1 implements Storefront.ProductQuery.OptionsArgumentsDefinition {
                public AnonymousClass1() {
                }

                @Override // com.shopify.buy3.Storefront.ProductQuery.OptionsArgumentsDefinition
                public void define(Storefront.ProductQuery.OptionsArguments optionsArguments) {
                    optionsArguments.first(3);
                }
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$2 */
            public class AnonymousClass2 implements Storefront.ProductOptionQueryDefinition {
                public AnonymousClass2() {
                }

                @Override // com.shopify.buy3.Storefront.ProductOptionQueryDefinition
                public void define(Storefront.ProductOptionQuery productOptionQuery) {
                    productOptionQuery.name();
                    productOptionQuery.values();
                }
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$3 */
            public class AnonymousClass3 implements Storefront.ProductQuery.ImagesArgumentsDefinition {
                public AnonymousClass3() {
                }

                @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
                public void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                    imagesArguments.first(3);
                }
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4 */
            public class C02104 implements Storefront.ImageConnectionQueryDefinition {
                public C02104() {
                }

                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1 */
                public class AnonymousClass1 implements Storefront.ImageEdgeQueryDefinition {
                    public AnonymousClass1() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                    public class C02111 implements Storefront.ImageQueryDefinition {
                        public C02111() {
                        }

                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                        public void define(Storefront.ImageQuery imageQuery) {
                            imageQuery.src();
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                    public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                        imageEdgeQuery.cursor();
                        imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                            public C02111() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        });
                    }
                }

                @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
                public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                    imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1
                        public AnonymousClass1() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                        public class C02111 implements Storefront.ImageQueryDefinition {
                            public C02111() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                        public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                            imageEdgeQuery.cursor();
                            imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                                public C02111() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                        }
                    });
                }
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$5 */
            public class AnonymousClass5 implements Storefront.ProductQuery.VariantsArgumentsDefinition {
                public AnonymousClass5() {
                }

                @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
                public void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                    variantsArguments.first(3);
                }
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6 */
            public class AnonymousClass6 implements Storefront.ProductVariantConnectionQueryDefinition {
                public AnonymousClass6() {
                }

                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1 */
                public class AnonymousClass1 implements Storefront.ProductVariantEdgeQueryDefinition {
                    public AnonymousClass1() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                    public class C02121 implements Storefront.ProductVariantQueryDefinition {
                        public C02121() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                        public class C02131 implements Storefront.ImageQueryDefinition {
                            public C02131() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                        public void define(Storefront.ProductVariantQuery productVariantQuery) {
                            productVariantQuery.price();
                            productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                            productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            });
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                        public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                            public AnonymousClass2() {
                            }

                            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                            public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                selectedOptionQuery.value();
                                selectedOptionQuery.name();
                            }
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                    public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                        productVariantEdgeQuery.cursor();
                        productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                            public C02121() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                            public class C02131 implements Storefront.ImageQueryDefinition {
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                            public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                productVariantQuery.price();
                                productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                                productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                });
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                            public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            }
                        });
                    }
                }

                @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
                public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                    productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1
                        public AnonymousClass1() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                        public class C02121 implements Storefront.ProductVariantQueryDefinition {
                            public C02121() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                            public class C02131 implements Storefront.ImageQueryDefinition {
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                            public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                productVariantQuery.price();
                                productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                                productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                });
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                            public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                        public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                            productVariantEdgeQuery.cursor();
                            productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                                public C02121() {
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                                public class C02131 implements Storefront.ImageQueryDefinition {
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                }

                                @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                                public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                    productVariantQuery.price();
                                    productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                        public C02131() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                        public void define(Storefront.ImageQuery imageQuery) {
                                            imageQuery.src();
                                        }
                                    });
                                    productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                        public AnonymousClass2() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                        public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                            selectedOptionQuery.value();
                                            selectedOptionQuery.name();
                                        }
                                    });
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                                public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4 */
    public class AnonymousClass4 implements Storefront.ProductQueryDefinition {
        @Override // com.shopify.buy3.Storefront.ProductQueryDefinition
        public void define(Storefront.ProductQuery productQuery) {
            productQuery.title();
            productQuery.descriptionHtml();
            productQuery.options(new Storefront.ProductQuery.OptionsArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.4.1
                public AnonymousClass1() {
                }

                @Override // com.shopify.buy3.Storefront.ProductQuery.OptionsArgumentsDefinition
                public void define(Storefront.ProductQuery.OptionsArguments optionsArguments) {
                    optionsArguments.first(3);
                }
            }, new Storefront.ProductOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.2
                public AnonymousClass2() {
                }

                @Override // com.shopify.buy3.Storefront.ProductOptionQueryDefinition
                public void define(Storefront.ProductOptionQuery productOptionQuery) {
                    productOptionQuery.name();
                    productOptionQuery.values();
                }
            });
            productQuery.images(new Storefront.ProductQuery.ImagesArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.4.3
                public AnonymousClass3() {
                }

                @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
                public void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                    imagesArguments.first(3);
                }
            }, new Storefront.ImageConnectionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4
                public C02104() {
                }

                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1 */
                public class AnonymousClass1 implements Storefront.ImageEdgeQueryDefinition {
                    public AnonymousClass1() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                    public class C02111 implements Storefront.ImageQueryDefinition {
                        public C02111() {
                        }

                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                        public void define(Storefront.ImageQuery imageQuery) {
                            imageQuery.src();
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                    public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                        imageEdgeQuery.cursor();
                        imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                            public C02111() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        });
                    }
                }

                @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
                public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                    imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1
                        public AnonymousClass1() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                        public class C02111 implements Storefront.ImageQueryDefinition {
                            public C02111() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                        public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                            imageEdgeQuery.cursor();
                            imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                                public C02111() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                        }
                    });
                }
            });
            productQuery.variants(new Storefront.ProductQuery.VariantsArgumentsDefinition() { // from class: com.shopify.sample.util.MallUtils.4.5
                public AnonymousClass5() {
                }

                @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
                public void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                    variantsArguments.first(3);
                }
            }, new Storefront.ProductVariantConnectionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6
                public AnonymousClass6() {
                }

                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1 */
                public class AnonymousClass1 implements Storefront.ProductVariantEdgeQueryDefinition {
                    public AnonymousClass1() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                    public class C02121 implements Storefront.ProductVariantQueryDefinition {
                        public C02121() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                        public class C02131 implements Storefront.ImageQueryDefinition {
                            public C02131() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                        public void define(Storefront.ProductVariantQuery productVariantQuery) {
                            productVariantQuery.price();
                            productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                            productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            });
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                        public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                            public AnonymousClass2() {
                            }

                            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                            public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                selectedOptionQuery.value();
                                selectedOptionQuery.name();
                            }
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                    public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                        productVariantEdgeQuery.cursor();
                        productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                            public C02121() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                            public class C02131 implements Storefront.ImageQueryDefinition {
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                            public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                productVariantQuery.price();
                                productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                                productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                });
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                            public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            }
                        });
                    }
                }

                @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
                public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                    productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1
                        public AnonymousClass1() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                        public class C02121 implements Storefront.ProductVariantQueryDefinition {
                            public C02121() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                            public class C02131 implements Storefront.ImageQueryDefinition {
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                            public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                productVariantQuery.price();
                                productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                                productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                });
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                            public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                        public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                            productVariantEdgeQuery.cursor();
                            productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                                public C02121() {
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                                public class C02131 implements Storefront.ImageQueryDefinition {
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                }

                                @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                                public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                    productVariantQuery.price();
                                    productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                        public C02131() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                        public void define(Storefront.ImageQuery imageQuery) {
                                            imageQuery.src();
                                        }
                                    });
                                    productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                        public AnonymousClass2() {
                                        }

                                        @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                        public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                            selectedOptionQuery.value();
                                            selectedOptionQuery.name();
                                        }
                                    });
                                }

                                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                                public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }

        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$1 */
        public class AnonymousClass1 implements Storefront.ProductQuery.OptionsArgumentsDefinition {
            public AnonymousClass1() {
            }

            @Override // com.shopify.buy3.Storefront.ProductQuery.OptionsArgumentsDefinition
            public void define(Storefront.ProductQuery.OptionsArguments optionsArguments) {
                optionsArguments.first(3);
            }
        }

        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$2 */
        public class AnonymousClass2 implements Storefront.ProductOptionQueryDefinition {
            public AnonymousClass2() {
            }

            @Override // com.shopify.buy3.Storefront.ProductOptionQueryDefinition
            public void define(Storefront.ProductOptionQuery productOptionQuery) {
                productOptionQuery.name();
                productOptionQuery.values();
            }
        }

        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$3 */
        public class AnonymousClass3 implements Storefront.ProductQuery.ImagesArgumentsDefinition {
            public AnonymousClass3() {
            }

            @Override // com.shopify.buy3.Storefront.ProductQuery.ImagesArgumentsDefinition
            public void define(Storefront.ProductQuery.ImagesArguments imagesArguments) {
                imagesArguments.first(3);
            }
        }

        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4 */
        public class C02104 implements Storefront.ImageConnectionQueryDefinition {
            public C02104() {
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1 */
            public class AnonymousClass1 implements Storefront.ImageEdgeQueryDefinition {
                public AnonymousClass1() {
                }

                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                public class C02111 implements Storefront.ImageQueryDefinition {
                    public C02111() {
                    }

                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                    public void define(Storefront.ImageQuery imageQuery) {
                        imageQuery.src();
                    }
                }

                @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                    imageEdgeQuery.cursor();
                    imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                        public C02111() {
                        }

                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                        public void define(Storefront.ImageQuery imageQuery) {
                            imageQuery.src();
                        }
                    });
                }
            }

            @Override // com.shopify.buy3.Storefront.ImageConnectionQueryDefinition
            public void define(Storefront.ImageConnectionQuery imageConnectionQuery) {
                imageConnectionQuery.edges(new Storefront.ImageEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1
                    public AnonymousClass1() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$4$1$1 */
                    public class C02111 implements Storefront.ImageQueryDefinition {
                        public C02111() {
                        }

                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                        public void define(Storefront.ImageQuery imageQuery) {
                            imageQuery.src();
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ImageEdgeQueryDefinition
                    public void define(Storefront.ImageEdgeQuery imageEdgeQuery) {
                        imageEdgeQuery.cursor();
                        imageEdgeQuery.node(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.4.1.1
                            public C02111() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        });
                    }
                });
            }
        }

        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$5 */
        public class AnonymousClass5 implements Storefront.ProductQuery.VariantsArgumentsDefinition {
            public AnonymousClass5() {
            }

            @Override // com.shopify.buy3.Storefront.ProductQuery.VariantsArgumentsDefinition
            public void define(Storefront.ProductQuery.VariantsArguments variantsArguments) {
                variantsArguments.first(3);
            }
        }

        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6 */
        public class AnonymousClass6 implements Storefront.ProductVariantConnectionQueryDefinition {
            public AnonymousClass6() {
            }

            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1 */
            public class AnonymousClass1 implements Storefront.ProductVariantEdgeQueryDefinition {
                public AnonymousClass1() {
                }

                /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                public class C02121 implements Storefront.ProductVariantQueryDefinition {
                    public C02121() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                    public class C02131 implements Storefront.ImageQueryDefinition {
                        public C02131() {
                        }

                        @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                        public void define(Storefront.ImageQuery imageQuery) {
                            imageQuery.src();
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                    public void define(Storefront.ProductVariantQuery productVariantQuery) {
                        productVariantQuery.price();
                        productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                            public C02131() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        });
                        productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                            public AnonymousClass2() {
                            }

                            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                            public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                selectedOptionQuery.value();
                                selectedOptionQuery.name();
                            }
                        });
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                    public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                        public AnonymousClass2() {
                        }

                        @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                        public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                            selectedOptionQuery.value();
                            selectedOptionQuery.name();
                        }
                    }
                }

                @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                    productVariantEdgeQuery.cursor();
                    productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                        public C02121() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                        public class C02131 implements Storefront.ImageQueryDefinition {
                            public C02131() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                        public void define(Storefront.ProductVariantQuery productVariantQuery) {
                            productVariantQuery.price();
                            productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                            productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            });
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                        public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                            public AnonymousClass2() {
                            }

                            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                            public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                selectedOptionQuery.value();
                                selectedOptionQuery.name();
                            }
                        }
                    });
                }
            }

            @Override // com.shopify.buy3.Storefront.ProductVariantConnectionQueryDefinition
            public void define(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
                productVariantConnectionQuery.edges(new Storefront.ProductVariantEdgeQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1
                    public AnonymousClass1() {
                    }

                    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1 */
                    public class C02121 implements Storefront.ProductVariantQueryDefinition {
                        public C02121() {
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                        public class C02131 implements Storefront.ImageQueryDefinition {
                            public C02131() {
                            }

                            @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                            public void define(Storefront.ImageQuery imageQuery) {
                                imageQuery.src();
                            }
                        }

                        @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                        public void define(Storefront.ProductVariantQuery productVariantQuery) {
                            productVariantQuery.price();
                            productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            });
                            productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            });
                        }

                        /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                        public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                            public AnonymousClass2() {
                            }

                            @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                            public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                selectedOptionQuery.value();
                                selectedOptionQuery.name();
                            }
                        }
                    }

                    @Override // com.shopify.buy3.Storefront.ProductVariantEdgeQueryDefinition
                    public void define(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
                        productVariantEdgeQuery.cursor();
                        productVariantEdgeQuery.node(new Storefront.ProductVariantQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1
                            public C02121() {
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$1 */
                            public class C02131 implements Storefront.ImageQueryDefinition {
                                public C02131() {
                                }

                                @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                public void define(Storefront.ImageQuery imageQuery) {
                                    imageQuery.src();
                                }
                            }

                            @Override // com.shopify.buy3.Storefront.ProductVariantQueryDefinition
                            public void define(Storefront.ProductVariantQuery productVariantQuery) {
                                productVariantQuery.price();
                                productVariantQuery.image(new Storefront.ImageQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.1
                                    public C02131() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.ImageQueryDefinition
                                    public void define(Storefront.ImageQuery imageQuery) {
                                        imageQuery.src();
                                    }
                                });
                                productVariantQuery.selectedOptions(new Storefront.SelectedOptionQueryDefinition() { // from class: com.shopify.sample.util.MallUtils.4.6.1.1.2
                                    public AnonymousClass2() {
                                    }

                                    @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                    public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                        selectedOptionQuery.value();
                                        selectedOptionQuery.name();
                                    }
                                });
                            }

                            /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$4$6$1$1$2 */
                            public class AnonymousClass2 implements Storefront.SelectedOptionQueryDefinition {
                                public AnonymousClass2() {
                                }

                                @Override // com.shopify.buy3.Storefront.SelectedOptionQueryDefinition
                                public void define(Storefront.SelectedOptionQuery selectedOptionQuery) {
                                    selectedOptionQuery.value();
                                    selectedOptionQuery.name();
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.shopify.sample.util.MallUtils$5 */
    public class AnonymousClass5 implements Function1<GraphCallResult<? extends Storefront.QueryRoot>, Unit> {
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ String val$productId;

        public AnonymousClass5(Context context, String str) {
            this.val$context = context;
            this.val$productId = str;
        }

        @Override // kotlin.jvm.functions.Function1
        public Unit invoke(GraphCallResult<? extends Storefront.QueryRoot> graphCallResult) {
            LoadDialog.dismissDialog();
            if (graphCallResult instanceof GraphCallResult.Success) {
                HashMap<String, Object> map = ((Storefront.QueryRoot) ((GraphCallResult.Success) graphCallResult).getResponse().getData()).responseData;
                Storefront.Product product = (Storefront.Product) map.get(map.keySet().toArray()[0]);
                if (product != null) {
                    List<Storefront.ImageEdge> edges = product.getImages() != null ? product.getImages().getEdges() : null;
                    List<Storefront.ProductVariantEdge> edges2 = product.getVariants() != null ? product.getVariants().getEdges() : null;
                    List<Storefront.ProductOption> options = product.getOptions() != null ? product.getOptions() : null;
                    Product product2 = new Product(product.getId().toString(), product.getTitle(), (String) Util.reduce(edges, new Util.ReduceCallback() { // from class: com.shopify.sample.util.MallUtils$5$$ExternalSyntheticLambda0
                        @Override // com.shopify.sample.util.Util.ReduceCallback
                        public final Object reduce(Object obj, Object obj2) {
                            return MallUtils.AnonymousClass5.lambda$invoke$0((String) obj, (Storefront.ImageEdge) obj2);
                        }
                    }, null), (BigDecimal) Util.reduce(edges2, new Util.ReduceCallback() { // from class: com.shopify.sample.util.MallUtils$5$$ExternalSyntheticLambda1
                        @Override // com.shopify.sample.util.Util.ReduceCallback
                        public final Object reduce(Object obj, Object obj2) {
                            return MallUtils.AnonymousClass5.lambda$invoke$1((BigDecimal) obj, (Storefront.ProductVariantEdge) obj2);
                        }
                    }, BigDecimal.ZERO), edges2.get(0).getCursor());
                    PetkitLog.d("Option name:" + options.get(0).getName());
                    PetkitLog.d("descriptionHtml:" + product.getDescriptionHtml());
                    PetkitLog.d("Option size:" + options.size());
                    for (int i = 0; i < edges2.size(); i++) {
                        PetkitLog.d("SlectedOption name:" + edges2.get(i).getNode().getSelectedOptions().get(0).getName());
                        PetkitLog.d("SlectedOption value:" + edges2.get(i).getNode().getSelectedOptions().get(0).getValue());
                        PetkitLog.d("SlectedOption size:" + edges2.get(i).getNode().getSelectedOptions().size());
                        PetkitLog.d("variants image:" + edges2.get(i).getNode().getImage().getSrc());
                    }
                    Intent intent = new Intent(this.val$context, (Class<?>) ProductDetailsActivity.class);
                    intent.putExtra("product_id", product2.id);
                    intent.putExtra("product_title", product2.title);
                    intent.putExtra("product_image_url", product2.image);
                    intent.putExtra("product_price", product2.price.doubleValue());
                    this.val$context.startActivity(intent);
                    HashMap map2 = new HashMap();
                    map2.put("id", this.val$productId);
                    map2.put("result", "success");
                } else {
                    HashMap map3 = new HashMap();
                    map3.put("id", this.val$productId);
                    map3.put("result", "fail");
                    PetkitLog.d("goToProductDetail error: product is null");
                    LogcatStorageHelper.addLog("goToProductDetail error: product is null");
                }
            } else {
                GraphError error = ((GraphCallResult.Failure) graphCallResult).getError();
                if (error != null) {
                    String message = error.getMessage();
                    PetkitToast.showToast(this.val$context.getResources().getString(R.string.Hint_network_failed));
                    PetkitLog.d("goToProductDetail error:" + message);
                    LogcatStorageHelper.addLog("goToProductDetail error:" + message);
                } else {
                    PetkitLog.d("goToProductDetail error: error is null");
                    LogcatStorageHelper.addLog("goToProductDetail error: error is null");
                }
                HashMap map4 = new HashMap();
                map4.put("id", this.val$productId);
                map4.put("result", "fail");
            }
            return Unit.INSTANCE;
        }

        public static /* synthetic */ String lambda$invoke$0(String str, Storefront.ImageEdge imageEdge) {
            return imageEdge.getNode().getSrc();
        }

        public static /* synthetic */ BigDecimal lambda$invoke$1(BigDecimal bigDecimal, Storefront.ProductVariantEdge productVariantEdge) {
            return productVariantEdge.getNode().getPrice();
        }
    }
}
