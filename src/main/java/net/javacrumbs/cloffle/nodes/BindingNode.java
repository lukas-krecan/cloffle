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

import clojure.lang.Keyword;
import clojure.lang.Symbol;
import com.oracle.truffle.api.frame.VirtualFrame;

// FIXME: Is it really a node?
public class BindingNode extends ClojureNode {
    public static final Keyword ARG = Keyword.find("arg");
    private final Symbol name;
    private final Keyword local;
    private final Long argId;

    @Child
    private ClojureNode init;

    public BindingNode(Symbol name, ClojureNode init, Keyword local, Long argId) {
        this.name = name;
        this.init = init;
        this.local = local;
        this.argId = argId;
    }

    private Object getValue(VirtualFrame virtualFrame) {
        if (init != null) {
            return init.execute(virtualFrame);
        } else if (ARG.equals(local)) {
            return virtualFrame.getArguments()[argId.intValue()];
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        Object value = getValue(virtualFrame);
        if (value != null) {
            doSetValue(virtualFrame, value);
        } else {
            throw new NullPointerException("Value not found");
        }
        // strange
        return null;
    }

    private void doSetValue(VirtualFrame virtualFrame, Object value) {
        virtualFrame.setObject(virtualFrame.getFrameDescriptor().addFrameSlot(name), value);
    }
}
