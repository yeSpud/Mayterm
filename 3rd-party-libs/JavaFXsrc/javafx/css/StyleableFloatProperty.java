/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javafx.css;

import javafx.beans.property.FloatPropertyBase;
import javafx.beans.value.ObservableValue;

/**
 * This class extends {@code FloatPropertyBase} and provides a partial
 * implementation of a {@code StyleableProperty}. The method
 * {@link StyleableProperty#getCssMetaData()} is not implemented.
 *
 * This class is used to make a {@link javafx.beans.property.FloatProperty},
 * that would otherwise be implemented as a {@link FloatPropertyBase},
 * style&#8209;able by CSS.
 *
 * @see javafx.beans.property.FloatPropertyBase
 * @see CssMetaData
 * @see StyleableProperty
 * @since JavaFX 8.0
 */
public abstract class StyleableFloatProperty
    extends FloatPropertyBase implements StyleableProperty<Number> {

    /**
     * The constructor of the {@code StyleableFloatProperty}.
     */
    public StyleableFloatProperty() {
        super();
    }

    /**
     * The constructor of the {@code StyleableFloatProperty}.
     *
     * @param initialValue
     *            the initial value of the wrapped {@code Object}
     */
    public StyleableFloatProperty(float initialValue) {
        super(initialValue);
    }

    /** {@inheritDoc} */
    @Override
    public void applyStyle(StyleOrigin origin, Number v) {
        setValue(v);
        this.origin = origin;
    }

    /** {@inheritDoc} */
    @Override
    public void bind(ObservableValue<? extends Number> observable) {
        super.bind(observable);
        origin = StyleOrigin.USER;
    }

    /** {@inheritDoc} */
    @Override
    public void set(float v) {
        super.set(v);
        origin = StyleOrigin.USER;
    }

    /** {@inheritDoc} */
    @Override
    public StyleOrigin getStyleOrigin() { return origin; }

    private StyleOrigin origin = null;

}
