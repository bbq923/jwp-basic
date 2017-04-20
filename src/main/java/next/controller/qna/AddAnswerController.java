package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private AnswerDao answerDao = new AnswerDao();
    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));
        question.increaseCountOfComment();
        Question updatedQuestion = questionDao.update(question);
        
        
        log.debug("count of comment : {}", answerDao.findAllByQuestionId(Long.parseLong(req.getParameter("questionId"))).size());
        log.debug("question comment count updated to ... {}", updatedQuestion.getCountOfComment());
        return jsonView().addObject("answer", savedAnswer).addObject("countOfComment", answerDao.findAllByQuestionId(Long.parseLong(req.getParameter("questionId"))).size());
    }
}
