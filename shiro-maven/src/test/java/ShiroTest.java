import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author danger
 * @date 2021/6/8
 */
public class ShiroTest {
    @Test
    public void authentication() {

        // 创建 realm
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("test", "123456");

        // 构建 SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        // 设置 SecurityManager
        SecurityUtils.setSecurityManager(securityManager);

        // 构建主体 subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");

        // 登录验证
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // 登出
        subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

    }

    @Test
    public void authorization() {
        // 创建 realm
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("test", "123456", "admin");

        // 构建 SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        // 设置 SecurityManager
        SecurityUtils.setSecurityManager(securityManager);

        // 构建主体 subject
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("test", "123456");

        // 登录验证
        subject.login(token);

        boolean admin = subject.hasRole("admin");
        boolean user = subject.hasRole("user");

        System.out.println("isAdmin: " + admin);
        System.out.println("isUser: " + user);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        // 登出
        subject.logout();
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
