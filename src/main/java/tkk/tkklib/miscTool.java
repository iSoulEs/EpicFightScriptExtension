package tkk.tkklib;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class miscTool {
    public static String getError(Exception e){
        StringWriter temp = new StringWriter();
        e.printStackTrace(new PrintWriter(temp,true));
        return temp.toString();
    }

    public static ScriptEngine getJsInv(String js)throws ScriptException{
        ScriptEngineManager mgr = new ScriptEngineManager(null);
        ScriptEngine engine;
        Invocable inv;

        engine = mgr.getEngineByName("js");
        engine.eval(js);
        return engine;
    }
}
