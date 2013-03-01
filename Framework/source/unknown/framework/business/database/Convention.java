package unknown.framework.business.database;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 约定
 */
public class Convention {
	/**
	 * 数据库标识符分隔符
	 */
	protected final static char SEPARATOR = '_';
	/**
	 * 类取值函数前缀
	 */
	protected final static String GET_METHOD_PREFIX = "fget";
	/**
	 * 类赋值函数前缀
	 */
	protected final static String SET_METHOD_PREFIX = "fset";

	/**
	 * 类取值函数缓存
	 */
	protected static Map<String, List<Method>> getMethodCache = new HashMap<String, List<Method>>();
	/**
	 * 类赋值函数缓存
	 */
	protected static Map<String, List<Method>> setMethodCache = new HashMap<String, List<Method>>();

	/**
	 * UUID
	 * 
	 * @return UUID
	 */
	public String Uuid() {
		String result = null;

		String uuid = UUID.randomUUID().toString().toUpperCase();
		StringBuilder stringBuilder = new StringBuilder();
		if (uuid != null) {
			if (!uuid.isEmpty()) {
				for (int i = 0; i < uuid.length(); i++) {
					char currentChar = uuid.charAt(i);
					if (((currentChar >= 'A') && (currentChar <= 'Z'))
							|| ((currentChar >= '0') && (currentChar <= '9'))) {
						stringBuilder.append(currentChar);
					}
				}
			}
		}
		result = stringBuilder.toString();

		return result;
	}

	/**
	 * 数据库标识符编码为编程语言标识符
	 * 
	 * TEMPLATE_TABLE编码为TemplateTable
	 * 
	 * @param value
	 *            数据库标识符
	 * @param capitalize
	 *            首字母大写
	 * @return 编程语言标识符
	 */
	public String encodeName(String value, boolean capitalize) {
		String result = null;

		if (value != null) {
			if (!value.isEmpty()) {
				StringBuilder stringBuilder = new StringBuilder();
				boolean upper = capitalize;
				for (int i = 0; i < value.length(); i++) {
					char current = value.charAt(i);
					if ((i > 0) && (current == Convention.SEPARATOR)) {
						upper = true;
						continue;
					} else {
						if (upper) {
							current = Character.toUpperCase(current);
						} else {
							current = Character.toLowerCase(current);
						}
						stringBuilder.append(current);
						upper = false;
					}
				}
				result = stringBuilder.toString();
			}
		}

		return result;
	}

	/**
	 * 编程语言标识符解码为数据库标识符
	 * 
	 * @param value
	 *            编程语言标识符
	 * @return 数据库标识符
	 */
	public String decodeName(String value) {
		String result = null;

		if (value != null) {
			if (!value.isEmpty()) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < value.length(); i++) {
					char current = value.charAt(i);
					if ((i > 0) && (Character.isUpperCase(current))) {
						stringBuilder.append(Convention.SEPARATOR);
					}
					stringBuilder.append(current);
				}
				result = stringBuilder.toString();
			}
		}

		return result;
	}

	/**
	 * 编码方法名称
	 * 
	 * @param value
	 *            POJO字段
	 * @param methodPrefix
	 *            方法前缀
	 * @return 方法名称
	 */
	protected String encodeMethodName(String value, String methodPrefix) {
		String result = null;

		if (value != null) {
			if (!value.isEmpty()) {
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append(methodPrefix);
				if (value.length() > 0) {
					stringBuilder.append(value.substring(0, 1).toUpperCase());
				}
				if (value.length() > 1) {
					stringBuilder.append(value.substring(1));
				}
				result = stringBuilder.toString();
			}
		}

		return result;
	}

	/**
	 * 解码方法名称
	 * 
	 * @param value
	 *            方法名称
	 * @param methodPrefix
	 *            方法前缀
	 * @return POJO字段
	 */
	protected String decodeMethodName(String value, String methodPrefix) {
		String result = null;

		if (value != null) {
			if (!value.isEmpty()) {
				StringBuilder stringBuilder = new StringBuilder();

				int prefixLength = methodPrefix.length();
				if (value.length() > prefixLength) {
					value = value.substring(prefixLength);
				}
				if (value.length() > 0) {
					stringBuilder.append(value.substring(0, 1).toLowerCase());
				}
				if (value.length() > 1) {
					stringBuilder.append(value.substring(1));
				}
				result = stringBuilder.toString();
			}
		}

		return result;
	}

	/**
	 * 编码获取方法名称
	 * 
	 * @param value
	 *            POJO字段
	 * @return 方法名称
	 */
	public String encodeGetMethodName(String value) {
		return this.encodeMethodName(value, Convention.GET_METHOD_PREFIX);
	}

	/**
	 * 解码获取方法名称
	 * 
	 * @param value
	 *            方法名称
	 * @return POJO字段
	 */
	public String decodeGetMethodName(String value) {
		return this.decodeMethodName(value, Convention.GET_METHOD_PREFIX);

	}

	/**
	 * 编码赋值方法名称
	 * 
	 * @param value
	 *            POJO字段
	 * @return 方法名称
	 */
	public String encodeSetMethodName(String value) {
		return this.encodeMethodName(value, Convention.SET_METHOD_PREFIX);

	}

	/**
	 * 解码赋值方法名称
	 * 
	 * @param value
	 *            方法名称
	 * @return POJO字段
	 */
	public String decodeSetMethodName(String value) {
		return this.decodeMethodName(value, Convention.SET_METHOD_PREFIX);
	}

	/**
	 * 筛选方法
	 * 
	 * @param value
	 *            实体对象
	 * @param methodCache
	 *            方法缓存
	 * @param methodPrefix
	 *            方法前缀
	 * @return 方法集合
	 */
	protected List<Method> filterMethod(Object value,
			Map<String, List<Method>> methodCache, String methodPrefix) {
		List<Method> results = new ArrayList<Method>();

		if (value != null) {
			String pojoType = value.getClass().getName();
			if (methodCache.containsKey(pojoType)) {
				results = methodCache.get(pojoType);
			} else {
				Method[] methods = value.getClass().getMethods();
				if (methods != null) {
					for (int i = 0; i < methods.length; i++) {
						Method method = methods[i];
						String methodName = method.getName();

						if (methodName.indexOf(methodPrefix) == 0) {
							results.add(method);
						}
					}
				}
				methodCache.put(pojoType, results);
			}
		}

		return results;
	}

	/**
	 * 筛选取值方法
	 * 
	 * @param value
	 *            实体对象
	 * @return 方法集合
	 */
	public List<Method> filterGetMethod(Object value) {
		return this.filterMethod(value, Convention.getMethodCache,
				Convention.GET_METHOD_PREFIX);
	}

	/**
	 * 筛选赋值方法
	 * 
	 * @param value
	 *            实体对象
	 * @return 方法集合
	 */
	public List<Method> filterSetMethod(Object value) {
		return this.filterMethod(value, Convention.setMethodCache,
				Convention.SET_METHOD_PREFIX);
	}
}
