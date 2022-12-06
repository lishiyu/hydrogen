package cn.net.hylink.hydrogen.core.page;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@AllArgsConstructor
public class PageRequestFilter extends OncePerRequestFilter {

    private final Environment env;

    private static class PageRequestWrapper extends HttpServletRequestWrapper {

        private final Map<String, String[]> params;

        public PageRequestWrapper(HttpServletRequest request, Map<String, String[]> allParams) {
            super(request);
            this.params = allParams;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Vector<>(params.keySet()).elements();
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean needChange = false;
        Map<String, String[]> allParams = new HashMap<>(request.getParameterMap());
        String currentKey = env.getProperty("common.page.current", PagePropertyNamingStrategy.PAGE_CURRENT_ORI);
        if (allParams.containsKey(currentKey)) {
            allParams.put(PagePropertyNamingStrategy.PAGE_CURRENT_ORI, allParams.get(currentKey));
            needChange = true;
        }
        String sizeKey = env.getProperty("common.page.size", PagePropertyNamingStrategy.PAGE_SIZE_ORI);
        if (allParams.containsKey(sizeKey)) {
            allParams.put(PagePropertyNamingStrategy.PAGE_SIZE_ORI, allParams.get(sizeKey));
            needChange = true;
        }
        if (needChange) {
            PageRequestWrapper req = new PageRequestWrapper(request, allParams);
            doFilter(req, response, filterChain);
        } else {
            doFilter(request, response, filterChain);
        }
    }

}
