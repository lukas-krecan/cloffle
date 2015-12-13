package net.javacrumbs.cloffle;

import clojure.lang.Keyword;
import net.javacrumbs.cloffle.ast.AstBuilder;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class E2ETest {
    private final AstBuilder astBuilder = new AstBuilder();
    private final Interpreter interpreter = new Interpreter();

    static {
        mikera.cljutils.Clojure.require("clojure.tools.analyzer.jvm");
    }

    @Test
    public void shouldAddThreeNumbers() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(+ 1 2 3.0))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(6d);
    }

    @Test
    public void shouldInterpretIf() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(if true 2.0 3.0))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(2.0);
    }

    @Test
    public void shouldInterpretNil() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(if nil 2.0 3.0))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(3.0);
    }

    @Test
    public void shouldInterpretLet() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(let [a 3.0] (+ a 5)))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(8.0);
    }

    @Test
    public void shouldInterpretLetNested() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(let [a 3.0] (let [a  4] (+ a 5))))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(9L);
    }

    @Test
    public void doShouldWork() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(do (let [a 3.0] (+ a 5)) (let [a 1] (+ a 5))))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(6L);
    }

    @Test
    public void fnShouldAlsoWork() {
        String s = "(clojure.tools.analyzer.jvm/analyze '((fn [a b c] (+ a b c)) 2 4 6))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(12L);
    }

    @Test
    public void fnWithLetShouldWork() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(let [a 5] ((fn [b] (+ a b)) 2)))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(7L);
    }

    @Test
    public void defFnShouldWork() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(do (defn myadd [x y z] (+ x y z)) (myadd 1 2 3)))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(6L);
    }

    @Test
     public void defShouldWork() {
         String s = "(clojure.tools.analyzer.jvm/analyze '(do (def myval true) (if myval 1 2)))";
         ClojureNode node = astBuilder.build(eval(s));
         assertThat(interpreter.interpret(node)).isEqualTo(1L);
     }

    @SuppressWarnings("unchecked")
    private Map<Keyword, Object> eval(String s) {
        return (Map<Keyword, Object>) mikera.cljutils.Clojure.eval(s);
    }
}