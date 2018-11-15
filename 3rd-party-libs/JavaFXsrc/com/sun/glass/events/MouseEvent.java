/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.glass.events;

public class MouseEvent {
    final static public int BUTTON_NONE     = 211;
    final static public int BUTTON_LEFT     = 212;
    final static public int BUTTON_RIGHT    = 213;
    final static public int BUTTON_OTHER    = 214;

    final static public int DOWN            = 221;
    final static public int UP              = 222;
    final static public int DRAG            = 223;
    final static public int MOVE            = 224;
    final static public int ENTER           = 225;
    final static public int EXIT            = 226;
    final static public int CLICK           = 227; // synthetic

    /**
     * Artificial WHEEL event type.
     * This kind of mouse event is NEVER sent to an app.
     * The app must listen to Scroll events instead.
     * This identifier is required for internal purposes.
     */
    final static public int WHEEL           = 228;
}
