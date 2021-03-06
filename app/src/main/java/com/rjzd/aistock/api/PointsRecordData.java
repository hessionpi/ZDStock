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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-08-21")
public class PointsRecordData implements org.apache.thrift.TBase<PointsRecordData, PointsRecordData._Fields>, java.io.Serializable, Cloneable, Comparable<PointsRecordData>, android.os.Parcelable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PointsRecordData");

  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("data", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("msg", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TOTAL_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("totalPage", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PointsRecordDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PointsRecordDataTupleSchemeFactory());
  }

  private StatusCode status; // required
  private List<PointsRecord> data; // required
  private String msg; // required
  private int totalPage; // required

  @Override
  public void writeToParcel(android.os.Parcel out, int flags) {
    //primitive bitfield of type: byte
    out.writeByte(__isset_bitfield);

    out.writeInt(status.getValue());
    out.writeTypedList(data);
    out.writeString(msg);
    out.writeInt(totalPage);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public PointsRecordData(android.os.Parcel in) {
    //primitive bitfield of type: byte
    __isset_bitfield = in.readByte();

    this.status = StatusCode.findByValue(in.readInt());
    this.data = new ArrayList<PointsRecord>();
    in.readTypedList(this.data, PointsRecord.CREATOR);
    this.msg= in.readString();
    this.totalPage = in.readInt();
  }

  public static final android.os.Parcelable.Creator<PointsRecordData> CREATOR = new android.os.Parcelable.Creator<PointsRecordData>() {
    @Override
    public PointsRecordData[] newArray(int size) {
      return new PointsRecordData[size];
    }

    @Override
    public PointsRecordData createFromParcel(android.os.Parcel in) {
      return new PointsRecordData(in);
    }
  };

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see StatusCode
     */
    STATUS((short)1, "status"),
    DATA((short)2, "data"),
    MSG((short)3, "msg"),
    TOTAL_PAGE((short)4, "totalPage");

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
        case 1: // STATUS
          return STATUS;
        case 2: // DATA
          return DATA;
        case 3: // MSG
          return MSG;
        case 4: // TOTAL_PAGE
          return TOTAL_PAGE;
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
  private static final int __TOTALPAGE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, StatusCode.class)));
    tmpMap.put(_Fields.DATA, new org.apache.thrift.meta_data.FieldMetaData("data", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PointsRecord.class))));
    tmpMap.put(_Fields.MSG, new org.apache.thrift.meta_data.FieldMetaData("msg", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOTAL_PAGE, new org.apache.thrift.meta_data.FieldMetaData("totalPage", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PointsRecordData.class, metaDataMap);
  }

  public PointsRecordData() {
  }

  public PointsRecordData(
    StatusCode status,
    List<PointsRecord> data,
    String msg,
    int totalPage)
  {
    this();
    this.status = status;
    this.data = data;
    this.msg = msg;
    this.totalPage = totalPage;
    set_totalPage_isSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PointsRecordData(PointsRecordData other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.is_set_status()) {
      this.status = other.status;
    }
    if (other.is_set_data()) {
      List<PointsRecord> __this__data = new ArrayList<PointsRecord>(other.data.size());
      for (PointsRecord other_element : other.data) {
        __this__data.add(new PointsRecord(other_element));
      }
      this.data = __this__data;
    }
    if (other.is_set_msg()) {
      this.msg = other.msg;
    }
    this.totalPage = other.totalPage;
  }

  public PointsRecordData deepCopy() {
    return new PointsRecordData(this);
  }

  public void clear() {
    this.status = null;
    if (this.data != null) {
      this.data.clear();
    }
    this.msg = null;
    set_totalPage_isSet(false);
    this.totalPage = 0;
  }

  /**
   * 
   * @see StatusCode
   */
  public StatusCode get_status() {
    return this.status;
  }

  /**
   * 
   * @see StatusCode
   */
  public void set_status(StatusCode status) {
    this.status = status;
  }

  public void unset_status() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean is_set_status() {
    return this.status != null;
  }

  public void set_status_isSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public int get_data_size() {
    return (this.data == null) ? 0 : this.data.size();
  }

  public java.util.Iterator<PointsRecord> get_data_iterator() {
    return (this.data == null) ? null : this.data.iterator();
  }

  public void add_to_data(PointsRecord elem) {
    if (this.data == null) {
      this.data = new ArrayList<PointsRecord>();
    }
    this.data.add(elem);
  }

  public List<PointsRecord> get_data() {
    return this.data;
  }

  public void set_data(List<PointsRecord> data) {
    this.data = data;
  }

  public void unset_data() {
    this.data = null;
  }

  /** Returns true if field data is set (has been assigned a value) and false otherwise */
  public boolean is_set_data() {
    return this.data != null;
  }

  public void set_data_isSet(boolean value) {
    if (!value) {
      this.data = null;
    }
  }

  public String get_msg() {
    return this.msg;
  }

  public void set_msg(String msg) {
    this.msg = msg;
  }

  public void unset_msg() {
    this.msg = null;
  }

  /** Returns true if field msg is set (has been assigned a value) and false otherwise */
  public boolean is_set_msg() {
    return this.msg != null;
  }

  public void set_msg_isSet(boolean value) {
    if (!value) {
      this.msg = null;
    }
  }

  public int get_totalPage() {
    return this.totalPage;
  }

  public void set_totalPage(int totalPage) {
    this.totalPage = totalPage;
    set_totalPage_isSet(true);
  }

  public void unset_totalPage() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALPAGE_ISSET_ID);
  }

  /** Returns true if field totalPage is set (has been assigned a value) and false otherwise */
  public boolean is_set_totalPage() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALPAGE_ISSET_ID);
  }

  public void set_totalPage_isSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALPAGE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STATUS:
      if (value == null) {
        unset_status();
      } else {
        set_status((StatusCode)value);
      }
      break;

    case DATA:
      if (value == null) {
        unset_data();
      } else {
        set_data((List<PointsRecord>)value);
      }
      break;

    case MSG:
      if (value == null) {
        unset_msg();
      } else {
        set_msg((String)value);
      }
      break;

    case TOTAL_PAGE:
      if (value == null) {
        unset_totalPage();
      } else {
        set_totalPage((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STATUS:
      return get_status();

    case DATA:
      return get_data();

    case MSG:
      return get_msg();

    case TOTAL_PAGE:
      return get_totalPage();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STATUS:
      return is_set_status();
    case DATA:
      return is_set_data();
    case MSG:
      return is_set_msg();
    case TOTAL_PAGE:
      return is_set_totalPage();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PointsRecordData)
      return this.equals((PointsRecordData)that);
    return false;
  }

  public boolean equals(PointsRecordData that) {
    if (that == null)
      return false;

    boolean this_present_status = true && this.is_set_status();
    boolean that_present_status = true && that.is_set_status();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    boolean this_present_data = true && this.is_set_data();
    boolean that_present_data = true && that.is_set_data();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    boolean this_present_msg = true && this.is_set_msg();
    boolean that_present_msg = true && that.is_set_msg();
    if (this_present_msg || that_present_msg) {
      if (!(this_present_msg && that_present_msg))
        return false;
      if (!this.msg.equals(that.msg))
        return false;
    }

    boolean this_present_totalPage = true;
    boolean that_present_totalPage = true;
    if (this_present_totalPage || that_present_totalPage) {
      if (!(this_present_totalPage && that_present_totalPage))
        return false;
      if (this.totalPage != that.totalPage)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_status = true && (is_set_status());
    list.add(present_status);
    if (present_status)
      list.add(status.getValue());

    boolean present_data = true && (is_set_data());
    list.add(present_data);
    if (present_data)
      list.add(data);

    boolean present_msg = true && (is_set_msg());
    list.add(present_msg);
    if (present_msg)
      list.add(msg);

    boolean present_totalPage = true;
    list.add(present_totalPage);
    if (present_totalPage)
      list.add(totalPage);

    return list.hashCode();
  }

  @Override
  public int compareTo(PointsRecordData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_status()).compareTo(other.is_set_status());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_status()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_data()).compareTo(other.is_set_data());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_data()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.data, other.data);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_msg()).compareTo(other.is_set_msg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_msg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.msg, other.msg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_totalPage()).compareTo(other.is_set_totalPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_totalPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalPage, other.totalPage);
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
    StringBuilder sb = new StringBuilder("PointsRecordData(");
    boolean first = true;

    sb.append("status:");
    if (this.status == null) {
      sb.append("null");
    } else {
      sb.append(this.status);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("data:");
    if (this.data == null) {
      sb.append("null");
    } else {
      sb.append(this.data);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("msg:");
    if (this.msg == null) {
      sb.append("null");
    } else {
      sb.append(this.msg);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalPage:");
    sb.append(this.totalPage);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_status()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'status' is unset! Struct:" + toString());
    }

    if (!is_set_data()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'data' is unset! Struct:" + toString());
    }

    if (!is_set_msg()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'msg' is unset! Struct:" + toString());
    }

    if (!is_set_totalPage()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'totalPage' is unset! Struct:" + toString());
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te.getMessage());
    }
  }

  private static class PointsRecordDataStandardSchemeFactory implements SchemeFactory {
    public PointsRecordDataStandardScheme getScheme() {
      return new PointsRecordDataStandardScheme();
    }
  }

  private static class PointsRecordDataStandardScheme extends StandardScheme<PointsRecordData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PointsRecordData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = com.rjzd.aistock.api.StatusCode.findByValue(iprot.readI32());
              struct.set_status_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list432 = iprot.readListBegin();
                if (struct.data == null) {
                  struct.data = new ArrayList<PointsRecord>(_list432.size);
                }
                PointsRecord _elem433 = new PointsRecord();
                for (int _i434 = 0; _i434 < _list432.size; ++_i434)
                {
                  if (_elem433 == null) {
                    _elem433 = new PointsRecord();
                  }
                  _elem433.read(iprot);
                  struct.data.add(_elem433);
                  _elem433 = null;
                }
                iprot.readListEnd();
              }
              struct.set_data_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.msg = iprot.readString();
              struct.set_msg_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TOTAL_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalPage = iprot.readI32();
              struct.set_totalPage_isSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PointsRecordData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.status != null) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeI32(struct.status.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.data != null) {
        oprot.writeFieldBegin(DATA_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.data.size()));
          for (PointsRecord _iter435 : struct.data)
          {
            _iter435.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.msg != null) {
        oprot.writeFieldBegin(MSG_FIELD_DESC);
        oprot.writeString(struct.msg);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TOTAL_PAGE_FIELD_DESC);
      oprot.writeI32(struct.totalPage);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PointsRecordDataTupleSchemeFactory implements SchemeFactory {
    public PointsRecordDataTupleScheme getScheme() {
      return new PointsRecordDataTupleScheme();
    }
  }

  private static class PointsRecordDataTupleScheme extends TupleScheme<PointsRecordData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PointsRecordData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.status.getValue());
      {
        oprot.writeI32(struct.data.size());
        for (PointsRecord _iter436 : struct.data)
        {
          _iter436.write(oprot);
        }
      }
      oprot.writeString(struct.msg);
      oprot.writeI32(struct.totalPage);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PointsRecordData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.status = com.rjzd.aistock.api.StatusCode.findByValue(iprot.readI32());
      struct.set_status_isSet(true);
      {
        org.apache.thrift.protocol.TList _list437 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        if (struct.data == null) {
          struct.data = new ArrayList<PointsRecord>(_list437.size);
        }
        PointsRecord _elem438 = new PointsRecord();
        for (int _i439 = 0; _i439 < _list437.size; ++_i439)
        {
          if (_elem438 == null) {
            _elem438 = new PointsRecord();
          }
          _elem438.read(iprot);
          struct.data.add(_elem438);
          _elem438 = null;
        }
      }
      struct.set_data_isSet(true);
      struct.msg = iprot.readString();
      struct.set_msg_isSet(true);
      struct.totalPage = iprot.readI32();
      struct.set_totalPage_isSet(true);
    }
  }

}

