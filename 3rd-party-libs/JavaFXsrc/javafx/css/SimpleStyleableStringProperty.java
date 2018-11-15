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

import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class extends {@code SimpleStringProperty} and provides a full
 * implementation of a {@code StyleableProperty}.
 *
 * This class is used to make a {@link javafx.beans.property.StringProperty},
 * that would otherwise be implemented as a {@link SimpleStringProperty},
 * style&#8209;able by CSS.
 *
 * @see javafx.beans.property.SimpleStringProperty
 * @see CssMetaData
 * @see StyleableProperty
 * @see StyleableStringProperty
 * @since JavaFX 8.0
 */
public class SimpleStyleableStringProperty extends StyleableStringProperty {

    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";

    private final Object bean;
    private final String name;
    private final CssMetaData<? extends Styleable, String> cssMetaData;

    /**
     * The constructor of the {@code SimpleStyleableStringProperty}.
     * @param cssMetaData
     *            the CssMetaData associated with this {@code StyleableProperty}
     */
    public SimpleStyleableStringProperty(@NamedArg("cssMetaData") CssMetaData<? extends Styleable, String> cssMetaData) {
        this(cssMetaData, DEFAULT_BEAN, DEFAULT_NAME);
    }

    /**
     * The constructor of the {@code SimpleStyleableStringProperty}.
     *
     * @param cssMetaData
     *            the CssMetaData associated with this {@code StyleableProperty}
     * @param initialValue
     *            the initial value of the wrapped {@code Object}
     */
    public SimpleStyleableStringProperty(@NamedArg("cssMetaData") CssMetaData<? extends Styleable, String> cssMetaData, @NamedArg("initialValue") String initialValue) {
        this(cssMetaData, DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    /**
     * The constructor of the {@code SimpleStyleableStringProperty}.
     *
     * @param cssMetaData
     *            the CssMetaData associated with this {@code StyleableProperty}
     * @param bean
     *            the bean of this {@code StringProperty}
     * @param name
     *            the name of this {@code StringProperty}
     */
    public SimpleStyleableStringProperty(@NamedArg("cssMetaData") CssMetaData<? extends Styleable, String> cssMetaData, @NamedArg("bean") Object bean, @NamedArg("name") String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
        this.cssMetaData = cssMetaData;
    }

    /**
     * The constructor of the {@code SimpleStyleableStringProperty}.
     *
     * @param cssMetaData
     *            the CssMetaData associated with this {@code StyleableProperty}
     * @param bean
     *            the bean of this {@code StringProperty}
     * @param name
     *            the name of this {@code StringProperty}
     * @param initialValue
     *            the initial value of the wrapped {@code Object}
     */
    public SimpleStyleableStringProperty(@NamedArg("cssMetaData") CssMetaData<? extends Styleable, String> cssMetaData, @NamedArg("bean") Object bean, @NamedArg("name") String name, @NamedArg("initialValue") String initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
        this.cssMetaData = cssMetaData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public final CssMetaData<? extends Styleable, String> getCssMetaData() {
        return cssMetaData;
    }

}
