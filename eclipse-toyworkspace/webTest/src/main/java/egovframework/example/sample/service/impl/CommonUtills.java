package egovframework.example.sample.service.impl;





import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import egovframework.example.board.service.AttFileVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtills {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtills.class);

	/**
	 * 한글로 변환
	 *
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toKor(String str) throws UnsupportedEncodingException {
		String returnStr = null;

		returnStr = new String(str.getBytes("8859_1"), "KSC5601");

		return returnStr;
	}

	public static String toEucKr(String str) throws UnsupportedEncodingException {
		String returnStr = null;

		// returnStr = new String(str.getBytes("8859_1"), "KSC5601");
		returnStr = new String(str.getBytes("8859_1"), "EUC-KR");

		return returnStr;
	}

	public static String toUtf8(String str) throws UnsupportedEncodingException {
		String returnStr = null;

		returnStr = new String(str.getBytes("8859_1"), "UTF-8");

		return returnStr;
	}

	/**
	 * 스트링을 Integer 로 형변환
	 *
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(String param, int defaultValue) {

		if (param == null || param.trim().equals(""))
			return defaultValue;

		try {
			return Integer.parseInt(param);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int toInt(String param) {
		int defaultValue = 0;

		if (param == null || param.trim().equals(""))
			return defaultValue;

		try {
			return Integer.parseInt(param);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 스트링배열을 비교하는 값과 일치하면 display 를 출력, 아니면 defaultValue 를 출력한다. radio 버튼 또는
	 * checkbox 에 이용하면 좋다.
	 *
	 * @param data
	 * @param compare
	 * @param display
	 * @param defaultValue
	 * @return
	 */
	public static String checkValue(String[] data, String compare, String display, String defaultValue) {
		if (data == null || data.length == 0) {
			return defaultValue;
		}

		for (String value : data) {
			if (value.equals(compare)) {
				return display;
			}
		}
		return defaultValue;

	}

	/**
	 * 스트링을 비교하는 값과 일치하면 display 를 출력, 아니면 defaultValue 를 출력한다. radio 버튼 또는 checkbox
	 * 에 이용하면 좋다.
	 *
	 * @param data
	 * @param compare
	 * @param display
	 * @param defaultValue
	 * @return
	 */
	public static String checkValue(String data, String compare, String display, String defaultValue) {
		if (data == null || data.equals("null") || data.trim().length() == 0) {
			return defaultValue;
		}

		if (data.equals(compare)) {
			return display;
		}
		return defaultValue;
	}

	/**
	 * 리스트를 스트링과 비교해서 값이 일치하면 display 를 출력, 아니면 defaultValue 를 출력 radio 버튼 또는
	 * checkbox 에 이용하면 좋다.
	 *
	 * @param list
	 * @param compare
	 * @param display
	 * @param defaultValue
	 * @return
	 */
	public static String checkValue(List list, String compare, String display, String defaultValue) {

		if (list == null) {
			return defaultValue;
		}

		String str = list.toString();

		if (str.indexOf(compare) >= 0) {
			return display;
		}

		return defaultValue;

	}

	/**
	 * 스트링을 변환하는 것
	 *
	 * @param target
	 * @return
	 */
	public static String translate(String target) {
		if (target == null)
			return "";
		if (target.indexOf("&") > -1)
			target = target.replaceAll("&", "&amp;");
		if (target.indexOf("<") > -1)
			target = target.replaceAll("<", "&lt;");
		if (target.indexOf(">") > -1)
			target = target.replaceAll(">", "&gt;");
		if (target.indexOf("'") > -1)
			target = target.replaceAll("'", "&#39;");
		if (target.indexOf("\"") > -1)
			target = target.replaceAll("\"", "&quot;");
		if (target.indexOf("(") > -1)
			target = target.replaceAll("\\x28", "&#40;");
		if (target.indexOf(")") > -1)
			target = target.replaceAll("\\x29", "&#41;");

		// target = target.replaceAll("\\n", "<br/>");

		return target;
	}

	public static String translateTO(String target) {
		if (target == null)
			return "";
		if (target.indexOf("&amp;") > -1)
			target = target.replaceAll("&amp;", "&");
		if (target.indexOf("&lt;") > -1)
			target = target.replaceAll("&lt;", "<");
		if (target.indexOf("&gt;") > -1)
			target = target.replaceAll("&gt;", ">");
		if (target.indexOf("&#39;") > -1)
			target = target.replaceAll("&#39;", "'");
		if (target.indexOf("&#039;") > -1)
			target = target.replaceAll("&#039;", "'");
		if (target.indexOf("&apos;") > -1)
			target = target.replaceAll("&quot;", "\'");
		if (target.indexOf("&quot;") > -1)
			target = target.replaceAll("&quot;", "\"");
		if (target.indexOf("&nbsp;") > -1)
			target = target.replaceAll("&nbsp;", " ");
		if (target.indexOf("&middot;") > -1)
			target = target.replaceAll("&middot;", "·");
		if (target.indexOf("&ldquo;") > -1)
			target = target.replaceAll("&ldquo;", "“");
		if (target.indexOf("&rdquo;") > -1)
			target = target.replaceAll("&rdquo;", "”");
		if (target.indexOf("&bull;") > -1)
			target = target.replaceAll("&bull;", "?");
		if (target.indexOf("&lsquo;") > -1)
			target = target.replaceAll("&lsquo;", "‘");
		if (target.indexOf("&rsquo;") > -1)
			target = target.replaceAll("&rsquo;", "’");
		if (target.indexOf("&rarr;") > -1)
			target = target.replaceAll("&rarr;", "→");
		return target;
	}

	public static String stripTags(String target) {
		target = target.replaceAll("&amp;", "&");
		target = target.replaceAll("&lt;", "<");
		target = target.replaceAll("&gt;", ">");
		target = target.replaceAll("&apos;", "\'");
		target = target.replaceAll("&quot;", "\"");

		Pattern p = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
		Matcher m = p.matcher(target);
		target = m.replaceAll("");

		Matcher mat;
		String str = target;

		// script 처리
		Pattern script = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
		mat = script.matcher(str);
		str = mat.replaceAll("");

		// style 처리
		Pattern style = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
		mat = style.matcher(str);
		str = mat.replaceAll("");

		// tag 처리
//		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
//		mat = tag.matcher(str);
//		str = mat.replaceAll("");

		// ntag 처리
		Pattern ntag = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		mat = ntag.matcher(str);
		str = mat.replaceAll("");

		// entity ref 처리
		Pattern Eentity = Pattern.compile("&[^;]+;");
		mat = Eentity.matcher(str);
		str = mat.replaceAll("");

		// whitespace 처리
		Pattern wspace = Pattern.compile("\\s\\s+");
		mat = wspace.matcher(str);
		str = mat.replaceAll("");

		target = str;
		target = target.replaceAll("&nbsp;", " ");
		target = target.replaceAll("&amp;", "&");

		return target;
	}

	public static String stripTagsBr(String target) {

		target = target.replaceAll("&nbsp;", " ");
		target = target.replaceAll("&amp;", "&");
		target = target.replaceAll("&#39;", "'");
		target = target.replaceAll("&quot;", "\"");

		Pattern p = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
		Matcher m = p.matcher(target);
		target = m.replaceAll("");

		Matcher mat;

		// script 처리
		Pattern script = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
		mat = script.matcher(target);
		target = mat.replaceAll("");

		target = translate(target);

		target = target.replaceAll("\\n", "<br/>");
		return target;
	}

	public static String stripTagsAndLength(String target, int len) {
		target = stripTags(target);

		if (target.length() > len) {
			try {
				target = target.substring(0, len) + "...";
			} catch (Exception e) {
				len = (int) (len * 1.4);
				target = textSubstring(target, len);
			}
		}

		return target;
	}

	/**
	 * 스트링을 자르때 쓴다. 게시판만의 제목 등에 사용한다. 길이가 큰 경우엔 ... 을 붙인다. 작은 경우엔 바로 리턴
	 *
	 * @param str    스트링
	 * @param cutLen 자르는 길이
	 * @return
	 */
	public static String textSubstring(String str, int cutLen) {

		if (str == null)
			return "";
		else if (str.length() <= cutLen)
			return str;

		byte[] ab = str.getBytes();
		int i, slen, cnt;

		slen = ab.length;

		if (slen <= cutLen)
			return str;

		cnt = 0;

		for (i = 0; i < cutLen; i++) {
			if ((((int) ab[i]) & 0xff) > 0x80)
				cnt++;
		}
		if ((cnt % 2) == 1)
			cutLen--;

		String returnStr = new String(ab, 0, cutLen) + "...";

		return returnStr.trim();
	}

	public static int textLen(String str) {
		if (str == null)
			return 0;

		byte[] ab = str.getBytes();
		int i, slen;

		slen = ab.length;
		return slen;
	}

	public static String numberFormat(int val) {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(val);
	}

	// get Client IP
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		// {
		// ip = request.getHeader("WL-Proxy-Client-IP"); // 웹 로직
		// }
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// String[] ipArr = ip.split(",");
		// if( ipArr.length > 0 ){
		// ip = ipArr[0];
		// }

		return ip;
	}

	public static String getRequestURL(HttpServletRequest request) {
		String requestURL = request.getScheme() + "://" + request.getServerName();
		int port = (Integer) request.getServerPort();

		if (port == 80)
			requestURL = request.getScheme() + "://" + request.getServerName()
					+ request.getAttribute("javax.servlet.forward.request_uri");
		else
			requestURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getAttribute("javax.servlet.forward.request_uri");

		String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");

		if (queryString != null && !"".equals(queryString)) {
			requestURL = requestURL + "?" + queryString;
		}

		return requestURL;
	}

	public static String getServerURL(HttpServletRequest request) {
		String requestURL = request.getScheme() + "://" + request.getServerName();
		int port = (Integer) request.getServerPort();

		if (port == 80)
			requestURL = request.getScheme() + "://" + request.getServerName();
		else
			requestURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		return requestURL;
	}

	public static String getRequestURI(HttpServletRequest request) {
		String requestURI = (String) request.getAttribute("javax.servlet.forward.request_uri");
		if (requestURI == null || "".equals(requestURI))
			requestURI = request.getRequestURI();
		return requestURI;
	}

	public static String encodeURI(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("%21", "!").replace("%27", "'")
				.replace("%28", "(").replace("%29", ")").replace("%26", "&").replace("%3D", "=").replace("%7E", "~");
	}

	public static final String replaceNull(Object obj) {
		String str = null;
		if (obj == null) {
			str = "";
			return str;
		} else {
			return obj.toString();
		}
	}

	/**
	 * D-day 반환
	 */
	public static final String dDay(int year, int monthOfYear, int dayOfMonth) {
		int ONE_DAY = 24 * 60 * 60 * 1000;
		// D-day 설정
		Calendar ddayCalendar = Calendar.getInstance();
		ddayCalendar.set(year, monthOfYear, dayOfMonth);

		// D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
		long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
		long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
		long result = dday - today;

		// 출력 시 d-day 에 맞게 표시
		String strFormat;
		if (result > 0) {
			strFormat = "D-%d";
		} else if (result == 0) {
			strFormat = "D-Day";
		} else {
			result *= -1;
			strFormat = "D+%d";
		}

		String strCount = (String.format(strFormat, result));
		return strCount;
	}

	/**
	 * D-day 반환
	 */
	public static final String dDay(String startDate) {
		int year = 0;
		int monthOfYear = 0;
		int dayOfMonth = 0;
		String strCount = "";
		try {
			if (startDate != null && startDate.indexOf("-") > -1) {
				String[] dateArr = startDate.split("-");
				year = Integer.parseInt(dateArr[0]);
				monthOfYear = Integer.parseInt(dateArr[1]) - 1;
				dayOfMonth = Integer.parseInt(dateArr[2]);
			} else if (startDate != null && startDate.indexOf(".") > -1) {
				String[] dateArr = startDate.split("\\.");
				year = Integer.parseInt(dateArr[0]);
				monthOfYear = Integer.parseInt(dateArr[1]) - 1;
				dayOfMonth = Integer.parseInt(dateArr[2]);
			} else if (startDate != null && startDate.length() == 8) {

				year = Integer.parseInt(startDate.substring(0, 4));
				monthOfYear = Integer.parseInt(startDate.substring(4, 6)) - 1;
				dayOfMonth = Integer.parseInt(startDate.substring(6, 8));

			}
		} catch (Exception ignore) {
			LOGGER.error("[" + ignore.getClass() + "] Connection Close : " + ignore.getMessage());
			year = 0;
			monthOfYear = 0;
			dayOfMonth = 0;
		}

		if (year >= 2000) {
			int ONE_DAY = 24 * 60 * 60 * 1000;
			// D-day 설정
			Calendar ddayCalendar = Calendar.getInstance();
			ddayCalendar.set(year, monthOfYear, dayOfMonth);

			// D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
			long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
			long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
			long result = dday - today;

			// 출력 시 d-day 에 맞게 표시
			String strFormat;
			if (result > 0) {
				strFormat = "D-%d";
			} else if (result == 0) {
				strFormat = "D-Day";
			} else {
				result *= -1;
				strFormat = "D+%d";
			}

			strCount = (String.format(strFormat, result));
		}
		return strCount;
	}

	public static String getDayOfWeek(String startDate) {
		String[] weekArr = { "", "일", "월", "화", "수", "목", "금", "토" };
		if (startDate != null && startDate.length() == 10) {

			int year = 0;
			int month = 0;
			int date = 0;

			Calendar cal = new GregorianCalendar();

			// 날짜 비교
			String[] dayArr = startDate.split("-");
			year = Integer.parseInt(dayArr[0]);
			month = Integer.parseInt(dayArr[1]) - 1;
			date = Integer.parseInt(dayArr[2]);
			cal.set(year, month, date);

			return weekArr[cal.get(Calendar.DAY_OF_WEEK)];

		} else if (startDate != null && startDate.length() == 8) {

			int year = 0;
			int month = 0;
			int date = 0;

			Calendar cal = new GregorianCalendar();

			// 날짜 비교
			year = Integer.parseInt(startDate.substring(0, 4));
			month = Integer.parseInt(startDate.substring(4, 6)) - 1;
			date = Integer.parseInt(startDate.substring(6, 8));

			cal.set(year, month, date);
			return weekArr[cal.get(Calendar.DAY_OF_WEEK)];

		}
		return "";
	}

	/**
	 * XSS 방지 처리.
	 *
	 * @param data
	 * @return
	 */
	public static String unscriptClear(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}

		// html 인코딩 값을 원래값으로 바꾼다.
		return getSafeParam(data);

	}

	public static String validateXSS(String sUserInput) {
		String xssPattern = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
		if (sUserInput == null)
			return null;
		sUserInput = sUserInput.trim();
		sUserInput = sUserInput.replaceAll(xssPattern, "");
		sUserInput = sUserInput.replaceAll("\\\"", "");
		sUserInput = sUserInput.replaceAll("\\\'", "");

		sUserInput = sUserInput.replaceAll("<", "&lt;");
		sUserInput = sUserInput.replaceAll(">", "&gt;");
		sUserInput = sUserInput.replaceAll("Set-Cookie", "");
		return sUserInput;
	}

	public static String scriptReplace(String htmlStr) {
		if (htmlStr == null)
			return "";
		Pattern p = Pattern.compile("(?i)<script");
		Matcher m = p.matcher(htmlStr);
		return m.replaceAll("<x-script");
	}

	public static String iframeReplace(String htmlStr) {
		if (htmlStr == null)
			return "";
		Pattern p = Pattern.compile("(?i)<iframe");
		Matcher m = p.matcher(htmlStr);
		return m.replaceAll("<x-iframe");
	}

	public static String embedReplace(String htmlStr) {
		if (htmlStr == null)
			return "";
		Pattern p = Pattern.compile("(?i)<embed");
		Matcher m = p.matcher(htmlStr);
		return m.replaceAll("<x-embed");
	}

	public static String inputReplace(String htmlStr) {
		if (htmlStr == null)
			return "";
		Pattern p = Pattern.compile("(?i)<input");
		Matcher m = p.matcher(htmlStr);
		return m.replaceAll("<x-input");
	}

	public static String metaReplace(String htmlStr) {
		if (htmlStr == null)
			return "";
		Pattern p = Pattern.compile("(?i)<meta");
		Matcher m = p.matcher(htmlStr);
		return m.replaceAll("<x-meta");
	}

	// 자바 금액 한글로 변환하기
	public static String convertMoneyHangul(String money) {
		String[] han1 = { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
		String[] han2 = { "", "십", "백", "천" };
		String[] han3 = { "", "만", "억", "조", "경" };

		StringBuffer result = new StringBuffer();
		int len = money.length();
		for (int i = len - 1; i >= 0; i--) {
			result.append(han1[Integer.parseInt(money.substring(len - i - 1, len - i))]);
			if (Integer.parseInt(money.substring(len - i - 1, len - i)) > 0)
				result.append(han2[i % 4]);
			if (i % 4 == 0)
				result.append(han3[i / 4]);
		}

		return result.toString();
	}

	public static boolean localCheck() {

		// 로컬서버는 제외
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();

			if (ip.contains("192.168.") || ip.contains("127.0.0.")) {
				return true;
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		return false;
	}

	
	/**
	 * Ineger null 값을 인 경우 defaultValue 를 사용한다.
	 *
	 * @param inValue
	 * @param defaultValue
	 * @return
	 */
	public static int checkNull(String inValue, int defaultValue) {
		int value = 0;
		if (inValue == null || inValue.equals("null") || "".equals(inValue) || " ".equals(inValue)) {
			return defaultValue;
		} else {

			try {
				value = Integer.parseInt(inValue.trim());
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}

	/**
	 * 스트링값을 int 로 처리 가능 여부 확인
	 *
	 * @param inValue
	 * @return
	 */
	public static int checkNumber(String inValue) {
		int value = 0;
		if (inValue == null || inValue.equals("null") || "".equals(inValue) || " ".equals(inValue)) {
			return 0;
		} else {

			try {
				value = Integer.parseInt(inValue.trim());
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}

	/**
	 * javascript및 event 관련 명령어 에 xss-를 추가해서 변경해 준다.
	 *
	 * @param value
	 * @return
	 */
	public static String getSafeParam(String value) {

		if (value != null) {

			String value_low = value.toLowerCase();

			if (value_low != null
					&& value_low.contains("&#x6A;&#x61;&#x76;&#x61;&#x73;&#xA;&#x63;&#x72;&#x69;&#x70;")) {
				value_low = value_low.replaceAll("&#x6A;&#x61;&#x76;&#x61;&#x73;&#xA;&#x63;&#x72;&#x69;&#x70;",
						"xss-javascript");
			}

			if (value_low != null
					&& value_low.contains("&#x6a;&#x61;&#x76;&#x61;&#x73;&#xa;&#x63;&#x72;&#x69;&#x70;")) {
				value_low = value_low.replaceAll("&#x6a;&#x61;&#x76;&#x61;&#x73;&#xa;&#x63;&#x72;&#x69;&#x70;",
						"xss-javascript");
			}

			if (value_low != null
					&& value_low.contains("&#x6a;&#x61;&#x76;&#x61;&#x73;&#x0a;&#x63;&#x72;&#x69;&#x70;")) {
				value_low = value_low.replaceAll("&#x6a;&#x61;&#x76;&#x61;&#x73;&#x0a;&#x63;&#x72;&#x69;&#x70;",
						"xss-javascript");
			}

			if (value_low.contains("javascript") || value_low.contains("script") || value_low.contains("iframe")
					|| value_low.contains("document") || value_low.contains("vbscript") || value_low.contains("applet")
					|| value_low.contains("embed") || value_low.contains("object") || value_low.contains("frame")
					|| value_low.contains("grameset") || value_low.contains("layer") || value_low.contains("bgsound")
					|| value_low.contains("alert") || value_low.contains("onblur") || value_low.contains("onchange")
					|| value_low.contains("onclick") || value_low.contains("ondblclick")
					|| value_low.contains("onerror") || value_low.contains("onfocus") || value_low.contains("onload")
					|| value_low.contains("onmouse") || value_low.contains("onscroll") || value_low.contains("onsubmit")
					|| value_low.contains("onunload")) {

				value = value.replaceAll("(?i)javascript", "xss-javascript");
				// value = value.replaceAll("(?i)script", "xss-script");
				// value = value.replaceAll("(?i)iframe", "xss-iframe");
				// value = value.replaceAll("(?i)document", "xss-document");

				value = value.replaceAll("(?i)script>", "xss-script>");
				value = value.replaceAll("(?i)iframe ", "xss-iframe ");
				value = value.replaceAll("(?i)document.", "xss-document.");

				value = value.replaceAll("(?i)vbscript", "xss-vbscript");
				value = value.replaceAll("(?i)applet", "xss-applet");
				value = value.replaceAll("(?i)embed", "xss-embed");
				value = value.replaceAll("(?i)object", "xss-object");
				// value = value.replaceAll("(?i)frame", "xss-frame");
				value = value.replaceAll("(?i)grameset", "xss-grameset");
				// value = value.replaceAll("(?i)layer", "xss-layer");
				value = value.replaceAll("(?i)bgsound", "xss-bgsound");
				value = value.replaceAll("(?i)alert", "xss-alert");
				value = value.replaceAll("(?i)onblur", "xss-onblur");
				value = value.replaceAll("(?i)onchange", "xss-onchange");
				value = value.replaceAll("(?i)onclick", "xss-onclick");
				value = value.replaceAll("(?i)ondblclick", "xss-ondblclick");
				value = value.replaceAll("(?i)onerror", "xss-onerror");
				value = value.replaceAll("(?i)onfocus", "xss-onfocus");
				value = value.replaceAll("(?i)onload", "xss-onload");
				value = value.replaceAll("(?i)onmouse", "xss-onmouse");
				value = value.replaceAll("(?i)onscroll", "xss-onscroll");
				value = value.replaceAll("(?i)onsubmit", "xss-onsubmit");
				value = value.replaceAll("(?i)onunload", "xss-onunload");

			}
		}
		return value;
	}

	/**
	 * xss- 삭제해서 html 사용 가능 처리
	 *
	 * @param srcString
	 * @return
	 */
	public static String getHtmlXssRemove(String srcString) {

		String tmpString = srcString;

		tmpString = tmpString.replaceAll("xss-", "");
		tmpString = tmpString.replaceAll("xss-", "");
		tmpString = tmpString.replaceAll("xss-", "");
		tmpString = tmpString.replaceAll("xss-", "");

		return tmpString;
	}

	/**
	 * html 사용 가능
	 *
	 * @param srcString
	 * @return String
	 * @exception Exception
	 * @see
	 */
	public static String getHtmlUseCnvr(String srcString) {

		String tmpString = srcString;

		// xss 제거
		tmpString = getSafeParam(tmpString);

		// HTML Entity 변환 by ISU 2022-11-02
		tmpString = translateTO(tmpString);

//		tmpString = tmpString.replaceAll("&amp;", "&");
//		tmpString = tmpString.replaceAll("&lt;", "<");
//		tmpString = tmpString.replaceAll("&gt;", ">");
//
//		tmpString = tmpString.replaceAll("&apos;", "\'");
//		tmpString = tmpString.replaceAll("&quot;", "\"");
//		tmpString = tmpString.replaceAll("&nbsp;", " ");

		return tmpString;
	}

	/**
	 * html의 특수문자를 표현하기 위해
	 *
	 * @param srcString
	 * @return String
	 * @exception Exception
	 * @see
	 */
	public static String getHtmlStrCnvr(String srcString) {

		String tmpString = srcString;

		tmpString = tmpString.replaceAll("&amp;", "&");

		tmpString = tmpString.replaceAll("&lt;", "<");
		tmpString = tmpString.replaceAll("&gt;", ">");

		tmpString = tmpString.replaceAll("&apos;", "\'");
		tmpString = tmpString.replaceAll("&quot;", "\"");

		tmpString = tmpString.replaceAll("&nbsp;", " ");
		return tmpString;
	}

	



	

	/**
	 * 생년월일 Format 변경
	 * @param birth
	 * @return
	 */
	public static String getFormatBirth(String birth) {
		String returnVal = null;
		if (StringUtils.isNotBlank(birth) && birth.length() == 8) {
			returnVal = birth.replaceFirst("(\\d{4})(\\d{2})(\\d+)", "$1-$2-$3");
		}

		return returnVal;
	}

	/**
	 * 이메일주소 Split
	 *
	 * @param email
	 * @return
	 */
	public static String[] getSplitEmail(String email) {
		String[] returnVal = new String[2];
		if (StringUtils.isBlank(email)) {
			return returnVal;
		}

		String[] split = email.split("@");
		int idx = 0;
		for (String str : split) {
			returnVal[idx] = str;
			idx++;
		}
		return returnVal;
	}

	/**
	 * 성별 세팅하기
	 *
	 * @return
	 */
	public static String getSexdstn(String sexdstn) throws Exception {
		String returnVal = null;

		if (StringUtils.isNotBlank(sexdstn)) {
			if (sexdstn.equals("1") || sexdstn.equals("3") || sexdstn.equals("5") || sexdstn.equals("7") || sexdstn.equals("M") || sexdstn.equals("남")) {
				returnVal = "M";
			} else if (sexdstn.equals("0") || sexdstn.equals("2") || sexdstn.equals("4") || sexdstn.equals("6") || sexdstn.equals("8") || sexdstn.equals("F") || sexdstn.equals("여")) {
				returnVal = "F";
			}
		}

		return returnVal;
	}

	public static String base64Encoder(String str) {
		if (StringUtils.isBlank(str)) return str;

		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	public static String base64Decoder(String str) {
		if (StringUtils.isBlank(str)) return str;

		byte[] decodedBytes = Base64.getDecoder().decode(str);
		return new String(decodedBytes);
	}
	
	
	//파일 다운로드
	public static void fileDownload (AttFileVO fileVO, HttpServletRequest request, HttpServletResponse response) throws Exception {

	  // globals.properties
	  File file = new File("c:\\upload", fileVO.getAttFileModname());
	  BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

	  //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
	  String header = request.getHeader("User-Agent");
	  String fileName;

	  if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
	    //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩 
	    fileName = URLEncoder.encode(fileVO.getAttFileOriname(), "UTF-8");
	  } else {
	    //나머지 브라우저에서 인코딩
	    fileName = new String(fileVO.getAttFileOriname().getBytes("UTF-8"), "iso-8859-1");
	  }
	  //형식을 모르는 파일첨부용 contentType
	  response.setContentType("application/octet-stream");
	  //다운로드와 다운로드될 파일이름
	  response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
	  //파일복사
	  FileCopyUtils.copy(in, response.getOutputStream());
	  in.close();
	  response.getOutputStream().flush();
	  response.getOutputStream().close();
	}
	
	
	
}