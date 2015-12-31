package net.javacrumbs.cloffle;

public class ClojureE2ETest extends  AbstractE2ETest {

    @Override
    protected Object run(String expression) {
        return mikera.cljutils.Clojure.eval(expression);
    }
}