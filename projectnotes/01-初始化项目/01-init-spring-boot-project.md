# 01-初始化项目

## 构建项目

* 需要环境
    * jdk 1.8或以上
    * maven 3.0+
    * IDEA
* 创建
    * 打开IDEA
    * new Project
    * Spring Initializr
    * 填写group、artifact
    * 钩上web(开启web功能）
    * 点下一步就行了。
* 工程目录
    * 目录结构图
    ```SHELL
        - .idea
        - .mvm
        - src
    	    - main
    		    - java
        			- com.wangjstu.springboothelloproject
        				- SpringBootHelloProjectApplication.java
        		- resouces
        			- statics
        			- templates
        			- application.properties
	    - test
    	    - java
        	    - com.wangjstu.springboothelloproject
            	    - SpringBootHelloProjectApplicationTests.java
	    - mvmw
	    - mvmw.cmd
        - pom.xml
        - spring-boot-hello-project.iml
    ```
    
    * 目录说明
        * pom.xml  //为基本的依赖管理文件
        * resouces //资源文件
        * statics  //静态资源
        * templates //模板资源
        * application.yml //配置文件
        * SpringBootHelloProjectApplication.java  //程序的入口
