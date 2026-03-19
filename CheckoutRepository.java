package com.shopify.sample.domain.repository;

import androidx.annotation.NonNull;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.HttpCachePolicy;
import com.shopify.buy3.QueryGraphCall;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import com.shopify.sample.RxUtil;
import com.shopify.sample.util.Util;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes6.dex */
public final class CheckoutRepository {
    public final GraphClient graphClient;

    public CheckoutRepository(@NonNull GraphClient graphClient) {
        this.graphClient = (GraphClient) Util.checkNotNull(graphClient, "graphClient == null");
    }

    public Single<Storefront.Checkout> create(@NonNull final Storefront.CheckoutCreateInput checkoutCreateInput, @NonNull final Storefront.CheckoutCreatePayloadQueryDefinition checkoutCreatePayloadQueryDefinition) {
        Util.checkNotNull(checkoutCreateInput, "input == null");
        Util.checkNotNull(checkoutCreatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(new Storefront.MutationQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda22
            @Override // com.shopify.buy3.Storefront.MutationQueryDefinition
            public final void define(Storefront.MutationQuery mutationQuery) {
                mutationQuery.checkoutCreate(checkoutCreateInput, checkoutCreatePayloadQueryDefinition);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda23
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Mutation) obj).getCheckoutCreate();
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.CheckoutCreatePayload) obj).getCheckout();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Single<Storefront.Checkout> updateShippingAddress(@NonNull final String str, @NonNull final Storefront.MailingAddressInput mailingAddressInput, @NonNull final Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotNull(mailingAddressInput, "input == null");
        Util.checkNotNull(checkoutShippingAddressUpdatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(new Storefront.MutationQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda13
            @Override // com.shopify.buy3.Storefront.MutationQueryDefinition
            public final void define(Storefront.MutationQuery mutationQuery) {
                CheckoutRepository.lambda$updateShippingAddress$3(mailingAddressInput, str, checkoutShippingAddressUpdatePayloadQueryDefinition, mutationQuery);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Mutation) obj).getCheckoutShippingAddressUpdate();
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$updateShippingAddress$4((Storefront.CheckoutShippingAddressUpdatePayload) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.CheckoutShippingAddressUpdatePayload) obj).getCheckout();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$updateShippingAddress$3(Storefront.MailingAddressInput mailingAddressInput, String str, final Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutShippingAddressUpdate(mailingAddressInput, new ID(str), new Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda12
            @Override // com.shopify.buy3.Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition
            public final void define(Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
                CheckoutRepository.lambda$updateShippingAddress$2(checkoutShippingAddressUpdatePayloadQueryDefinition, checkoutShippingAddressUpdatePayloadQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$updateShippingAddress$1(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    public static /* synthetic */ void lambda$updateShippingAddress$2(Storefront.CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition, Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
        checkoutShippingAddressUpdatePayloadQueryDefinition.define(checkoutShippingAddressUpdatePayloadQuery.userErrors(new Storefront.UserErrorQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda25
            @Override // com.shopify.buy3.Storefront.UserErrorQueryDefinition
            public final void define(Storefront.UserErrorQuery userErrorQuery) {
                CheckoutRepository.lambda$updateShippingAddress$1(userErrorQuery);
            }
        }));
    }

    public static /* synthetic */ SingleSource lambda$updateShippingAddress$4(Storefront.CheckoutShippingAddressUpdatePayload checkoutShippingAddressUpdatePayload) throws Exception {
        if (checkoutShippingAddressUpdatePayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutShippingAddressUpdatePayload);
        }
        return Single.error(new UserError((List<String>) Util.mapItems(checkoutShippingAddressUpdatePayload.getUserErrors(), new CheckoutRepository$$ExternalSyntheticLambda4())));
    }

    public Single<Storefront.Checkout> checkout(@NonNull final String str, @NonNull final Storefront.NodeQueryDefinition nodeQueryDefinition) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotNull(nodeQueryDefinition, "query == null");
        return RxUtil.rxGraphQueryCall(this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda7
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                CheckoutRepository.lambda$checkout$5(str, nodeQueryDefinition, queryRootQuery);
            }
        })).cachePolicy(HttpCachePolicy.Default.NETWORK_FIRST.expireAfter(5L, TimeUnit.MINUTES))).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$checkout$6((Storefront.QueryRoot) obj);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$checkout$5(String str, Storefront.NodeQueryDefinition nodeQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(str), nodeQueryDefinition);
    }

    public static /* synthetic */ Storefront.Checkout lambda$checkout$6(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Checkout) queryRoot.getNode();
    }

    public Single<Storefront.AvailableShippingRates> shippingRates(@NonNull final String str, @NonNull final Storefront.CheckoutQueryDefinition checkoutQueryDefinition) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotNull(checkoutQueryDefinition, "query == null");
        QueryGraphCall queryGraphCallCachePolicy = this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda30
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                CheckoutRepository.lambda$shippingRates$8(str, checkoutQueryDefinition, queryRootQuery);
            }
        })).cachePolicy(HttpCachePolicy.Default.NETWORK_ONLY);
        Objects.requireNonNull(queryGraphCallCachePolicy);
        return Single.fromCallable(new CheckoutRepository$$ExternalSyntheticLambda27(queryGraphCallCachePolicy)).flatMap(new CheckoutRepository$$ExternalSyntheticLambda28()).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda31
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$shippingRates$9((Storefront.QueryRoot) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda32
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Checkout) obj).getAvailableShippingRates();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$shippingRates$8(String str, final Storefront.CheckoutQueryDefinition checkoutQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(str), new Storefront.NodeQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda9
            @Override // com.shopify.buy3.Storefront.NodeQueryDefinition
            public final void define(Storefront.NodeQuery nodeQuery) {
                nodeQuery.onCheckout(checkoutQueryDefinition);
            }
        });
    }

    public static /* synthetic */ Storefront.Checkout lambda$shippingRates$9(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Checkout) queryRoot.getNode();
    }

    public Single<Storefront.Checkout> updateShippingLine(@NonNull final String str, @NonNull final String str2, @NonNull final Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
        Util.checkNotNull(checkoutShippingLineUpdatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(new Storefront.MutationQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda34
            @Override // com.shopify.buy3.Storefront.MutationQueryDefinition
            public final void define(Storefront.MutationQuery mutationQuery) {
                CheckoutRepository.lambda$updateShippingLine$12(str, str2, checkoutShippingLineUpdatePayloadQueryDefinition, mutationQuery);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda35
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Mutation) obj).getCheckoutShippingLineUpdate();
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda36
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$updateShippingLine$13((Storefront.CheckoutShippingLineUpdatePayload) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda37
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.CheckoutShippingLineUpdatePayload) obj).getCheckout();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$updateShippingLine$12(String str, String str2, final Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutShippingLineUpdate(new ID(str), str2, new Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda5
            @Override // com.shopify.buy3.Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition
            public final void define(Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
                CheckoutRepository.lambda$updateShippingLine$11(checkoutShippingLineUpdatePayloadQueryDefinition, checkoutShippingLineUpdatePayloadQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$updateShippingLine$10(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    public static /* synthetic */ void lambda$updateShippingLine$11(Storefront.CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition, Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
        checkoutShippingLineUpdatePayloadQueryDefinition.define(checkoutShippingLineUpdatePayloadQuery.userErrors(new Storefront.UserErrorQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda33
            @Override // com.shopify.buy3.Storefront.UserErrorQueryDefinition
            public final void define(Storefront.UserErrorQuery userErrorQuery) {
                CheckoutRepository.lambda$updateShippingLine$10(userErrorQuery);
            }
        }));
    }

    public static /* synthetic */ SingleSource lambda$updateShippingLine$13(Storefront.CheckoutShippingLineUpdatePayload checkoutShippingLineUpdatePayload) throws Exception {
        if (checkoutShippingLineUpdatePayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutShippingLineUpdatePayload);
        }
        return Single.error(new UserError((List<String>) Util.mapItems(checkoutShippingLineUpdatePayload.getUserErrors(), new CheckoutRepository$$ExternalSyntheticLambda4())));
    }

    public Single<Storefront.Checkout> updateEmail(@NonNull final String str, @NonNull final String str2, @NonNull final Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotBlank(str2, "email can't be empty");
        Util.checkNotNull(checkoutEmailUpdatePayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(new Storefront.MutationQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda18
            @Override // com.shopify.buy3.Storefront.MutationQueryDefinition
            public final void define(Storefront.MutationQuery mutationQuery) {
                CheckoutRepository.lambda$updateEmail$16(str, str2, checkoutEmailUpdatePayloadQueryDefinition, mutationQuery);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Mutation) obj).getCheckoutEmailUpdate();
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$updateEmail$17((Storefront.CheckoutEmailUpdatePayload) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.CheckoutEmailUpdatePayload) obj).getCheckout();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$updateEmail$16(String str, String str2, final Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutEmailUpdate(new ID(str), str2, new Storefront.CheckoutEmailUpdatePayloadQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda17
            @Override // com.shopify.buy3.Storefront.CheckoutEmailUpdatePayloadQueryDefinition
            public final void define(Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
                CheckoutRepository.lambda$updateEmail$15(checkoutEmailUpdatePayloadQueryDefinition, checkoutEmailUpdatePayloadQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$updateEmail$14(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    public static /* synthetic */ void lambda$updateEmail$15(Storefront.CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition, Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
        checkoutEmailUpdatePayloadQueryDefinition.define(checkoutEmailUpdatePayloadQuery.userErrors(new Storefront.UserErrorQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda10
            @Override // com.shopify.buy3.Storefront.UserErrorQueryDefinition
            public final void define(Storefront.UserErrorQuery userErrorQuery) {
                CheckoutRepository.lambda$updateEmail$14(userErrorQuery);
            }
        }));
    }

    public static /* synthetic */ SingleSource lambda$updateEmail$17(Storefront.CheckoutEmailUpdatePayload checkoutEmailUpdatePayload) throws Exception {
        if (checkoutEmailUpdatePayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutEmailUpdatePayload);
        }
        return Single.error(new UserError((List<String>) Util.mapItems(checkoutEmailUpdatePayload.getUserErrors(), new CheckoutRepository$$ExternalSyntheticLambda4())));
    }

    public Single<Storefront.Payment> complete(@NonNull final String str, @NonNull final Storefront.TokenizedPaymentInput tokenizedPaymentInput, @NonNull final Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
        Util.checkNotBlank(str, "checkoutId can't be empty");
        Util.checkNotNull(tokenizedPaymentInput, "paymentInput == null");
        Util.checkNotNull(checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, "query == null");
        return RxUtil.rxGraphMutationCall(this.graphClient.mutateGraph(Storefront.mutation(new Storefront.MutationQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda0
            @Override // com.shopify.buy3.Storefront.MutationQueryDefinition
            public final void define(Storefront.MutationQuery mutationQuery) {
                CheckoutRepository.lambda$complete$20(str, tokenizedPaymentInput, checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, mutationQuery);
            }
        }))).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.Mutation) obj).getCheckoutCompleteWithTokenizedPayment();
            }
        }).flatMap(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$complete$21((Storefront.CheckoutCompleteWithTokenizedPaymentPayload) obj);
            }
        }).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((Storefront.CheckoutCompleteWithTokenizedPaymentPayload) obj).getPayment();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$complete$20(String str, Storefront.TokenizedPaymentInput tokenizedPaymentInput, final Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, Storefront.MutationQuery mutationQuery) {
        mutationQuery.checkoutCompleteWithTokenizedPayment(new ID(str), tokenizedPaymentInput, new Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda11
            @Override // com.shopify.buy3.Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition
            public final void define(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
                CheckoutRepository.lambda$complete$19(checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, checkoutCompleteWithTokenizedPaymentPayloadQuery);
            }
        });
    }

    public static /* synthetic */ void lambda$complete$18(Storefront.UserErrorQuery userErrorQuery) {
        userErrorQuery.field().message();
    }

    public static /* synthetic */ void lambda$complete$19(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition, Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
        checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition.define(checkoutCompleteWithTokenizedPaymentPayloadQuery.userErrors(new Storefront.UserErrorQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda6
            @Override // com.shopify.buy3.Storefront.UserErrorQueryDefinition
            public final void define(Storefront.UserErrorQuery userErrorQuery) {
                CheckoutRepository.lambda$complete$18(userErrorQuery);
            }
        }));
    }

    public static /* synthetic */ SingleSource lambda$complete$21(Storefront.CheckoutCompleteWithTokenizedPaymentPayload checkoutCompleteWithTokenizedPaymentPayload) throws Exception {
        if (checkoutCompleteWithTokenizedPaymentPayload.getUserErrors().isEmpty()) {
            return Single.just(checkoutCompleteWithTokenizedPaymentPayload);
        }
        return Single.error(new UserError((List<String>) Util.mapItems(checkoutCompleteWithTokenizedPaymentPayload.getUserErrors(), new CheckoutRepository$$ExternalSyntheticLambda4())));
    }

    public Single<Storefront.Payment> paymentById(@NonNull final String str, @NonNull final Storefront.NodeQueryDefinition nodeQueryDefinition) {
        Util.checkNotBlank(str, "paymentId can't be empty");
        Util.checkNotNull(nodeQueryDefinition, "query == null");
        QueryGraphCall queryGraphCallCachePolicy = this.graphClient.queryGraph(Storefront.query(new Storefront.QueryRootQueryDefinition() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda26
            @Override // com.shopify.buy3.Storefront.QueryRootQueryDefinition
            public final void define(Storefront.QueryRootQuery queryRootQuery) {
                CheckoutRepository.lambda$paymentById$22(str, nodeQueryDefinition, queryRootQuery);
            }
        })).cachePolicy(HttpCachePolicy.Default.NETWORK_ONLY);
        Objects.requireNonNull(queryGraphCallCachePolicy);
        return Single.fromCallable(new CheckoutRepository$$ExternalSyntheticLambda27(queryGraphCallCachePolicy)).flatMap(new CheckoutRepository$$ExternalSyntheticLambda28()).map(new Function() { // from class: com.shopify.sample.domain.repository.CheckoutRepository$$ExternalSyntheticLambda29
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return CheckoutRepository.lambda$paymentById$23((Storefront.QueryRoot) obj);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static /* synthetic */ void lambda$paymentById$22(String str, Storefront.NodeQueryDefinition nodeQueryDefinition, Storefront.QueryRootQuery queryRootQuery) {
        queryRootQuery.node(new ID(str), nodeQueryDefinition);
    }

    public static /* synthetic */ Storefront.Payment lambda$paymentById$23(Storefront.QueryRoot queryRoot) throws Exception {
        return (Storefront.Payment) queryRoot.getNode();
    }
}
