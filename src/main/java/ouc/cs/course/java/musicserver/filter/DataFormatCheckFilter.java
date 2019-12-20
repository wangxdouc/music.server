package ouc.cs.course.java.musicserver.filter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;

/**
 * Servlet Filter implementation class DataFormatCheckFilter
 */
@WebFilter(filterName = "CharsetFilter", urlPatterns = { "/sheetUpload", "/upload" }, initParams = {
		@WebInitParam(name = "charset", value = "utf-8") })
public class DataFormatCheckFilter implements Filter {

	public DataFormatCheckFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("[Data Format Check Filter] ");

		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("UTF-8");

		// 返回参数
		Map<String, Object> params = new HashMap<>();

		// 获取内容格式
		String contentType = req.getContentType();

		if (contentType != null && !contentType.equals("")) {
			contentType = contentType.split(";")[0];
		}

		System.out.println("[Upload ContentType] " + contentType);
		
		if ("appliction/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
			Map<String, String[]> parameterMap = req.getParameterMap();
			if (parameterMap != null) {
				for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
					params.put(entry.getKey(), entry.getValue()[0]);
				}
			}
			System.out.println(params);
		}

		if ("multipart/form-data".equalsIgnoreCase(contentType)) {
			
		}

		if ("application/json".equalsIgnoreCase(contentType)) {
			String paramJson = IOUtils.toString(req.getInputStream(), "UTF-8");
			Map<String, Object> parseObject = JSON.parseObject(paramJson, Map.class);
			params.putAll(parseObject);
			System.out.println(params);
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
