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
package net.javacrumbs.cloffle.ast;

import clojure.lang.Keyword;
import com.oracle.truffle.api.Truffle;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.ClojureRootNode;
import net.javacrumbs.cloffle.nodes.invoke.InvokeNode;

import java.util.Map;

public class InvokeNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword INVOKE = keyword("invoke");
    private static final Keyword FN = keyword("fn");
    private static final Keyword ARGS = keyword("args");

    protected InvokeNodeBuilder(AstBuilder astBuilder) {
        super(INVOKE, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        ClojureNode fn = build(tree.get(FN));
        ClojureNode[] args = convertToNodes(tree.get(ARGS), ClojureNode[]::new);
        return new InvokeNode(Truffle.getRuntime().createCallTarget(ClojureRootNode.create(fn, getFrameDescriptor())), args);
    }
}
