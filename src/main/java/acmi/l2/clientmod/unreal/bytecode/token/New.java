/*
 * Copyright (c) 2016 acmi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package acmi.l2.clientmod.unreal.bytecode.token;

import acmi.l2.clientmod.unreal.UnrealPackageContext;

public class New extends Token {
    public static final int OPCODE = 0x11;

    public Token outer;
    public Token name;
    public Token flags;
    public Token clazz;

    public New(Token outer, Token name, Token flags, Token clazz) {
        this.outer = outer;
        this.name = name;
        this.flags = flags;
        this.clazz = clazz;
    }

    public New() {
    }

    @Override
    protected int getOpcode() {
        return OPCODE;
    }

    @Override
    public String toString() {
        return "New("
                + outer
                + ", " + name
                + ", " + flags
                + ", " + clazz
                + ')';
    }

    @Override
    public String toString(UnrealPackageContext context) {
        return "New(" + outer.toString(context) + "," + name.toString(context) + "," + flags.toString(context) + ") " + clazz.toString(context);
    }
}
