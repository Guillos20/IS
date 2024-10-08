// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: buffer.proto

package main;

public interface StudentBufferOrBuilder extends
    // @@protoc_insertion_point(interface_extends:main.StudentBuffer)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional int32 id = 1;</code>
   * @return Whether the id field is set.
   */
  boolean hasId();
  /**
   * <code>optional int32 id = 1;</code>
   * @return The id.
   */
  int getId();

  /**
   * <code>optional string name = 2;</code>
   * @return Whether the name field is set.
   */
  boolean hasName();
  /**
   * <code>optional string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>optional string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>optional string cell = 3;</code>
   * @return Whether the cell field is set.
   */
  boolean hasCell();
  /**
   * <code>optional string cell = 3;</code>
   * @return The cell.
   */
  java.lang.String getCell();
  /**
   * <code>optional string cell = 3;</code>
   * @return The bytes for cell.
   */
  com.google.protobuf.ByteString
      getCellBytes();

  /**
   * <code>optional string gender = 4;</code>
   * @return Whether the gender field is set.
   */
  boolean hasGender();
  /**
   * <code>optional string gender = 4;</code>
   * @return The gender.
   */
  java.lang.String getGender();
  /**
   * <code>optional string gender = 4;</code>
   * @return The bytes for gender.
   */
  com.google.protobuf.ByteString
      getGenderBytes();

  /**
   * <code>optional string birthdate = 5;</code>
   * @return Whether the birthdate field is set.
   */
  boolean hasBirthdate();
  /**
   * <code>optional string birthdate = 5;</code>
   * @return The birthdate.
   */
  java.lang.String getBirthdate();
  /**
   * <code>optional string birthdate = 5;</code>
   * @return The bytes for birthdate.
   */
  com.google.protobuf.ByteString
      getBirthdateBytes();

  /**
   * <code>optional int32 age = 6;</code>
   * @return Whether the age field is set.
   */
  boolean hasAge();
  /**
   * <code>optional int32 age = 6;</code>
   * @return The age.
   */
  int getAge();

  /**
   * <code>optional string regDate = 7;</code>
   * @return Whether the regDate field is set.
   */
  boolean hasRegDate();
  /**
   * <code>optional string regDate = 7;</code>
   * @return The regDate.
   */
  java.lang.String getRegDate();
  /**
   * <code>optional string regDate = 7;</code>
   * @return The bytes for regDate.
   */
  com.google.protobuf.ByteString
      getRegDateBytes();

  /**
   * <code>optional string address = 8;</code>
   * @return Whether the address field is set.
   */
  boolean hasAddress();
  /**
   * <code>optional string address = 8;</code>
   * @return The address.
   */
  java.lang.String getAddress();
  /**
   * <code>optional string address = 8;</code>
   * @return The bytes for address.
   */
  com.google.protobuf.ByteString
      getAddressBytes();

  /**
   * <code>repeated .main.ProfessorBuffer profBuf = 9;</code>
   */
  java.util.List<main.ProfessorBuffer> 
      getProfBufList();
  /**
   * <code>repeated .main.ProfessorBuffer profBuf = 9;</code>
   */
  main.ProfessorBuffer getProfBuf(int index);
  /**
   * <code>repeated .main.ProfessorBuffer profBuf = 9;</code>
   */
  int getProfBufCount();
  /**
   * <code>repeated .main.ProfessorBuffer profBuf = 9;</code>
   */
  java.util.List<? extends main.ProfessorBufferOrBuilder> 
      getProfBufOrBuilderList();
  /**
   * <code>repeated .main.ProfessorBuffer profBuf = 9;</code>
   */
  main.ProfessorBufferOrBuilder getProfBufOrBuilder(
      int index);
}
