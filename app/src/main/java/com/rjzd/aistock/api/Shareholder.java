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
 * 十大流通股 和 机构持股
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-08-21")
public class Shareholder implements org.apache.thrift.TBase<Shareholder, Shareholder._Fields>, java.io.Serializable, Cloneable, Comparable<Shareholder>, android.os.Parcelable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Shareholder");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PROPORTION_FIELD_DESC = new org.apache.thrift.protocol.TField("proportion", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CHANGE_FIELD_DESC = new org.apache.thrift.protocol.TField("change", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("quantity", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ShareholderStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ShareholderTupleSchemeFactory());
  }

  private String name; // required
  private String proportion; // required
  private String change; // optional
  private String quantity; // optional

  @Override
  public void writeToParcel(android.os.Parcel out, int flags) {
    out.writeString(name);
    out.writeString(proportion);
    out.writeString(change);
    out.writeString(quantity);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public Shareholder(android.os.Parcel in) {
    this.name= in.readString();
    this.proportion= in.readString();
    this.change= in.readString();
    this.quantity= in.readString();
  }

  public static final android.os.Parcelable.Creator<Shareholder> CREATOR = new android.os.Parcelable.Creator<Shareholder>() {
    @Override
    public Shareholder[] newArray(int size) {
      return new Shareholder[size];
    }

    @Override
    public Shareholder createFromParcel(android.os.Parcel in) {
      return new Shareholder(in);
    }
  };

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "name"),
    PROPORTION((short)2, "proportion"),
    CHANGE((short)3, "change"),
    QUANTITY((short)4, "quantity");

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
        case 1: // NAME
          return NAME;
        case 2: // PROPORTION
          return PROPORTION;
        case 3: // CHANGE
          return CHANGE;
        case 4: // QUANTITY
          return QUANTITY;
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
  private static final _Fields optionals[] = {_Fields.CHANGE,_Fields.QUANTITY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROPORTION, new org.apache.thrift.meta_data.FieldMetaData("proportion", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHANGE, new org.apache.thrift.meta_data.FieldMetaData("change", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("quantity", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Shareholder.class, metaDataMap);
  }

  public Shareholder() {
  }

  public Shareholder(
    String name,
    String proportion)
  {
    this();
    this.name = name;
    this.proportion = proportion;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Shareholder(Shareholder other) {
    if (other.is_set_name()) {
      this.name = other.name;
    }
    if (other.is_set_proportion()) {
      this.proportion = other.proportion;
    }
    if (other.is_set_change()) {
      this.change = other.change;
    }
    if (other.is_set_quantity()) {
      this.quantity = other.quantity;
    }
  }

  public Shareholder deepCopy() {
    return new Shareholder(this);
  }

  public void clear() {
    this.name = null;
    this.proportion = null;
    this.change = null;
    this.quantity = null;
  }

  public String get_name() {
    return this.name;
  }

  public void set_name(String name) {
    this.name = name;
  }

  public void unset_name() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean is_set_name() {
    return this.name != null;
  }

  public void set_name_isSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String get_proportion() {
    return this.proportion;
  }

  public void set_proportion(String proportion) {
    this.proportion = proportion;
  }

  public void unset_proportion() {
    this.proportion = null;
  }

  /** Returns true if field proportion is set (has been assigned a value) and false otherwise */
  public boolean is_set_proportion() {
    return this.proportion != null;
  }

  public void set_proportion_isSet(boolean value) {
    if (!value) {
      this.proportion = null;
    }
  }

  public String get_change() {
    return this.change;
  }

  public void set_change(String change) {
    this.change = change;
  }

  public void unset_change() {
    this.change = null;
  }

  /** Returns true if field change is set (has been assigned a value) and false otherwise */
  public boolean is_set_change() {
    return this.change != null;
  }

  public void set_change_isSet(boolean value) {
    if (!value) {
      this.change = null;
    }
  }

  public String get_quantity() {
    return this.quantity;
  }

  public void set_quantity(String quantity) {
    this.quantity = quantity;
  }

  public void unset_quantity() {
    this.quantity = null;
  }

  /** Returns true if field quantity is set (has been assigned a value) and false otherwise */
  public boolean is_set_quantity() {
    return this.quantity != null;
  }

  public void set_quantity_isSet(boolean value) {
    if (!value) {
      this.quantity = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unset_name();
      } else {
        set_name((String)value);
      }
      break;

    case PROPORTION:
      if (value == null) {
        unset_proportion();
      } else {
        set_proportion((String)value);
      }
      break;

    case CHANGE:
      if (value == null) {
        unset_change();
      } else {
        set_change((String)value);
      }
      break;

    case QUANTITY:
      if (value == null) {
        unset_quantity();
      } else {
        set_quantity((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return get_name();

    case PROPORTION:
      return get_proportion();

    case CHANGE:
      return get_change();

    case QUANTITY:
      return get_quantity();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return is_set_name();
    case PROPORTION:
      return is_set_proportion();
    case CHANGE:
      return is_set_change();
    case QUANTITY:
      return is_set_quantity();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Shareholder)
      return this.equals((Shareholder)that);
    return false;
  }

  public boolean equals(Shareholder that) {
    if (that == null)
      return false;

    boolean this_present_name = true && this.is_set_name();
    boolean that_present_name = true && that.is_set_name();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_proportion = true && this.is_set_proportion();
    boolean that_present_proportion = true && that.is_set_proportion();
    if (this_present_proportion || that_present_proportion) {
      if (!(this_present_proportion && that_present_proportion))
        return false;
      if (!this.proportion.equals(that.proportion))
        return false;
    }

    boolean this_present_change = true && this.is_set_change();
    boolean that_present_change = true && that.is_set_change();
    if (this_present_change || that_present_change) {
      if (!(this_present_change && that_present_change))
        return false;
      if (!this.change.equals(that.change))
        return false;
    }

    boolean this_present_quantity = true && this.is_set_quantity();
    boolean that_present_quantity = true && that.is_set_quantity();
    if (this_present_quantity || that_present_quantity) {
      if (!(this_present_quantity && that_present_quantity))
        return false;
      if (!this.quantity.equals(that.quantity))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_name = true && (is_set_name());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_proportion = true && (is_set_proportion());
    list.add(present_proportion);
    if (present_proportion)
      list.add(proportion);

    boolean present_change = true && (is_set_change());
    list.add(present_change);
    if (present_change)
      list.add(change);

    boolean present_quantity = true && (is_set_quantity());
    list.add(present_quantity);
    if (present_quantity)
      list.add(quantity);

    return list.hashCode();
  }

  @Override
  public int compareTo(Shareholder other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_name()).compareTo(other.is_set_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_name()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_proportion()).compareTo(other.is_set_proportion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_proportion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.proportion, other.proportion);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_change()).compareTo(other.is_set_change());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_change()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.change, other.change);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_quantity()).compareTo(other.is_set_quantity());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_quantity()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.quantity, other.quantity);
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
    StringBuilder sb = new StringBuilder("Shareholder(");
    boolean first = true;

    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("proportion:");
    if (this.proportion == null) {
      sb.append("null");
    } else {
      sb.append(this.proportion);
    }
    first = false;
    if (is_set_change()) {
      if (!first) sb.append(", ");
      sb.append("change:");
      if (this.change == null) {
        sb.append("null");
      } else {
        sb.append(this.change);
      }
      first = false;
    }
    if (is_set_quantity()) {
      if (!first) sb.append(", ");
      sb.append("quantity:");
      if (this.quantity == null) {
        sb.append("null");
      } else {
        sb.append(this.quantity);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_name()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'name' is unset! Struct:" + toString());
    }

    if (!is_set_proportion()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'proportion' is unset! Struct:" + toString());
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

  private static class ShareholderStandardSchemeFactory implements SchemeFactory {
    public ShareholderStandardScheme getScheme() {
      return new ShareholderStandardScheme();
    }
  }

  private static class ShareholderStandardScheme extends StandardScheme<Shareholder> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Shareholder struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.set_name_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PROPORTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.proportion = iprot.readString();
              struct.set_proportion_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CHANGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.change = iprot.readString();
              struct.set_change_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // QUANTITY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.quantity = iprot.readString();
              struct.set_quantity_isSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Shareholder struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.proportion != null) {
        oprot.writeFieldBegin(PROPORTION_FIELD_DESC);
        oprot.writeString(struct.proportion);
        oprot.writeFieldEnd();
      }
      if (struct.change != null) {
        if (struct.is_set_change()) {
          oprot.writeFieldBegin(CHANGE_FIELD_DESC);
          oprot.writeString(struct.change);
          oprot.writeFieldEnd();
        }
      }
      if (struct.quantity != null) {
        if (struct.is_set_quantity()) {
          oprot.writeFieldBegin(QUANTITY_FIELD_DESC);
          oprot.writeString(struct.quantity);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ShareholderTupleSchemeFactory implements SchemeFactory {
    public ShareholderTupleScheme getScheme() {
      return new ShareholderTupleScheme();
    }
  }

  private static class ShareholderTupleScheme extends TupleScheme<Shareholder> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Shareholder struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.name);
      oprot.writeString(struct.proportion);
      BitSet optionals = new BitSet();
      if (struct.is_set_change()) {
        optionals.set(0);
      }
      if (struct.is_set_quantity()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.is_set_change()) {
        oprot.writeString(struct.change);
      }
      if (struct.is_set_quantity()) {
        oprot.writeString(struct.quantity);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Shareholder struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.name = iprot.readString();
      struct.set_name_isSet(true);
      struct.proportion = iprot.readString();
      struct.set_proportion_isSet(true);
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.change = iprot.readString();
        struct.set_change_isSet(true);
      }
      if (incoming.get(1)) {
        struct.quantity = iprot.readString();
        struct.set_quantity_isSet(true);
      }
    }
  }

}
