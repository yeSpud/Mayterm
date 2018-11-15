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
package com.sun.glass.ui.gtk;

import com.sun.glass.ui.*;
import java.nio.IntBuffer;

final class GtkRobot extends Robot {

    @Override
    protected void _create() {
        // no-op
    }

    @Override
    protected void _destroy() {
        // no-op
    }

    @Override
    protected native void _keyPress(int code);

    @Override
    protected native void _keyRelease(int code);

    @Override
    protected native void _mouseMove(int x, int y);

    @Override
    protected native void _mousePress(int buttons);

    @Override
    protected native void _mouseRelease(int buttons);

    @Override
    protected native void _mouseWheel(int wheelAmt);

    @Override
    protected native int _getMouseX();

    @Override
    protected native int _getMouseY();

    @Override
    protected int _getPixelColor(int x, int y) {
        int[] result = new int[1];
        _getScreenCapture(x, y, 1, 1, result);
        return result[0];
    }

    native private void _getScreenCapture(int x, int y, int width, int height, int[] data);
    @Override protected Pixels _getScreenCapture(int x, int y, int width, int height, boolean isHiDPI) {
        int data[] = new int[width * height];
        _getScreenCapture(x, y, width, height, data);
        return Application.GetApplication().createPixels(width, height, IntBuffer.wrap(data));
    }
}
