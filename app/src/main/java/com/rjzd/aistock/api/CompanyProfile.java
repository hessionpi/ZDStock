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
 * 公司概况
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-08-21")
public class CompanyProfile implements org.apache.thrift.TBase<CompanyProfile, CompanyProfile._Fields>, java.io.Serializable, Cloneable, Comparable<CompanyProfile>, android.os.Parcelable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CompanyProfile");

  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField BASIC_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("basicInfo", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField EXECUTIVE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("executiveList", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField DIVIDENDS_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("dividendsList", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("msg", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CompanyProfileStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CompanyProfileTupleSchemeFactory());
  }

  private StatusCode status; // required
  private List<NameValuePairs> basicInfo; // required
  private List<Executive> executiveList; // required
  private List<Dividends> dividendsList; // required
  private String msg; // required

  @Override
  public void writeToParcel(android.os.Parcel out, int flags) {
    out.writeInt(status.getValue());
    out.writeTypedList(basicInfo);
    out.writeTypedList(executiveList);
    out.writeTypedList(dividendsList);
    out.writeString(msg);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public CompanyProfile(android.os.Parcel in) {
    this.status = StatusCode.findByValue(in.readInt());
    this.basicInfo = new ArrayList<NameValuePairs>();
    in.readTypedList(this.basicInfo, NameValuePairs.CREATOR);
    this.executiveList = new ArrayList<Executive>();
    in.readTypedList(this.executiveList, Executive.CREATOR);
    this.dividendsList = new ArrayList<Dividends>();
    in.readTypedList(this.dividendsList, Dividends.CREATOR);
    this.msg= in.readString();
  }

  public static final android.os.Parcelable.Creator<CompanyProfile> CREATOR = new android.os.Parcelable.Creator<CompanyProfile>() {
    @Override
    public CompanyProfile[] newArray(int size) {
      return new CompanyProfile[size];
    }

    @Override
    public CompanyProfile createFromParcel(android.os.Parcel in) {
      return new CompanyProfile(in);
    }
  };

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see StatusCode
     */
    STATUS((short)1, "status"),
    BASIC_INFO((short)2, "basicInfo"),
    EXECUTIVE_LIST((short)3, "executiveList"),
    DIVIDENDS_LIST((short)4, "dividendsList"),
    MSG((short)5, "msg");

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
        case 2: // BASIC_INFO
          return BASIC_INFO;
        case 3: // EXECUTIVE_LIST
          return EXECUTIVE_LIST;
        case 4: // DIVIDENDS_LIST
          return DIVIDENDS_LIST;
        case 5: // MSG
          return MSG;
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
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, StatusCode.class)));
    tmpMap.put(_Fields.BASIC_INFO, new org.apache.thrift.meta_data.FieldMetaData("basicInfo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, NameValuePairs.class))));
    tmpMap.put(_Fields.EXECUTIVE_LIST, new org.apache.thrift.meta_data.FieldMetaData("executiveList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Executive.class))));
    tmpMap.put(_Fields.DIVIDENDS_LIST, new org.apache.thrift.meta_data.FieldMetaData("dividendsList", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Dividends.class))));
    tmpMap.put(_Fields.MSG, new org.apache.thrift.meta_data.FieldMetaData("msg", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CompanyProfile.class, metaDataMap);
  }

  public CompanyProfile() {
  }

  public CompanyProfile(
    StatusCode status,
    List<NameValuePairs> basicInfo,
    List<Executive> executiveList,
    List<Dividends> dividendsList,
    String msg)
  {
    this();
    this.status = status;
    this.basicInfo = basicInfo;
    this.executiveList = executiveList;
    this.dividendsList = dividendsList;
    this.msg = msg;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CompanyProfile(CompanyProfile other) {
    if (other.is_set_status()) {
      this.status = other.status;
    }
    if (other.is_set_basicInfo()) {
      List<NameValuePairs> __this__basicInfo = new ArrayList<NameValuePairs>(other.basicInfo.size());
      for (NameValuePairs other_element : other.basicInfo) {
        __this__basicInfo.add(new NameValuePairs(other_element));
      }
      this.basicInfo = __this__basicInfo;
    }
    if (other.is_set_executiveList()) {
      List<Executive> __this__executiveList = new ArrayList<Executive>(other.executiveList.size());
      for (Executive other_element : other.executiveList) {
        __this__executiveList.add(new Executive(other_element));
      }
      this.executiveList = __this__executiveList;
    }
    if (other.is_set_dividendsList()) {
      List<Dividends> __this__dividendsList = new ArrayList<Dividends>(other.dividendsList.size());
      for (Dividends other_element : other.dividendsList) {
        __this__dividendsList.add(new Dividends(other_element));
      }
      this.dividendsList = __this__dividendsList;
    }
    if (other.is_set_msg()) {
      this.msg = other.msg;
    }
  }

  public CompanyProfile deepCopy() {
    return new CompanyProfile(this);
  }

  public void clear() {
    this.status = null;
    if (this.basicInfo != null) {
      this.basicInfo.clear();
    }
    if (this.executiveList != null) {
      this.executiveList.clear();
    }
    if (this.dividendsList != null) {
      this.dividendsList.clear();
    }
    this.msg = null;
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

  public int get_basicInfo_size() {
    return (this.basicInfo == null) ? 0 : this.basicInfo.size();
  }

  public java.util.Iterator<NameValuePairs> get_basicInfo_iterator() {
    return (this.basicInfo == null) ? null : this.basicInfo.iterator();
  }

  public void add_to_basicInfo(NameValuePairs elem) {
    if (this.basicInfo == null) {
      this.basicInfo = new ArrayList<NameValuePairs>();
    }
    this.basicInfo.add(elem);
  }

  public List<NameValuePairs> get_basicInfo() {
    return this.basicInfo;
  }

  public void set_basicInfo(List<NameValuePairs> basicInfo) {
    this.basicInfo = basicInfo;
  }

  public void unset_basicInfo() {
    this.basicInfo = null;
  }

  /** Returns true if field basicInfo is set (has been assigned a value) and false otherwise */
  public boolean is_set_basicInfo() {
    return this.basicInfo != null;
  }

  public void set_basicInfo_isSet(boolean value) {
    if (!value) {
      this.basicInfo = null;
    }
  }

  public int get_executiveList_size() {
    return (this.executiveList == null) ? 0 : this.executiveList.size();
  }

  public java.util.Iterator<Executive> get_executiveList_iterator() {
    return (this.executiveList == null) ? null : this.executiveList.iterator();
  }

  public void add_to_executiveList(Executive elem) {
    if (this.executiveList == null) {
      this.executiveList = new ArrayList<Executive>();
    }
    this.executiveList.add(elem);
  }

  public List<Executive> get_executiveList() {
    return this.executiveList;
  }

  public void set_executiveList(List<Executive> executiveList) {
    this.executiveList = executiveList;
  }

  public void unset_executiveList() {
    this.executiveList = null;
  }

  /** Returns true if field executiveList is set (has been assigned a value) and false otherwise */
  public boolean is_set_executiveList() {
    return this.executiveList != null;
  }

  public void set_executiveList_isSet(boolean value) {
    if (!value) {
      this.executiveList = null;
    }
  }

  public int get_dividendsList_size() {
    return (this.dividendsList == null) ? 0 : this.dividendsList.size();
  }

  public java.util.Iterator<Dividends> get_dividendsList_iterator() {
    return (this.dividendsList == null) ? null : this.dividendsList.iterator();
  }

  public void add_to_dividendsList(Dividends elem) {
    if (this.dividendsList == null) {
      this.dividendsList = new ArrayList<Dividends>();
    }
    this.dividendsList.add(elem);
  }

  public List<Dividends> get_dividendsList() {
    return this.dividendsList;
  }

  public void set_dividendsList(List<Dividends> dividendsList) {
    this.dividendsList = dividendsList;
  }

  public void unset_dividendsList() {
    this.dividendsList = null;
  }

  /** Returns true if field dividendsList is set (has been assigned a value) and false otherwise */
  public boolean is_set_dividendsList() {
    return this.dividendsList != null;
  }

  public void set_dividendsList_isSet(boolean value) {
    if (!value) {
      this.dividendsList = null;
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STATUS:
      if (value == null) {
        unset_status();
      } else {
        set_status((StatusCode)value);
      }
      break;

    case BASIC_INFO:
      if (value == null) {
        unset_basicInfo();
      } else {
        set_basicInfo((List<NameValuePairs>)value);
      }
      break;

    case EXECUTIVE_LIST:
      if (value == null) {
        unset_executiveList();
      } else {
        set_executiveList((List<Executive>)value);
      }
      break;

    case DIVIDENDS_LIST:
      if (value == null) {
        unset_dividendsList();
      } else {
        set_dividendsList((List<Dividends>)value);
      }
      break;

    case MSG:
      if (value == null) {
        unset_msg();
      } else {
        set_msg((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STATUS:
      return get_status();

    case BASIC_INFO:
      return get_basicInfo();

    case EXECUTIVE_LIST:
      return get_executiveList();

    case DIVIDENDS_LIST:
      return get_dividendsList();

    case MSG:
      return get_msg();

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
    case BASIC_INFO:
      return is_set_basicInfo();
    case EXECUTIVE_LIST:
      return is_set_executiveList();
    case DIVIDENDS_LIST:
      return is_set_dividendsList();
    case MSG:
      return is_set_msg();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CompanyProfile)
      return this.equals((CompanyProfile)that);
    return false;
  }

  public boolean equals(CompanyProfile that) {
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

    boolean this_present_basicInfo = true && this.is_set_basicInfo();
    boolean that_present_basicInfo = true && that.is_set_basicInfo();
    if (this_present_basicInfo || that_present_basicInfo) {
      if (!(this_present_basicInfo && that_present_basicInfo))
        return false;
      if (!this.basicInfo.equals(that.basicInfo))
        return false;
    }

    boolean this_present_executiveList = true && this.is_set_executiveList();
    boolean that_present_executiveList = true && that.is_set_executiveList();
    if (this_present_executiveList || that_present_executiveList) {
      if (!(this_present_executiveList && that_present_executiveList))
        return false;
      if (!this.executiveList.equals(that.executiveList))
        return false;
    }

    boolean this_present_dividendsList = true && this.is_set_dividendsList();
    boolean that_present_dividendsList = true && that.is_set_dividendsList();
    if (this_present_dividendsList || that_present_dividendsList) {
      if (!(this_present_dividendsList && that_present_dividendsList))
        return false;
      if (!this.dividendsList.equals(that.dividendsList))
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

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_status = true && (is_set_status());
    list.add(present_status);
    if (present_status)
      list.add(status.getValue());

    boolean present_basicInfo = true && (is_set_basicInfo());
    list.add(present_basicInfo);
    if (present_basicInfo)
      list.add(basicInfo);

    boolean present_executiveList = true && (is_set_executiveList());
    list.add(present_executiveList);
    if (present_executiveList)
      list.add(executiveList);

    boolean present_dividendsList = true && (is_set_dividendsList());
    list.add(present_dividendsList);
    if (present_dividendsList)
      list.add(dividendsList);

    boolean present_msg = true && (is_set_msg());
    list.add(present_msg);
    if (present_msg)
      list.add(msg);

    return list.hashCode();
  }

  @Override
  public int compareTo(CompanyProfile other) {
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
    lastComparison = Boolean.valueOf(is_set_basicInfo()).compareTo(other.is_set_basicInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_basicInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.basicInfo, other.basicInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_executiveList()).compareTo(other.is_set_executiveList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_executiveList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.executiveList, other.executiveList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_dividendsList()).compareTo(other.is_set_dividendsList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_dividendsList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dividendsList, other.dividendsList);
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
    StringBuilder sb = new StringBuilder("CompanyProfile(");
    boolean first = true;

    sb.append("status:");
    if (this.status == null) {
      sb.append("null");
    } else {
      sb.append(this.status);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("basicInfo:");
    if (this.basicInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.basicInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("executiveList:");
    if (this.executiveList == null) {
      sb.append("null");
    } else {
      sb.append(this.executiveList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dividendsList:");
    if (this.dividendsList == null) {
      sb.append("null");
    } else {
      sb.append(this.dividendsList);
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
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_status()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'status' is unset! Struct:" + toString());
    }

    if (!is_set_basicInfo()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'basicInfo' is unset! Struct:" + toString());
    }

    if (!is_set_executiveList()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'executiveList' is unset! Struct:" + toString());
    }

    if (!is_set_dividendsList()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'dividendsList' is unset! Struct:" + toString());
    }

    if (!is_set_msg()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'msg' is unset! Struct:" + toString());
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

  private static class CompanyProfileStandardSchemeFactory implements SchemeFactory {
    public CompanyProfileStandardScheme getScheme() {
      return new CompanyProfileStandardScheme();
    }
  }

  private static class CompanyProfileStandardScheme extends StandardScheme<CompanyProfile> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CompanyProfile struct) throws org.apache.thrift.TException {
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
          case 2: // BASIC_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list64 = iprot.readListBegin();
                if (struct.basicInfo == null) {
                  struct.basicInfo = new ArrayList<NameValuePairs>(_list64.size);
                }
                NameValuePairs _elem65 = new NameValuePairs();
                for (int _i66 = 0; _i66 < _list64.size; ++_i66)
                {
                  if (_elem65 == null) {
                    _elem65 = new NameValuePairs();
                  }
                  _elem65.read(iprot);
                  struct.basicInfo.add(_elem65);
                  _elem65 = null;
                }
                iprot.readListEnd();
              }
              struct.set_basicInfo_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EXECUTIVE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list67 = iprot.readListBegin();
                if (struct.executiveList == null) {
                  struct.executiveList = new ArrayList<Executive>(_list67.size);
                }
                Executive _elem68 = new Executive();
                for (int _i69 = 0; _i69 < _list67.size; ++_i69)
                {
                  if (_elem68 == null) {
                    _elem68 = new Executive();
                  }
                  _elem68.read(iprot);
                  struct.executiveList.add(_elem68);
                  _elem68 = null;
                }
                iprot.readListEnd();
              }
              struct.set_executiveList_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DIVIDENDS_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list70 = iprot.readListBegin();
                if (struct.dividendsList == null) {
                  struct.dividendsList = new ArrayList<Dividends>(_list70.size);
                }
                Dividends _elem71 = new Dividends();
                for (int _i72 = 0; _i72 < _list70.size; ++_i72)
                {
                  if (_elem71 == null) {
                    _elem71 = new Dividends();
                  }
                  _elem71.read(iprot);
                  struct.dividendsList.add(_elem71);
                  _elem71 = null;
                }
                iprot.readListEnd();
              }
              struct.set_dividendsList_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.msg = iprot.readString();
              struct.set_msg_isSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CompanyProfile struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.status != null) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeI32(struct.status.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.basicInfo != null) {
        oprot.writeFieldBegin(BASIC_INFO_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.basicInfo.size()));
          for (NameValuePairs _iter73 : struct.basicInfo)
          {
            _iter73.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.executiveList != null) {
        oprot.writeFieldBegin(EXECUTIVE_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.executiveList.size()));
          for (Executive _iter74 : struct.executiveList)
          {
            _iter74.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.dividendsList != null) {
        oprot.writeFieldBegin(DIVIDENDS_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.dividendsList.size()));
          for (Dividends _iter75 : struct.dividendsList)
          {
            _iter75.write(oprot);
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
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CompanyProfileTupleSchemeFactory implements SchemeFactory {
    public CompanyProfileTupleScheme getScheme() {
      return new CompanyProfileTupleScheme();
    }
  }

  private static class CompanyProfileTupleScheme extends TupleScheme<CompanyProfile> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CompanyProfile struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.status.getValue());
      {
        oprot.writeI32(struct.basicInfo.size());
        for (NameValuePairs _iter76 : struct.basicInfo)
        {
          _iter76.write(oprot);
        }
      }
      {
        oprot.writeI32(struct.executiveList.size());
        for (Executive _iter77 : struct.executiveList)
        {
          _iter77.write(oprot);
        }
      }
      {
        oprot.writeI32(struct.dividendsList.size());
        for (Dividends _iter78 : struct.dividendsList)
        {
          _iter78.write(oprot);
        }
      }
      oprot.writeString(struct.msg);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CompanyProfile struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.status = com.rjzd.aistock.api.StatusCode.findByValue(iprot.readI32());
      struct.set_status_isSet(true);
      {
        org.apache.thrift.protocol.TList _list79 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        if (struct.basicInfo == null) {
          struct.basicInfo = new ArrayList<NameValuePairs>(_list79.size);
        }
        NameValuePairs _elem80 = new NameValuePairs();
        for (int _i81 = 0; _i81 < _list79.size; ++_i81)
        {
          if (_elem80 == null) {
            _elem80 = new NameValuePairs();
          }
          _elem80.read(iprot);
          struct.basicInfo.add(_elem80);
          _elem80 = null;
        }
      }
      struct.set_basicInfo_isSet(true);
      {
        org.apache.thrift.protocol.TList _list82 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        if (struct.executiveList == null) {
          struct.executiveList = new ArrayList<Executive>(_list82.size);
        }
        Executive _elem83 = new Executive();
        for (int _i84 = 0; _i84 < _list82.size; ++_i84)
        {
          if (_elem83 == null) {
            _elem83 = new Executive();
          }
          _elem83.read(iprot);
          struct.executiveList.add(_elem83);
          _elem83 = null;
        }
      }
      struct.set_executiveList_isSet(true);
      {
        org.apache.thrift.protocol.TList _list85 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        if (struct.dividendsList == null) {
          struct.dividendsList = new ArrayList<Dividends>(_list85.size);
        }
        Dividends _elem86 = new Dividends();
        for (int _i87 = 0; _i87 < _list85.size; ++_i87)
        {
          if (_elem86 == null) {
            _elem86 = new Dividends();
          }
          _elem86.read(iprot);
          struct.dividendsList.add(_elem86);
          _elem86 = null;
        }
      }
      struct.set_dividendsList_isSet(true);
      struct.msg = iprot.readString();
      struct.set_msg_isSet(true);
    }
  }

}

