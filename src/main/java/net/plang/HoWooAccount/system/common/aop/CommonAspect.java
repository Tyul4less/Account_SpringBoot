package net.plang.HoWooAccount.system.common.aop;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import net.plang.HoWooAccount.system.base.exception.IdNotFoundException;
import net.plang.HoWooAccount.system.base.exception.PwMissmatchException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;


import lombok.extern.slf4j.Slf4j;

@ControllerAdvice 
@Slf4j
@Component
@Aspect
@Configuration
public class CommonAspect {
	
    @org.springframework.web.bind.annotation.ExceptionHandler(IdNotFoundException.class)//익셉션 어노테이션. UserNotFoundException.class(우리가 새로 만든 익셉션) 도 선언할 수 있다.
     public ModelAndView idNotFoundExceptionHandler(HttpServletRequest request, IdNotFoundException e) {
        ModelAndView mv = new ModelAndView("/loginForm"); 
        mv.addObject("errorCode", -2);
        mv.addObject("errorMsg", e.getMessage());
         System.out.println("#######################IdNotFoundException#################1");
                 
         log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e); 
         
         return mv;
     }
    
     @org.springframework.web.bind.annotation.ExceptionHandler(PwMissmatchException.class)
     public ModelAndView pwMissMatchException(HttpServletRequest request, PwMissmatchException e) { //패스워드가일치하지않을때나는오류
        ModelAndView mv = new ModelAndView("/loginForm");//예외가 발생하면 여기 경로로 가라.
        mv.addObject("errorCode", -4);
        mv.addObject("errorMsg", e.getMessage());
         System.out.println("#######################PwMissMatchException#################1");
         log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
         return mv;
     }
   
     
     @org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
     public ModelAndView pwNotFoundExceptionHandler(HttpServletRequest request, DataAccessException e) {
        
        ModelAndView mv = new ModelAndView("/errorPage");
        
        mv.addObject("errorCode", -3);
        mv.addObject("errorMsg", e.getMessage());
         System.out.println("#####################PwNotFoundExceptionHandler###################1");
                 
         log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
         
         return mv;
     }
     /*ㅁㄴㅇㅁㄴㅇㅁㄴㅇㄴㅁㅇ*/
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request,Exception exception){ 
      
       ModelAndView mv = new ModelAndView("/errorPage"); 
       mv.addObject("exception", exception);
       System.out.println("#####################DefaultExceptionHandler###################");
       log.error("defaultExceptionHandler", exception);
    
    return mv;
    }
    
    @Around("execution(* net..controller.*.*(..)) || execution(* net..serviceFacade.*.*(..)) || execution(* net..applicationService.*.*(..)) || execution(* net..mapper.*.*(..))")
      /*
       @Around 어노테이션은 타겟 메서드를 감싸서 특정 Advice를 실행한다는 의미이다. 위 코드의 Advice는 타겟 메서드가 실행된 시간을 측정하기 위한 로직을 구현하였다. 
        추가적으로 execution(* com.saelobi..*.EventService.*(..))가 의미하는 바는 com.saelobi 아래의 패키지 경로의 EventService 객체의 모든 메서드에 이 Aspect를 적용하겠다는 의미다.
        또한
      @Around("@annotation(PerLogging)") 이렇게 경로지정 방식말고 특정 어노테이션이 붙은 포인트에 해당 Aspect를 실행할 수 있는 기능도 제공한다
      출력 결과에서 @PerLogging 어노테이션이 붙은 메서드만 Aspect가 적용된 것을 볼 수 있다. */
      
      
      public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
         String type = "";
         String name = joinPoint.getSignature().getDeclaringTypeName();//--> 해당 메소드의 클래스이름을 반환//  ++joinpoint는 메소드
         if (name.indexOf("Controller") > -1) {//'Controller' 문자열이 포함되어 있으면
            type = "Controller  \t:  ";
         }
         else if (name.indexOf("Facade") > -1) {//'Facade' 문자열이 포함되어 있으면
            type = "ServiceFacadeImpl  \t:  ";
         }
         else if (name.indexOf("Service") > -1) {
            type = "ServiceImpl  \t:  ";
         }
         else if (name.indexOf("DAO") > -1) {
            type = "DAO  \t\t:  ";
         }
         log.info(type + name + "." + joinPoint.getSignature().getName() + "()"); //-info : 상태변경등과 같은 정보성 메시지를 나타냅니다. 
         Object obj = joinPoint.proceed();
         
         return obj;
         /*
          여기서는 Object를 리턴하도록 되어있는데 아주 중요하다.
                     왜냐하면 AOP는 메서드를 가로채서(Proxy) 앞이나 뒤에 필요한 공통로직을 한다고 했기 때문이다. --> 실행되던 객체 그자체를 의미함, xxxController의 xxxMethod이면 그거에 대한 Logging을 하고 재차 동작을 이어나갸야함
         즉 proceed()에서 정상적으로 메서드를 실행한 후 리턴 값을 주는데 가로채서 어떤 action을 한 후에 기존 리턴 값을 되돌려 주지 않으면 가로챈 AOP가 결과값을 변경한 것, 지워버린 것과 다름없다.
         위 메서드에서는 단순하게 앞/뒤로 추가 로깅을 찍어주고 기존 메서드가 실행될 수 있게 pjp.proceed();를 호출했다.
          */
         
      }
    
    private static final String AOP_TRANSACTION_METHOD_NAME = "*"; //이름.
    private static final String AOP_TRANSACTION_EXPRESSION = "execution(* net..serviceFacade.*.*(..) ) ";   //포인트컷. 이렇게 설정 값을 상수로 선언함.
      
      @Autowired    //이 타입을 자동으로 찾아줘서 DI 시켜줌.
      private PlatformTransactionManager transactionManager;

      
      
   /*
   @SuppressWarnings 
   1. all : 모든 경고를 억제
   2. cast : 캐스트 연산자 관련 경고 억제
   3. dep-ann : 사용하지 말아야 할 주석 관련 경고 억제
   4. deprecation : 사용하지 말아야 할 메소드 관련 경고 억제
   5. fallthrough : switch문에서의 break 누락 관련 경고 억제
   6. finally : 반환하지 않는 finally 블럭 관련 경고 억제
   7. null : null 분석 관련 경고 억제
   8. rawtypes : 제네릭을 사용하는 클래스 매개 변수가 불특정일 때의 경고 억제
   9. unchecked : 검증되지 않은 연산자 관련 경고 억제
   10. unused : 사용하지 않는 코드 관련 경고 억제
    
       */

      @SuppressWarnings("deprecation")
      @Bean    
      public TransactionInterceptor transactionAdvice(){
         MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
         RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
         transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);  //메서드 명-->모든메서드에대해서 적용하겠다  
         transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(DataAccessException.class))); //트랜잭션에서 롤백을 하는 롤(rule)을 설정.
         source.setTransactionAttribute(transactionAttribute);
         return new TransactionInterceptor(transactionManager, source); //TransactionInterceptor가 아니라 Advice다?? --트랜잭션매니저도 주고 소스객체도 주었다 
      }
      
      @Bean
      public Advisor transactionAdviceAdvisor(){ //어드바이저 : 포인트컷과 연결시켜 줌.
         AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(); 
         pointcut.setExpression(AOP_TRANSACTION_EXPRESSION); 
         return new DefaultPointcutAdvisor(pointcut, transactionAdvice()); //Advisor객체가 리턴된다 
         //------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>트랜잭션관련된 처리부분이다 현업에서는 만들일이 없다 보면이해는할수있어야한다 
      }
    

}