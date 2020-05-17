# 02 配置文件详解

## 基本信息
* Spring Boot 使用“习惯优于配置”（项目中存在大量的配置，此外还内置了一个习惯性的配置，让你无需手动进行配置）的理念让你的项目快速运行起来。
* Spring Boot使用了一个全局的配置文件application.properties，放在src/main/resources目录下。
    > 注:如果你工程没有这个application.properties，那就在src/main/java/resources目录下新建一个。

## 自定义属性
* application.properties提供自定义属性的支持，这样我们就可以把一些常量配置在这里：
    ```Shell
    com.wangjstu.name=wangjstu
    com.wangjstu.age=30
    com.wangjstu.hello=${com.wangjstu.name} say hello
    ```
* application.properties中的各个参数之间也可以直接引用来使用，就像：`${com.wangjstu.name} say hello`，会将其中`${com.wangjstu.name}`的值进行转化。
* 在要使用的地方，使用注解 `@Value("${com.wangjstu.name}")` 将值绑定到对应到属性上。
* 建一个Controller，测试使用以下：
    ```SHELL
      package com.wangjstu.springboothelloproject.controllers;
      
      import org.springframework.beans.factory.annotation.Value;
      import org.springframework.web.bind.annotation.RequestMapping;
      import org.springframework.web.bind.annotation.RestController;
      
      @RestController
      public class HelloPropertiesController {
      
          @Value("${com.wangjstu.name}")
          private String myName;
      
          @Value("${com.wangjstu.age}")
          private int myAge;
      
          @RequestMapping(value = "/getMyProperties")
          public String getMyProperties() {
              return this.myName + ":" + this.myAge;
          }
      }
    ```
* 打开浏览器或curl请求：`http://127.0.0.1:8080/getMyProperties`，可以看到输出。
* 属性多的时候，使用上述方法，会很累，官方建议使用`Bean`进行绑定，在`com.wangjstu.springboothelloproject`下新建`package`：`com.wangjstu.springboothelloproject.beans`:
    ```SHELL
    package com.wangjstu.springboothelloproject.beans;
    
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.stereotype.Component;
    
    @Component
    @ConfigurationProperties(prefix = "com.wangjstu")
    public class ConfigBean {
    
        private String name;
        private int age;
        private String hello;
    
        //省略get和set方法······
    }

    ```
  * 使用注解`@ConfigurationProperties(prefix = “com.wangjstu”)`来指明使用哪个属性
* 使用以上bean，修改刚刚的`Controller`，进行调用：
  ```SHELL
  package com.wangjstu.springboothelloproject.controllers;
  
  import com.wangjstu.springboothelloproject.beans.ConfigBean;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.boot.context.properties.EnableConfigurationProperties;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @EnableConfigurationProperties({ConfigBean.class})
  @RestController
  public class HelloPropertiesController {
  
      @Value("${com.wangjstu.name}")
      private String myName;
  
      @Value("${com.wangjstu.age}")
      private int myAge;
  
      @Autowired
      private ConfigBean configBean;
  
      @RequestMapping(value = "/getMyProperties")
      public String getMyProperties() {
          return this.myName + ":" + this.myAge;
      }
  
      @RequestMapping(value = "/getMyPropertiesByBean")
      public String getMyPropertiesByBean() {
          return this.configBean.getName() + ":" + this.configBean.getAge() + "-->" + this.configBean.getHello();
      }
  }

  ```
  * 其中在`controller`入口类加上`@EnableConfigurationProperties`并指明要加载哪个bean，使用`@Autowired`注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。
* 打开浏览器或curl请求：`http://127.0.0.1:8080/getMyPropertiesByBean`，可以看到输出。

## 使用自定义配置文件
* 在目录`/src/main/resources`下建`config`目录，新建配置文件`test.properties`,编辑内入如下：
    ```SHELL
    com.wangjstutest.scret=${random.value}
    com.wangjstutest.number=${random.int}
    com.wangjstutest.bignumber=${random.long}
    com.wangjstutest.uuid=${random.uuid}
    com.wangjstutest.lessthanten=${random.int(10)}
    com.wangjstutest.numberinrange=${random.int[1024,65536]}
    ```
* 在`com.wangjstu.springboothelloproject.beans`下创建`ConfigTestBean.java`:
    ```SHELL
    package com.wangjstu.springboothelloproject.beans;
    
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.PropertySource;
    
    @Configuration
    @ConfigurationProperties(prefix = "com.wangjstutest")
    @PropertySource("classpath:config/test.properties")
    public class ConfigTestBean {
    
        private String scret;
        private int number;
        private long bignumber;
        private String uuid;
        private int lessthanten;
        private int numberinrange;
    
        //省略get和set方法······
    
    }

    ```
    * 增加来`@Configuration`和`@PropertySource("classpath:config/test.properties")`来读取配置
