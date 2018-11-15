/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.tk;

import java.awt.AWTPermission;
import java.security.Permission;

public class PermissionHelper {

    // TODO: use refelction in the future to avoid hard dependency on AWT
    private static final Permission accessClipboardPermission = new AWTPermission("accessClipboard");

    public static Permission getAccessClipboardPermission() {
        return accessClipboardPermission;
    }

    // Static helper class; do not construct an instance
    private PermissionHelper() {}
}
