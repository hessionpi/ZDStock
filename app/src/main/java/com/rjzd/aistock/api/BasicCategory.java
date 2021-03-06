/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.rjzd.aistock.api;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * 基础类别
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-08-21")
public class BasicCategory implements org.apache.thrift.TBase<BasicCategory, BasicCategory._Fields>, java.io.Serializable, Cloneable, Comparable<BasicCategory>, android.os.Parcelable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BasicCategory");

  private static final org.apache.thrift.protocol.TField CATEGORY_FIELD_DESC = new org.apache.thrift.protocol.TField("category", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ANALYSIS_OPTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("analysisOptions", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BasicCategoryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BasicCategoryTupleSchemeFactory());
  }

  private String category; // required
  private List<NameValuePairs> analysisOptions; // required

  @Override
  public void writeToParcel(android.os.Parcel out, int flags) {
    out.writeString(category);
    out.writeTypedList(analysisOptions);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public BasicCategory(android.os.Parcel in) {
    this.category= in.readString();
    this.analysisOptions = new ArrayList<NameValuePairs>();
    in.readTypedList(this.analysisOptions, NameValuePairs.CREATOR);
  }

  public static final android.os.Parcelable.Creator<BasicCategory> CREATOR = new android.os.Parcelable.Creator<BasicCategory>() {
    @Override
    public BasicCategory[] newArray(int size) {
      return new BasicCategory[size];
    }

    @Override
    public BasicCategory createFromParcel(android.os.Parcel in) {
      return new BasicCategory(in);
    }
  };

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CATEGORY((short)1, "category"),
    ANALYSIS_OPTIONS((short)2, "analysisOptions");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CATEGORY
          return CATEGORY;
        case 2: // ANALYSIS_OPTIONS
          return ANALYSIS_OPTIONS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CATEGORY, new org.apache.thrift.meta_data.FieldMetaData("category", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ANALYSIS_OPTIONS, new org.apache.thrift.meta_data.FieldMetaData("analysisOptions", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, NameValuePairs.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BasicCategory.class, metaDataMap);
  }

  public BasicCategory() {
  }

  public BasicCategory(
    String category,
    List<NameValuePairs> analysisOptions)
  {
    this();
    this.category = category;
    this.analysisOptions = analysisOptions;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BasicCategory(BasicCategory other) {
    if (other.is_set_category()) {
      this.category = other.category;
    }
    if (other.is_set_analysisOptions()) {
      List<NameValuePairs> __this__analysisOptions = new ArrayList<NameValuePairs>(other.analysisOptions.size());
      for (NameValuePairs other_element : other.analysisOptions) {
        __this__analysisOptions.add(new NameValuePairs(other_element));
      }
      this.analysisOptions = __this__analysisOptions;
    }
  }

  public BasicCategory deepCopy() {
    return new BasicCategory(this);
  }

  public void clear() {
    this.category = null;
    if (this.analysisOptions != null) {
      this.analysisOptions.clear();
    }
  }

  public String get_category() {
    return this.category;
  }

  public void set_category(String category) {
    this.category = category;
  }

  public void unset_category() {
    this.category = null;
  }

  /** Returns true if field category is set (has been assigned a value) and false otherwise */
  public boolean is_set_category() {
    return this.category != null;
  }

  public void set_category_isSet(boolean value) {
    if (!value) {
      this.category = null;
    }
  }

  public int get_analysisOptions_size() {
    return (this.analysisOptions == null) ? 0 : this.analysisOptions.size();
  }

  public java.util.Iterator<NameValuePairs> get_analysisOptions_iterator() {
    return (this.analysisOptions == null) ? null : this.analysisOptions.iterator();
  }

  public void add_to_analysisOptions(NameValuePairs elem) {
    if (this.analysisOptions == null) {
      this.analysisOptions = new ArrayList<NameValuePairs>();
    }
    this.analysisOptions.add(elem);
  }

  public List<NameValuePairs> get_analysisOptions() {
    return this.analysisOptions;
  }

  public void set_analysisOptions(List<NameValuePairs> analysisOptions) {
    this.analysisOptions = analysisOptions;
  }

  public void unset_analysisOptions() {
    this.analysisOptions = null;
  }

  /** Returns true if field analysisOptions is set (has been assigned a value) and false otherwise */
  public boolean is_set_analysisOptions() {
    return this.analysisOptions != null;
  }

  public void set_analysisOptions_isSet(boolean value) {
    if (!value) {
      this.analysisOptions = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CATEGORY:
      if (value == null) {
        unset_category();
      } else {
        set_category((String)value);
      }
      break;

    case ANALYSIS_OPTIONS:
      if (value == null) {
        unset_analysisOptions();
      } else {
        set_analysisOptions((List<NameValuePairs>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CATEGORY:
      return get_category();

    case ANALYSIS_OPTIONS:
      return get_analysisOptions();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CATEGORY:
      return is_set_category();
    case ANALYSIS_OPTIONS:
      return is_set_analysisOptions();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BasicCategory)
      return this.equals((BasicCategory)that);
    return false;
  }

  public boolean equals(BasicCategory that) {
    if (that == null)
      return false;

    boolean this_present_category = true && this.is_set_category();
    boolean that_present_category = true && that.is_set_category();
    if (this_present_category || that_present_category) {
      if (!(this_present_category && that_present_category))
        return false;
      if (!this.category.equals(that.category))
        return false;
    }

    boolean this_present_analysisOptions = true && this.is_set_analysisOptions();
    boolean that_present_analysisOptions = true && that.is_set_analysisOptions();
    if (this_present_analysisOptions || that_present_analysisOptions) {
      if (!(this_present_analysisOptions && that_present_analysisOptions))
        return false;
      if (!this.analysisOptions.equals(that.analysisOptions))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_category = true && (is_set_category());
    list.add(present_category);
    if (present_category)
      list.add(category);

    boolean present_analysisOptions = true && (is_set_analysisOptions());
    list.add(present_analysisOptions);
    if (present_analysisOptions)
      list.add(analysisOptions);

    return list.hashCode();
  }

  @Override
  public int compareTo(BasicCategory other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_category()).compareTo(other.is_set_category());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_category()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.category, other.category);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_analysisOptions()).compareTo(other.is_set_analysisOptions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_analysisOptions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.analysisOptions, other.analysisOptions);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("BasicCategory(");
    boolean first = true;

    sb.append("category:");
    if (this.category == null) {
      sb.append("null");
    } else {
      sb.append(this.category);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("analysisOptions:");
    if (this.analysisOptions == null) {
      sb.append("null");
    } else {
      sb.append(this.analysisOptions);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_category()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'category' is unset! Struct:" + toString());
    }

    if (!is_set_analysisOptions()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'analysisOptions' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te.getMessage());
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te.getMessage());
    }
  }

  private static class BasicCategoryStandardSchemeFactory implements SchemeFactory {
    public BasicCategoryStandardScheme getScheme() {
      return new BasicCategoryStandardScheme();
    }
  }

  private static class BasicCategoryStandardScheme extends StandardScheme<BasicCategory> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BasicCategory struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CATEGORY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.category = iprot.readString();
              struct.set_category_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ANALYSIS_OPTIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list56 = iprot.readListBegin();
                if (struct.analysisOptions == null) {
                  struct.analysisOptions = new ArrayList<NameValuePairs>(_list56.size);
                }
                NameValuePairs _elem57 = new NameValuePairs();
                for (int _i58 = 0; _i58 < _list56.size; ++_i58)
                {
                  if (_elem57 == null) {
                    _elem57 = new NameValuePairs();
                  }
                  _elem57.read(iprot);
                  struct.analysisOptions.add(_elem57);
                  _elem57 = null;
                }
                iprot.readListEnd();
              }
              struct.set_analysisOptions_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, BasicCategory struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.category != null) {
        oprot.writeFieldBegin(CATEGORY_FIELD_DESC);
        oprot.writeString(struct.category);
        oprot.writeFieldEnd();
      }
      if (struct.analysisOptions != null) {
        oprot.writeFieldBegin(ANALYSIS_OPTIONS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.analysisOptions.size()));
          for (NameValuePairs _iter59 : struct.analysisOptions)
          {
            _iter59.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BasicCategoryTupleSchemeFactory implements SchemeFactory {
    public BasicCategoryTupleScheme getScheme() {
      return new BasicCategoryTupleScheme();
    }
  }

  private static class BasicCategoryTupleScheme extends TupleScheme<BasicCategory> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BasicCategory struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.category);
      {
        oprot.writeI32(struct.analysisOptions.size());
        for (NameValuePairs _iter60 : struct.analysisOptions)
        {
          _iter60.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BasicCategory struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.category = iprot.readString();
      struct.set_category_isSet(true);
      {
        org.apache.thrift.protocol.TList _list61 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        if (struct.analysisOptions == null) {
          struct.analysisOptions = new ArrayList<NameValuePairs>(_list61.size);
        }
        NameValuePairs _elem62 = new NameValuePairs();
        for (int _i63 = 0; _i63 < _list61.size; ++_i63)
        {
          if (_elem62 == null) {
            _elem62 = new NameValuePairs();
          }
          _elem62.read(iprot);
          struct.analysisOptions.add(_elem62);
          _elem62 = null;
        }
      }
      struct.set_analysisOptions_isSet(true);
    }
  }

}

