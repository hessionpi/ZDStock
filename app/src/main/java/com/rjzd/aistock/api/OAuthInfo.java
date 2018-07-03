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
public class OAuthInfo implements org.apache.thrift.TBase<OAuthInfo, OAuthInfo._Fields>, java.io.Serializable, Cloneable, Comparable<OAuthInfo>, android.os.Parcelable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OAuthInfo");

  private static final org.apache.thrift.protocol.TField OPEN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("openId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField NICKNAME_FIELD_DESC = new org.apache.thrift.protocol.TField("nickname", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField AVATAR_FIELD_DESC = new org.apache.thrift.protocol.TField("avatar", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField SEX_FIELD_DESC = new org.apache.thrift.protocol.TField("sex", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField AREA_FIELD_DESC = new org.apache.thrift.protocol.TField("area", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField WX_OFFICIAL_ACCOUNTS_OPEN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("wxOfficialAccountsOpenId", org.apache.thrift.protocol.TType.STRING, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new OAuthInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new OAuthInfoTupleSchemeFactory());
  }

  private String openId; // required
  private LoginType type; // required
  private String nickname; // required
  private String avatar; // optional
  private String sex; // optional
  private String area; // optional
  private String wxOfficialAccountsOpenId; // optional

  @Override
  public void writeToParcel(android.os.Parcel out, int flags) {
    out.writeString(openId);
    out.writeInt(type.getValue());
    out.writeString(nickname);
    out.writeString(avatar);
    out.writeString(sex);
    out.writeString(area);
    out.writeString(wxOfficialAccountsOpenId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public OAuthInfo(android.os.Parcel in) {
    this.openId= in.readString();
    this.type = LoginType.findByValue(in.readInt());
    this.nickname= in.readString();
    this.avatar= in.readString();
    this.sex= in.readString();
    this.area= in.readString();
    this.wxOfficialAccountsOpenId= in.readString();
  }

  public static final android.os.Parcelable.Creator<OAuthInfo> CREATOR = new android.os.Parcelable.Creator<OAuthInfo>() {
    @Override
    public OAuthInfo[] newArray(int size) {
      return new OAuthInfo[size];
    }

    @Override
    public OAuthInfo createFromParcel(android.os.Parcel in) {
      return new OAuthInfo(in);
    }
  };

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OPEN_ID((short)1, "openId"),
    /**
     * 
     * @see LoginType
     */
    TYPE((short)2, "type"),
    NICKNAME((short)3, "nickname"),
    AVATAR((short)4, "avatar"),
    SEX((short)5, "sex"),
    AREA((short)6, "area"),
    WX_OFFICIAL_ACCOUNTS_OPEN_ID((short)7, "wxOfficialAccountsOpenId");

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
        case 1: // OPEN_ID
          return OPEN_ID;
        case 2: // TYPE
          return TYPE;
        case 3: // NICKNAME
          return NICKNAME;
        case 4: // AVATAR
          return AVATAR;
        case 5: // SEX
          return SEX;
        case 6: // AREA
          return AREA;
        case 7: // WX_OFFICIAL_ACCOUNTS_OPEN_ID
          return WX_OFFICIAL_ACCOUNTS_OPEN_ID;
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
  private static final _Fields optionals[] = {_Fields.AVATAR,_Fields.SEX,_Fields.AREA,_Fields.WX_OFFICIAL_ACCOUNTS_OPEN_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OPEN_ID, new org.apache.thrift.meta_data.FieldMetaData("openId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LoginType.class)));
    tmpMap.put(_Fields.NICKNAME, new org.apache.thrift.meta_data.FieldMetaData("nickname", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AVATAR, new org.apache.thrift.meta_data.FieldMetaData("avatar", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SEX, new org.apache.thrift.meta_data.FieldMetaData("sex", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.AREA, new org.apache.thrift.meta_data.FieldMetaData("area", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.WX_OFFICIAL_ACCOUNTS_OPEN_ID, new org.apache.thrift.meta_data.FieldMetaData("wxOfficialAccountsOpenId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OAuthInfo.class, metaDataMap);
  }

  public OAuthInfo() {
  }

  public OAuthInfo(
    String openId,
    LoginType type,
    String nickname)
  {
    this();
    this.openId = openId;
    this.type = type;
    this.nickname = nickname;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OAuthInfo(OAuthInfo other) {
    if (other.is_set_openId()) {
      this.openId = other.openId;
    }
    if (other.is_set_type()) {
      this.type = other.type;
    }
    if (other.is_set_nickname()) {
      this.nickname = other.nickname;
    }
    if (other.is_set_avatar()) {
      this.avatar = other.avatar;
    }
    if (other.is_set_sex()) {
      this.sex = other.sex;
    }
    if (other.is_set_area()) {
      this.area = other.area;
    }
    if (other.is_set_wxOfficialAccountsOpenId()) {
      this.wxOfficialAccountsOpenId = other.wxOfficialAccountsOpenId;
    }
  }

  public OAuthInfo deepCopy() {
    return new OAuthInfo(this);
  }

  public void clear() {
    this.openId = null;
    this.type = null;
    this.nickname = null;
    this.avatar = null;
    this.sex = null;
    this.area = null;
    this.wxOfficialAccountsOpenId = null;
  }

  public String get_openId() {
    return this.openId;
  }

  public void set_openId(String openId) {
    this.openId = openId;
  }

  public void unset_openId() {
    this.openId = null;
  }

  /** Returns true if field openId is set (has been assigned a value) and false otherwise */
  public boolean is_set_openId() {
    return this.openId != null;
  }

  public void set_openId_isSet(boolean value) {
    if (!value) {
      this.openId = null;
    }
  }

  /**
   * 
   * @see LoginType
   */
  public LoginType get_type() {
    return this.type;
  }

  /**
   * 
   * @see LoginType
   */
  public void set_type(LoginType type) {
    this.type = type;
  }

  public void unset_type() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean is_set_type() {
    return this.type != null;
  }

  public void set_type_isSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public String get_nickname() {
    return this.nickname;
  }

  public void set_nickname(String nickname) {
    this.nickname = nickname;
  }

  public void unset_nickname() {
    this.nickname = null;
  }

  /** Returns true if field nickname is set (has been assigned a value) and false otherwise */
  public boolean is_set_nickname() {
    return this.nickname != null;
  }

  public void set_nickname_isSet(boolean value) {
    if (!value) {
      this.nickname = null;
    }
  }

  public String get_avatar() {
    return this.avatar;
  }

  public void set_avatar(String avatar) {
    this.avatar = avatar;
  }

  public void unset_avatar() {
    this.avatar = null;
  }

  /** Returns true if field avatar is set (has been assigned a value) and false otherwise */
  public boolean is_set_avatar() {
    return this.avatar != null;
  }

  public void set_avatar_isSet(boolean value) {
    if (!value) {
      this.avatar = null;
    }
  }

  public String get_sex() {
    return this.sex;
  }

  public void set_sex(String sex) {
    this.sex = sex;
  }

  public void unset_sex() {
    this.sex = null;
  }

  /** Returns true if field sex is set (has been assigned a value) and false otherwise */
  public boolean is_set_sex() {
    return this.sex != null;
  }

  public void set_sex_isSet(boolean value) {
    if (!value) {
      this.sex = null;
    }
  }

  public String get_area() {
    return this.area;
  }

  public void set_area(String area) {
    this.area = area;
  }

  public void unset_area() {
    this.area = null;
  }

  /** Returns true if field area is set (has been assigned a value) and false otherwise */
  public boolean is_set_area() {
    return this.area != null;
  }

  public void set_area_isSet(boolean value) {
    if (!value) {
      this.area = null;
    }
  }

  public String get_wxOfficialAccountsOpenId() {
    return this.wxOfficialAccountsOpenId;
  }

  public void set_wxOfficialAccountsOpenId(String wxOfficialAccountsOpenId) {
    this.wxOfficialAccountsOpenId = wxOfficialAccountsOpenId;
  }

  public void unset_wxOfficialAccountsOpenId() {
    this.wxOfficialAccountsOpenId = null;
  }

  /** Returns true if field wxOfficialAccountsOpenId is set (has been assigned a value) and false otherwise */
  public boolean is_set_wxOfficialAccountsOpenId() {
    return this.wxOfficialAccountsOpenId != null;
  }

  public void set_wxOfficialAccountsOpenId_isSet(boolean value) {
    if (!value) {
      this.wxOfficialAccountsOpenId = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case OPEN_ID:
      if (value == null) {
        unset_openId();
      } else {
        set_openId((String)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unset_type();
      } else {
        set_type((LoginType)value);
      }
      break;

    case NICKNAME:
      if (value == null) {
        unset_nickname();
      } else {
        set_nickname((String)value);
      }
      break;

    case AVATAR:
      if (value == null) {
        unset_avatar();
      } else {
        set_avatar((String)value);
      }
      break;

    case SEX:
      if (value == null) {
        unset_sex();
      } else {
        set_sex((String)value);
      }
      break;

    case AREA:
      if (value == null) {
        unset_area();
      } else {
        set_area((String)value);
      }
      break;

    case WX_OFFICIAL_ACCOUNTS_OPEN_ID:
      if (value == null) {
        unset_wxOfficialAccountsOpenId();
      } else {
        set_wxOfficialAccountsOpenId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OPEN_ID:
      return get_openId();

    case TYPE:
      return get_type();

    case NICKNAME:
      return get_nickname();

    case AVATAR:
      return get_avatar();

    case SEX:
      return get_sex();

    case AREA:
      return get_area();

    case WX_OFFICIAL_ACCOUNTS_OPEN_ID:
      return get_wxOfficialAccountsOpenId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case OPEN_ID:
      return is_set_openId();
    case TYPE:
      return is_set_type();
    case NICKNAME:
      return is_set_nickname();
    case AVATAR:
      return is_set_avatar();
    case SEX:
      return is_set_sex();
    case AREA:
      return is_set_area();
    case WX_OFFICIAL_ACCOUNTS_OPEN_ID:
      return is_set_wxOfficialAccountsOpenId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof OAuthInfo)
      return this.equals((OAuthInfo)that);
    return false;
  }

  public boolean equals(OAuthInfo that) {
    if (that == null)
      return false;

    boolean this_present_openId = true && this.is_set_openId();
    boolean that_present_openId = true && that.is_set_openId();
    if (this_present_openId || that_present_openId) {
      if (!(this_present_openId && that_present_openId))
        return false;
      if (!this.openId.equals(that.openId))
        return false;
    }

    boolean this_present_type = true && this.is_set_type();
    boolean that_present_type = true && that.is_set_type();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_nickname = true && this.is_set_nickname();
    boolean that_present_nickname = true && that.is_set_nickname();
    if (this_present_nickname || that_present_nickname) {
      if (!(this_present_nickname && that_present_nickname))
        return false;
      if (!this.nickname.equals(that.nickname))
        return false;
    }

    boolean this_present_avatar = true && this.is_set_avatar();
    boolean that_present_avatar = true && that.is_set_avatar();
    if (this_present_avatar || that_present_avatar) {
      if (!(this_present_avatar && that_present_avatar))
        return false;
      if (!this.avatar.equals(that.avatar))
        return false;
    }

    boolean this_present_sex = true && this.is_set_sex();
    boolean that_present_sex = true && that.is_set_sex();
    if (this_present_sex || that_present_sex) {
      if (!(this_present_sex && that_present_sex))
        return false;
      if (!this.sex.equals(that.sex))
        return false;
    }

    boolean this_present_area = true && this.is_set_area();
    boolean that_present_area = true && that.is_set_area();
    if (this_present_area || that_present_area) {
      if (!(this_present_area && that_present_area))
        return false;
      if (!this.area.equals(that.area))
        return false;
    }

    boolean this_present_wxOfficialAccountsOpenId = true && this.is_set_wxOfficialAccountsOpenId();
    boolean that_present_wxOfficialAccountsOpenId = true && that.is_set_wxOfficialAccountsOpenId();
    if (this_present_wxOfficialAccountsOpenId || that_present_wxOfficialAccountsOpenId) {
      if (!(this_present_wxOfficialAccountsOpenId && that_present_wxOfficialAccountsOpenId))
        return false;
      if (!this.wxOfficialAccountsOpenId.equals(that.wxOfficialAccountsOpenId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_openId = true && (is_set_openId());
    list.add(present_openId);
    if (present_openId)
      list.add(openId);

    boolean present_type = true && (is_set_type());
    list.add(present_type);
    if (present_type)
      list.add(type.getValue());

    boolean present_nickname = true && (is_set_nickname());
    list.add(present_nickname);
    if (present_nickname)
      list.add(nickname);

    boolean present_avatar = true && (is_set_avatar());
    list.add(present_avatar);
    if (present_avatar)
      list.add(avatar);

    boolean present_sex = true && (is_set_sex());
    list.add(present_sex);
    if (present_sex)
      list.add(sex);

    boolean present_area = true && (is_set_area());
    list.add(present_area);
    if (present_area)
      list.add(area);

    boolean present_wxOfficialAccountsOpenId = true && (is_set_wxOfficialAccountsOpenId());
    list.add(present_wxOfficialAccountsOpenId);
    if (present_wxOfficialAccountsOpenId)
      list.add(wxOfficialAccountsOpenId);

    return list.hashCode();
  }

  @Override
  public int compareTo(OAuthInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_openId()).compareTo(other.is_set_openId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_openId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.openId, other.openId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_type()).compareTo(other.is_set_type());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_type()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_nickname()).compareTo(other.is_set_nickname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_nickname()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nickname, other.nickname);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_avatar()).compareTo(other.is_set_avatar());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_avatar()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.avatar, other.avatar);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_sex()).compareTo(other.is_set_sex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_sex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sex, other.sex);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_area()).compareTo(other.is_set_area());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_area()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.area, other.area);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_wxOfficialAccountsOpenId()).compareTo(other.is_set_wxOfficialAccountsOpenId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_wxOfficialAccountsOpenId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.wxOfficialAccountsOpenId, other.wxOfficialAccountsOpenId);
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
    StringBuilder sb = new StringBuilder("OAuthInfo(");
    boolean first = true;

    sb.append("openId:");
    if (this.openId == null) {
      sb.append("null");
    } else {
      sb.append(this.openId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("nickname:");
    if (this.nickname == null) {
      sb.append("null");
    } else {
      sb.append(this.nickname);
    }
    first = false;
    if (is_set_avatar()) {
      if (!first) sb.append(", ");
      sb.append("avatar:");
      if (this.avatar == null) {
        sb.append("null");
      } else {
        sb.append(this.avatar);
      }
      first = false;
    }
    if (is_set_sex()) {
      if (!first) sb.append(", ");
      sb.append("sex:");
      if (this.sex == null) {
        sb.append("null");
      } else {
        sb.append(this.sex);
      }
      first = false;
    }
    if (is_set_area()) {
      if (!first) sb.append(", ");
      sb.append("area:");
      if (this.area == null) {
        sb.append("null");
      } else {
        sb.append(this.area);
      }
      first = false;
    }
    if (is_set_wxOfficialAccountsOpenId()) {
      if (!first) sb.append(", ");
      sb.append("wxOfficialAccountsOpenId:");
      if (this.wxOfficialAccountsOpenId == null) {
        sb.append("null");
      } else {
        sb.append(this.wxOfficialAccountsOpenId);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_openId()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'openId' is unset! Struct:" + toString());
    }

    if (!is_set_type()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'type' is unset! Struct:" + toString());
    }

    if (!is_set_nickname()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'nickname' is unset! Struct:" + toString());
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

  private static class OAuthInfoStandardSchemeFactory implements SchemeFactory {
    public OAuthInfoStandardScheme getScheme() {
      return new OAuthInfoStandardScheme();
    }
  }

  private static class OAuthInfoStandardScheme extends StandardScheme<OAuthInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OAuthInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OPEN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.openId = iprot.readString();
              struct.set_openId_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = com.rjzd.aistock.api.LoginType.findByValue(iprot.readI32());
              struct.set_type_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NICKNAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.nickname = iprot.readString();
              struct.set_nickname_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // AVATAR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.avatar = iprot.readString();
              struct.set_avatar_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SEX
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sex = iprot.readString();
              struct.set_sex_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // AREA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.area = iprot.readString();
              struct.set_area_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // WX_OFFICIAL_ACCOUNTS_OPEN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.wxOfficialAccountsOpenId = iprot.readString();
              struct.set_wxOfficialAccountsOpenId_isSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, OAuthInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.openId != null) {
        oprot.writeFieldBegin(OPEN_ID_FIELD_DESC);
        oprot.writeString(struct.openId);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeI32(struct.type.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.nickname != null) {
        oprot.writeFieldBegin(NICKNAME_FIELD_DESC);
        oprot.writeString(struct.nickname);
        oprot.writeFieldEnd();
      }
      if (struct.avatar != null) {
        if (struct.is_set_avatar()) {
          oprot.writeFieldBegin(AVATAR_FIELD_DESC);
          oprot.writeString(struct.avatar);
          oprot.writeFieldEnd();
        }
      }
      if (struct.sex != null) {
        if (struct.is_set_sex()) {
          oprot.writeFieldBegin(SEX_FIELD_DESC);
          oprot.writeString(struct.sex);
          oprot.writeFieldEnd();
        }
      }
      if (struct.area != null) {
        if (struct.is_set_area()) {
          oprot.writeFieldBegin(AREA_FIELD_DESC);
          oprot.writeString(struct.area);
          oprot.writeFieldEnd();
        }
      }
      if (struct.wxOfficialAccountsOpenId != null) {
        if (struct.is_set_wxOfficialAccountsOpenId()) {
          oprot.writeFieldBegin(WX_OFFICIAL_ACCOUNTS_OPEN_ID_FIELD_DESC);
          oprot.writeString(struct.wxOfficialAccountsOpenId);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OAuthInfoTupleSchemeFactory implements SchemeFactory {
    public OAuthInfoTupleScheme getScheme() {
      return new OAuthInfoTupleScheme();
    }
  }

  private static class OAuthInfoTupleScheme extends TupleScheme<OAuthInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OAuthInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.openId);
      oprot.writeI32(struct.type.getValue());
      oprot.writeString(struct.nickname);
      BitSet optionals = new BitSet();
      if (struct.is_set_avatar()) {
        optionals.set(0);
      }
      if (struct.is_set_sex()) {
        optionals.set(1);
      }
      if (struct.is_set_area()) {
        optionals.set(2);
      }
      if (struct.is_set_wxOfficialAccountsOpenId()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.is_set_avatar()) {
        oprot.writeString(struct.avatar);
      }
      if (struct.is_set_sex()) {
        oprot.writeString(struct.sex);
      }
      if (struct.is_set_area()) {
        oprot.writeString(struct.area);
      }
      if (struct.is_set_wxOfficialAccountsOpenId()) {
        oprot.writeString(struct.wxOfficialAccountsOpenId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OAuthInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.openId = iprot.readString();
      struct.set_openId_isSet(true);
      struct.type = com.rjzd.aistock.api.LoginType.findByValue(iprot.readI32());
      struct.set_type_isSet(true);
      struct.nickname = iprot.readString();
      struct.set_nickname_isSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.avatar = iprot.readString();
        struct.set_avatar_isSet(true);
      }
      if (incoming.get(1)) {
        struct.sex = iprot.readString();
        struct.set_sex_isSet(true);
      }
      if (incoming.get(2)) {
        struct.area = iprot.readString();
        struct.set_area_isSet(true);
      }
      if (incoming.get(3)) {
        struct.wxOfficialAccountsOpenId = iprot.readString();
        struct.set_wxOfficialAccountsOpenId_isSet(true);
      }
    }
  }

}
