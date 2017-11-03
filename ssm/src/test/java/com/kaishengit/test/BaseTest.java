package com.kaishengit.test;

import com.sun.glass.ui.Application;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xiaogao on 2017/11/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)//(locations = "classpath:applicationContext.xml")
public class BaseTest {
}
