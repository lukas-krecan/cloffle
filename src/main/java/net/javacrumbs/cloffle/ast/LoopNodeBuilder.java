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
import net.javacrumbs.cloffle.nodes.binding.BindingNode;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.LoopNode;

import java.util.Map;

public class LoopNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword LOOP = keyword("loop");
    private static final Keyword BINDINGS = keyword("bindings");
    private static final Keyword BODY = keyword("body");

    protected LoopNodeBuilder(AstBuilder astBuilder) {
        super(LOOP, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        BindingNode[] bindings = convertToNodes(tree.get(BINDINGS), BindingNode[]::new);
        ClojureNode body =  build(tree.get(BODY));
        return new LoopNode(bindings, body);
    }
}
