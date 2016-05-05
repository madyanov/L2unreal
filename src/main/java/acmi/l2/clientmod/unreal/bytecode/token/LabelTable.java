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

import acmi.l2.clientmod.io.ObjectInput;
import acmi.l2.clientmod.io.ObjectOutput;
import acmi.l2.clientmod.io.annotation.Compact;
import acmi.l2.clientmod.io.annotation.ReadMethod;
import acmi.l2.clientmod.io.annotation.UShort;
import acmi.l2.clientmod.io.annotation.WriteMethod;
import acmi.l2.clientmod.unreal.UnrealPackageContext;
import acmi.l2.clientmod.unreal.annotation.NameRef;
import acmi.l2.clientmod.unreal.annotation.Offset;
import acmi.l2.clientmod.unreal.bytecode.BytecodeContext;
import acmi.l2.clientmod.unreal.bytecode.TokenSerializerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LabelTable extends Token {
    public static final int OPCODE = 0x0c;

    public Label[] labels;

    public LabelTable(Label... labels) {
        this.labels = labels;
    }

    public LabelTable() {
    }

    @Override
    protected int getOpcode() {
        return OPCODE;
    }

    @ReadMethod
    public void readFrom(ObjectInput<BytecodeContext> input) throws IOException {
        List<Label> labels = new ArrayList<>();
        Label tmp;
        do {
            tmp = input.readObject(Label.class);
            if (tmp.nameRef == TokenSerializerFactory.getNoneInd(input.getContext()))
                break;
            labels.add(tmp);
        } while (true);
        this.labels = labels.toArray(new Label[labels.size()]);
    }

    @WriteMethod
    public void writeLabelTable(ObjectOutput<BytecodeContext> output) throws UncheckedIOException {
        if (labels != null)
            for (Label label : labels) {
                output.write(label);
            }
        output.write(new Label(TokenSerializerFactory.getNoneInd(output.getContext()), 0xffff, 0));
    }

    private static final Sizer sizer =
            (token, context) -> 1 +
                    Arrays.stream(((LabelTable) token).labels)
                            .mapToInt(l -> 8).sum() +
                    8;

    @Override
    protected Sizer getSizer() {
        return sizer;
    }

    @Override
    public String toString() {
        return "LabelTable("
                + (labels == null || labels.length == 0 ? "" : Arrays.stream(labels).map(Objects::toString).collect(Collectors.joining(", ")))
                + ')';
    }

    public static class Label {
        @Compact
        @NameRef
        public int nameRef;
        @UShort
        @Offset
        public int offset;
        @UShort
        public int unk;

        public Label(int nameRef, int offset, int unk) {
            this.nameRef = nameRef;
            this.offset = offset;
            this.unk = unk;
        }

        public Label() {
        }

        @Override
        public String toString() {
            return "Label("
                    + nameRef
                    + ", " + String.format("0x%04x", offset)
                    + ')';
        }
    }

    @Override
    public String toString(UnrealPackageContext context) {
        return "";
    }
}
