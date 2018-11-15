/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.event;

import javafx.event.Event;

/**
 * An {@code EventDispatcher} which represents a chain of event dispatchers, but
 * can still be set or replaced as a single entity.
 */
public abstract class CompositeEventDispatcher extends BasicEventDispatcher {
    public abstract BasicEventDispatcher getFirstDispatcher();

    public abstract BasicEventDispatcher getLastDispatcher();

    @Override
    public final Event dispatchCapturingEvent(Event event) {
        BasicEventDispatcher childDispatcher = getFirstDispatcher();
        while (childDispatcher != null) {
            event = childDispatcher.dispatchCapturingEvent(event);
            if (event.isConsumed()) {
                break;
            }

            childDispatcher = childDispatcher.getNextDispatcher();
        }

        return event;
    }

    @Override
    public final Event dispatchBubblingEvent(Event event) {
        // need to dispatch in reversed direction
        BasicEventDispatcher childDispatcher = getLastDispatcher();
        while (childDispatcher != null) {
            event = childDispatcher.dispatchBubblingEvent(event);
            if (event.isConsumed()) {
                break;
            }

            childDispatcher = childDispatcher.getPreviousDispatcher();
        }

        return event;
    }
}
