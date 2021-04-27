package com.example.maven;

import com.example.maven.DemoTestApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ZhengHao Lou
 * @date 2020/05/17
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DemoTestApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
public class BaseTest {

}
