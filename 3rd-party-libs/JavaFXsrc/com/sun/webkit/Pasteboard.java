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

package com.sun.webkit;

import com.sun.webkit.graphics.WCImageFrame;

public interface Pasteboard {
    public String getPlainText();
    public String getHtml();
    public void writePlainText(String text);
    public void writeSelection(boolean canSmartCopyOrDelete, String text, String html);
    public void writeImage(WCImageFrame img);
    public void writeUrl(String url, String markup);
}
