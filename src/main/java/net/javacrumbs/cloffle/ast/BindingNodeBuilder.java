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

import java.util.Map;

public class BindingNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword BINDING = Keyword.find("binding");
    public static final Keyword NAME = Keyword.find("name");
    private static final Keyword INIT = Keyword.find("init");

    protected BindingNodeBuilder(AstBuilder astBuilder) {
        super(BINDING, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        String name = (String) tree.get(NAME);
        ClojureNode init = build(tree.get(INIT));
        return new BindingNode(name, init);
    }
}
