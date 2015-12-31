/**
 * Copyright 2009-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.cloffle.nodes.binding;

import clojure.lang.Symbol;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import net.javacrumbs.cloffle.nodes.ClojureNode;

@NodeChild("init")
@NodeField(name = "slot", type = FrameSlot.class)
public abstract class BindingNode extends ClojureNode {
    private final Symbol name;

    public BindingNode(Symbol name) {
        this.name = name;
    }

    protected abstract FrameSlot getSlot();

    @Specialization(guards = "isLongKind(frame)")
    protected long writeLong(VirtualFrame frame, long value) {
        frame.setLong(getSlot(), value);
        return value;
    }

    @Specialization(guards = "isDoubleKind(frame)")
    protected long writeDouble(VirtualFrame frame, long value) {
        frame.setDouble(getSlot(), value);
        return value;
    }

    @Specialization(guards = "isBooleanKind(frame)")
    protected boolean writeBoolean(VirtualFrame frame, boolean value) {
        frame.setBoolean(getSlot(), value);
        return value;
    }

    @Specialization(contains = {"writeLong", "writeDouble", "writeBoolean"})
    protected Object write(VirtualFrame frame, Object value) {
        if (getSlot().getKind() != FrameSlotKind.Object) {
               /*
                * The local variable has still a primitive type, we need to change it to Object. Since
                * the variable type is important when the compiler optimizes a method, we also discard
                * compiled code.
                */
            CompilerDirectives.transferToInterpreterAndInvalidate();
            getSlot().setKind(FrameSlotKind.Object);
        }
        frame.setObject(getSlot(), value);
        return value;
    }


    @SuppressWarnings("unused")
    protected boolean isLongKind(VirtualFrame frame) {
        return isKind(FrameSlotKind.Long);
    }


    @SuppressWarnings("unused")
    protected boolean isDoubleKind(VirtualFrame frame) {
        return isKind(FrameSlotKind.Double);
    }

    @SuppressWarnings("unused")
    protected boolean isBooleanKind(VirtualFrame frame) {
        return isKind(FrameSlotKind.Boolean);
    }

    private boolean isKind(FrameSlotKind kind) {
        if (getSlot().getKind() == kind) {
            return true;
        } else if (getSlot().getKind() == FrameSlotKind.Illegal) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            getSlot().setKind(kind);
            return true;
        } else {
            return false;
        }
    }

    public void rebind(ClojureNode expr, VirtualFrame virtualFrame) {
        // FIXME: bloody slow, should inject the node instead of init
        write(virtualFrame, expr.executeGeneric(virtualFrame));
    }
}
