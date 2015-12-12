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
import net.javacrumbs.cloffle.nodes.BindingNode;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.LetNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class LetNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword LET = Keyword.find("let");
    private static final Keyword BINDINGS = Keyword.find("bindings");
    private static final Keyword BODY = Keyword.find("body");

    protected LetNodeBuilder(AstBuilder astBuilder) {
        super(LET, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        List<Object> bindings = (List<Object>) tree.get(BINDINGS);
        ClojureNode[] bindingNodes = bindings.stream().map(this::build).toArray(ClojureNode[]::new);
        ClojureNode body =  build(tree.get(BODY));
        return new LetNode(bindingNodes, body);
    }
}
