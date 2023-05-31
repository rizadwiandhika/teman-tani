/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.temantani.kafka.investment.avro.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class FundraisingRegisteredAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 1218094168379176380L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FundraisingRegisteredAvroModel\",\"namespace\":\"com.temantani.kafka.investment.avro.model\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"fundraisingTarget\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"fundraisingDeadline\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.DecimalConversion());
  }

  private static final BinaryMessageEncoder<FundraisingRegisteredAvroModel> ENCODER =
      new BinaryMessageEncoder<FundraisingRegisteredAvroModel>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<FundraisingRegisteredAvroModel> DECODER =
      new BinaryMessageDecoder<FundraisingRegisteredAvroModel>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<FundraisingRegisteredAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<FundraisingRegisteredAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<FundraisingRegisteredAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<FundraisingRegisteredAvroModel>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this FundraisingRegisteredAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a FundraisingRegisteredAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a FundraisingRegisteredAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static FundraisingRegisteredAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.String id;
  private java.math.BigDecimal fundraisingTarget;
  private java.time.Instant fundraisingDeadline;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public FundraisingRegisteredAvroModel() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param fundraisingTarget The new value for fundraisingTarget
   * @param fundraisingDeadline The new value for fundraisingDeadline
   */
  public FundraisingRegisteredAvroModel(java.lang.String id, java.math.BigDecimal fundraisingTarget, java.time.Instant fundraisingDeadline) {
    this.id = id;
    this.fundraisingTarget = fundraisingTarget;
    this.fundraisingDeadline = fundraisingDeadline.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return fundraisingTarget;
    case 2: return fundraisingDeadline;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      new org.apache.avro.Conversions.DecimalConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = value$ != null ? value$.toString() : null; break;
    case 1: fundraisingTarget = (java.math.BigDecimal)value$; break;
    case 2: fundraisingDeadline = (java.time.Instant)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'fundraisingTarget' field.
   * @return The value of the 'fundraisingTarget' field.
   */
  public java.math.BigDecimal getFundraisingTarget() {
    return fundraisingTarget;
  }


  /**
   * Sets the value of the 'fundraisingTarget' field.
   * @param value the value to set.
   */
  public void setFundraisingTarget(java.math.BigDecimal value) {
    this.fundraisingTarget = value;
  }

  /**
   * Gets the value of the 'fundraisingDeadline' field.
   * @return The value of the 'fundraisingDeadline' field.
   */
  public java.time.Instant getFundraisingDeadline() {
    return fundraisingDeadline;
  }


  /**
   * Sets the value of the 'fundraisingDeadline' field.
   * @param value the value to set.
   */
  public void setFundraisingDeadline(java.time.Instant value) {
    this.fundraisingDeadline = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Creates a new FundraisingRegisteredAvroModel RecordBuilder.
   * @return A new FundraisingRegisteredAvroModel RecordBuilder
   */
  public static com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder newBuilder() {
    return new com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder();
  }

  /**
   * Creates a new FundraisingRegisteredAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new FundraisingRegisteredAvroModel RecordBuilder
   */
  public static com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder newBuilder(com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder other) {
    if (other == null) {
      return new com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder();
    } else {
      return new com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new FundraisingRegisteredAvroModel RecordBuilder by copying an existing FundraisingRegisteredAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new FundraisingRegisteredAvroModel RecordBuilder
   */
  public static com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder newBuilder(com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel other) {
    if (other == null) {
      return new com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder();
    } else {
      return new com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for FundraisingRegisteredAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<FundraisingRegisteredAvroModel>
    implements org.apache.avro.data.RecordBuilder<FundraisingRegisteredAvroModel> {

    private java.lang.String id;
    private java.math.BigDecimal fundraisingTarget;
    private java.time.Instant fundraisingDeadline;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.fundraisingTarget)) {
        this.fundraisingTarget = data().deepCopy(fields()[1].schema(), other.fundraisingTarget);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.fundraisingDeadline)) {
        this.fundraisingDeadline = data().deepCopy(fields()[2].schema(), other.fundraisingDeadline);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing FundraisingRegisteredAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.fundraisingTarget)) {
        this.fundraisingTarget = data().deepCopy(fields()[1].schema(), other.fundraisingTarget);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.fundraisingDeadline)) {
        this.fundraisingDeadline = data().deepCopy(fields()[2].schema(), other.fundraisingDeadline);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'fundraisingTarget' field.
      * @return The value.
      */
    public java.math.BigDecimal getFundraisingTarget() {
      return fundraisingTarget;
    }


    /**
      * Sets the value of the 'fundraisingTarget' field.
      * @param value The value of 'fundraisingTarget'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setFundraisingTarget(java.math.BigDecimal value) {
      validate(fields()[1], value);
      this.fundraisingTarget = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'fundraisingTarget' field has been set.
      * @return True if the 'fundraisingTarget' field has been set, false otherwise.
      */
    public boolean hasFundraisingTarget() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'fundraisingTarget' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearFundraisingTarget() {
      fundraisingTarget = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'fundraisingDeadline' field.
      * @return The value.
      */
    public java.time.Instant getFundraisingDeadline() {
      return fundraisingDeadline;
    }


    /**
      * Sets the value of the 'fundraisingDeadline' field.
      * @param value The value of 'fundraisingDeadline'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setFundraisingDeadline(java.time.Instant value) {
      validate(fields()[2], value);
      this.fundraisingDeadline = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'fundraisingDeadline' field has been set.
      * @return True if the 'fundraisingDeadline' field has been set, false otherwise.
      */
    public boolean hasFundraisingDeadline() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'fundraisingDeadline' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearFundraisingDeadline() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FundraisingRegisteredAvroModel build() {
      try {
        FundraisingRegisteredAvroModel record = new FundraisingRegisteredAvroModel();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.fundraisingTarget = fieldSetFlags()[1] ? this.fundraisingTarget : (java.math.BigDecimal) defaultValue(fields()[1]);
        record.fundraisingDeadline = fieldSetFlags()[2] ? this.fundraisingDeadline : (java.time.Instant) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<FundraisingRegisteredAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<FundraisingRegisteredAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<FundraisingRegisteredAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<FundraisingRegisteredAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










