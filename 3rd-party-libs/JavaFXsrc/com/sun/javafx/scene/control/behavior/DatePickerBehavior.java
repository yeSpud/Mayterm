/*
 * Copyright (c) 2013, 2015, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.F4;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyEvent.KEY_RELEASED;


public class DatePickerBehavior extends ComboBoxBaseBehavior<LocalDate> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     *
     */
    public DatePickerBehavior(final DatePicker datePicker) {
        super(datePicker, DATE_PICKER_BINDINGS);
    }

    /***************************************************************************
     *                                                                         *
     * Key event handling                                                      *
     *                                                                         *
     **************************************************************************/

    protected static final List<KeyBinding> DATE_PICKER_BINDINGS = new ArrayList<KeyBinding>();
    static {
        DATE_PICKER_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
    }

    @Override public void onAutoHide() {
        // when we click on some non-interactive part of the
        // calendar - we do not want to hide.
        DatePicker datePicker = (DatePicker)getControl();
        DatePickerSkin cpSkin = (DatePickerSkin)datePicker.getSkin();
        cpSkin.syncWithAutoUpdate();
        // if the DatePicker is no longer showing, then invoke the super method
        // to keep its show/hide state in sync.
        if (!datePicker.isShowing()) super.onAutoHide();
    }

}
