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

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public class LocalNode extends AbstractValueNode {


    public LocalNode(FrameSlot frameSlot) {
        super(frameSlot);
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return getValue(virtualFrame);
    }

    @Override
    public long executeLong(VirtualFrame virtualFrame) throws UnexpectedResultException {
        try {
            return virtualFrame.getLong(getFrameSlot());
        } catch (FrameSlotTypeException ignore) {
            throw new UnexpectedResultException(executeGeneric(virtualFrame));
        }
    }

    @Override
    public double executeDouble(VirtualFrame virtualFrame) throws UnexpectedResultException {
        try {
            return virtualFrame.getDouble(getFrameSlot());
        } catch (FrameSlotTypeException ignore) {
            throw new UnexpectedResultException(executeGeneric(virtualFrame));
        }
    }

    @Override
    public boolean executeBoolean(VirtualFrame virtualFrame) throws UnexpectedResultException {
        try {
            return virtualFrame.getBoolean(getFrameSlot());
        } catch (FrameSlotTypeException ignore) {
            throw new UnexpectedResultException(executeGeneric(virtualFrame));
        }
    }
}
