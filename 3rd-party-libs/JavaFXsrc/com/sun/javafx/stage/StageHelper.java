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

package com.sun.javafx.stage;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Utility class class used for accessing certain implementation-specific
 * runtime functionality.
 */
public class StageHelper {

    private static StageAccessor stageAccessor;

    public static interface StageAccessor {
        public ObservableList<Stage> getStages();
        public void initSecurityDialog(Stage stage, boolean securityDialog);
    }

    /**
     * Returns a ObservableList containing {@code Stage}s created at this point.
     *
     * Note that application must use/reference javafx.stage.Stage class prior to
     * using this method (for example, by creating a Stage).
     *
     * @return ObservableList containing existing stages
     */
    public static ObservableList<Stage> getStages() {
        if (stageAccessor == null) {
            try {
                // Force stage static initialization, see http://java.sun.com/j2se/1.5.0/compatibility.html
                Class.forName(Stage.class.getName(), true, Stage.class.getClassLoader());
            } catch (ClassNotFoundException ex) {
                // Cannot happen
            }
        }
        return stageAccessor.getStages();
    }

    public static void initSecurityDialog(Stage stage, boolean securityDialog) {
        stageAccessor.initSecurityDialog(stage, securityDialog);
    }

    public static void setStageAccessor(StageAccessor a) {
        if (stageAccessor != null) {
            System.out.println("Warning: Stage accessor already set: " + stageAccessor);
            Thread.dumpStack();
        }
        stageAccessor = a;
    }
}
