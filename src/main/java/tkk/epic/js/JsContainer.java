package tkk.epic.js;

import org.apache.logging.log4j.Level;
import tkk.epic.TkkEpic;

import javax.script.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;

public class JsContainer {
    public String js = "";
    public String print;
    public StringWriter sw = new StringWriter();
    public PrintWriter pw = new PrintWriter(sw);
    public ScriptEngineManager mgr = new ScriptEngineManager(null);
    public ScriptEngine engine;
    public boolean errored = false;
    public HashSet<String> unknownFunctions;
    public Consumer<JsContainer> errorPrint = null;

    public JsContainer(String code, Consumer<JsContainer> errorPrint) {
        this.errorPrint = errorPrint;
        initJs(code);
    }

    public JsContainer(String code) {
        initJs(code);
    }

    public void initJs(String js) {
        this.errored = false;
        print = "";
        try {
            Class<?> c = Class.forName("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory");
            ScriptEngineFactory factory = (ScriptEngineFactory) c.getDeclaredConstructor().newInstance();
            mgr.registerEngineName("nashorn", factory);
            engine = factory.getScriptEngine();
        } catch (Exception e) {
            TkkEpic.LOGGER.log(Level.ERROR, "tkk ERROR initJs " + e);
        }

        if (engine == null) {
            TkkEpic.LOGGER.log(Level.ERROR, "Nashorn Script Engine not found");
            this.errored = true;
            return;
        }

        engine.getContext().setWriter(pw);
        engine.getContext().setErrorWriter(pw);
        try {
            engine.eval(js);
            this.js = js;
        } catch (Exception e) {
            this.errored = true;
            appendConsole(e.getMessage());
            if (errorPrint != null) {
                errorPrint.accept(this);
            }
        }
        appendConsole(sw.getBuffer().toString().trim());
        sw.getBuffer().delete(0, sw.getBuffer().length());
        unknownFunctions = new HashSet<>();
    }

    public void appendConsole(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        if (this.print == null || this.print.equals("")) {
            this.print = message;
            return;
        }
        this.print = message + "\n" + this.print;
    }

    public void setVar(String name, Object value) {
        engine.put(name, value);
    }

    public Object getVar(String name) {
        return engine.get(name);
    }

    public Object run(String fn, Object... args) {
        if (errored || unknownFunctions.contains(fn)) {
            return null;
        }
        Object obj = null;
        try {
            obj = ((Invocable) engine).invokeFunction(fn, args);
        } catch (NoSuchMethodException e) {
            unknownFunctions.add(fn);
        } catch (ScriptException e) {
            this.errored = true;
            appendConsole(e.getMessage());
            TkkEpic.LOGGER.log(Level.ERROR, "ScriptException:" + getError(e));
            if (errorPrint != null) {
                errorPrint.accept(this);
            }
        } catch (Exception e) {
            this.errored = true;
            appendConsole(e.getMessage());
            TkkEpic.LOGGER.log(Level.ERROR, "Exception:" + getError(e));
            if (errorPrint != null) {
                errorPrint.accept(this);
            }
        }
        appendConsole(sw.getBuffer().toString().trim());
        sw.getBuffer().delete(0, sw.getBuffer().length());
        return obj;
    }

    public Function<Object[], Object> getFunction(String functionName) {
        return (args) -> this.run(functionName, args);
    }

    public static String getError(Exception e) {
        StringWriter temp = new StringWriter();
        e.printStackTrace(new PrintWriter(temp, true));
        return temp.toString();
    }
}
