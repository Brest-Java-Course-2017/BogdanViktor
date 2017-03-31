package com.epam.result.client.rest_test;

import com.epam.result.client.rest_api.DirectorConsumer;
import com.epam.result.dao.DirectorDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by sw0rd on 31.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:client-rest-test.xml"})
public class DirectorConsumerImplTest {

    @Autowired
    DirectorConsumer directorConsumer;

    @Ignore
    @Test
    public void testGetAllDirectorDTO() {
        List<DirectorDTO> list =directorConsumer.getAllDirectorDTO();
        System.out.println(list);
    }
}