* 在`controller`中修改如下：
    ```SHELL
    package com.wangjstu.springboothelloproject.controllers;
    
    import com.wangjstu.springboothelloproject.beans.ConfigBean;
    import com.wangjstu.springboothelloproject.beans.ConfigTestBean;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.context.properties.EnableConfigurationProperties;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    //修改来这里
    @EnableConfigurationProperties({ConfigBean.class, ConfigTestBean.class})
    @RestController
    public class HelloPropertiesController {
    
        @Value("${com.wangjstu.name}")
        private String myName;
    
        @Value("${com.wangjstu.age}")
        private int myAge;
    
        @Autowired
        private ConfigBean configBean;
    
        //新增属性
        @Autowired
        private ConfigTestBean configTestBean;
    
        @RequestMapping(value = "/getMyProperties")
        public String getMyProperties() {
            return this.myName + ":" + this.myAge;
        }
    
        @RequestMapping(value = "/getMyPropertiesByBean")
        public String getMyPropertiesByBean() {
            return this.configBean.getName() + ":" + this.configBean.getAge() + "-->" + this.configBean.getHello();
        }
        
        //新增以下方法
        @RequestMapping(value = {"/", ""})
        public String getMyTestPropertiesByBean() {
            return "less than ten: " + this.configTestBean.getLessthanten()
                    + "<br/>scret: " + this.configTestBean.getScret()
                    + "<br/>uuid: " + this.configTestBean.getUuid()
                    + "<br/>long: " + this.configTestBean.getBignumber()
                    + "<br/>numberinrange: " + this.configTestBean.getNumberinrange()
                    + "<br/>number: " + this.configTestBean.getNumber();
        }
    }
    ```
  
## 配置文件其他整理
* 实际上，Spring Boot应用程序有多种设置途径，Spring Boot能从多重属性源获得属性，包括如下几种：
    * 根目录下的开发工具全局设置属性(当开发工具激活时为~/.spring-boot-devtools.properties)。
    * 测试中的@TestPropertySource注解。
    * 测试中的@SpringBootTest#properties注解特性。
    * 命令行参数，如`java -jar xx.jar --server.port=9090` 指定服务启动端口
    * SPRING_APPLICATION_JSON中的属性(环境变量或系统属性中的内联JSON嵌入)。
    * ServletConfig初始化参数。
    * ServletContext初始化参数。
    * java:comp/env里的JNDI属性
    * JVM系统属性
    * 操作系统环境变量
    * 随机生成的带random.* 前缀的属性（在设置其他属性时，可以应用他们，比如${random.long}）
    * 应用程序以外的application.properties或者appliaction.yml文件
    * 打包在应用程序内的application.properties或者appliaction.yml文件
    * 通过@PropertySource标注的属性源
    * 默认属性(通过SpringApplication.setDefaultProperties指定).
    * 这里列表按组优先级排序，也就是说，任何在高优先级属性源里设置的属性都会覆盖低优先级的相同属性，列如我们上面提到的命令行属性就覆盖了application.properties的属性。

* application.properties和application.yml文件可以放在以下四个位置：
    * 外置，在相对于应用程序运行目录的/congfig子目录里。
    * 外置，在应用程序运行的目录里
    * 内置，在config包内
    * 内置，在Classpath根目录
    * 同样，这个列表按照优先级排序，也就是说，src/main/resources/config下application.properties覆盖src/main/resources下application.properties中相同的属性
    * 如果你在相同优先级位置同时有application.properties和application.yml，那么application.properties里的属性里面的属性就会覆盖application.yml。

## profile 多环境配置
* 在Spring Boot中多环境配置文件名需要满足application-{profile}.properties的格式，其中{profile}对应你的环境标识，比如：
    * application-dev.properties：开发环境
    * application-prod.properties：生产环境
    想要使用对应的环境，只需要在application.properties中使用spring.profiles.active属性来设置，值对应上面提到的{profile}，这里就是指dev、prod这2个。
    当然你也可以用命令行启动的时候带上参数：`java -jar xxx.jar --spring.profiles.active=dev`
* 除了可以用profile的配置文件来分区配置我们的环境变量，在代码里，我们还可以直接用@Profile注解来进行配置:
    ```SHELL
    //定义接口
    public  interface DBConnector { public  void  configure(); }
    //实现
    /**
      * 测试数据库
    */
    @Component
    @Profile("testdb")
    public class TestDBConnector implements DBConnector {
        @Override
        public void configure() {
            System.out.println("testdb");
        }
    }
    /**
     * 生产数据库
     */
    @Component
    @Profile("devdb")
    public class DevDBConnector implements DBConnector {
        @Override
        public void configure() {
            System.out.println("devdb");
        }
    }
    ```
   * 在配置文件中设置`spring.profiles.active=testdb`，进行使用
    ```SHELL
    @RestController
    @RequestMapping("/task")
    public class TaskController {
    
        @Autowired DBConnector connector ;
    
        @RequestMapping(value = {"/",""})
        public String hellTask(){
    
            connector.configure(); //最终打印testdb     
            return "hello task !! myage is " + myage;
        }
    }
    ```
* 除了spring.profiles.active来激活一个或者多个profile之外，还可以用spring.profiles.include来叠加profile
    ```SHELL
    spring.profiles.active: testdb  
    spring.profiles.include: proddb,prodmq
    ```

## 参考
* [Spring Boot干货系列：（二）配置文件解析](http://tengj.top/2017/02/28/springboot2/)
* [Spring Boot教程第2篇：配置文件详解](https://www.fangzhipeng.com/springboot/2017/05/02/sb2-config-file.html)

