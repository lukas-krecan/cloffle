package net.javacrumbs.cloffle;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractE2ETest {

    @Test
    public void shouldDoSimpleAddition() {
        runAndAssert("(+ 1 2)", 3L);
    }

    @Test
    public void shouldAddThreeNumbers() {
        runAndAssert("(+ 1 2 3.0)", 6d);
    }

    @Test
    public void shouldInterpretIf() {
        runAndAssert("(if true 2.0 3.0)", 2.0);
    }

    @Test
    public void shouldInterpretNil() {
        runAndAssert("(if nil 2.0 3.0)", 3.0);
    }

    @Test
    public void shouldInterpretLet() {
        runAndAssert("(let [a 3.0] (+ a 5))", 8.0);
    }

    @Test
    public void letShouldHaveContext() {
        runAndAssert("(let [a 3.0] (+ (let [a 2] a) a))", 5.0);
    }

    @Test
    public void shouldInterpretLetNested() {
        runAndAssert("(let [a 3.0] (let [a  4] (+ a 5))))", 9L);
    }

    @Test
    public void doShouldWork() {
        runAndAssert("(do (let [a 3.0] (+ a 5)) (let [a 1] (+ a 5))))", 6L);
    }

    @Test
    public void fnShouldAlsoWork() {
        runAndAssert("((fn [a b c] (+ a b c)) 2 4 6))", 12L);
    }

    @Test
    public void fnWithLetShouldWork() {
        runAndAssert("(let [a 5] ((fn [b] (+ a b)) 2)))", 7L);
    }

    @Test
    public void defFnShouldWork() {
        runAndAssert("(do (defn myadd [x y z] (+ x y z)) (myadd 1 2 3)))", 6L);
    }

    @Test
    public void defShouldWork() {
        runAndAssert("(do (def myval true) (if myval 1 2)))", 1L);
    }

    @Test
    public void fibShouldWork() {
        runAndAssert("(do (defn fib [n] (if (< n 3) 1 (+ (fib (- n 1)) (fib (- n 2))))) (fib 10))", 55L);
    }

    @Test
    public void fibShouldBeFast() {
        System.out.println(run("(let [start (System/currentTimeMillis)] (do (defn fib [n] (if (< n 3) 1 (+ (fib (- n 1)) (fib (- n 2))))) (fib 30) (- (System/currentTimeMillis) start)))"));
    }

    @Test
    public void loopShouldWork() {
        runAndAssert("(loop [sum 0 cnt 10]\n" +
            "    ; If count reaches 0 then exit the loop and return sum\n" +
            "    (if (= cnt 0)\n" +
            "    sum\n" +
            "    ; Otherwise add count to sum, decrease count and \n" +
            "    ; use recur to feed the new values back into the loop\n" +
            "    (recur (+ cnt sum) (dec cnt))))", 55L);
    }

    @Test
    public void staticFieldShouldWork() {
        runAndAssert("(Math/PI)", Math.PI);
    }

    @Test
    public void instanceCallShouldWork() {
        runAndAssert("(.toUpperCase \"fred\")", "FRED");
    }

    @Test
    public void doTimesShouldWork() {
        runAndAssert("(dotimes [n 5] (.println (System/out) n))", null);
    }

    protected abstract Object run(String expression);

    protected void runAndAssert(String expression, Object expected) {
        assertThat(run(expression)).isEqualTo(expected);
    }
}