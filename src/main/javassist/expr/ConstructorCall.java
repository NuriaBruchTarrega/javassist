/*
 * Javassist, a Java-bytecode translator toolkit.
 * Copyright (C) 1999- Shigeru Chiba. All Rights Reserved.
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License.  Alternatively, the contents of this file may be used under
 * the terms of the GNU Lesser General Public License Version 2.1 or later,
 * or the Apache License Version 2.0.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 */

package javassist.expr;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

import java.util.Objects;

/**
 * Constructor call such as <code>this()</code> and <code>super()</code>
 * within a constructor body.
 *
 * @see NewExpr
 */
public class ConstructorCall extends MethodCall {
    /**
     * Undocumented constructor.  Do not use; internal-use only.
     */
    protected ConstructorCall(int pos, CodeIterator i, CtClass decl, MethodInfo m) {
        super(pos, i, decl, m);
    }

    /**
     * Returns <code>"super"</code> or "<code>"this"</code>.
     */
    @Override
    public String getMethodName() {
        return isSuper() ? "super" : "this";
    }

    /**
     * Always throws a <code>NotFoundException</code>.
     *
     * @see #getConstructor()
     */
    @Override
    public CtMethod getMethod() throws NotFoundException {
        throw new NotFoundException("this is a constructor call.  Call getConstructor().");
    }

    /**
     * Returns the called constructor.
     */
    public CtConstructor getConstructor() throws NotFoundException {
        return getCtClass().getConstructor(getSignature());
    }

    /**
     * Returns true if the called constructor is not <code>this()</code>
     * but <code>super()</code> (a constructor declared in the super class).
     */
    @Override
    public boolean isSuper() {
        return super.isSuper();
    }

    @Override
    public int hashCode() {
        return this.getLineNumber() +
                Objects.hash(this.where()) +
                Objects.hash(this.getClassName()) +
                Objects.hash(this.getMethodName()) +
                Objects.hash(this.getSignature());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ConstructorCall constructorCall = (ConstructorCall) obj;

        boolean whereEquals = (this.where() == null && constructorCall.where() == null)
                || (this.where() != null && this.where().equals(constructorCall.where()));
        boolean classNameEquals = (this.getClassName() == null && constructorCall.getClassName() == null)
                || (this.getClassName() != null && this.getClassName().equals(constructorCall.getClassName()));
        boolean signatureEquals = (this.getSignature() == null && constructorCall.getSignature() == null)
                || (this.getSignature() != null && this.getSignature().equals(constructorCall.getSignature()));
        boolean methodNameEquals = (this.getMethodName() == null && constructorCall.getMethodName() == null)
                || (this.getMethodName() != null && this.getMethodName().equals(constructorCall.getMethodName()));

        return this.getLineNumber() == constructorCall.getLineNumber() &&
                whereEquals && classNameEquals && methodNameEquals && signatureEquals;
    }
}
