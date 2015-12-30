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
        doRun("(+ 1 2 3.0))", 6d);
    }

    @Test
    public void shouldInterpretIf() {
        doRun("(if true 2.0 3.0))", 2.0);
    }

    @Test
    public void shouldInterpretNil() {
        doRun("(if nil 2.0 3.0))", 3.0);
    }

    @Test
    public void shouldInterpretLet() {
        doRun("(let [a 3.0] (+ a 5)))", 8.0);
    }

    @Test
    public void letShouldHaveContext() {
        doRun("(let [a 3.0] (+ (let [a 2] a) a))", 5.0);
    }

    @Test
    public void shouldInterpretLetNested() {
        doRun("(let [a 3.0] (let [a  4] (+ a 5))))", 9L);
    }

    @Test
    public void doShouldWork() {
        doRun("(do (let [a 3.0] (+ a 5)) (let [a 1] (+ a 5))))", 6L);
    }

    @Test
    public void fnShouldAlsoWork() {
        doRun("((fn [a b c] (+ a b c)) 2 4 6))", 12L);
    }

    @Test
    public void fnWithLetShouldWork() {
        doRun("(let [a 5] ((fn [b] (+ a b)) 2)))", 7L);
    }

    @Test
    public void defFnShouldWork() {
        doRun("(do (defn myadd [x y z] (+ x y z)) (myadd 1 2 3)))", 6L);
    }

    @Test
    public void defShouldWork() {
        doRun("(do (def myval true) (if myval 1 2)))", 1L);
    }

    @Test
    public void fibShouldWork() {
        doRun("(do (defn fib [n] (if (< n 3) 1 (+ (fib (- n 1)) (fib (- n 2))))) (fib 10))", 55L);
    }

    @Test
    public void loopShouldWork() {
        doRun("(loop [sum 0 cnt 10]\n" +
            "    ; If count reaches 0 then exit the loop and return sum\n" +
            "    (if (= cnt 0)\n" +
            "    sum\n" +
            "    ; Otherwise add count to sum, decrease count and \n" +
            "    ; use recur to feed the new values back into the loop\n" +
            "    (recur (+ cnt sum) (dec cnt))))", 55L);
    }

    @Test
    public void doTimesShouldWork() {
        doRun("(dotimes [n 5] (println \"n is\" n))", 55L);
    }

    private void doRun(String expression, Object expected) {
        String s = "(clojure.tools.analyzer.jvm/analyze '" + expression + ")";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(expected);
    }

    @SuppressWarnings("unchecked")
    private Map<Keyword, Object> eval(String s) {
        return (Map<Keyword, Object>) mikera.cljutils.Clojure.eval(s);
    }
}