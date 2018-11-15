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

package javafx.css;

import com.sun.javafx.css.PseudoClassState;

/**
 * PseudoClass represents one unique pseudo-class state. Introducing a
 * pseudo-class into a JavaFX class only requires that the method
 * {@link javafx.scene.Node#pseudoClassStateChanged(javafx.css.PseudoClass, boolean)}
 * be called when the pseudo-class state changes. Typically, the
 * {@code pseudoClassStateChanged} method is called from the
 * {@code protected void invalidated()} method of one of the property base
 * classes in the {@code javafx.beans.property} package.
 * <p>
 * Note that if a node has a default pseudo-class state, a horizontal orientation
 * for example, {@code pseudoClassStateChanged} should be called from the
 * constructor to set the initial state.
 * <p>
 * The following example would allow &quot;xyzzy&quot; to be used as a
 *  pseudo-class in a CSS selector.
 * <code>
 * <pre>
 *  public boolean isMagic() {
 *       return magic.get();
 *   }
 *
 *   public BooleanProperty magicProperty() {
 *       return magic;
 *   }
 *
 *   public BooleanProperty magic =
 *       new BooleanPropertyBase(false) {
 *
 *       {@literal @}Override protected void invalidated() {
 *           pseudoClassStateChanged(MAGIC_PSEUDO_CLASS. get());
 *       }
 *
 *       {@literal @}Override public Object getBean() {
 *           return MyControl.this;
 *       }
 *
 *       {@literal @}Override public String getName() {
 *           return "magic";
 *       }
 *   }
 *
 *   private static final PseudoClass
 *       MAGIC_PSEUDO_CLASS = PseudoClass.getPseudoClass("xyzzy");
 * </pre>
 * </code>
 * @since JavaFX 8.0
 */
public abstract class PseudoClass {

    /**
     * There is only one PseudoClass instance for a given pseudoClass.
     * @return The PseudoClass for the given pseudoClass. Will not return null.
     * @throws IllegalArgumentException if pseudoClass parameter is null or an empty String
     */
    public static PseudoClass getPseudoClass(String pseudoClass) {

        return PseudoClassState.getPseudoClass(pseudoClass);

    }

    /** @return the pseudo-class state */
    abstract public String getPseudoClassName();

}
