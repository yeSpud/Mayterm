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
import javafx.scene.text.Font;

/* Convert a Size to Number */
public final class SizeConverter extends StyleConverterImpl<ParsedValue<?, Size>, Number> {

    // lazy, thread-safe instatiation
    private static class Holder {
        static final SizeConverter INSTANCE = new SizeConverter();
        static final SequenceConverter SEQUENCE_INSTANCE = new SequenceConverter();
    }

    public static StyleConverter<ParsedValue<?, Size>, Number> getInstance() {
        return Holder.INSTANCE;
    }

    private SizeConverter() {
        super();
    }

    @Override
    public Number convert(ParsedValue<ParsedValue<?, Size>, Number> value, Font font) {
        ParsedValue<?, Size> size = value.getValue();
        return size.convert(font).pixels(font);
    }

    @Override
    public String toString() {
        return "SizeConverter";
    }

    /*
     * Convert [<size>]+ to an array of Number[].
     */
    public static final class SequenceConverter extends StyleConverterImpl<ParsedValue[], Number[]> {

        public static SequenceConverter getInstance() {
            return Holder.SEQUENCE_INSTANCE;
        }

        private SequenceConverter() {
            super();
        }

        @Override
        public Number[] convert(ParsedValue<ParsedValue[], Number[]> value, Font font) {
            ParsedValue[] sizes = value.getValue();
            Number[] doubles = new Number[sizes.length];
            for (int i = 0; i < sizes.length; i++) {
                doubles[i] = ((Size)sizes[i].convert(font)).pixels(font);
            }
            return doubles;
        }

        @Override
        public String toString() {
            return "Size.SequenceConverter";
        }
    }

}
