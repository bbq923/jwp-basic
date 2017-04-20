package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Result;
import next.model.User;

public class QuestionModifyController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(QuestionModifyController.class);
	
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav;
		User user = UserSessionUtils.getUserFromSession(request.getSession());
		String questionId = request.getParameter("questionId");
		
		log.debug("user: {}", user);
		log.debug("writer : {}", request.getParameter("writer"));
		log.debug("questionId : {}", new Long(questionId));
		
		if (user != null && user.matchName(request.getParameter("writer"))) {
			mav = jsonView().addObject("result", Result.ok()).addObject("url", "/qna/modify/form?questionId=" + questionId);
			log.debug("succeed");
		} else {
			mav = jsonView().addObject("result", Result.fail("질문을 작성한 사용자만 수정할 수 있습니다."));
		}
		
		return mav;
	}

}
