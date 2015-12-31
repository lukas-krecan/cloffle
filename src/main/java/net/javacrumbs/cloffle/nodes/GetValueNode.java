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
package net.javacrumbs.cloffle.nodes;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

import static com.oracle.truffle.api.frame.FrameInstance.FrameAccess.READ_ONLY;

public abstract class GetValueNode extends ClojureNode {
    private final Object key;

    public GetValueNode(Object key) {
        this.key = key;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        FrameSlot frameSlot = getFrameSlot(virtualFrame);
        if (frameSlot != null) {
            Object localValue = virtualFrame.getValue(frameSlot);
            if (localValue != null) {
                return localValue;
            } else {
                // Slow path arg ???
                return Truffle.getRuntime().iterateFrames(i -> i.getFrame(READ_ONLY, false).getValue(frameSlot));
            }
        } else {
            throw new IllegalStateException("Variable not found " + getKey());
        }
    }

    @Override
    public long executeLong(VirtualFrame virtualFrame) throws UnexpectedResultException {
        FrameSlot frameSlot = getFrameSlot(virtualFrame);
        if (frameSlot != null) {
            try {
                return virtualFrame.getLong(frameSlot);
            } catch (FrameSlotTypeException ignore) {
            }
        }
        throw new UnexpectedResultException(executeGeneric(virtualFrame));
    }

    @Override
    public double executeDouble(VirtualFrame virtualFrame) throws UnexpectedResultException {
        FrameSlot frameSlot = getFrameSlot(virtualFrame);
        if (frameSlot != null) {
            try {
                return virtualFrame.getDouble(frameSlot);
            } catch (FrameSlotTypeException ignore) {
            }
        }
        throw new UnexpectedResultException(executeGeneric(virtualFrame));
    }

    @Override
    public boolean executeBoolean(VirtualFrame virtualFrame) throws UnexpectedResultException {
        FrameSlot frameSlot = getFrameSlot(virtualFrame);
        if (frameSlot != null) {
            try {
                return virtualFrame.getBoolean(frameSlot);
            } catch (FrameSlotTypeException ignore) {
            }
        }
        throw new UnexpectedResultException(executeGeneric(virtualFrame));
    }


    private FrameSlot getFrameSlot(VirtualFrame virtualFrame) {
        return virtualFrame.getFrameDescriptor().findFrameSlot(key);
    }

    public Object getKey() {
        return key;
    }
}
