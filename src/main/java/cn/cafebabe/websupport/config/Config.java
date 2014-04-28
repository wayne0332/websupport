package cn.cafebabe.websupport.config;

/**
 * TODO
 *
 * @author linjiangsheng
 * @created 14-3-4
 */
public class Config {
	public class SpringConfig {
		public class BeanScope{
			public final static String SINGLETON = "singleton",
					PROTOTYPE = "prototype", REQUEST = "request ", SESSION = "session";
		}
	}
}
