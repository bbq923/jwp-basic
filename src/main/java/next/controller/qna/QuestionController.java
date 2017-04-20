package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;

public class QuestionController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if (UserSessionUtils.isLogined(session)) {
			return jspView("/qna/form.jsp").addObject("user", UserSessionUtils.getUserFromSession(session).getName());
		}
			
		log.debug("not logined... forward to /user/login.jsp");
		return jspView("/user/login.jsp");
	}

}
