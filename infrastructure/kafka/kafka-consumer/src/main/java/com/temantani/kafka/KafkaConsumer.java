package com.temantani.kafka;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<T extends Serializable> {

  void recieve(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);

}
