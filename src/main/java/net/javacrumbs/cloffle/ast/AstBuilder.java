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
import net.javacrumbs.cloffle.ClojureNode;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class AstBuilder {

    private final List<AbstractNodeBuilder> builders = asList(
        new ConstNodeBuilder(this),
        new IfNodeBuilder(this),
        new StaticCodeNodeBuilder(this)
    );


    public ClojureNode build(Object node) {
        Map<Keyword, Object> tree = (Map<Keyword, Object>) node;
        return builders.stream()
            .filter(b -> b.supports(tree))
            .findFirst().map(b -> b.buildNode(tree))
            .orElseThrow(() -> new AstBuildException("Unsupported operation " + tree));
    }

}
