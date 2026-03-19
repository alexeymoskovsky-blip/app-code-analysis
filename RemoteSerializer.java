package com.google.firebase.firestore.remote;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.CompositeFilter;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.local.TargetData;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.model.mutation.VerifyMutation;
import com.google.firebase.firestore.remote.WatchChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.v1.ArrayValue;
import com.google.firestore.v1.BatchGetDocumentsResponse;
import com.google.firestore.v1.Cursor;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.DocumentChange;
import com.google.firestore.v1.DocumentDelete;
import com.google.firestore.v1.DocumentMask;
import com.google.firestore.v1.DocumentRemove;
import com.google.firestore.v1.DocumentTransform;
import com.google.firestore.v1.ListenResponse;
import com.google.firestore.v1.Precondition;
import com.google.firestore.v1.StructuredQuery;
import com.google.firestore.v1.Target;
import com.google.firestore.v1.TargetChange;
import com.google.firestore.v1.Write;
import com.google.firestore.v1.WriteResult;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class RemoteSerializer {
    private final DatabaseId databaseId;
    private final String databaseName;

    public RemoteSerializer(DatabaseId databaseId) {
        this.databaseId = databaseId;
        this.databaseName = encodedDatabaseId(databaseId).canonicalString();
    }

    public Timestamp encodeTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp.Builder builderNewBuilder = Timestamp.newBuilder();
        builderNewBuilder.setSeconds(timestamp.getSeconds());
        builderNewBuilder.setNanos(timestamp.getNanoseconds());
        return builderNewBuilder.build();
    }

    public com.google.firebase.Timestamp decodeTimestamp(Timestamp timestamp) {
        return new com.google.firebase.Timestamp(timestamp.getSeconds(), timestamp.getNanos());
    }

    public Timestamp encodeVersion(SnapshotVersion snapshotVersion) {
        return encodeTimestamp(snapshotVersion.getTimestamp());
    }

    public SnapshotVersion decodeVersion(Timestamp timestamp) {
        if (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0) {
            return SnapshotVersion.NONE;
        }
        return new SnapshotVersion(decodeTimestamp(timestamp));
    }

    public String encodeKey(DocumentKey documentKey) {
        return encodeResourceName(this.databaseId, documentKey.getPath());
    }

    public DocumentKey decodeKey(String str) {
        ResourcePath resourcePathDecodeResourceName = decodeResourceName(str);
        Assert.hardAssert(resourcePathDecodeResourceName.getSegment(1).equals(this.databaseId.getProjectId()), "Tried to deserialize key from different project.", new Object[0]);
        Assert.hardAssert(resourcePathDecodeResourceName.getSegment(3).equals(this.databaseId.getDatabaseId()), "Tried to deserialize key from different database.", new Object[0]);
        return DocumentKey.fromPath(extractLocalPathFromResourceName(resourcePathDecodeResourceName));
    }

    private String encodeQueryPath(ResourcePath resourcePath) {
        return encodeResourceName(this.databaseId, resourcePath);
    }

    private ResourcePath decodeQueryPath(String str) {
        ResourcePath resourcePathDecodeResourceName = decodeResourceName(str);
        if (resourcePathDecodeResourceName.length() == 4) {
            return ResourcePath.EMPTY;
        }
        return extractLocalPathFromResourceName(resourcePathDecodeResourceName);
    }

    private String encodeResourceName(DatabaseId databaseId, ResourcePath resourcePath) {
        return encodedDatabaseId(databaseId).append("documents").append(resourcePath).canonicalString();
    }

    private ResourcePath decodeResourceName(String str) {
        ResourcePath resourcePathFromString = ResourcePath.fromString(str);
        Assert.hardAssert(isValidResourceName(resourcePathFromString), "Tried to deserialize invalid key %s", resourcePathFromString);
        return resourcePathFromString;
    }

    private static ResourcePath encodedDatabaseId(DatabaseId databaseId) {
        return ResourcePath.fromSegments(Arrays.asList("projects", databaseId.getProjectId(), "databases", databaseId.getDatabaseId()));
    }

    private static ResourcePath extractLocalPathFromResourceName(ResourcePath resourcePath) {
        Assert.hardAssert(resourcePath.length() > 4 && resourcePath.getSegment(4).equals("documents"), "Tried to deserialize invalid key %s", resourcePath);
        return resourcePath.popFirst(5);
    }

    private static boolean isValidResourceName(ResourcePath resourcePath) {
        return resourcePath.length() >= 4 && resourcePath.getSegment(0).equals("projects") && resourcePath.getSegment(2).equals("databases");
    }

    public boolean isLocalResourceName(ResourcePath resourcePath) {
        return isValidResourceName(resourcePath) && resourcePath.getSegment(1).equals(this.databaseId.getProjectId()) && resourcePath.getSegment(3).equals(this.databaseId.getDatabaseId());
    }

    public String databaseName() {
        return this.databaseName;
    }

    public Document encodeDocument(DocumentKey documentKey, ObjectValue objectValue) {
        Document.Builder builderNewBuilder = Document.newBuilder();
        builderNewBuilder.setName(encodeKey(documentKey));
        builderNewBuilder.putAllFields(objectValue.getFieldsMap());
        return builderNewBuilder.build();
    }

    public MutableDocument decodeMaybeDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        if (batchGetDocumentsResponse.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.FOUND)) {
            return decodeFoundDocument(batchGetDocumentsResponse);
        }
        if (batchGetDocumentsResponse.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.MISSING)) {
            return decodeMissingDocument(batchGetDocumentsResponse);
        }
        throw new IllegalArgumentException("Unknown result case: " + batchGetDocumentsResponse.getResultCase());
    }

    private MutableDocument decodeFoundDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        Assert.hardAssert(batchGetDocumentsResponse.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.FOUND), "Tried to deserialize a found document from a missing document.", new Object[0]);
        DocumentKey documentKeyDecodeKey = decodeKey(batchGetDocumentsResponse.getFound().getName());
        ObjectValue objectValueFromMap = ObjectValue.fromMap(batchGetDocumentsResponse.getFound().getFieldsMap());
        SnapshotVersion snapshotVersionDecodeVersion = decodeVersion(batchGetDocumentsResponse.getFound().getUpdateTime());
        Assert.hardAssert(!snapshotVersionDecodeVersion.equals(SnapshotVersion.NONE), "Got a document response with no snapshot version", new Object[0]);
        return MutableDocument.newFoundDocument(documentKeyDecodeKey, snapshotVersionDecodeVersion, objectValueFromMap);
    }

    private MutableDocument decodeMissingDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        Assert.hardAssert(batchGetDocumentsResponse.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.MISSING), "Tried to deserialize a missing document from a found document.", new Object[0]);
        DocumentKey documentKeyDecodeKey = decodeKey(batchGetDocumentsResponse.getMissing());
        SnapshotVersion snapshotVersionDecodeVersion = decodeVersion(batchGetDocumentsResponse.getReadTime());
        Assert.hardAssert(!snapshotVersionDecodeVersion.equals(SnapshotVersion.NONE), "Got a no document response with no snapshot version", new Object[0]);
        return MutableDocument.newNoDocument(documentKeyDecodeKey, snapshotVersionDecodeVersion);
    }

    public Write encodeMutation(Mutation mutation) {
        Write.Builder builderNewBuilder = Write.newBuilder();
        if (mutation instanceof SetMutation) {
            builderNewBuilder.setUpdate(encodeDocument(mutation.getKey(), ((SetMutation) mutation).getValue()));
        } else if (mutation instanceof PatchMutation) {
            builderNewBuilder.setUpdate(encodeDocument(mutation.getKey(), ((PatchMutation) mutation).getValue()));
            builderNewBuilder.setUpdateMask(encodeDocumentMask(mutation.getFieldMask()));
        } else if (mutation instanceof DeleteMutation) {
            builderNewBuilder.setDelete(encodeKey(mutation.getKey()));
        } else {
            if (!(mutation instanceof VerifyMutation)) {
                throw Assert.fail("unknown mutation type %s", mutation.getClass());
            }
            builderNewBuilder.setVerify(encodeKey(mutation.getKey()));
        }
        Iterator<FieldTransform> it = mutation.getFieldTransforms().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addUpdateTransforms(encodeFieldTransform(it.next()));
        }
        if (!mutation.getPrecondition().isNone()) {
            builderNewBuilder.setCurrentDocument(encodePrecondition(mutation.getPrecondition()));
        }
        return builderNewBuilder.build();
    }

    public Mutation decodeMutation(Write write) {
        Precondition preconditionDecodePrecondition;
        if (write.hasCurrentDocument()) {
            preconditionDecodePrecondition = decodePrecondition(write.getCurrentDocument());
        } else {
            preconditionDecodePrecondition = Precondition.NONE;
        }
        Precondition precondition = preconditionDecodePrecondition;
        ArrayList arrayList = new ArrayList();
        Iterator<DocumentTransform.FieldTransform> it = write.getUpdateTransformsList().iterator();
        while (it.hasNext()) {
            arrayList.add(decodeFieldTransform(it.next()));
        }
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$Write$OperationCase[write.getOperationCase().ordinal()];
        if (i == 1) {
            if (write.hasUpdateMask()) {
                return new PatchMutation(decodeKey(write.getUpdate().getName()), ObjectValue.fromMap(write.getUpdate().getFieldsMap()), decodeDocumentMask(write.getUpdateMask()), precondition, arrayList);
            }
            return new SetMutation(decodeKey(write.getUpdate().getName()), ObjectValue.fromMap(write.getUpdate().getFieldsMap()), precondition, arrayList);
        }
        if (i == 2) {
            return new DeleteMutation(decodeKey(write.getDelete()), precondition);
        }
        if (i == 3) {
            return new VerifyMutation(decodeKey(write.getVerify()), precondition);
        }
        throw Assert.fail("Unknown mutation operation: %d", write.getOperationCase());
    }

    private com.google.firestore.v1.Precondition encodePrecondition(Precondition precondition) {
        Assert.hardAssert(!precondition.isNone(), "Can't serialize an empty precondition", new Object[0]);
        Precondition.Builder builderNewBuilder = com.google.firestore.v1.Precondition.newBuilder();
        if (precondition.getUpdateTime() != null) {
            return builderNewBuilder.setUpdateTime(encodeVersion(precondition.getUpdateTime())).build();
        }
        if (precondition.getExists() != null) {
            return builderNewBuilder.setExists(precondition.getExists().booleanValue()).build();
        }
        throw Assert.fail("Unknown Precondition", new Object[0]);
    }

    private com.google.firebase.firestore.model.mutation.Precondition decodePrecondition(com.google.firestore.v1.Precondition precondition) {
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[precondition.getConditionTypeCase().ordinal()];
        if (i == 1) {
            return com.google.firebase.firestore.model.mutation.Precondition.updateTime(decodeVersion(precondition.getUpdateTime()));
        }
        if (i == 2) {
            return com.google.firebase.firestore.model.mutation.Precondition.exists(precondition.getExists());
        }
        if (i == 3) {
            return com.google.firebase.firestore.model.mutation.Precondition.NONE;
        }
        throw Assert.fail("Unknown precondition", new Object[0]);
    }

    private DocumentMask encodeDocumentMask(FieldMask fieldMask) {
        DocumentMask.Builder builderNewBuilder = DocumentMask.newBuilder();
        Iterator<FieldPath> it = fieldMask.getMask().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addFieldPaths(it.next().canonicalString());
        }
        return builderNewBuilder.build();
    }

    private FieldMask decodeDocumentMask(DocumentMask documentMask) {
        int fieldPathsCount = documentMask.getFieldPathsCount();
        HashSet hashSet = new HashSet(fieldPathsCount);
        for (int i = 0; i < fieldPathsCount; i++) {
            hashSet.add(FieldPath.fromServerFormat(documentMask.getFieldPaths(i)));
        }
        return FieldMask.fromSet(hashSet);
    }

    private DocumentTransform.FieldTransform encodeFieldTransform(FieldTransform fieldTransform) {
        TransformOperation operation = fieldTransform.getOperation();
        if (operation instanceof ServerTimestampOperation) {
            return DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setSetToServerValue(DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME).build();
        }
        if (operation instanceof ArrayTransformOperation.Union) {
            return DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setAppendMissingElements(ArrayValue.newBuilder().addAllValues(((ArrayTransformOperation.Union) operation).getElements())).build();
        }
        if (operation instanceof ArrayTransformOperation.Remove) {
            return DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setRemoveAllFromArray(ArrayValue.newBuilder().addAllValues(((ArrayTransformOperation.Remove) operation).getElements())).build();
        }
        if (operation instanceof NumericIncrementTransformOperation) {
            return DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setIncrement(((NumericIncrementTransformOperation) operation).getOperand()).build();
        }
        throw Assert.fail("Unknown transform: %s", operation);
    }

    private FieldTransform decodeFieldTransform(DocumentTransform.FieldTransform fieldTransform) {
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[fieldTransform.getTransformTypeCase().ordinal()];
        if (i == 1) {
            Assert.hardAssert(fieldTransform.getSetToServerValue() == DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME, "Unknown transform setToServerValue: %s", fieldTransform.getSetToServerValue());
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), ServerTimestampOperation.getInstance());
        }
        if (i == 2) {
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new ArrayTransformOperation.Union(fieldTransform.getAppendMissingElements().getValuesList()));
        }
        if (i == 3) {
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new ArrayTransformOperation.Remove(fieldTransform.getRemoveAllFromArray().getValuesList()));
        }
        if (i == 4) {
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new NumericIncrementTransformOperation(fieldTransform.getIncrement()));
        }
        throw Assert.fail("Unknown FieldTransform proto: %s", fieldTransform);
    }

    public MutationResult decodeMutationResult(WriteResult writeResult, SnapshotVersion snapshotVersion) {
        SnapshotVersion snapshotVersionDecodeVersion = decodeVersion(writeResult.getUpdateTime());
        if (!SnapshotVersion.NONE.equals(snapshotVersionDecodeVersion)) {
            snapshotVersion = snapshotVersionDecodeVersion;
        }
        int transformResultsCount = writeResult.getTransformResultsCount();
        ArrayList arrayList = new ArrayList(transformResultsCount);
        for (int i = 0; i < transformResultsCount; i++) {
            arrayList.add(writeResult.getTransformResults(i));
        }
        return new MutationResult(snapshotVersion, arrayList);
    }

    @Nullable
    public Map<String, String> encodeListenRequestLabels(TargetData targetData) {
        String strEncodeLabel = encodeLabel(targetData.getPurpose());
        if (strEncodeLabel == null) {
            return null;
        }
        HashMap map = new HashMap(1);
        map.put("goog-listen-tags", strEncodeLabel);
        return map;
    }

    @Nullable
    private String encodeLabel(QueryPurpose queryPurpose) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$local$QueryPurpose[queryPurpose.ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            return "existence-filter-mismatch";
        }
        if (i == 3) {
            return "limbo-document";
        }
        throw Assert.fail("Unrecognized query purpose: %s", queryPurpose);
    }

    public Target encodeTarget(TargetData targetData) {
        Target.Builder builderNewBuilder = Target.newBuilder();
        com.google.firebase.firestore.core.Target target = targetData.getTarget();
        if (target.isDocumentQuery()) {
            builderNewBuilder.setDocuments(encodeDocumentsTarget(target));
        } else {
            builderNewBuilder.setQuery(encodeQueryTarget(target));
        }
        builderNewBuilder.setTargetId(targetData.getTargetId());
        if (targetData.getResumeToken().isEmpty() && targetData.getSnapshotVersion().compareTo(SnapshotVersion.NONE) > 0) {
            builderNewBuilder.setReadTime(encodeTimestamp(targetData.getSnapshotVersion().getTimestamp()));
        } else {
            builderNewBuilder.setResumeToken(targetData.getResumeToken());
        }
        return builderNewBuilder.build();
    }

    public Target.DocumentsTarget encodeDocumentsTarget(com.google.firebase.firestore.core.Target target) {
        Target.DocumentsTarget.Builder builderNewBuilder = Target.DocumentsTarget.newBuilder();
        builderNewBuilder.addDocuments(encodeQueryPath(target.getPath()));
        return builderNewBuilder.build();
    }

    public com.google.firebase.firestore.core.Target decodeDocumentsTarget(Target.DocumentsTarget documentsTarget) {
        int documentsCount = documentsTarget.getDocumentsCount();
        Assert.hardAssert(documentsCount == 1, "DocumentsTarget contained other than 1 document %d", Integer.valueOf(documentsCount));
        return Query.atPath(decodeQueryPath(documentsTarget.getDocuments(0))).toTarget();
    }

    public Target.QueryTarget encodeQueryTarget(com.google.firebase.firestore.core.Target target) {
        Target.QueryTarget.Builder builderNewBuilder = Target.QueryTarget.newBuilder();
        StructuredQuery.Builder builderNewBuilder2 = StructuredQuery.newBuilder();
        ResourcePath path = target.getPath();
        if (target.getCollectionGroup() != null) {
            Assert.hardAssert(path.length() % 2 == 0, "Collection Group queries should be within a document path or root.", new Object[0]);
            builderNewBuilder.setParent(encodeQueryPath(path));
            StructuredQuery.CollectionSelector.Builder builderNewBuilder3 = StructuredQuery.CollectionSelector.newBuilder();
            builderNewBuilder3.setCollectionId(target.getCollectionGroup());
            builderNewBuilder3.setAllDescendants(true);
            builderNewBuilder2.addFrom(builderNewBuilder3);
        } else {
            Assert.hardAssert(path.length() % 2 != 0, "Document queries with filters are not supported.", new Object[0]);
            builderNewBuilder.setParent(encodeQueryPath(path.popLast()));
            StructuredQuery.CollectionSelector.Builder builderNewBuilder4 = StructuredQuery.CollectionSelector.newBuilder();
            builderNewBuilder4.setCollectionId(path.getLastSegment());
            builderNewBuilder2.addFrom(builderNewBuilder4);
        }
        if (target.getFilters().size() > 0) {
            builderNewBuilder2.setWhere(encodeFilters(target.getFilters()));
        }
        Iterator<OrderBy> it = target.getOrderBy().iterator();
        while (it.hasNext()) {
            builderNewBuilder2.addOrderBy(encodeOrderBy(it.next()));
        }
        if (target.hasLimit()) {
            builderNewBuilder2.setLimit(Int32Value.newBuilder().setValue((int) target.getLimit()));
        }
        if (target.getStartAt() != null) {
            Cursor.Builder builderNewBuilder5 = Cursor.newBuilder();
            builderNewBuilder5.addAllValues(target.getStartAt().getPosition());
            builderNewBuilder5.setBefore(target.getStartAt().isInclusive());
            builderNewBuilder2.setStartAt(builderNewBuilder5);
        }
        if (target.getEndAt() != null) {
            Cursor.Builder builderNewBuilder6 = Cursor.newBuilder();
            builderNewBuilder6.addAllValues(target.getEndAt().getPosition());
            builderNewBuilder6.setBefore(!target.getEndAt().isInclusive());
            builderNewBuilder2.setEndAt(builderNewBuilder6);
        }
        builderNewBuilder.setStructuredQuery(builderNewBuilder2);
        return builderNewBuilder.build();
    }

    public com.google.firebase.firestore.core.Target decodeQueryTarget(String str, StructuredQuery structuredQuery) {
        ResourcePath resourcePath;
        String collectionId;
        List<Filter> listEmptyList;
        List listEmptyList2;
        ResourcePath resourcePathDecodeQueryPath = decodeQueryPath(str);
        int fromCount = structuredQuery.getFromCount();
        if (fromCount <= 0) {
            resourcePath = resourcePathDecodeQueryPath;
            collectionId = null;
        } else {
            Assert.hardAssert(fromCount == 1, "StructuredQuery.from with more than one collection is not supported.", new Object[0]);
            StructuredQuery.CollectionSelector from = structuredQuery.getFrom(0);
            if (from.getAllDescendants()) {
                resourcePath = resourcePathDecodeQueryPath;
                collectionId = from.getCollectionId();
            } else {
                resourcePathDecodeQueryPath = resourcePathDecodeQueryPath.append(from.getCollectionId());
                resourcePath = resourcePathDecodeQueryPath;
                collectionId = null;
            }
        }
        if (structuredQuery.hasWhere()) {
            listEmptyList = decodeFilters(structuredQuery.getWhere());
        } else {
            listEmptyList = Collections.emptyList();
        }
        List<Filter> list = listEmptyList;
        int orderByCount = structuredQuery.getOrderByCount();
        if (orderByCount > 0) {
            ArrayList arrayList = new ArrayList(orderByCount);
            for (int i = 0; i < orderByCount; i++) {
                arrayList.add(decodeOrderBy(structuredQuery.getOrderBy(i)));
            }
            listEmptyList2 = arrayList;
        } else {
            listEmptyList2 = Collections.emptyList();
        }
        return new com.google.firebase.firestore.core.Target(resourcePath, collectionId, list, listEmptyList2, structuredQuery.hasLimit() ? structuredQuery.getLimit().getValue() : -1L, structuredQuery.hasStartAt() ? new Bound(structuredQuery.getStartAt().getValuesList(), structuredQuery.getStartAt().getBefore()) : null, structuredQuery.hasEndAt() ? new Bound(structuredQuery.getEndAt().getValuesList(), !structuredQuery.getEndAt().getBefore()) : null);
    }

    public com.google.firebase.firestore.core.Target decodeQueryTarget(Target.QueryTarget queryTarget) {
        return decodeQueryTarget(queryTarget.getParent(), queryTarget.getStructuredQuery());
    }

    private StructuredQuery.Filter encodeFilters(List<Filter> list) {
        return encodeFilter(new CompositeFilter(list, CompositeFilter.Operator.AND));
    }

    private List<Filter> decodeFilters(StructuredQuery.Filter filter) {
        Filter filterDecodeFilter = decodeFilter(filter);
        if (filterDecodeFilter instanceof CompositeFilter) {
            CompositeFilter compositeFilter = (CompositeFilter) filterDecodeFilter;
            if (compositeFilter.isFlatConjunction()) {
                return compositeFilter.getFilters();
            }
        }
        return Collections.singletonList(filterDecodeFilter);
    }

    @VisibleForTesting
    public StructuredQuery.Filter encodeFilter(Filter filter) {
        if (filter instanceof FieldFilter) {
            return encodeUnaryOrFieldFilter((FieldFilter) filter);
        }
        if (filter instanceof CompositeFilter) {
            return encodeCompositeFilter((CompositeFilter) filter);
        }
        throw Assert.fail("Unrecognized filter type %s", filter.toString());
    }

    @VisibleForTesting
    public StructuredQuery.Filter encodeUnaryOrFieldFilter(FieldFilter fieldFilter) {
        StructuredQuery.UnaryFilter.Operator operator;
        StructuredQuery.UnaryFilter.Operator operator2;
        FieldFilter.Operator operator3 = fieldFilter.getOperator();
        FieldFilter.Operator operator4 = FieldFilter.Operator.EQUAL;
        if (operator3 == operator4 || fieldFilter.getOperator() == FieldFilter.Operator.NOT_EQUAL) {
            StructuredQuery.UnaryFilter.Builder builderNewBuilder = StructuredQuery.UnaryFilter.newBuilder();
            builderNewBuilder.setField(encodeFieldPath(fieldFilter.getField()));
            if (Values.isNanValue(fieldFilter.getValue())) {
                if (fieldFilter.getOperator() == operator4) {
                    operator2 = StructuredQuery.UnaryFilter.Operator.IS_NAN;
                } else {
                    operator2 = StructuredQuery.UnaryFilter.Operator.IS_NOT_NAN;
                }
                builderNewBuilder.setOp(operator2);
                return StructuredQuery.Filter.newBuilder().setUnaryFilter(builderNewBuilder).build();
            }
            if (Values.isNullValue(fieldFilter.getValue())) {
                if (fieldFilter.getOperator() == operator4) {
                    operator = StructuredQuery.UnaryFilter.Operator.IS_NULL;
                } else {
                    operator = StructuredQuery.UnaryFilter.Operator.IS_NOT_NULL;
                }
                builderNewBuilder.setOp(operator);
                return StructuredQuery.Filter.newBuilder().setUnaryFilter(builderNewBuilder).build();
            }
        }
        StructuredQuery.FieldFilter.Builder builderNewBuilder2 = StructuredQuery.FieldFilter.newBuilder();
        builderNewBuilder2.setField(encodeFieldPath(fieldFilter.getField()));
        builderNewBuilder2.setOp(encodeFieldFilterOperator(fieldFilter.getOperator()));
        builderNewBuilder2.setValue(fieldFilter.getValue());
        return StructuredQuery.Filter.newBuilder().setFieldFilter(builderNewBuilder2).build();
    }

    public StructuredQuery.CompositeFilter.Operator encodeCompositeFilterOperator(CompositeFilter.Operator operator) {
        int i = AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$CompositeFilter$Operator[operator.ordinal()];
        if (i == 1) {
            return StructuredQuery.CompositeFilter.Operator.AND;
        }
        if (i == 2) {
            return StructuredQuery.CompositeFilter.Operator.OR;
        }
        throw Assert.fail("Unrecognized composite filter type.", new Object[0]);
    }

    public CompositeFilter.Operator decodeCompositeFilterOperator(StructuredQuery.CompositeFilter.Operator operator) {
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$CompositeFilter$Operator[operator.ordinal()];
        if (i == 1) {
            return CompositeFilter.Operator.AND;
        }
        if (i == 2) {
            return CompositeFilter.Operator.OR;
        }
        throw Assert.fail("Only AND and OR composite filter types are supported.", new Object[0]);
    }

    @VisibleForTesting
    public StructuredQuery.Filter encodeCompositeFilter(CompositeFilter compositeFilter) {
        ArrayList arrayList = new ArrayList(compositeFilter.getFilters().size());
        Iterator<Filter> it = compositeFilter.getFilters().iterator();
        while (it.hasNext()) {
            arrayList.add(encodeFilter(it.next()));
        }
        if (arrayList.size() == 1) {
            return (StructuredQuery.Filter) arrayList.get(0);
        }
        StructuredQuery.CompositeFilter.Builder builderNewBuilder = StructuredQuery.CompositeFilter.newBuilder();
        builderNewBuilder.setOp(encodeCompositeFilterOperator(compositeFilter.getOperator()));
        builderNewBuilder.addAllFilters(arrayList);
        return StructuredQuery.Filter.newBuilder().setCompositeFilter(builderNewBuilder).build();
    }

    @VisibleForTesting
    public Filter decodeFilter(StructuredQuery.Filter filter) {
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[filter.getFilterTypeCase().ordinal()];
        if (i == 1) {
            return decodeCompositeFilter(filter.getCompositeFilter());
        }
        if (i == 2) {
            return decodeFieldFilter(filter.getFieldFilter());
        }
        if (i == 3) {
            return decodeUnaryFilter(filter.getUnaryFilter());
        }
        throw Assert.fail("Unrecognized Filter.filterType %d", filter.getFilterTypeCase());
    }

    @VisibleForTesting
    public FieldFilter decodeFieldFilter(StructuredQuery.FieldFilter fieldFilter) {
        return FieldFilter.create(FieldPath.fromServerFormat(fieldFilter.getField().getFieldPath()), decodeFieldFilterOperator(fieldFilter.getOp()), fieldFilter.getValue());
    }

    private Filter decodeUnaryFilter(StructuredQuery.UnaryFilter unaryFilter) {
        FieldPath fieldPathFromServerFormat = FieldPath.fromServerFormat(unaryFilter.getField().getFieldPath());
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[unaryFilter.getOp().ordinal()];
        if (i == 1) {
            return FieldFilter.create(fieldPathFromServerFormat, FieldFilter.Operator.EQUAL, Values.NAN_VALUE);
        }
        if (i == 2) {
            return FieldFilter.create(fieldPathFromServerFormat, FieldFilter.Operator.EQUAL, Values.NULL_VALUE);
        }
        if (i == 3) {
            return FieldFilter.create(fieldPathFromServerFormat, FieldFilter.Operator.NOT_EQUAL, Values.NAN_VALUE);
        }
        if (i == 4) {
            return FieldFilter.create(fieldPathFromServerFormat, FieldFilter.Operator.NOT_EQUAL, Values.NULL_VALUE);
        }
        throw Assert.fail("Unrecognized UnaryFilter.operator %d", unaryFilter.getOp());
    }

    @VisibleForTesting
    public CompositeFilter decodeCompositeFilter(StructuredQuery.CompositeFilter compositeFilter) {
        ArrayList arrayList = new ArrayList();
        Iterator<StructuredQuery.Filter> it = compositeFilter.getFiltersList().iterator();
        while (it.hasNext()) {
            arrayList.add(decodeFilter(it.next()));
        }
        return new CompositeFilter(arrayList, decodeCompositeFilterOperator(compositeFilter.getOp()));
    }

    private StructuredQuery.FieldReference encodeFieldPath(FieldPath fieldPath) {
        return StructuredQuery.FieldReference.newBuilder().setFieldPath(fieldPath.canonicalString()).build();
    }

    private StructuredQuery.FieldFilter.Operator encodeFieldFilterOperator(FieldFilter.Operator operator) {
        switch (AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[operator.ordinal()]) {
            case 1:
                return StructuredQuery.FieldFilter.Operator.LESS_THAN;
            case 2:
                return StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case 3:
                return StructuredQuery.FieldFilter.Operator.EQUAL;
            case 4:
                return StructuredQuery.FieldFilter.Operator.NOT_EQUAL;
            case 5:
                return StructuredQuery.FieldFilter.Operator.GREATER_THAN;
            case 6:
                return StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case 7:
                return StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS;
            case 8:
                return StructuredQuery.FieldFilter.Operator.IN;
            case 9:
                return StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS_ANY;
            case 10:
                return StructuredQuery.FieldFilter.Operator.NOT_IN;
            default:
                throw Assert.fail("Unknown operator %d", operator);
        }
    }

    private FieldFilter.Operator decodeFieldFilterOperator(StructuredQuery.FieldFilter.Operator operator) {
        switch (AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[operator.ordinal()]) {
            case 1:
                return FieldFilter.Operator.LESS_THAN;
            case 2:
                return FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case 3:
                return FieldFilter.Operator.EQUAL;
            case 4:
                return FieldFilter.Operator.NOT_EQUAL;
            case 5:
                return FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case 6:
                return FieldFilter.Operator.GREATER_THAN;
            case 7:
                return FieldFilter.Operator.ARRAY_CONTAINS;
            case 8:
                return FieldFilter.Operator.IN;
            case 9:
                return FieldFilter.Operator.ARRAY_CONTAINS_ANY;
            case 10:
                return FieldFilter.Operator.NOT_IN;
            default:
                throw Assert.fail("Unhandled FieldFilter.operator %d", operator);
        }
    }

    private StructuredQuery.Order encodeOrderBy(OrderBy orderBy) {
        StructuredQuery.Order.Builder builderNewBuilder = StructuredQuery.Order.newBuilder();
        if (orderBy.getDirection().equals(OrderBy.Direction.ASCENDING)) {
            builderNewBuilder.setDirection(StructuredQuery.Direction.ASCENDING);
        } else {
            builderNewBuilder.setDirection(StructuredQuery.Direction.DESCENDING);
        }
        builderNewBuilder.setField(encodeFieldPath(orderBy.getField()));
        return builderNewBuilder.build();
    }

    private OrderBy decodeOrderBy(StructuredQuery.Order order) {
        OrderBy.Direction direction;
        FieldPath fieldPathFromServerFormat = FieldPath.fromServerFormat(order.getField().getFieldPath());
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[order.getDirection().ordinal()];
        if (i == 1) {
            direction = OrderBy.Direction.ASCENDING;
        } else {
            if (i != 2) {
                throw Assert.fail("Unrecognized direction %d", order.getDirection());
            }
            direction = OrderBy.Direction.DESCENDING;
        }
        return OrderBy.getInstance(direction, fieldPathFromServerFormat);
    }

    /* JADX INFO: renamed from: com.google.firebase.firestore.remote.RemoteSerializer$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$CompositeFilter$Operator;
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator;
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$local$QueryPurpose;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$CompositeFilter$Operator;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType;
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Write$OperationCase;

        static {
            int[] iArr = new int[ListenResponse.ResponseTypeCase.values().length];
            $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase = iArr;
            try {
                iArr[ListenResponse.ResponseTypeCase.TARGET_CHANGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[ListenResponse.ResponseTypeCase.DOCUMENT_CHANGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[ListenResponse.ResponseTypeCase.DOCUMENT_DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[ListenResponse.ResponseTypeCase.DOCUMENT_REMOVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[ListenResponse.ResponseTypeCase.FILTER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[ListenResponse.ResponseTypeCase.RESPONSETYPE_NOT_SET.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[TargetChange.TargetChangeType.values().length];
            $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType = iArr2;
            try {
                iArr2[TargetChange.TargetChangeType.NO_CHANGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.ADD.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.REMOVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.CURRENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.RESET.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[TargetChange.TargetChangeType.UNRECOGNIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            int[] iArr3 = new int[StructuredQuery.Direction.values().length];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction = iArr3;
            try {
                iArr3[StructuredQuery.Direction.ASCENDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[StructuredQuery.Direction.DESCENDING.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr4 = new int[StructuredQuery.FieldFilter.Operator.values().length];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator = iArr4;
            try {
                iArr4[StructuredQuery.FieldFilter.Operator.LESS_THAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.NOT_EQUAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.GREATER_THAN.ordinal()] = 6;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS.ordinal()] = 7;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.IN.ordinal()] = 8;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS_ANY.ordinal()] = 9;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$FieldFilter$Operator[StructuredQuery.FieldFilter.Operator.NOT_IN.ordinal()] = 10;
            } catch (NoSuchFieldError unused24) {
            }
            int[] iArr5 = new int[FieldFilter.Operator.values().length];
            $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator = iArr5;
            try {
                iArr5[FieldFilter.Operator.LESS_THAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.NOT_EQUAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.GREATER_THAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.ARRAY_CONTAINS.ordinal()] = 7;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.IN.ordinal()] = 8;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.ARRAY_CONTAINS_ANY.ordinal()] = 9;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator[FieldFilter.Operator.NOT_IN.ordinal()] = 10;
            } catch (NoSuchFieldError unused34) {
            }
            int[] iArr6 = new int[StructuredQuery.UnaryFilter.Operator.values().length];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator = iArr6;
            try {
                iArr6[StructuredQuery.UnaryFilter.Operator.IS_NAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[StructuredQuery.UnaryFilter.Operator.IS_NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[StructuredQuery.UnaryFilter.Operator.IS_NOT_NAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator[StructuredQuery.UnaryFilter.Operator.IS_NOT_NULL.ordinal()] = 4;
            } catch (NoSuchFieldError unused38) {
            }
            int[] iArr7 = new int[StructuredQuery.Filter.FilterTypeCase.values().length];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase = iArr7;
            try {
                iArr7[StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase[StructuredQuery.Filter.FilterTypeCase.UNARY_FILTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused41) {
            }
            int[] iArr8 = new int[StructuredQuery.CompositeFilter.Operator.values().length];
            $SwitchMap$com$google$firestore$v1$StructuredQuery$CompositeFilter$Operator = iArr8;
            try {
                iArr8[StructuredQuery.CompositeFilter.Operator.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$StructuredQuery$CompositeFilter$Operator[StructuredQuery.CompositeFilter.Operator.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused43) {
            }
            int[] iArr9 = new int[CompositeFilter.Operator.values().length];
            $SwitchMap$com$google$firebase$firestore$core$CompositeFilter$Operator = iArr9;
            try {
                iArr9[CompositeFilter.Operator.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$CompositeFilter$Operator[CompositeFilter.Operator.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused45) {
            }
            int[] iArr10 = new int[QueryPurpose.values().length];
            $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = iArr10;
            try {
                iArr10[QueryPurpose.LISTEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[QueryPurpose.EXISTENCE_FILTER_MISMATCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$local$QueryPurpose[QueryPurpose.LIMBO_RESOLUTION.ordinal()] = 3;
            } catch (NoSuchFieldError unused48) {
            }
            int[] iArr11 = new int[DocumentTransform.FieldTransform.TransformTypeCase.values().length];
            $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase = iArr11;
            try {
                iArr11[DocumentTransform.FieldTransform.TransformTypeCase.SET_TO_SERVER_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[DocumentTransform.FieldTransform.TransformTypeCase.APPEND_MISSING_ELEMENTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[DocumentTransform.FieldTransform.TransformTypeCase.REMOVE_ALL_FROM_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase[DocumentTransform.FieldTransform.TransformTypeCase.INCREMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused52) {
            }
            int[] iArr12 = new int[Precondition.ConditionTypeCase.values().length];
            $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase = iArr12;
            try {
                iArr12[Precondition.ConditionTypeCase.UPDATE_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[Precondition.ConditionTypeCase.EXISTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase[Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET.ordinal()] = 3;
            } catch (NoSuchFieldError unused55) {
            }
            int[] iArr13 = new int[Write.OperationCase.values().length];
            $SwitchMap$com$google$firestore$v1$Write$OperationCase = iArr13;
            try {
                iArr13[Write.OperationCase.UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Write$OperationCase[Write.OperationCase.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Write$OperationCase[Write.OperationCase.VERIFY.ordinal()] = 3;
            } catch (NoSuchFieldError unused58) {
            }
        }
    }

    public WatchChange decodeWatchChange(ListenResponse listenResponse) {
        WatchChange.WatchTargetChangeType watchTargetChangeType;
        WatchChange watchTargetChange;
        int i = AnonymousClass1.$SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase[listenResponse.getResponseTypeCase().ordinal()];
        Status statusFromStatus = null;
        if (i == 1) {
            com.google.firestore.v1.TargetChange targetChange = listenResponse.getTargetChange();
            int i2 = AnonymousClass1.$SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[targetChange.getTargetChangeType().ordinal()];
            if (i2 == 1) {
                watchTargetChangeType = WatchChange.WatchTargetChangeType.NoChange;
            } else if (i2 == 2) {
                watchTargetChangeType = WatchChange.WatchTargetChangeType.Added;
            } else if (i2 == 3) {
                watchTargetChangeType = WatchChange.WatchTargetChangeType.Removed;
                statusFromStatus = fromStatus(targetChange.getCause());
            } else if (i2 == 4) {
                watchTargetChangeType = WatchChange.WatchTargetChangeType.Current;
            } else if (i2 == 5) {
                watchTargetChangeType = WatchChange.WatchTargetChangeType.Reset;
            } else {
                throw new IllegalArgumentException("Unknown target change type");
            }
            watchTargetChange = new WatchChange.WatchTargetChange(watchTargetChangeType, targetChange.getTargetIdsList(), targetChange.getResumeToken(), statusFromStatus);
        } else if (i == 2) {
            DocumentChange documentChange = listenResponse.getDocumentChange();
            List<Integer> targetIdsList = documentChange.getTargetIdsList();
            List<Integer> removedTargetIdsList = documentChange.getRemovedTargetIdsList();
            DocumentKey documentKeyDecodeKey = decodeKey(documentChange.getDocument().getName());
            SnapshotVersion snapshotVersionDecodeVersion = decodeVersion(documentChange.getDocument().getUpdateTime());
            Assert.hardAssert(!snapshotVersionDecodeVersion.equals(SnapshotVersion.NONE), "Got a document change without an update time", new Object[0]);
            MutableDocument mutableDocumentNewFoundDocument = MutableDocument.newFoundDocument(documentKeyDecodeKey, snapshotVersionDecodeVersion, ObjectValue.fromMap(documentChange.getDocument().getFieldsMap()));
            watchTargetChange = new WatchChange.DocumentChange(targetIdsList, removedTargetIdsList, mutableDocumentNewFoundDocument.getKey(), mutableDocumentNewFoundDocument);
        } else {
            if (i == 3) {
                DocumentDelete documentDelete = listenResponse.getDocumentDelete();
                List<Integer> removedTargetIdsList2 = documentDelete.getRemovedTargetIdsList();
                MutableDocument mutableDocumentNewNoDocument = MutableDocument.newNoDocument(decodeKey(documentDelete.getDocument()), decodeVersion(documentDelete.getReadTime()));
                return new WatchChange.DocumentChange(Collections.emptyList(), removedTargetIdsList2, mutableDocumentNewNoDocument.getKey(), mutableDocumentNewNoDocument);
            }
            if (i != 4) {
                if (i == 5) {
                    com.google.firestore.v1.ExistenceFilter filter = listenResponse.getFilter();
                    return new WatchChange.ExistenceFilterWatchChange(filter.getTargetId(), new ExistenceFilter(filter.getCount()));
                }
                throw new IllegalArgumentException("Unknown change type set");
            }
            DocumentRemove documentRemove = listenResponse.getDocumentRemove();
            watchTargetChange = new WatchChange.DocumentChange(Collections.emptyList(), documentRemove.getRemovedTargetIdsList(), decodeKey(documentRemove.getDocument()), null);
        }
        return watchTargetChange;
    }

    public SnapshotVersion decodeVersionFromListenResponse(ListenResponse listenResponse) {
        if (listenResponse.getResponseTypeCase() != ListenResponse.ResponseTypeCase.TARGET_CHANGE) {
            return SnapshotVersion.NONE;
        }
        if (listenResponse.getTargetChange().getTargetIdsCount() != 0) {
            return SnapshotVersion.NONE;
        }
        return decodeVersion(listenResponse.getTargetChange().getReadTime());
    }

    private Status fromStatus(com.google.rpc.Status status) {
        return Status.fromCodeValue(status.getCode()).withDescription(status.getMessage());
    }
}
