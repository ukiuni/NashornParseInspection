package org.ukiuni.inspect.nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import jdk.nashorn.internal.objects.ScriptFunctionImpl;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

public class Main {
	public static void main(String[] args) throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		engine.put("parser", new Main());
		engine.eval("function MyJSClass() {"
						+ "this.param = \"testParam\";"
						+ "this.func=function(){};this.p2=1"
					+ "}");
		engine.eval("parser.parse(new MyJSClass())");
	}

	public void parse(ScriptObject scriptObject) {
		//クラス名の取得
		Object constructor = scriptObject.getProto().get("constructor");
		ScriptFunction scriptFunction = (ScriptFunction) constructor;
		System.out.println("className = " + scriptFunction.getName());

		//メンバの取得
		PropertyMap properties = scriptObject.getMap();
		for (Property property : properties.getProperties()) {
			String key = property.getKey();
			Object obj = scriptObject.get(property.getKey());
			System.out.println(" property = " + key + ", arg = " + obj + " (" + obj.getClass() + ")");
		}
	}
}
