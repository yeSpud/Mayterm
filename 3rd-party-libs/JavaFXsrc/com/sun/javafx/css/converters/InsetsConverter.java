/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.css.converters;

import com.sun.javafx.css.Size;
import com.sun.javafx.css.StyleConverterImpl;
import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

/**
 * Convert <size>{1,4} to Insets. The size values are interpreted as
 * top, right, bottom, left.
 * If only top is given, that value is used on all sides.
 * If there is only top and right, then bottom is set to top and left is set to right.
 * If top, right and bottom are given, then left is set to right.
 */
public final class InsetsConverter extends StyleConverterImpl<ParsedValue[], Insets> {

    // lazy, thread-safe instatiation
    private static class Holder {
        static final InsetsConverter INSTANCE = new InsetsConverter();
        static final SequenceConverter SEQUENCE_INSTANCE = new SequenceConverter();
    }

    public static StyleConverter<ParsedValue[], Insets> getInstance() {
        return Holder.INSTANCE;
    }

    private InsetsConverter() {
        super();
    }

    @Override
    public Insets convert(ParsedValue<ParsedValue[], Insets> value, Font font) {
        ParsedValue[] sides = value.getValue();
        double top = ((Size)sides[0].convert(font)).pixels(font);
        double right = (sides.length > 1) ? ((Size)sides[1].convert(font)).pixels(font) : top;
        double bottom = (sides.length > 2) ? ((Size)sides[2].convert(font)).pixels(font) : top;
        double left = (sides.length > 3) ? ((Size)sides[3].convert(font)).pixels(font) : right;
        return new Insets(top, right, bottom, left);
    }

    @Override
    public String toString() {
        return "InsetsConverter";
    }

    /**
     * Convert a <size>{1,4} [,<size>{1,4}]*  an Insets[]
     */
    public static final class SequenceConverter extends StyleConverterImpl<ParsedValue<ParsedValue[], Insets>[], Insets[]> {

        public static SequenceConverter getInstance() {
            return Holder.SEQUENCE_INSTANCE;
        }

        private SequenceConverter() {
            super();
        }

        @Override
        public Insets[] convert(ParsedValue<ParsedValue<ParsedValue[], Insets>[], Insets[]> value, Font font) {
            ParsedValue<ParsedValue[], Insets>[] layers = value.getValue();
            Insets[] insets = new Insets[layers.length];
            for (int layer = 0; layer < layers.length; layer++) {
                insets[layer] = InsetsConverter.getInstance().convert(layers[layer], font);
            }
            return insets;
        }

        @Override
        public String toString() {
            return "InsetsSequenceConverter";
        }
    }
}

