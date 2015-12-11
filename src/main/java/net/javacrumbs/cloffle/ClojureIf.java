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
package net.javacrumbs.cloffle;

import com.oracle.truffle.api.frame.VirtualFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClojureIf extends ClojureNode {
    @Child
    private ClojureNode condition;
    @Child
    private ClojureNode thenNode;
    @Child
    private ClojureNode elseNode;

    public ClojureIf(ClojureNode condition, ClojureNode thenNode, ClojureNode elseNode) {
        this.condition = condition;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }


    @Override
    public Object execute(VirtualFrame virtualFrame) {
        Object value = condition.execute(virtualFrame);
        //FIMXE: nil
        if (!Boolean.FALSE.equals(value) && value != null) {
            return thenNode.execute(virtualFrame);
        } else {
            return elseNode.execute(virtualFrame);
        }
    }
}
