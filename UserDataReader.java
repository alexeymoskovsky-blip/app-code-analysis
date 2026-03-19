package com.google.firebase.firestore;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.core.UserData;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.CustomClassMapper;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firebase.firestore.util.Util;
import com.google.firestore.v1.ArrayValue;
import com.google.firestore.v1.MapValue;
import com.google.firestore.v1.Value;
import com.google.protobuf.NullValue;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class UserDataReader {
    private final DatabaseId databaseId;

    public UserDataReader(DatabaseId databaseId) {
        this.databaseId = databaseId;
    }

    public UserData.ParsedSetData parseSetData(Object obj) {
        UserData.ParseAccumulator parseAccumulator = new UserData.ParseAccumulator(UserData.Source.Set);
        return parseAccumulator.toSetData(convertAndParseDocumentData(obj, parseAccumulator.rootContext()));
    }

    public UserData.ParsedSetData parseMergeData(Object obj, @Nullable FieldMask fieldMask) {
        UserData.ParseAccumulator parseAccumulator = new UserData.ParseAccumulator(UserData.Source.MergeSet);
        ObjectValue objectValueConvertAndParseDocumentData = convertAndParseDocumentData(obj, parseAccumulator.rootContext());
        if (fieldMask != null) {
            for (com.google.firebase.firestore.model.FieldPath fieldPath : fieldMask.getMask()) {
                if (!parseAccumulator.contains(fieldPath)) {
                    throw new IllegalArgumentException("Field '" + fieldPath.toString() + "' is specified in your field mask but not in your input data.");
                }
            }
            return parseAccumulator.toMergeData(objectValueConvertAndParseDocumentData, fieldMask);
        }
        return parseAccumulator.toMergeData(objectValueConvertAndParseDocumentData);
    }

    public UserData.ParsedUpdateData parseUpdateData(Map<String, Object> map) {
        Preconditions.checkNotNull(map, "Provided update data must not be null.");
        UserData.ParseAccumulator parseAccumulator = new UserData.ParseAccumulator(UserData.Source.Update);
        UserData.ParseContext parseContextRootContext = parseAccumulator.rootContext();
        ObjectValue objectValue = new ObjectValue();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            com.google.firebase.firestore.model.FieldPath internalPath = FieldPath.fromDotSeparatedPath(entry.getKey()).getInternalPath();
            Object value = entry.getValue();
            if (value instanceof FieldValue.DeleteFieldValue) {
                parseContextRootContext.addToFieldMask(internalPath);
            } else {
                Value valueConvertAndParseFieldData = convertAndParseFieldData(value, parseContextRootContext.childContext(internalPath));
                if (valueConvertAndParseFieldData != null) {
                    parseContextRootContext.addToFieldMask(internalPath);
                    objectValue.set(internalPath, valueConvertAndParseFieldData);
                }
            }
        }
        return parseAccumulator.toUpdateData(objectValue);
    }

    public UserData.ParsedUpdateData parseUpdateData(List<Object> list) {
        com.google.firebase.firestore.model.FieldPath internalPath;
        Assert.hardAssert(list.size() % 2 == 0, "Expected fieldAndValues to contain an even number of elements", new Object[0]);
        UserData.ParseAccumulator parseAccumulator = new UserData.ParseAccumulator(UserData.Source.Update);
        UserData.ParseContext parseContextRootContext = parseAccumulator.rootContext();
        ObjectValue objectValue = new ObjectValue();
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            Object next2 = it.next();
            boolean z = next instanceof String;
            Assert.hardAssert(z || (next instanceof FieldPath), "Expected argument to be String or FieldPath.", new Object[0]);
            if (z) {
                internalPath = FieldPath.fromDotSeparatedPath((String) next).getInternalPath();
            } else {
                internalPath = ((FieldPath) next).getInternalPath();
            }
            if (next2 instanceof FieldValue.DeleteFieldValue) {
                parseContextRootContext.addToFieldMask(internalPath);
            } else {
                Value valueConvertAndParseFieldData = convertAndParseFieldData(next2, parseContextRootContext.childContext(internalPath));
                if (valueConvertAndParseFieldData != null) {
                    parseContextRootContext.addToFieldMask(internalPath);
                    objectValue.set(internalPath, valueConvertAndParseFieldData);
                }
            }
        }
        return parseAccumulator.toUpdateData(objectValue);
    }

    public Value parseQueryValue(Object obj) {
        return parseQueryValue(obj, false);
    }

    public Value parseQueryValue(Object obj, boolean z) {
        UserData.ParseAccumulator parseAccumulator = new UserData.ParseAccumulator(z ? UserData.Source.ArrayArgument : UserData.Source.Argument);
        Value valueConvertAndParseFieldData = convertAndParseFieldData(obj, parseAccumulator.rootContext());
        Assert.hardAssert(valueConvertAndParseFieldData != null, "Parsed data should not be null.", new Object[0]);
        Assert.hardAssert(parseAccumulator.getFieldTransforms().isEmpty(), "Field transforms should have been disallowed.", new Object[0]);
        return valueConvertAndParseFieldData;
    }

    public Value convertAndParseFieldData(Object obj, UserData.ParseContext parseContext) {
        return parseData(CustomClassMapper.convertToPlainJavaTypes(obj), parseContext);
    }

    private ObjectValue convertAndParseDocumentData(Object obj, UserData.ParseContext parseContext) {
        if (obj.getClass().isArray()) {
            throw new IllegalArgumentException("Invalid data. Data must be a Map<String, Object> or a suitable POJO object, but it was an array");
        }
        Value data = parseData(CustomClassMapper.convertToPlainJavaTypes(obj), parseContext);
        if (data.getValueTypeCase() != Value.ValueTypeCase.MAP_VALUE) {
            throw new IllegalArgumentException("Invalid data. Data must be a Map<String, Object> or a suitable POJO object, but it was of type: " + Util.typeName(obj));
        }
        return new ObjectValue(data);
    }

    @Nullable
    private Value parseData(Object obj, UserData.ParseContext parseContext) {
        if (obj instanceof Map) {
            return parseMap((Map) obj, parseContext);
        }
        if (obj instanceof FieldValue) {
            parseSentinelFieldValue((FieldValue) obj, parseContext);
            return null;
        }
        if (parseContext.getPath() != null) {
            parseContext.addToFieldMask(parseContext.getPath());
        }
        if (obj instanceof List) {
            if (parseContext.isArrayElement() && parseContext.getDataSource() != UserData.Source.ArrayArgument) {
                throw parseContext.createError("Nested arrays are not supported");
            }
            return parseList((List) obj, parseContext);
        }
        return parseScalarValue(obj, parseContext);
    }

    private <K, V> Value parseMap(Map<K, V> map, UserData.ParseContext parseContext) {
        if (map.isEmpty()) {
            if (parseContext.getPath() != null && !parseContext.getPath().isEmpty()) {
                parseContext.addToFieldMask(parseContext.getPath());
            }
            return Value.newBuilder().setMapValue(MapValue.getDefaultInstance()).build();
        }
        MapValue.Builder builderNewBuilder = MapValue.newBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (!(entry.getKey() instanceof String)) {
                throw parseContext.createError(String.format("Non-String Map key (%s) is not allowed", entry.getValue()));
            }
            String str = (String) entry.getKey();
            Value data = parseData(entry.getValue(), parseContext.childContext(str));
            if (data != null) {
                builderNewBuilder.putFields(str, data);
            }
        }
        return Value.newBuilder().setMapValue(builderNewBuilder).build();
    }

    private <T> Value parseList(List<T> list, UserData.ParseContext parseContext) {
        ArrayValue.Builder builderNewBuilder = ArrayValue.newBuilder();
        Iterator<T> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            Value data = parseData(it.next(), parseContext.childContext(i));
            if (data == null) {
                data = Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();
            }
            builderNewBuilder.addValues(data);
            i++;
        }
        return Value.newBuilder().setArrayValue(builderNewBuilder).build();
    }

    private void parseSentinelFieldValue(FieldValue fieldValue, UserData.ParseContext parseContext) {
        if (!parseContext.isWrite()) {
            throw parseContext.createError(String.format("%s() can only be used with set() and update()", fieldValue.getMethodName()));
        }
        if (parseContext.getPath() == null) {
            throw parseContext.createError(String.format("%s() is not currently supported inside arrays", fieldValue.getMethodName()));
        }
        if (fieldValue instanceof FieldValue.DeleteFieldValue) {
            if (parseContext.getDataSource() == UserData.Source.MergeSet) {
                parseContext.addToFieldMask(parseContext.getPath());
                return;
            } else {
                if (parseContext.getDataSource() == UserData.Source.Update) {
                    Assert.hardAssert(parseContext.getPath().length() > 0, "FieldValue.delete() at the top level should have already been handled.", new Object[0]);
                    throw parseContext.createError("FieldValue.delete() can only appear at the top level of your update data");
                }
                throw parseContext.createError("FieldValue.delete() can only be used with update() and set() with SetOptions.merge()");
            }
        }
        if (fieldValue instanceof FieldValue.ServerTimestampFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), ServerTimestampOperation.getInstance());
            return;
        }
        if (fieldValue instanceof FieldValue.ArrayUnionFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), new ArrayTransformOperation.Union(parseArrayTransformElements(((FieldValue.ArrayUnionFieldValue) fieldValue).getElements())));
        } else if (fieldValue instanceof FieldValue.ArrayRemoveFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), new ArrayTransformOperation.Remove(parseArrayTransformElements(((FieldValue.ArrayRemoveFieldValue) fieldValue).getElements())));
        } else {
            if (!(fieldValue instanceof FieldValue.NumericIncrementFieldValue)) {
                throw Assert.fail("Unknown FieldValue type: %s", Util.typeName(fieldValue));
            }
            parseContext.addToFieldTransforms(parseContext.getPath(), new NumericIncrementTransformOperation(parseQueryValue(((FieldValue.NumericIncrementFieldValue) fieldValue).getOperand())));
        }
    }

    private Value parseScalarValue(Object obj, UserData.ParseContext parseContext) {
        if (obj == null) {
            return Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();
        }
        if (obj instanceof Integer) {
            return Value.newBuilder().setIntegerValue(((Integer) obj).intValue()).build();
        }
        if (obj instanceof Long) {
            return Value.newBuilder().setIntegerValue(((Long) obj).longValue()).build();
        }
        if (obj instanceof Float) {
            return Value.newBuilder().setDoubleValue(((Float) obj).doubleValue()).build();
        }
        if (obj instanceof Double) {
            return Value.newBuilder().setDoubleValue(((Double) obj).doubleValue()).build();
        }
        if (obj instanceof Boolean) {
            return Value.newBuilder().setBooleanValue(((Boolean) obj).booleanValue()).build();
        }
        if (obj instanceof String) {
            return Value.newBuilder().setStringValue((String) obj).build();
        }
        if (obj instanceof Date) {
            return parseTimestamp(new Timestamp((Date) obj));
        }
        if (obj instanceof Timestamp) {
            return parseTimestamp((Timestamp) obj);
        }
        if (obj instanceof GeoPoint) {
            GeoPoint geoPoint = (GeoPoint) obj;
            return Value.newBuilder().setGeoPointValue(LatLng.newBuilder().setLatitude(geoPoint.getLatitude()).setLongitude(geoPoint.getLongitude())).build();
        }
        if (obj instanceof Blob) {
            return Value.newBuilder().setBytesValue(((Blob) obj).toByteString()).build();
        }
        if (obj instanceof DocumentReference) {
            DocumentReference documentReference = (DocumentReference) obj;
            if (documentReference.getFirestore() != null) {
                DatabaseId databaseId = documentReference.getFirestore().getDatabaseId();
                if (!databaseId.equals(this.databaseId)) {
                    throw parseContext.createError(String.format("Document reference is for database %s/%s but should be for database %s/%s", databaseId.getProjectId(), databaseId.getDatabaseId(), this.databaseId.getProjectId(), this.databaseId.getDatabaseId()));
                }
            }
            return Value.newBuilder().setReferenceValue(String.format("projects/%s/databases/%s/documents/%s", this.databaseId.getProjectId(), this.databaseId.getDatabaseId(), documentReference.getPath())).build();
        }
        if (obj.getClass().isArray()) {
            throw parseContext.createError("Arrays are not supported; use a List instead");
        }
        throw parseContext.createError("Unsupported type: " + Util.typeName(obj));
    }

    private Value parseTimestamp(Timestamp timestamp) {
        return Value.newBuilder().setTimestampValue(com.google.protobuf.Timestamp.newBuilder().setSeconds(timestamp.getSeconds()).setNanos((timestamp.getNanoseconds() / 1000) * 1000)).build();
    }

    private List<Value> parseArrayTransformElements(List<Object> list) {
        UserData.ParseAccumulator parseAccumulator = new UserData.ParseAccumulator(UserData.Source.Argument);
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(convertAndParseFieldData(list.get(i), parseAccumulator.rootContext().childContext(i)));
        }
        return arrayList;
    }
}
