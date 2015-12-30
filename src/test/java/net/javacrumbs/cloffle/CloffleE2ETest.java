package net.javacrumbs.cloffle;

import clojure.lang.Keyword;
import net.javacrumbs.cloffle.ast.AstBuilder;
import net.javacrumbs.cloffle.nodes.ClojureNode;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CloffleE2ETest extends  AbstractE2ETest {
    private final AstBuilder astBuilder = new AstBuilder();
    private final Interpreter interpreter = new Interpreter();

    static {
        mikera.cljutils.Clojure.require("clojure.tools.analyzer.jvm");
    }

    protected void doRun(String expression, Object expected) {
        String s = "(clojure.tools.analyzer.jvm/analyze '" + expression + ")";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(expected);
    }

    @SuppressWarnings("unchecked")
    private Map<Keyword, Object> eval(String s) {
        return (Map<Keyword, Object>) mikera.cljutils.Clojure.eval(s);
    }
}