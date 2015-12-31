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
import clojure.lang.Symbol;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.binding.ArgInitNode;
import net.javacrumbs.cloffle.nodes.binding.BindingNodeGen;

import java.util.Map;

public class BindingNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword BINDING = keyword("binding");
    public static final Keyword NAME = keyword("name");
    private static final Keyword INIT = keyword("init");
    private static final Keyword LOCAL = keyword("local");
    private static final Keyword ARG_ID = keyword("arg-id");
    public static final Keyword ARG = Keyword.find("arg");

    protected BindingNodeBuilder(AstBuilder astBuilder) {
        super(BINDING, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Symbol name = (Symbol) tree.get(NAME);
        ClojureNode init = buildOptional(tree.get(INIT));
        Keyword local = (Keyword) tree.get(LOCAL);
        Long argId = (Long) tree.get(ARG_ID);
        if (init == null) {
            if (ARG.equals(local)) {
                init = new ArgInitNode(argId);
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return BindingNodeGen.create(name, init, getFrameSlot(name));
    }
}
