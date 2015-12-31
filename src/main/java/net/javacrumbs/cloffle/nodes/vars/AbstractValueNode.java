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
package net.javacrumbs.cloffle.nodes.vars;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import net.javacrumbs.cloffle.nodes.ClojureNode;

import static com.oracle.truffle.api.frame.FrameInstance.FrameAccess.READ_ONLY;

abstract class AbstractValueNode extends ClojureNode {
    private final FrameSlot frameSlot;

    protected AbstractValueNode(FrameSlot frameSlot) {
        this.frameSlot = frameSlot;
    }

    protected Object getValue(VirtualFrame virtualFrame) {
        if (frameSlot != null) {
            Object localValue = virtualFrame.getValue(frameSlot);
            if (localValue != null) {
                return localValue;
            } else {
                // Slow path arg ???
                return Truffle.getRuntime().iterateFrames(fi -> fi.getFrame(READ_ONLY, false).getValue(frameSlot));
            }
        } else {
            throw new IllegalStateException("Variable not found " + frameSlot);
        }
    }

    protected FrameSlot getFrameSlot() {
        return frameSlot;
    }
}
