/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.scene.text;

public interface TextLayoutFactory {
    /**
     * Returns a new TextLayout instance.
     */
    public TextLayout createLayout();

    /**
     * Returns a reusable instance of TextLayout, the caller is responsible by
     * returning the instance back to the factory using disposeLayout().
     */
    public TextLayout getLayout();

    /**
     * Disposes the reusable TextLayout.
     */
    public void disposeLayout(TextLayout layout);
}
