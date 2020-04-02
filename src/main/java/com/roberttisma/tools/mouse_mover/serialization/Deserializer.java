package com.roberttisma.tools.mouse_mover.serialization;

public interface  Deserializer<T, S> {

  T deserialize(S s);
}
