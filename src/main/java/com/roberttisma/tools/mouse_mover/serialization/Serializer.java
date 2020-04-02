package com.roberttisma.tools.mouse_mover.serialization;

public interface Serializer<T, S> {

  S serialize(T object);

}
