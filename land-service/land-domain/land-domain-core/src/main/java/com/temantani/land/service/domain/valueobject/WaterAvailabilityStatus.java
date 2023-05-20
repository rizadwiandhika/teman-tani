package com.temantani.land.service.domain.valueobject;

/*
 * When evaluating water availability for a farm, the parameter can have varying
 * values depending on the specific conditions of the location. Here are some
 * possible values and examples:
 * 
 * Abundant water supply: This indicates that the farm has access to a reliable
 * and plentiful water source, such as a river, lake, or well, which provides
 * sufficient water for irrigation needs throughout the growing season.
 * 
 * Example: The farm is located adjacent to a river, allowing for easy access to
 * water for irrigation purposes. There are no significant concerns regarding
 * water scarcity or limitations.
 * 
 * Limited water supply: This value suggests that the farm has access to water,
 * but the supply is limited or inconsistent. It may require careful management
 * of water resources to ensure efficient use.
 * 
 * Example: The farm relies on a well as the primary water source. However, the
 * well has a limited yield, resulting in water scarcity during dry periods,
 * requiring strategic irrigation scheduling and conservation measures.
 * 
 * Seasonal water availability: In this case, water is available, but it is
 * limited to specific seasons or periods. The farm needs to plan irrigation
 * activities accordingly and may need alternative water storage solutions.
 * 
 * Example: The farm has access to a nearby reservoir that fills up during the
 * rainy season. The water can be used for irrigation during the wet months but
 * is not available during the dry season, necessitating careful water
 * management.
 * 
 * Water scarcity: This indicates that the farm faces significant challenges in
 * terms of water availability. It may require alternative irrigation methods or
 * reliance on rainwater harvesting techniques.
 * 
 * Example: The farm is located in a semi-arid region with limited rainfall and
 * lacks access to reliable water sources. Irrigation relies mainly on rainwater
 * collection systems, such as large catchment areas or rainwater tanks.
 * 
 * It's important to note that these values are not exhaustive, and the specific
 * water availability for a farm will depend on factors such as geographic
 * location, climate, infrastructure, and local water resources.
 */
public enum WaterAvailabilityStatus {
  ABUNDANT, LIMITED, SEASONAL, SCARCITY
}
