package com.wisdom.bussiness.service.impl;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by 赵得良 on 21/09/2016.
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BusinessServiceApplication.class)
//@AutoConfigureMockMvc
//@WebAppConfiguration
public class ArchivesTest {

//    private MockMvc mvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private WithDrawService withDrawService;
//
//    @Before
//    public void setupMockMvc() throws Exception {
//        mvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    public void testAccount(){
//        accountService.testCreateUserAccount();
//    }

    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        float min = 1.5f;
        float max = 3.0f;
        float floatBounded = Float.parseFloat(decimalFormat.format(min + new Random().nextFloat() * (max - min)));
        System.out.println("args = [" + floatBounded + "]");

    }
}
