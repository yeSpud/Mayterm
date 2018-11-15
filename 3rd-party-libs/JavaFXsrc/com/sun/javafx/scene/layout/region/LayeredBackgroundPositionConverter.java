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

package com.sun.javafx.scene.layout.region;

import com.sun.javafx.css.StyleConverterImpl;
import javafx.css.ParsedValue;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.text.Font;

/**
 * background-position: <bg-position>
 * where <bg-position> = [
 *   [ [ <size> | left | center | right ] [ <size> | top | center | bottom ]? ]
 *   | [ [ center | [ left | right ] <size>? ] || [ center | [ top | bottom ] <size>? ]
 * ]
 * @see <a href="http://www.w3.org/TR/css3-background/#the-background-position">background-position</a>
 */
public final class LayeredBackgroundPositionConverter extends StyleConverterImpl<ParsedValue<ParsedValue[], BackgroundPosition>[], BackgroundPosition[]> {
    private static final LayeredBackgroundPositionConverter LAYERED_BACKGROUND_POSITION_CONVERTER =
            new LayeredBackgroundPositionConverter();

    public static LayeredBackgroundPositionConverter getInstance() {
        return LAYERED_BACKGROUND_POSITION_CONVERTER;
    }

    private LayeredBackgroundPositionConverter() {
        super();
    }

    @Override
    public BackgroundPosition[] convert(ParsedValue<ParsedValue<ParsedValue[], BackgroundPosition>[], BackgroundPosition[]> value, Font font) {
        ParsedValue<ParsedValue[], BackgroundPosition>[] layers = value.getValue();
        BackgroundPosition[] positions = new BackgroundPosition[layers.length];
        for (int l = 0; l < layers.length; l++) {
            positions[l] = layers[l].convert(font);
        }
        return positions;
    }

    @Override
    public String toString() {
        return "LayeredBackgroundPositionConverter";
    }
}
