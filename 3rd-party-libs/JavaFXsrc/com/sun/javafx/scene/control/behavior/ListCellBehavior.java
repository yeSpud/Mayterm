/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.control.FocusModel;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

import java.util.Collections;

public class ListCellBehavior<T> extends CellBehaviorBase<ListCell<T>> {

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public ListCellBehavior(ListCell<T> control) {
        super(control, Collections.emptyList());
    }



    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    @Override protected MultipleSelectionModel<T> getSelectionModel() {
        return getCellContainer().getSelectionModel();
    }

    @Override protected FocusModel<T> getFocusModel() {
        return getCellContainer().getFocusModel();
    }

    @Override protected ListView<T> getCellContainer() {
        return getControl().getListView();
    }

    @Override protected void edit(ListCell<T> cell) {
        int index = cell == null ? -1 : cell.getIndex();
        getCellContainer().edit(index);
    }
}
