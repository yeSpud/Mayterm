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
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * Used as a wrapper to protect an {@code Event} from being redirected by
 * {@code EventRedirector}. The redirector only unwraps such event and sends
 * it to the rest of the event chain.
 */
public class DirectEvent extends Event {
    private static final long serialVersionUID = 20121107L;

    public static final EventType<DirectEvent> DIRECT =
            new EventType<DirectEvent>(Event.ANY, "DIRECT");

    private final Event originalEvent;

    public DirectEvent(final Event originalEvent) {
        this(originalEvent, null, null);
    }

    public DirectEvent(final Event originalEvent,
                       final Object source,
                       final EventTarget target) {
        super(source, target, DIRECT);
        this.originalEvent = originalEvent;
    }

    public Event getOriginalEvent() {
        return originalEvent;
    }
}
