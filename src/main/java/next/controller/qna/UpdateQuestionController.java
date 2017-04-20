package next.controller.qna;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdateQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Question question = new Question(new Long(request.getParameter("questionId")), request.getParameter("writer"),
				request.getParameter("title"), request.getParameter("contents"), new Date(), new Integer(request.getParameter("countOfComment")));
		
		questionDao.update(question);
		return jspView("redirect:/");
	}
}
