/*
 * Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.scene.control.behavior;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.*;


import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ColorPicker;
import com.sun.javafx.scene.control.skin.ColorPickerSkin;

import javafx.scene.paint.Color;

public class ColorPickerBehavior extends ComboBoxBaseBehavior<Color> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     *
     */
    public ColorPickerBehavior(final ColorPicker colorPicker) {
        super(colorPicker, COLOR_PICKER_BINDINGS);
    }

    /***************************************************************************
     *                                                                         *
     * Key event handling                                                      *
     *                                                                         *
     **************************************************************************/

    /**
     * Opens the Color Picker Palette.
     */
    protected static final String OPEN_ACTION = "Open";

    /**
     * Closes the Color Picker Palette.
     */
    protected static final String CLOSE_ACTION = "Close";


    protected static final List<KeyBinding> COLOR_PICKER_BINDINGS = new ArrayList<KeyBinding>();
    static {
//        COLOR_PICKER_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
        COLOR_PICKER_BINDINGS.add(new KeyBinding(ESCAPE, KEY_PRESSED, CLOSE_ACTION));
        COLOR_PICKER_BINDINGS.add(new KeyBinding(SPACE, KEY_PRESSED, OPEN_ACTION));
        COLOR_PICKER_BINDINGS.add(new KeyBinding(ENTER, KEY_PRESSED, OPEN_ACTION));

    }

    @Override protected void callAction(String name) {
        if (OPEN_ACTION.equals(name)) {
            show();
        } else if(CLOSE_ACTION.equals(name)) {
            hide();
        }
        else super.callAction(name);
    }

     /**************************************************************************
     *                                                                        *
     * Mouse Events                                                           *
     *                                                                        *
     *************************************************************************/

    @Override public void onAutoHide() {
        // when we click on some non  interactive part of the
        // Color Palette - we do not want to hide.
        ColorPicker colorPicker = (ColorPicker)getControl();
        ColorPickerSkin cpSkin = (ColorPickerSkin)colorPicker.getSkin();
        cpSkin.syncWithAutoUpdate();
        // if the ColorPicker is no longer showing, then invoke the super method
        // to keep its show/hide state in sync.
        if (!colorPicker.isShowing()) super.onAutoHide();
    }

}
