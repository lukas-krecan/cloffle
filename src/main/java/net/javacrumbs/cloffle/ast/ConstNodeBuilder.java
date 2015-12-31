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
import net.javacrumbs.cloffle.nodes.value.BooleanNode;
import net.javacrumbs.cloffle.nodes.value.DoubleNode;
import net.javacrumbs.cloffle.nodes.value.LongNode;
import net.javacrumbs.cloffle.nodes.value.NilNode;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.value.ObjectNode;

import java.util.Map;

public class ConstNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword CONST = keyword("const");

    protected ConstNodeBuilder(AstBuilder astBuilder) {
        super(CONST, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Class<?> tag = (Class<?>) tree.get(TAG);
        if (long.class.equals(tag) || Long.class.equals(tag)) {
            return new LongNode((Long) tree.get(VAL));
        }
        if (double.class.equals(tag)) {
            return new DoubleNode((Double) tree.get(VAL));
        }
        if (Boolean.class.equals(tag)) {
            return new BooleanNode((Boolean) tree.get(VAL));
        }
        if (String.class.equals(tag)) {
            return new ObjectNode(tree.get(VAL));
        }
        if (NIL.equals(tree.get(TYPE))) {
            return new NilNode();
        }
        throw new AstBuildException("Unsupported constant type " + tree);
    }
}
