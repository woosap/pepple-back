package woosap.Pepple.util.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woosap.Pepple.dto.SessionSaveInfo;
import woosap.Pepple.util.Constants;

public class SaveInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasSaveInfo = SessionSaveInfo.class.isAssignableFrom(parameter.getParameterType());
        boolean hasSavedInfoAnnotation = parameter.hasParameterAnnotation(SavedInfo.class);

        return hasSaveInfo && hasSavedInfoAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession();

        return session.getAttribute(Constants.SESSION_KEY);
    }
}
