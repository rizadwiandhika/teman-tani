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
  private static final long serialVersionUID = -6473251766513141279L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FundraisingRegisteredAvroModel\",\"namespace\":\"com.temantani.kafka.investment.avro.model\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"landId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"description\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"harvest\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"fundraisingTarget\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"fundraisingDeadline\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},{\"name\":\"estimatedFinished\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},{\"name\":\"createdAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");
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
  private java.lang.String landId;
  private java.lang.String description;
  private java.lang.String harvest;
  private java.math.BigDecimal fundraisingTarget;
  private java.time.Instant fundraisingDeadline;
  private java.time.Instant estimatedFinished;
  private java.time.Instant createdAt;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public FundraisingRegisteredAvroModel() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param landId The new value for landId
   * @param description The new value for description
   * @param harvest The new value for harvest
   * @param fundraisingTarget The new value for fundraisingTarget
   * @param fundraisingDeadline The new value for fundraisingDeadline
   * @param estimatedFinished The new value for estimatedFinished
   * @param createdAt The new value for createdAt
   */
  public FundraisingRegisteredAvroModel(java.lang.String id, java.lang.String landId, java.lang.String description, java.lang.String harvest, java.math.BigDecimal fundraisingTarget, java.time.Instant fundraisingDeadline, java.time.Instant estimatedFinished, java.time.Instant createdAt) {
    this.id = id;
    this.landId = landId;
    this.description = description;
    this.harvest = harvest;
    this.fundraisingTarget = fundraisingTarget;
    this.fundraisingDeadline = fundraisingDeadline.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
    this.estimatedFinished = estimatedFinished.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
    this.createdAt = createdAt.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return landId;
    case 2: return description;
    case 3: return harvest;
    case 4: return fundraisingTarget;
    case 5: return fundraisingDeadline;
    case 6: return estimatedFinished;
    case 7: return createdAt;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      null,
      null,
      new org.apache.avro.Conversions.DecimalConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
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
    case 1: landId = value$ != null ? value$.toString() : null; break;
    case 2: description = value$ != null ? value$.toString() : null; break;
    case 3: harvest = value$ != null ? value$.toString() : null; break;
    case 4: fundraisingTarget = (java.math.BigDecimal)value$; break;
    case 5: fundraisingDeadline = (java.time.Instant)value$; break;
    case 6: estimatedFinished = (java.time.Instant)value$; break;
    case 7: createdAt = (java.time.Instant)value$; break;
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
   * Gets the value of the 'landId' field.
   * @return The value of the 'landId' field.
   */
  public java.lang.String getLandId() {
    return landId;
  }


  /**
   * Sets the value of the 'landId' field.
   * @param value the value to set.
   */
  public void setLandId(java.lang.String value) {
    this.landId = value;
  }

  /**
   * Gets the value of the 'description' field.
   * @return The value of the 'description' field.
   */
  public java.lang.String getDescription() {
    return description;
  }


  /**
   * Sets the value of the 'description' field.
   * @param value the value to set.
   */
  public void setDescription(java.lang.String value) {
    this.description = value;
  }

  /**
   * Gets the value of the 'harvest' field.
   * @return The value of the 'harvest' field.
   */
  public java.lang.String getHarvest() {
    return harvest;
  }


  /**
   * Sets the value of the 'harvest' field.
   * @param value the value to set.
   */
  public void setHarvest(java.lang.String value) {
    this.harvest = value;
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
   * Gets the value of the 'estimatedFinished' field.
   * @return The value of the 'estimatedFinished' field.
   */
  public java.time.Instant getEstimatedFinished() {
    return estimatedFinished;
  }


  /**
   * Sets the value of the 'estimatedFinished' field.
   * @param value the value to set.
   */
  public void setEstimatedFinished(java.time.Instant value) {
    this.estimatedFinished = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Gets the value of the 'createdAt' field.
   * @return The value of the 'createdAt' field.
   */
  public java.time.Instant getCreatedAt() {
    return createdAt;
  }


  /**
   * Sets the value of the 'createdAt' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.time.Instant value) {
    this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
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
    private java.lang.String landId;
    private java.lang.String description;
    private java.lang.String harvest;
    private java.math.BigDecimal fundraisingTarget;
    private java.time.Instant fundraisingDeadline;
    private java.time.Instant estimatedFinished;
    private java.time.Instant createdAt;

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
      if (isValidValue(fields()[1], other.landId)) {
        this.landId = data().deepCopy(fields()[1].schema(), other.landId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.description)) {
        this.description = data().deepCopy(fields()[2].schema(), other.description);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.harvest)) {
        this.harvest = data().deepCopy(fields()[3].schema(), other.harvest);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.fundraisingTarget)) {
        this.fundraisingTarget = data().deepCopy(fields()[4].schema(), other.fundraisingTarget);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.fundraisingDeadline)) {
        this.fundraisingDeadline = data().deepCopy(fields()[5].schema(), other.fundraisingDeadline);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.estimatedFinished)) {
        this.estimatedFinished = data().deepCopy(fields()[6].schema(), other.estimatedFinished);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[7].schema(), other.createdAt);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
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
      if (isValidValue(fields()[1], other.landId)) {
        this.landId = data().deepCopy(fields()[1].schema(), other.landId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.description)) {
        this.description = data().deepCopy(fields()[2].schema(), other.description);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.harvest)) {
        this.harvest = data().deepCopy(fields()[3].schema(), other.harvest);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.fundraisingTarget)) {
        this.fundraisingTarget = data().deepCopy(fields()[4].schema(), other.fundraisingTarget);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.fundraisingDeadline)) {
        this.fundraisingDeadline = data().deepCopy(fields()[5].schema(), other.fundraisingDeadline);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.estimatedFinished)) {
        this.estimatedFinished = data().deepCopy(fields()[6].schema(), other.estimatedFinished);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.createdAt)) {
        this.createdAt = data().deepCopy(fields()[7].schema(), other.createdAt);
        fieldSetFlags()[7] = true;
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
      * Gets the value of the 'landId' field.
      * @return The value.
      */
    public java.lang.String getLandId() {
      return landId;
    }


    /**
      * Sets the value of the 'landId' field.
      * @param value The value of 'landId'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setLandId(java.lang.String value) {
      validate(fields()[1], value);
      this.landId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'landId' field has been set.
      * @return True if the 'landId' field has been set, false otherwise.
      */
    public boolean hasLandId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'landId' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearLandId() {
      landId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'description' field.
      * @return The value.
      */
    public java.lang.String getDescription() {
      return description;
    }


    /**
      * Sets the value of the 'description' field.
      * @param value The value of 'description'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setDescription(java.lang.String value) {
      validate(fields()[2], value);
      this.description = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'description' field has been set.
      * @return True if the 'description' field has been set, false otherwise.
      */
    public boolean hasDescription() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'description' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearDescription() {
      description = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'harvest' field.
      * @return The value.
      */
    public java.lang.String getHarvest() {
      return harvest;
    }


    /**
      * Sets the value of the 'harvest' field.
      * @param value The value of 'harvest'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setHarvest(java.lang.String value) {
      validate(fields()[3], value);
      this.harvest = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'harvest' field has been set.
      * @return True if the 'harvest' field has been set, false otherwise.
      */
    public boolean hasHarvest() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'harvest' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearHarvest() {
      harvest = null;
      fieldSetFlags()[3] = false;
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
      validate(fields()[4], value);
      this.fundraisingTarget = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'fundraisingTarget' field has been set.
      * @return True if the 'fundraisingTarget' field has been set, false otherwise.
      */
    public boolean hasFundraisingTarget() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'fundraisingTarget' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearFundraisingTarget() {
      fundraisingTarget = null;
      fieldSetFlags()[4] = false;
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
      validate(fields()[5], value);
      this.fundraisingDeadline = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'fundraisingDeadline' field has been set.
      * @return True if the 'fundraisingDeadline' field has been set, false otherwise.
      */
    public boolean hasFundraisingDeadline() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'fundraisingDeadline' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearFundraisingDeadline() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'estimatedFinished' field.
      * @return The value.
      */
    public java.time.Instant getEstimatedFinished() {
      return estimatedFinished;
    }


    /**
      * Sets the value of the 'estimatedFinished' field.
      * @param value The value of 'estimatedFinished'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setEstimatedFinished(java.time.Instant value) {
      validate(fields()[6], value);
      this.estimatedFinished = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'estimatedFinished' field has been set.
      * @return True if the 'estimatedFinished' field has been set, false otherwise.
      */
    public boolean hasEstimatedFinished() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'estimatedFinished' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearEstimatedFinished() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'createdAt' field.
      * @return The value.
      */
    public java.time.Instant getCreatedAt() {
      return createdAt;
    }


    /**
      * Sets the value of the 'createdAt' field.
      * @param value The value of 'createdAt'.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder setCreatedAt(java.time.Instant value) {
      validate(fields()[7], value);
      this.createdAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'createdAt' field has been set.
      * @return True if the 'createdAt' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'createdAt' field.
      * @return This builder.
      */
    public com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel.Builder clearCreatedAt() {
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FundraisingRegisteredAvroModel build() {
      try {
        FundraisingRegisteredAvroModel record = new FundraisingRegisteredAvroModel();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        record.landId = fieldSetFlags()[1] ? this.landId : (java.lang.String) defaultValue(fields()[1]);
        record.description = fieldSetFlags()[2] ? this.description : (java.lang.String) defaultValue(fields()[2]);
        record.harvest = fieldSetFlags()[3] ? this.harvest : (java.lang.String) defaultValue(fields()[3]);
        record.fundraisingTarget = fieldSetFlags()[4] ? this.fundraisingTarget : (java.math.BigDecimal) defaultValue(fields()[4]);
        record.fundraisingDeadline = fieldSetFlags()[5] ? this.fundraisingDeadline : (java.time.Instant) defaultValue(fields()[5]);
        record.estimatedFinished = fieldSetFlags()[6] ? this.estimatedFinished : (java.time.Instant) defaultValue(fields()[6]);
        record.createdAt = fieldSetFlags()[7] ? this.createdAt : (java.time.Instant) defaultValue(fields()[7]);
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









