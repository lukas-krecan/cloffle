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
import net.javacrumbs.cloffle.ClojureBooleanNode;
import net.javacrumbs.cloffle.ClojureDoubleNode;
import net.javacrumbs.cloffle.ClojureLongNode;
import net.javacrumbs.cloffle.ClojureNilNode;
import net.javacrumbs.cloffle.ClojureNode;

import java.util.Map;

public class ConstNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword CONST = Keyword.find("const");

    protected ConstNodeBuilder(AstBuilder astBuilder) {
        super(CONST, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Class<?> tag = (Class<?>) tree.get(TAG);
        if (long.class.equals(tag)) {
            return new ClojureLongNode((Long) tree.get(VAL));
        }
        if (double.class.equals(tag)) {
            return new ClojureDoubleNode((Double) tree.get(VAL));
        }
        if (Boolean.class.equals(tag)) {
            return new ClojureBooleanNode((Boolean) tree.get(VAL));
        }
        if (NIL.equals(tree.get(TYPE))) {
            return new ClojureNilNode();
        }
        throw new AstBuildException("Unsupported constant type " + tree);
    }
}
