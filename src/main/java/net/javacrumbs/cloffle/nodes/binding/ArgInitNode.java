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

import com.oracle.truffle.api.frame.VirtualFrame;
import net.javacrumbs.cloffle.nodes.ClojureNode;

/**
 * Initializes binding from argument value.
 */
public class ArgInitNode extends ClojureNode {
    private final Long argId;

    public ArgInitNode(Long argId) {
        this.argId = argId;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        return virtualFrame.getArguments()[argId.intValue()];
    }
}