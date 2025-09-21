package org.apache.graphar.info.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

abstract class ElementGenericAbstractBuilder<T, B extends ElementGenericAbstractBuilder<T, B>> {

    private final Class<T> elementClass; // Needed for builded class constructor
    private final Class<B> builderClass;

    public abstract B builder();

    private ElementGenericAbstractBuilder(Class<T> elementClass, Class<B> builderClass) {
        this.elementClass = elementClass;
        this.builderClass = builderClass;
    }


    abstract void check();

    private B getSelf(){ // Generic safety for children
        return (B)this;
    }

    public final T build() {
        check();
        try {
            Constructor<T> elementBuilderConstructor = elementClass.getConstructor(builderClass);
            return elementBuilderConstructor.newInstance(getSelf());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
