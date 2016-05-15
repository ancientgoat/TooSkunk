package com.premierinc.rule.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.StringWriter;
import javax.annotation.PostConstruct;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;

/**
 *
 */
public class JsonMapperHelper {
	private JsonMapperHelper() {
	}

	public static final ObjectMapper newInstanceJson() {
		ObjectMapper mapper = new CustomMapper();
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

		// mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
		// mapper.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);

		//mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
		//mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);

		return mapper;
	}

	public static final ObjectMapper newInstanceYaml() {
		ObjectMapper mapper = new CustomMapper(new YAMLFactory());
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

		// mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
		// mapper.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);

		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);

		return mapper;
	}

	///**
	// * Use Jackson's XML Mapper.
	// */
	//public static ObjectMapper xmlMapper() {
	//	final JacksonXmlModule module = new JacksonXmlModule();
	//	module.setDefaultUseWrapper(false);
	//	final XmlMapper xmlMapper = new XmlMapper(module);
	//	return commonProperties(xmlMapper);
	//}

	/**
	 * Use Jackson's JSON Mapper.
	 */
	public static final ObjectMapper jsonMapper() {
		ObjectMapper mapper = new CustomMapper();
		//mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
		return mapper;
	}

	/**
	 * Take any bean and return as JSON String.
	 */
	public static String beanToJsonPretty(final Object inObject) {
		return prettyPrint(inObject, jsonMapper());
	}

	public static String beanToJsonPretty(final Object inObject, String inRootName) {
		return prettyPrint(inObject, jsonMapper(), inRootName);
	}

	public static String beanToJson(final Object inObject) {
		return prettyPrint(inObject, jsonMapper());
	}

	/**
	 * Pretty Print (output) the input Object, using the input Jackson ObjectMapper.
	 */
	public static String prettyPrint(Object inObject, ObjectMapper inObjectMapper) {
		return prettyPrint(inObject, inObjectMapper, null);
	}

	/**
	 * Pretty Print (output) the input Object, using the input Jackson ObjectMapper.
	 */
	public static String prettyPrint(Object inObject, ObjectMapper inObjectMapper, String inRootName) {
		try {
			//return inObjectMapper.writerWithDefaultPrettyPrinter()
			//		.writeValueAsString(inObject);

			StringWriter w = new StringWriter();
			ObjectWriter writer = inObjectMapper.writerWithDefaultPrettyPrinter();
			if (null != inRootName) {
				writer = writer.withRootName(inRootName);
			}
			writer.writeValue(w, inObject);
			return w.toString();

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Normal Print (output) the input Object, using the input Jackson ObjectMapper.
	 */
	public static String beanToJsonString(Object inObject) {
		return beanToString(inObject, jsonMapper());
	}

	/**
	 * Normal Print (output) the input Object, using the input Jackson ObjectMapper.
	 */
	public static String beanToJsonString(Object inObject, String inRootName) {
		return beanToString(inObject, inRootName, jsonMapper());
	}

	/**
	 * Normal Print (output) the input Object, using the input Jackson ObjectMapper.
	 */
	public static String beanToString(Object inObject, ObjectMapper inObjectMapper) {
		return beanToString(inObject, null, inObjectMapper);
	}

	public static String beanToString(Object inObject, String inRootName, ObjectMapper inObjectMapper) {
		try {
			StringWriter w = new StringWriter();
			ObjectWriter writer = inObjectMapper.writer();
			if (null != inRootName) {
				writer = writer.withRootName(inRootName);
			}
			writer.writeValue(w, inObject);
			return w.toString();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * This method ensures that the output String has only
	 * valid XML unicode characters as specified by the
	 * XML 1.0 standard. For reference, please see
	 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
	 * standard</a>. This method will return an empty
	 * String if the input is null or empty.
	 *
	 * @param in The String whose non-valid characters we want to remove.
	 * @return The in String, stripped of non-valid characters.
	 */
	public static String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || ("".equals(in)))
			return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if ((current == 0x9) ||
					(current == 0xA) ||
					(current == 0xD) ||
					((current >= 0x20) && (current <= 0xD7FF)) ||
					((current >= 0xE000) && (current <= 0xFFFD)) ||
					((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	/**
	 * Add some properties to any Jackson ObjectMapper.
	 */
	private static ObjectMapper commonProperties(final ObjectMapper inMapper) {
		inMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		inMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		inMapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
		inMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return inMapper;
	}

	private static class CustomMapper extends ObjectMapper {

		public CustomMapper() {
			super();
		}

		public CustomMapper(final JsonFactory inFactory) {
			super(inFactory);
		}

		@PostConstruct
		public void customConfiguration() {
			// Uses Enum.toString() for serialization of an Enum
			this.enable(WRITE_ENUMS_USING_TO_STRING);
			// Uses Enum.toString() for deserialization of an Enum
			this.enable(READ_ENUMS_USING_TO_STRING);

			// this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			// this.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
			// this.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);
			// this.configure(SerializationFeature.INDENT_OUTPUT, true);
		}
	}
}
