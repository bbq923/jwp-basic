#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* Tomcat은 WebServerLauncher에서 설정한 webapp 디렉터리를 basedir로 앱을 시작한다. Catalina Servlet Container 에서 WEB-INF/web.xml 을 참조하려고 시도하나 찾지 못한다.
* ServletContextListener를 구현하고 있는 ContextLoaderListener가 실행 된다.
* DB를 초기화하고 jwp.sql 스크립트를 실행해 테스트용 데이터를 저장한다.
* HttpServlet을 상속받는 DispatcherServlet에서 init 메서드가 호출된다. init 메서드는 RequestMapping 인스턴스를 생성하고 RequestMapping의 initMapping 메서드를 호출한다.
* initMapping 메서드에서는 사용자의 요청을 받을 URL과 해당 URL에 대한 작업을 처리할 Controller의 인스턴스를 각각 생성해 Map\<String, Controller\> 안에 저장해 둔다. 

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* DispatcherServlet에 "/" 경로로 들어온다. 이후 추가로 들어오는 /js/scripts.js 에 대한 요청은 ResourceFilter에 의해 걸러진다.
* RequestMapping 인스턴스에서 "/" 경로에 대해 매핑되어있는 HomeController의 인스턴스를 가져온다. 
* HomeController의 execute 메서드를 호출한다. execute 메서드는 home.jsp를 그리는 데 필요한 Model과 View 정보를 가지고 있는 ModelAndView 객체를 리턴한다.
* 리턴 받은 ModelAndView 객체에서 View를 꺼낸 다음, 마찬가지로 꺼낸 Model 객체를 View의 render 메서드에 인자로 넘겨주어 렌더링한다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 사용자의 요청이 들어오면 RequestMapping의 Map\<String, Controller\> 안에 저장되어 있던 ShowController의 인스턴스를 꺼내 사용한다. 하나의 인스턴스를 사용하기 때문에 
이전 사용자의 요청에 의해 할당된 Question과 List\<Answer\>의 경우 나중에 들어온 사용자의 요청에 의해 이전 사용자를 위해 저장해 두었던 값이 덮어 써질 수 있다. 
